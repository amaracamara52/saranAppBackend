package org.sid.saranApp.dto.mobile;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.enume.Boutique;
import org.sid.saranApp.enume.EnumTypeBoutique;
import org.sid.saranApp.enume.Genre;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDto {
    private ResponseDto response = new ResponseDto();
    private CustomerDto  customer = new CustomerDto();
    private BoutiqueMobileDto boutique;
//    private List<DomainBoutiqueDto> domainBoutiqueDtos = new ArrayList<>();

    public ResponseDto getResponse() {
        return response;
    }

    public void setResponse(ResponseDto response) {
        this.response = response;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public BoutiqueMobileDto getBoutique() {
        return boutique;
    }

    public void setBoutique(BoutiqueMobileDto boutique) {
        this.boutique = boutique;
    }


}
