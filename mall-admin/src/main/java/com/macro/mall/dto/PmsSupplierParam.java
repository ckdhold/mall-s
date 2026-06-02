package com.macro.mall.dto;

import com.macro.mall.validator.FlagValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 供应商请求参数（练手模块 · 第 3 步）
 */
@Data
@EqualsAndHashCode
public class PmsSupplierParam {
    @NotEmpty
    @Schema(title = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(title = "供应商编码")
    private String code;

    @Schema(title = "联系人")
    private String contactName;

    @Schema(title = "联系电话")
    private String contactPhone;

    @Schema(title = "联系邮箱")
    private String email;

    @Schema(title = "地址")
    private String address;

    @Schema(title = "备注")
    private String remark;

    @Min(value = 0)
    @Schema(title = "排序（越大越靠前）")
    private Integer sort;

    @FlagValidator(value = {"0", "1"}, message = "状态不正确")
    @Schema(title = "状态：0->禁用；1->启用")
    private Integer status;
}
