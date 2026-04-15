package org.sid.saranApp.controller.mobile;

import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.dto.mobile.RegistrationDto;
import org.sid.saranApp.dto.mobile.ShopMountedDto;
import org.sid.saranApp.dto.mobile.UserDto;
import org.sid.saranApp.enume.MonnaieEnum;
import org.sid.saranApp.service.mobile.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MobileController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public RegistrationDto makeRegistration(@RequestBody RegistrationDto registrationDto){
        return registrationService.addRegistration(registrationDto);
    }

    @GetMapping("/get-connected-user")
    public UserDto getUserLogin(){
        return registrationService.getRegistration();
    }
    @GetMapping("/shops-mounted")
    public ShopMountedDto getShopMounted(){
        return registrationService.shopMounted();
    }
    @GetMapping("/getRegistration-connected-user")
    public RegistrationDto getRegistrationByUserConnected(){
        return registrationService.getRegistrationByUserConnected();
    }
    @PostMapping("/registration/{uuidImageProfile}/{uuidLogo}/{monnaie}")
    public RegistrationDto finalizeConfigProfil(@PathVariable String uuidImageProfile, @PathVariable String uuidLogo, @RequestBody List<DomainDto> domainDtos,@PathVariable  String monnaie){
        return registrationService.finalizeConfigProfil(uuidImageProfile,uuidLogo,domainDtos,MonnaieEnum.valueOf(monnaie));
    }


}
