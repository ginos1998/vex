package com.vex.models.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DefaultFilter {
    protected Integer pageNumber;
    protected Integer pageSize;
    protected String sort;
    protected String order;
    protected Integer offset;
    private final Integer defaultPageSize = 10;
    private final Integer defaultPageNumber = 1;
    private final String defaultSort = "id";
    private final String defaultOrder = "desc";

    public Integer getPageNumber() {
        return pageNumber == null ? defaultPageNumber : pageNumber;
    }

    public Integer getPageSize() {
        return pageSize == null ? defaultPageSize : pageSize;
    }

    public Object getSort() {
        return sort == null ? defaultSort : sort;
    }

    public String getOrder() {
        return order == null ? defaultOrder : order;
    }

}
