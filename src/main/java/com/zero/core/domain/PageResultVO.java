package com.zero.core.domain;

/**
 * PageResultVO
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public class PageResultVO {
    private Object records;
    private PageVO pageVO;

    public PageResultVO(PageVO pageVO, Object records) {
        super();
        this.pageVO = pageVO;
        this.records = records;
    }

    public Object getRecords() {
        return records;
    }

    public void setRecords(Object records) {
        this.records = records;
    }

    public PageVO getPageVO() {
        return pageVO;
    }

    public void setPageVO(PageVO pageVO) {
        this.pageVO = pageVO;
    }

}