package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.PmsSupplierParam;
import com.macro.mall.model.PmsSupplier;
import com.macro.mall.service.PmsSupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品供应商管理 Controller（练手模块 · 第 3 步）
 */
@Controller
@Tag(name = "PmsSupplierController", description = "商品供应商管理")
@RequestMapping("/supplier")
public class PmsSupplierController {
    @Autowired
    private PmsSupplierService supplierService;

    @Operation(summary = "获取全部供应商列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsSupplier>> listAll() {
        return CommonResult.success(supplierService.listAll());
    }

    @Operation(summary = "添加供应商")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> create(@Validated @RequestBody PmsSupplierParam param) {
        int count = supplierService.create(param);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "更新供应商")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> update(@PathVariable("id") Long id,
                                        @Validated @RequestBody PmsSupplierParam param) {
        int count = supplierService.update(id, param);
        if (count == 1) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "删除供应商")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Void> delete(@PathVariable("id") Long id) {
        int count = supplierService.delete(id);
        if (count == 1) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "根据关键字分页获取供应商列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsSupplier>> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsSupplier> list = supplierService.list(keyword, status, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @Operation(summary = "根据编号查询供应商")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsSupplier> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(supplierService.getItem(id));
    }

    @Operation(summary = "批量删除供应商")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        int count = supplierService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "批量更新供应商状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> updateStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("status") Integer status) {
        int count = supplierService.updateStatus(ids, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
