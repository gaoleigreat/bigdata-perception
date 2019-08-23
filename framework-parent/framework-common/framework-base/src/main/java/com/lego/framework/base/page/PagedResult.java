package com.lego.framework.base.page;

import com.survey.lib.common.page.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedResult<T> implements Serializable {

	private static final long serialVersionUID = 176319318287181287L;

	private Page page;

	private List<T> resultList = new ArrayList<T>();

	public PagedResult() {
		super();
	}

	public PagedResult(Page page) {
		super();
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
