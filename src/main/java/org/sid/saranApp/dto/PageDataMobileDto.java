package org.sid.saranApp.dto;

import org.sid.saranApp.dto.mobile.MountedDto;

import java.util.List;

public class PageDataMobileDto<T> {


    MountedDto mountedDto = new MountedDto();
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

    public MountedDto getMountedDto() {
        return mountedDto;
    }

    public void setMountedDto(MountedDto mountedDto) {
        this.mountedDto = mountedDto;
    }
}
