<script lang="ts" setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { createSupplierAPI, getSupplierAPI, updateSupplierAPI } from '@/apis/supplier'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import type { PmsSupplier } from '@/types/supplier'

const route = useRoute()
const router = useRouter()

const props = defineProps({
  isEdit: {
    type: Boolean,
    default: false,
  },
})

const supplierId = computed(() => Number(route.query.id))

const defaultSupplier: PmsSupplier = {
  name: '',
  code: '',
  contactName: '',
  contactPhone: '',
  email: '',
  address: '',
  remark: '',
  sort: 0,
  status: 1,
}

const supplier = ref<PmsSupplier>({ ...defaultSupplier })
const supplierFormRef = ref<FormInstance>()

const rules = reactive<FormRules<PmsSupplier>>({
  name: [
    { required: true, message: '请输入供应商名称', trigger: 'blur' },
    { min: 2, max: 128, message: '长度在 2 到 128 个字符', trigger: 'blur' },
  ],
  sort: [{ type: 'number', message: '排序必须为数字' }],
})

onMounted(async () => {
  if (props.isEdit) {
    const res = await getSupplierAPI(supplierId.value)
    supplier.value = res.data
  } else {
    supplier.value = { ...defaultSupplier }
  }
})

const handleSubmit = () => {
  supplierFormRef.value!.validate(async (valid) => {
    if (!valid) {
      ElMessage.error('验证失败')
      return
    }
    await ElMessageBox.confirm('是否提交数据', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
    if (props.isEdit) {
      await updateSupplierAPI(supplierId.value, supplier.value)
      ElMessage.success('修改成功')
      router.back()
    } else {
      await createSupplierAPI(supplier.value)
      supplierFormRef.value!.resetFields()
      supplier.value = { ...defaultSupplier }
      ElMessage.success('提交成功')
    }
  })
}

const handleReset = () => {
  supplierFormRef.value!.resetFields()
  supplier.value = { ...defaultSupplier }
}
</script>

<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="supplier" :rules="rules" ref="supplierFormRef" label-width="120px">
      <el-form-item label="供应商名称：" prop="name">
        <el-input v-model="supplier.name" />
      </el-form-item>
      <el-form-item label="供应商编码：">
        <el-input v-model="supplier.code" placeholder="唯一编码，如 SUP005" />
      </el-form-item>
      <el-form-item label="联系人：">
        <el-input v-model="supplier.contactName" />
      </el-form-item>
      <el-form-item label="联系电话：">
        <el-input v-model="supplier.contactPhone" />
      </el-form-item>
      <el-form-item label="联系邮箱：">
        <el-input v-model="supplier.email" />
      </el-form-item>
      <el-form-item label="地址：">
        <el-input v-model="supplier.address" />
      </el-form-item>
      <el-form-item label="备注：">
        <el-input type="textarea" v-model="supplier.remark" :autosize="{ minRows: 2 }" />
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model.number="supplier.sort" />
      </el-form-item>
      <el-form-item label="状态：">
        <el-radio-group v-model="supplier.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button v-if="!props.isEdit" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
