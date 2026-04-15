package org.sid.saranApp.service.mobile;

import org.sid.saranApp.dto.PageDataMobileDto;
import org.sid.saranApp.dto.mobile.PubliciteDto;

public interface PubliciteService {

    PubliciteDto add(PubliciteDto publiciteDto);

    PageDataMobileDto<PubliciteDto> findAllPublishByCategory(String uuidCategorie, String name, double minPrice, double maxPrice, int page, int size);

    PubliciteDto update(PubliciteDto publiciteDto,String uuid);

    PubliciteDto get(String uuidProduct);

}
