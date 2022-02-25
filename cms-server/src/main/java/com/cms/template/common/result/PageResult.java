package com.cms.template.common.result;

public class PageResult {
    private long page;
    private long pageSize;
    private long pages;
    private long total;
    private Object content;

    public PageResult(long page, long pageSize, long pages, long total, Object content) {
        this.page = page;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
        this.content = content;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
