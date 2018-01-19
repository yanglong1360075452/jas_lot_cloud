package com.wizinno.jas.common.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuMei on 2017-05-25.
 *
 * 分页数据基类
 */
public class PageData<T>{
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 每页显示个数
     */
    private int pageSize;
    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 总记录数
     */
    private long totalCount;
    private Long paraml;
    private Long ParamlO;
    private String paramO;
    private String param;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParaml() {
        return paraml;
    }

    public void setParaml(Long paraml) {
        this.paraml = paraml;
    }

    public Long getParamlO() {
        return ParamlO;
    }

    public void setParamlO(Long paramlO) {
        ParamlO = paramlO;
    }

    public String getParamO() {
        return paramO;
    }

    public void setParamO(String paramO) {
        this.paramO = paramO;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * 结果列表
     */
    private List<T> rows;

    public PageData(){
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    public PageData(int currentPage, int pageSize, List<T> rows){
        this.currentPage=currentPage<=0?1:currentPage;
        this.pageSize=pageSize<=0?1:pageSize;
        this.rows = rows;
        //this.totalPage = totalPage;
        //this.totalCount = totalCount;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 设置结果 及总页数
     */
    public void build(List<T> rows) {
        this.setRows(rows);
        long count =  this.getTotalCount();
        long divisor = count / this.getPageSize();
        long remainder = count % this.getPageSize();
        this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
