package com.vex.models.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter extends DefaultFilter {
    private Integer branchId;
    private Integer categoryId;
    private String name;
    private String code;
    private String enabled;
    private Integer supplierId;
    private Integer brandId;

    public Map<String, Object> getBindValues() {
        Map<String, Object> bindValues = new HashMap<>();
        bindValues.put("branch_id", this.getBranchId() == null ? 0 : this.getBranchId());
        bindValues.put("category_id", this.getCategoryId() == null ? 0 : this.getCategoryId());
        bindValues.put("name", this.getName() == null ? "" : this.getName());
        bindValues.put("code", this.getCode() == null ? "" : this.getCode());
        bindValues.put("supplier_id", this.getSupplierId() == null ? 0 : this.getSupplierId());
        bindValues.put("brand_id", this.getBrandId() == null ? 0 : this.getBrandId());
        bindValues.put("page", this.getPageNumber());
        bindValues.put("size", this.getPageSize());
        bindValues.put("sort", this.getSort());
        bindValues.put("order", this.getOrder());

        return bindValues;
    }

    public String getQueryParams() {
        StringBuilder queryParams = new StringBuilder();
        Set<String> keys = getBindValues().keySet();
        for (String key : keys) {
            queryParams.append(":").append(key).append(", ");
        }
        queryParams.deleteCharAt(queryParams.length() - 2);
        return queryParams.toString();
    }
    
}
