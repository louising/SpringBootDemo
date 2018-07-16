package com.zero.core.domain;

public class PageVO {
    private static int DEFAULT_PAGE_SIZE = 10;

    private int pageIndex; //IN 1-based
    private int pageSize;  //IN

    private int recordCount; //OUT

    private int startIndex;  //TMP for internal use when query
    private int endIndex;    //TMP for internal use when query

    public PageVO() {

    }

    public PageVO(int pageSize, int pageIndex) {
        super();
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        if (pageIndex < 1)
            pageIndex = 1;

        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        if (pageSize < 1)
            pageSize = DEFAULT_PAGE_SIZE;

        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public String toString() {
        return "PageVO(" + pageSize + "-" + pageIndex + ")";
    }
}
