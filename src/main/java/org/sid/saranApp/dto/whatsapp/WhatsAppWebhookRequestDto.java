package org.sid.saranApp.dto.whatsapp;

public class WhatsAppWebhookRequestDto {

    private String boutiqueUuid;
    private String fromPhone;
    private String messageText;
    private String audioUrl;
    private String transcriptionText;

    public String getBoutiqueUuid() {
        return boutiqueUuid;
    }

    public void setBoutiqueUuid(String boutiqueUuid) {
        this.boutiqueUuid = boutiqueUuid;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTranscriptionText() {
        return transcriptionText;
    }

    public void setTranscriptionText(String transcriptionText) {
        this.transcriptionText = transcriptionText;
    }
}
