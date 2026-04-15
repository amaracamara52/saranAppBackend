package org.sid.saranApp.dto.whatsapp;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppOrderResponseDto {

    private String orderUuid;
    private String orderNumber;
    private String status;
    private double total;
    private String sourceMessage;
    private String clientMessage;
    private String shopMessage;
    private List<WhatsAppOrderLineDto> lines = new ArrayList<>();

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSourceMessage() {
        return sourceMessage;
    }

    public void setSourceMessage(String sourceMessage) {
        this.sourceMessage = sourceMessage;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public String getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(String shopMessage) {
        this.shopMessage = shopMessage;
    }

    public List<WhatsAppOrderLineDto> getLines() {
        return lines;
    }

    public void setLines(List<WhatsAppOrderLineDto> lines) {
        this.lines = lines;
    }
}
