package org.sid.saranApp.dto;

import java.util.List;

public class PageDataDto<T> {

    PageDto page = new PageDto();
    List<T> data;
    
    
	public PageDto getPage() {
        return page;
    }

    public List<T> getData() {
        return data;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
