<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type Action } from 'element-plus'
import {
  getSupplierListAPI,
  supplierDeleteByIdAPI,
  supplierUpdateStatusAPI,
} from '@/apis/supplier'
import { Search, Tickets } from '@element-plus/icons-vue'
import type { PmsSupplier, SupplierListQuery } from '@/types/supplier'

const router = useRouter()

const listQuery = ref<SupplierListQuery>({
  keyword: '',
  status: undefined,
  pageNum: 1,
  pageSize: 10,
})

const list = ref<PmsSupplier[]>([])
const multipleSelection = ref<PmsSupplier[]>([])
const listLoading = ref(true)
const total = ref(0)

const getList = async () => {
  listLoading.value = true
  try {
    const res = await getSupplierListAPI(listQuery.value)
    list.value = res.data.list
    total.value = res.data.total
  } finally {
    listLoading.value = false
  }
}

onMounted(() => {
  getList()
})

const handleSearch = () => {
  listQuery.value.pageNum = 1
  getList()
}

const handleAdd = () => {
  router.push({ path: '/pms/addSupplier' })
}

const handleUpdate = (_index: number, row: PmsSupplier) => {
  router.push({ path: '/pms/updateSupplier', query: { id: String(row.id) } })
}

const handleDelete = async (_index: number, row: PmsSupplier) => {
  ElMessageBox.confirm('是否要删除该供应商', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    callback: async (action: Action) => {
      if (action === 'confirm') {
        await supplierDeleteByIdAPI(row.id!)
        ElMessage.success('删除成功')
        getList()
      }
    },
  })
}

const handleSelectionChange = (val: PmsSupplier[]) => {
  multipleSelection.value = val
}

const handleStatusChange = async (_index: number, row: PmsSupplier) => {
  const prev = row.status === 1 ? 0 : 1
  try {
    await supplierUpdateStatusAPI({
      ids: String(row.id),
      status: row.status,
    })
    ElMessage.success('修改成功')
  } catch {
    row.status = prev
  }
}

const handleSizeChange = (val: number) => {
  listQuery.value.pageNum = 1
  listQuery.value.pageSize = val
  getList()
}

const handleCurrentChange = (val: number) => {
  listQuery.value.pageNum = val
  getList()
}

const operates = [
  { label: '批量启用', value: 'enable' },
  { label: '批量禁用', value: 'disable' },
]
const operateType = ref<string | null>()

const handleBatchOperate = async () => {
  if (!multipleSelection.value.length) {
    ElMessage.warning('请选择一条记录')
    return
  }
  let status = 1
  if (operateType.value === 'enable') {
    status = 1
  } else if (operateType.value === 'disable') {
    status = 0
  } else {
    ElMessage.warning('请选择批量操作类型')
    return
  }
  const ids = multipleSelection.value.map((item) => item.id).join(',')
  await supplierUpdateStatusAPI({ ids, status })
  ElMessage.success('修改成功')
  getList()
}
</script>

<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <el-icon class="el-icon-middle"><Search /></el-icon>
        <span>筛选搜索</span>
        <el-button style="float: right" type="primary" @click="handleSearch">查询结果</el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" label-width="100px">
          <el-form-item label="关键字：">
            <el-input v-model="listQuery.keyword" placeholder="名称/编码" style="width: 203px" />
          </el-form-item>
          <el-form-item label="状态：">
            <el-select v-model="listQuery.status" placeholder="全部" clearable style="width: 120px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-card class="operate-container" shadow="never">
      <el-icon class="el-icon-middle"><Tickets /></el-icon>
      <span>数据列表</span>
      <el-button class="btn-add" @click="handleAdd">添加</el-button>
    </el-card>

    <div class="table-container">
      <el-table
        :data="list"
        v-loading="listLoading"
        border
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60" align="center" />
        <el-table-column label="编号" width="80" align="center">
          <template #default="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column label="供应商名称" align="center" min-width="140">
          <template #default="scope">{{ scope.row.name }}</template>
        </el-table-column>
        <el-table-column label="编码" width="100" align="center">
          <template #default="scope">{{ scope.row.code }}</template>
        </el-table-column>
        <el-table-column label="联系人" width="100" align="center">
          <template #default="scope">{{ scope.row.contactName }}</template>
        </el-table-column>
        <el-table-column label="联系电话" width="120" align="center">
          <template #default="scope">{{ scope.row.contactPhone }}</template>
        </el-table-column>
        <el-table-column label="排序" width="80" align="center">
          <template #default="scope">{{ scope.row.sort }}</template>
        </el-table-column>
        <el-table-column label="状态" width="100" align="center">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.$index, scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button size="small" @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="batch-operate-container">
      <el-select v-model="operateType" placeholder="批量操作">
        <el-option v-for="item in operates" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button style="margin-left: 20px" type="primary" @click="handleBatchOperate">确定</el-button>
    </div>

    <div class="pagination-container">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :page-size="listQuery.pageSize"
        :page-sizes="[5, 10, 15]"
        v-model:current-page="listQuery.pageNum"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>
