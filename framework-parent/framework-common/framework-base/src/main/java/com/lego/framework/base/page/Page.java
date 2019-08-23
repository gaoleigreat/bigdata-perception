package com.lego.framework.base.page;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 4121168324340189627L;

	private Integer pageIndex;

	private Integer pageSize;

	private Integer startIndex;

	private Integer totalCount = 0;

	private Integer totalPages;

	public Page() {
	}

	public Page(Integer pageIndex, Integer pageSize, Integer startIndex, Integer totalCount, Integer totalPages) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.startIndex = startIndex;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		return (this.pageIndex - 1) * this.pageSize;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
