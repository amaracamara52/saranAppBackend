package org.sid.saranApp.service.mobile;

import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.dto.mobile.RegistrationDto;
import org.sid.saranApp.dto.mobile.ShopMountedDto;
import org.sid.saranApp.dto.mobile.UserDto;
import org.sid.saranApp.enume.MonnaieEnum;

import java.util.List;

public interface RegistrationService {

        RegistrationDto addRegistration(RegistrationDto registrationDto);
        RegistrationDto updateRegistration(RegistrationDto registrationDto,String uuid);
        UserDto getRegistration();
        List<RegistrationDto> findAllRegistration();
        ShopMountedDto  shopMounted();
        RegistrationDto getRegistrationByUserConnected();

        RegistrationDto finalizeConfigProfil(String uuidProfileImage, String uuidLogo, List<DomainDto> domainDtos, MonnaieEnum monnaie);
}
