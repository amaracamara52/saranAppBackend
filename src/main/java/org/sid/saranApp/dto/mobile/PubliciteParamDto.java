package org.sid.saranApp.dto.mobile;

public class PubliciteParamDto extends ResponseDto {

    private String uuid;
    private String uuidPublicite;
    private String libelle;
    private String value;

    public String getUuidPublicite() {
        return uuidPublicite;
    }

    public void setUuidPublicite(String uuidPublicite) {
        this.uuidPublicite = uuidPublicite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
