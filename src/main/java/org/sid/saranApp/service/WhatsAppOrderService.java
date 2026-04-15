package org.sid.saranApp.service;

import org.sid.saranApp.dto.whatsapp.WhatsAppOrderDecisionDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppOrderResponseDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppWebhookRequestDto;

public interface WhatsAppOrderService {

    WhatsAppOrderResponseDto processIncomingMessage(WhatsAppWebhookRequestDto request);

    WhatsAppOrderResponseDto approveOrder(String orderUuid, WhatsAppOrderDecisionDto decision);

    WhatsAppOrderResponseDto rejectOrder(String orderUuid, WhatsAppOrderDecisionDto decision);
}
