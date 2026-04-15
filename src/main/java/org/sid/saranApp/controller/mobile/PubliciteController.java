package org.sid.saranApp.controller.mobile;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PageDataMobileDto;
import org.sid.saranApp.dto.mobile.PubliciteDto;
import org.sid.saranApp.service.mobile.PubliciteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PubliciteController {
    @Autowired
    private PubliciteService publiciteService;

    @PostMapping("/product")
    public PubliciteDto addPublicite(@RequestBody PubliciteDto publiciteDto){
        return publiciteService.add(publiciteDto);
    }

    @GetMapping("/product")
    public PageDataMobileDto<PubliciteDto> findAll(@RequestParam(required = true,defaultValue = "") String name,
                                                   @RequestParam(required = false) String uuidCategorie,
                                                   @RequestParam(required = true,defaultValue = "1000") int minPrice,
                                                   @RequestParam(required = true,defaultValue = "10000000") int maxPrice,
                                                   @RequestParam(required = true,defaultValue = "0") int page,
                                                   @RequestParam(required = true,defaultValue = "10") int size){
        double minPriceC = (double) minPrice;
        double maxPriceC = (double) maxPrice;
        return publiciteService.findAllPublishByCategory(uuidCategorie,name,minPriceC,maxPriceC,page,size);
    }
}
