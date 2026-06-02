import type { PmsSupplier, SupplierListQuery } from '@/types/supplier'
import type { CommonPage } from '@/types/common'
import http from '@/utils/http'

export function getSupplierListAPI(params: SupplierListQuery) {
  return http<CommonPage<PmsSupplier>>({
    url: '/supplier/list',
    method: 'get',
    params,
  })
}

export function getSupplierListAllAPI() {
  return http<PmsSupplier[]>({
    url: '/supplier/listAll',
    method: 'get',
  })
}

export function createSupplierAPI(data: PmsSupplier) {
  return http({
    url: '/supplier/create',
    method: 'post',
    data,
  })
}

export function updateSupplierAPI(id: number, data: PmsSupplier) {
  return http({
    url: '/supplier/update/' + id,
    method: 'post',
    data,
  })
}

export function getSupplierAPI(id: number) {
  return http<PmsSupplier>({
    url: '/supplier/' + id,
    method: 'get',
  })
}

export function supplierDeleteByIdAPI(id: number) {
  return http({
    url: '/supplier/delete/' + id,
    method: 'get',
  })
}

export function supplierUpdateStatusAPI(params: { ids: string; status: number }) {
  return http({
    url: '/supplier/update/status',
    method: 'post',
    params,
  })
}
