package com.macro.mall.service;

import com.macro.mall.dto.PmsSupplierParam;
import com.macro.mall.model.PmsSupplier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品供应商管理 Service（练手模块 · 第 3 步）
 */
public interface PmsSupplierService {
    List<PmsSupplier> listAll();

    int create(PmsSupplierParam param);

    @Transactional
    int update(Long id, PmsSupplierParam param);

    int delete(Long id);

    int delete(List<Long> ids);

    List<PmsSupplier> list(String keyword, Integer status, int pageNum, int pageSize);

    PmsSupplier getItem(Long id);

    int updateStatus(List<Long> ids, Integer status);
}
