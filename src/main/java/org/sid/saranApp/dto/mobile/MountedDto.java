package org.sid.saranApp.dto.mobile;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.CategorieDto;

import java.util.ArrayList;
import java.util.List;

public class MountedDto extends ResponseDto {

    private UserDto user;
    private BoutiqueDto boutique;
    private List<CategorieDto> categories = new ArrayList<>();
    //private List<PubliciteDto> publicites = new ArrayList<>();

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public BoutiqueDto getBoutique() {
        return boutique;
    }

    public void setBoutique(BoutiqueDto boutique) {
        this.boutique = boutique;
    }

    public List<CategorieDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorieDto> categories) {
        this.categories = categories;
    }

//    public List<PubliciteDto> getPublicites() {
//        return publicites;
//    }
//
//    public void setPublicites(List<PubliciteDto> publicites) {
//        this.publicites = publicites;
//    }
}
