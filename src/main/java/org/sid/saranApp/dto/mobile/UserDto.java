package org.sid.saranApp.dto.mobile;

public class UserDto extends ResponseDto {
    private String uuid;
    private String phone;
    private String username;
    private String email;
    private String addresse;
    private String imageProfile;
    private String uuidImageProfile;
    private String boutique;
    private String logoBoutique;
    private String password;
    private String role;
    private int nombreCommande;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNombreCommande() {
        return nombreCommande;
    }

    public void setNombreCommande(int nombreCommande) {
        this.nombreCommande = nombreCommande;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getUuidImageProfile() {
        return uuidImageProfile;
    }

    public void setUuidImageProfile(String uuidImageProfile) {
        this.uuidImageProfile = uuidImageProfile;
    }

    public String getBoutique() {
        return boutique;
    }

    public void setBoutique(String boutique) {
        this.boutique = boutique;
    }

    public String getLogoBoutique() {
        return logoBoutique;
    }

    public void setLogoBoutique(String logoBoutique) {
        this.logoBoutique = logoBoutique;
    }
}
