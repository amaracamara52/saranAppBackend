package org.sid.saranApp.controller;

import org.sid.saranApp.dto.whatsapp.WhatsAppOrderDecisionDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppOrderResponseDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppWebhookRequestDto;
import org.sid.saranApp.service.WhatsAppOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/whatsapp")
public class WhatsAppCommandeController {

    private final WhatsAppOrderService whatsAppOrderService;

    public WhatsAppCommandeController(WhatsAppOrderService whatsAppOrderService) {
        this.whatsAppOrderService = whatsAppOrderService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<WhatsAppOrderResponseDto> receiveWebhook(
            @RequestBody WhatsAppWebhookRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(whatsAppOrderService.processIncomingMessage(request));
    }

    @PostMapping("/orders/{orderUuid}/approve")
    public ResponseEntity<WhatsAppOrderResponseDto> approveOrder(
            @PathVariable String orderUuid,
            @RequestBody(required = false) WhatsAppOrderDecisionDto decision) {
        return ResponseEntity.ok(whatsAppOrderService.approveOrder(orderUuid, decision));
    }

    @PostMapping("/orders/{orderUuid}/reject")
    public ResponseEntity<WhatsAppOrderResponseDto> rejectOrder(
            @PathVariable String orderUuid,
            @RequestBody(required = false) WhatsAppOrderDecisionDto decision) {
        return ResponseEntity.ok(whatsAppOrderService.rejectOrder(orderUuid, decision));
    }
}
