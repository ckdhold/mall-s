package com.macro.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.PmsSupplierParam;
import com.macro.mall.mapper.PmsSupplierMapper;
import com.macro.mall.model.PmsSupplier;
import com.macro.mall.model.PmsSupplierExample;
import com.macro.mall.service.PmsSupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品供应商管理 Service 实现（练手模块 · 第 3 步）
 */
@Service
public class PmsSupplierServiceImpl implements PmsSupplierService {
    @Autowired
    private PmsSupplierMapper supplierMapper;

    @Override
    public List<PmsSupplier> listAll() {
        PmsSupplierExample example = new PmsSupplierExample();
        example.setOrderByClause("sort desc");
        return supplierMapper.selectByExample(example);
    }

    @Override
    public int create(PmsSupplierParam param) {
        if (StrUtil.isNotEmpty(param.getCode()) && existsCode(param.getCode(), null)) {
            return 0;
        }
        PmsSupplier supplier = new PmsSupplier();
        BeanUtils.copyProperties(param, supplier);
        if (supplier.getSort() == null) {
            supplier.setSort(0);
        }
        if (supplier.getStatus() == null) {
            supplier.setStatus(1);
        }
        return supplierMapper.insertSelective(supplier);
    }

    @Override
    public int update(Long id, PmsSupplierParam param) {
        if (StrUtil.isNotEmpty(param.getCode()) && existsCode(param.getCode(), id)) {
            return 0;
        }
        PmsSupplier supplier = new PmsSupplier();
        BeanUtils.copyProperties(param, supplier);
        supplier.setId(id);
        return supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    @Override
    public int delete(Long id) {
        return supplierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        PmsSupplierExample example = new PmsSupplierExample();
        example.createCriteria().andIdIn(ids);
        return supplierMapper.deleteByExample(example);
    }

    @Override
    public List<PmsSupplier> list(String keyword, Integer status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsSupplierExample example = new PmsSupplierExample();
        example.setOrderByClause("sort desc");
        if (StrUtil.isNotEmpty(keyword)) {
            PmsSupplierExample.Criteria nameCriteria = example.createCriteria();
            nameCriteria.andNameLike("%" + keyword + "%");
            if (status != null) {
                nameCriteria.andStatusEqualTo(status);
            }
            PmsSupplierExample.Criteria codeCriteria = example.or();
            codeCriteria.andCodeLike("%" + keyword + "%");
            if (status != null) {
                codeCriteria.andStatusEqualTo(status);
            }
        } else {
            PmsSupplierExample.Criteria criteria = example.createCriteria();
            if (status != null) {
                criteria.andStatusEqualTo(status);
            }
        }
        return supplierMapper.selectByExample(example);
    }

    @Override
    public PmsSupplier getItem(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        PmsSupplier supplier = new PmsSupplier();
        supplier.setStatus(status);
        PmsSupplierExample example = new PmsSupplierExample();
        example.createCriteria().andIdIn(ids);
        return supplierMapper.updateByExampleSelective(supplier, example);
    }

    private boolean existsCode(String code, Long excludeId) {
        PmsSupplierExample example = new PmsSupplierExample();
        PmsSupplierExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if (excludeId != null) {
            criteria.andIdNotEqualTo(excludeId);
        }
        return supplierMapper.countByExample(example) > 0;
    }
}
