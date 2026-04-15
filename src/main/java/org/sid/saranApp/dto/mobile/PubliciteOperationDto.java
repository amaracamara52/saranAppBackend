package org.sid.saranApp.dto.mobile;

import org.sid.saranApp.enume.EnumStatutOnlineOrder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class PubliciteOperationDto extends ResponseDto {

    private String uuid;
    private String uuidPublicite;
    private boolean like;
    private boolean favorite;
    private boolean interested;
    private String user;
    private boolean publish;
    @Enumerated(EnumType.STRING)
    private EnumStatutOnlineOrder enumStatutOnlineOrder;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidPublicite() {
        return uuidPublicite;
    }

    public void setUuidPublicite(String uuidPublicite) {
        this.uuidPublicite = uuidPublicite;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public EnumStatutOnlineOrder getEnumStatutOnlineOrder() {
        return enumStatutOnlineOrder;
    }

    public void setEnumStatutOnlineOrder(EnumStatutOnlineOrder enumStatutOnlineOrder) {
        this.enumStatutOnlineOrder = enumStatutOnlineOrder;
    }
}
