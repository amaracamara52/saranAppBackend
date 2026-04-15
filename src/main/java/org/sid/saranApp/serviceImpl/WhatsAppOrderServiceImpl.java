package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.whatsapp.WhatsAppOrderDecisionDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppOrderLineDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppOrderResponseDto;
import org.sid.saranApp.dto.whatsapp.WhatsAppWebhookRequestDto;
import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.WhatsAppOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WhatsAppOrderServiceImpl implements WhatsAppOrderService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppOrderServiceImpl.class);

    private static final Pattern ORDER_ITEM_PATTERN =
            Pattern.compile("(\\d+)\\s+([\\p{L}0-9\\- ]+?)(?=\\s*(,| et |$))", Pattern.CASE_INSENSITIVE);

    private final BoutiqueRepository boutiqueRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;
    private final TypeUniteDeVenteRepository typeUniteDeVenteRepository;
    private final CommandeVenteRepository commandeVenteRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ModePaiementRepository modePaiementRepository;

    public WhatsAppOrderServiceImpl(
            BoutiqueRepository boutiqueRepository,
            UtilisateurRepository utilisateurRepository,
            ProduitRepository produitRepository,
            TypeUniteDeVenteRepository typeUniteDeVenteRepository,
            CommandeVenteRepository commandeVenteRepository,
            LigneCommandeRepository ligneCommandeRepository,
            ModePaiementRepository modePaiementRepository) {
        this.boutiqueRepository = boutiqueRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.produitRepository = produitRepository;
        this.typeUniteDeVenteRepository = typeUniteDeVenteRepository;
        this.commandeVenteRepository = commandeVenteRepository;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.modePaiementRepository = modePaiementRepository;
    }

    @Override
    @Transactional
    public WhatsAppOrderResponseDto processIncomingMessage(WhatsAppWebhookRequestDto request) {
        validateRequest(request);

        Boutique boutique = boutiqueRepository.findById(request.getBoutiqueUuid())
                .orElseThrow(() -> new IllegalArgumentException("Boutique introuvable"));

        Utilisateur systemUser = resolveSystemUser(boutique.getUuid());
        ModePaiement modePaiement = resolveModePaiement(systemUser);
        String normalizedMessage = resolveMessageText(request);
        List<ParsedItem> parsedItems = parseOrder(normalizedMessage);
        if (parsedItems.isEmpty()) {
            throw new IllegalArgumentException("Impossible de comprendre la commande. Exemple: 2 pizza et 1 coca");
        }

        List<Produit> produits = produitRepository.findByBoutique(boutique);
        if (produits.isEmpty()) {
            throw new IllegalArgumentException("Aucun produit disponible pour cette boutique");
        }

        CommandeVente commande = new CommandeVente();
        commande.setBoutique(boutique);
        commande.setUtilisateur(systemUser);
        commande.setModePaiement(modePaiement);
        commande.setDatePaiement(new Date());
        commande.setCommandeVenteEnum(StatusCommandeVenteEnum.EN_ATTENTE);
        commande.setTypeCommande(EnumTypeCommande.DETAIL);
        commande.setPaye(false);
        commande.setNumeroCommande(generateWhatsAppOrderNumber());

        List<WhatsAppOrderLineDto> responseLines = new ArrayList<>();
        double total = 0d;

        commande = commandeVenteRepository.save(commande);
        for (ParsedItem parsedItem : parsedItems) {
            Produit produit = resolveProduct(parsedItem.label, produits)
                    .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé: " + parsedItem.label));
            total += produit.getPrixVente() * parsedItem.quantity;

            LigneCommande ligne = new LigneCommande();
            ligne.setCommandeVente(commande);
            ligne.setProduit(produit);
            ligne.setBoutique(boutique);
            ligne.setUtilisateur(systemUser);
            ligne.setQuantite(parsedItem.quantity);
            ligne.setTypeUniteDeVente(resolveTypeUniteForProduct(produit));
            ligneCommandeRepository.save(ligne);

            WhatsAppOrderLineDto lineDto = new WhatsAppOrderLineDto();
            lineDto.setProductUuid(produit.getUuid());
            lineDto.setProductLabel(produit.getArticle() != null ? produit.getArticle().getLibelle() : produit.getUuid());
            lineDto.setQuantity(parsedItem.quantity);
            lineDto.setUnitPrice(produit.getPrixVente());
            lineDto.setLineTotal(produit.getPrixVente() * parsedItem.quantity);
            responseLines.add(lineDto);
        }

        commande.setMontantCommade(total);
        commande.setMontantCommadeImage(total);
        commande = commandeVenteRepository.save(commande);

        String receipt = buildReceipt(commande.getNumeroCommande(), responseLines, total);
        String clientText = "Commande recue ✅\n" + receipt + "\nStatut: en attente de validation boutique.";
        String shopText = "Nouvelle commande WhatsApp " + commande.getNumeroCommande()
                + " a valider.\nTotal: " + formatAmount(total);

        log.info("Commande WhatsApp creee {}, client={}, total={}", commande.getUuid(), request.getFromPhone(), total);
        return toResponse(commande, normalizedMessage, responseLines, clientText, shopText);
    }

    @Override
    @Transactional
    public WhatsAppOrderResponseDto approveOrder(String orderUuid, WhatsAppOrderDecisionDto decision) {
        CommandeVente commande = commandeVenteRepository.findById(orderUuid)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        commande.setCommandeVenteEnum(StatusCommandeVenteEnum.VALIDE);
        commandeVenteRepository.save(commande);

        List<WhatsAppOrderLineDto> lines = buildResponseLines(commande);
        String clientText = "Votre commande " + commande.getNumeroCommande()
                + " est confirmee ✅. Total: " + formatAmount(commande.getMontantCommade());
        String shopText = "Commande " + commande.getNumeroCommande() + " validee par "
                + valueOrDefault(decision != null ? decision.getDecisionBy() : null, "boutique");

        return toResponse(commande, null, lines, clientText, shopText);
    }

    @Override
    @Transactional
    public WhatsAppOrderResponseDto rejectOrder(String orderUuid, WhatsAppOrderDecisionDto decision) {
        CommandeVente commande = commandeVenteRepository.findById(orderUuid)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        commande.setCommandeVenteEnum(StatusCommandeVenteEnum.ANNULEE);
        commandeVenteRepository.save(commande);

        List<WhatsAppOrderLineDto> lines = buildResponseLines(commande);
        String reason = valueOrDefault(decision != null ? decision.getReason() : null, "indisponibilite produit");
        String clientText = "Votre commande " + commande.getNumeroCommande()
                + " a ete refusee ❌. Motif: " + reason;
        String shopText = "Commande " + commande.getNumeroCommande() + " refusee.";

        return toResponse(commande, null, lines, clientText, shopText);
    }

    private void validateRequest(WhatsAppWebhookRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("Corps de requete requis");
        }
        if (isBlank(request.getBoutiqueUuid())) {
            throw new IllegalArgumentException("boutiqueUuid est requis");
        }
        if (isBlank(request.getMessageText()) && isBlank(request.getTranscriptionText()) && isBlank(request.getAudioUrl())) {
            throw new IllegalArgumentException("messageText, transcriptionText ou audioUrl requis");
        }
    }

    private String resolveMessageText(WhatsAppWebhookRequestDto request) {
        if (!isBlank(request.getMessageText())) {
            return request.getMessageText().trim();
        }
        if (!isBlank(request.getTranscriptionText())) {
            return request.getTranscriptionText().trim();
        }
        throw new IllegalArgumentException("audioUrl recu mais transcriptionText manquant pour cette V1");
    }

    private Utilisateur resolveSystemUser(String boutiqueUuid) {
        List<Utilisateur> users = utilisateurRepository.listeByBoutique(boutiqueUuid);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Aucun utilisateur lie a la boutique");
        }
        return users.get(0);
    }

    private ModePaiement resolveModePaiement(Utilisateur systemUser) {
        List<ModePaiement> modes = modePaiementRepository.findAll();
        for (ModePaiement mode : modes) {
            if (mode.getBoutique() != null
                    && systemUser.getBoutique() != null
                    && systemUser.getBoutique().getUuid().equals(mode.getBoutique().getUuid())) {
                return mode;
            }
        }
        if (modes.isEmpty()) {
            throw new IllegalArgumentException("Aucun mode de paiement configure");
        }
        return modes.get(0);
    }

    private Optional<Produit> resolveProduct(String requestedLabel, List<Produit> boutiqueProducts) {
        String target = normalize(requestedLabel);
        Produit best = null;
        int bestScore = Integer.MIN_VALUE;
        for (Produit produit : boutiqueProducts) {
            if (produit.getArticle() == null || isBlank(produit.getArticle().getLibelle())) {
                continue;
            }
            String label = normalize(produit.getArticle().getLibelle());
            int score = score(label, target);
            if (score > bestScore) {
                bestScore = score;
                best = produit;
            }
        }
        return bestScore >= 2 ? Optional.ofNullable(best) : Optional.empty();
    }

    private int score(String candidate, String target) {
        if (candidate.equals(target)) {
            return 4;
        }
        if (candidate.contains(target)) {
            return 3;
        }
        if (target.contains(candidate)) {
            return 2;
        }
        return 0;
    }

    private TypeUniteDeVente resolveTypeUniteForProduct(Produit produit) {
        if (produit.getArticle() == null) {
            return null;
        }
        List<TypeUniteDeVente> unites = typeUniteDeVenteRepository.findByArticle(produit.getArticle());
        if (unites.isEmpty()) {
            return null;
        }
        return unites.get(0);
    }

    private List<ParsedItem> parseOrder(String text) {
        String normalized = text.replace(";", ",").replace(" et ", ",");
        Matcher matcher = ORDER_ITEM_PATTERN.matcher(normalized);
        List<ParsedItem> items = new ArrayList<>();
        while (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1).trim());
            String label = matcher.group(2).trim();
            if (!label.isEmpty() && quantity > 0) {
                items.add(new ParsedItem(label, quantity));
            }
        }
        return items;
    }

    private String buildReceipt(String orderNumber, List<WhatsAppOrderLineDto> lines, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("Recu ").append(orderNumber).append("\n");
        for (WhatsAppOrderLineDto line : lines) {
            sb.append("- ")
                    .append(line.getQuantity())
                    .append(" ")
                    .append(line.getProductLabel())
                    .append(" = ")
                    .append(formatAmount(line.getLineTotal()))
                    .append("\n");
        }
        sb.append("Total: ").append(formatAmount(total));
        return sb.toString();
    }

    private List<WhatsAppOrderLineDto> buildResponseLines(CommandeVente commande) {
        List<LigneCommande> lines = ligneCommandeRepository.findByCommandeVenteUuid(commande.getUuid());
        List<WhatsAppOrderLineDto> result = new ArrayList<>();
        for (LigneCommande line : lines) {
            if (line.getCommandeVente() == null || !commande.getUuid().equals(line.getCommandeVente().getUuid())) {
                continue;
            }
            WhatsAppOrderLineDto dto = new WhatsAppOrderLineDto();
            dto.setProductUuid(line.getProduit() != null ? line.getProduit().getUuid() : null);
            dto.setProductLabel(line.getProduit() != null && line.getProduit().getArticle() != null
                    ? line.getProduit().getArticle().getLibelle()
                    : "Produit");
            dto.setQuantity(line.getQuantite());
            double unitPrice = line.getProduit() != null ? line.getProduit().getPrixVente() : 0d;
            dto.setUnitPrice(unitPrice);
            dto.setLineTotal(unitPrice * line.getQuantite());
            result.add(dto);
        }
        return result;
    }

    private WhatsAppOrderResponseDto toResponse(
            CommandeVente commande,
            String sourceMessage,
            List<WhatsAppOrderLineDto> lines,
            String clientMessage,
            String shopMessage) {
        WhatsAppOrderResponseDto response = new WhatsAppOrderResponseDto();
        response.setOrderUuid(commande.getUuid());
        response.setOrderNumber(commande.getNumeroCommande());
        response.setStatus(commande.getCommandeVenteEnum() != null ? commande.getCommandeVenteEnum().name() : null);
        response.setTotal(commande.getMontantCommade());
        response.setSourceMessage(sourceMessage);
        response.setLines(lines);
        response.setClientMessage(clientMessage);
        response.setShopMessage(shopMessage);
        return response;
    }

    private String formatAmount(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }

    private String generateWhatsAppOrderNumber() {
        return "WSP-" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase(Locale.ROOT);
    }

    private String normalize(String value) {
        return Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9 ]", " ")
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ")
                .trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String valueOrDefault(String value, String defaultValue) {
        return isBlank(value) ? defaultValue : value.trim();
    }

    private static class ParsedItem {
        private final String label;
        private final int quantity;

        private ParsedItem(String label, int quantity) {
            this.label = label;
            this.quantity = quantity;
        }
    }
}
