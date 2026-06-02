/** 供应商信息 */
export type PmsSupplier = {
  id?: number
  name: string
  code?: string
  contactName?: string
  contactPhone?: string
  email?: string
  address?: string
  remark?: string
  sort: number
  status: number
  createTime?: string
  updateTime?: string
}

/** 供应商列表查询参数 */
export type SupplierListQuery = {
  pageNum: number
  pageSize: number
  keyword?: string
  status?: number
}
