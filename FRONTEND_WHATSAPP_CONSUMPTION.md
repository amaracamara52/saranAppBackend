# Guide Frontend - Consommation API WhatsApp Commande

Ce document explique comment le frontend doit consommer le module WhatsApp commande expose par le backend.

## Base URL

```text
http://localhost:8091
```

Prefixe API WhatsApp:

```text
/api/whatsapp
```

---

## 1) Webhook / Creation de commande

### Endpoint

`POST /api/whatsapp/webhook`

### Objectif

- Recevoir le message client (texte WhatsApp ou transcription vocal)
- Comprendre la commande
- Creer une `CommandeVente` + lignes
- Retourner un recu et les messages prets a envoyer

### Body JSON

```json
{
  "boutiqueUuid": "UUID_BOUTIQUE",
  "fromPhone": "+2250700000000",
  "messageText": "2 pizza et 1 coca",
  "audioUrl": null,
  "transcriptionText": null
}
```

Notes:
- `boutiqueUuid` est obligatoire.
- V1: si message vocal, envoyer `transcriptionText` deja transcrit.
- `messageText` peut etre null si `transcriptionText` est renseigne.

### Reponse (201)

```json
{
  "orderUuid": "fbe0f7f2-4d1e-43a3-ae8e-05379d0d1b4f",
  "orderNumber": "WSP-12ABCD",
  "status": "EN_ATTENTE",
  "total": 23.0,
  "sourceMessage": "2 pizza et 1 coca",
  "clientMessage": "Commande recue ...",
  "shopMessage": "Nouvelle commande WhatsApp ...",
  "lines": [
    {
      "productUuid": "a1",
      "productLabel": "Pizza",
      "quantity": 2,
      "unitPrice": 10.0,
      "lineTotal": 20.0
    },
    {
      "productUuid": "b2",
      "productLabel": "Coca",
      "quantity": 1,
      "unitPrice": 3.0,
      "lineTotal": 3.0
    }
  ]
}
```

### Erreurs frequentes

- `400` si `boutiqueUuid` manquant
- `400` si format de commande incomprehensible
- `400` si produit non trouve

---

## 2) Validation boutique (accepter)

### Endpoint

`POST /api/whatsapp/orders/{orderUuid}/approve`

### Body JSON (optionnel)

```json
{
  "decisionBy": "manager@boutique.com",
  "reason": "OK"
}
```

### Reponse (200)

- Meme structure que la reponse webhook (`WhatsAppOrderResponseDto`)
- `status` passe a `VALIDE`
- `clientMessage` contient le message final de confirmation

---

## 3) Refus boutique

### Endpoint

`POST /api/whatsapp/orders/{orderUuid}/reject`

### Body JSON (optionnel)

```json
{
  "decisionBy": "manager@boutique.com",
  "reason": "Produit indisponible"
}
```

### Reponse (200)

- Meme structure que `WhatsAppOrderResponseDto`
- `status` passe a `ANNULEE`
- `clientMessage` contient le motif de refus

---

## 4) Mapping UI recommande

- `EN_ATTENTE` -> badge orange "En attente validation"
- `VALIDE` -> badge vert "Confirmee"
- `ANNULEE` -> badge rouge "Refusee"

Actions ecran boutique:
- bouton `Valider` -> endpoint `/approve`
- bouton `Refuser` -> endpoint `/reject`

---

## 5) Exemples frontend (TypeScript)

### a) Axios - creation commande

```ts
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8091"
});

export async function createWhatsAppOrder(payload: {
  boutiqueUuid: string;
  fromPhone?: string;
  messageText?: string;
  audioUrl?: string | null;
  transcriptionText?: string | null;
}) {
  const { data } = await api.post("/api/whatsapp/webhook", payload);
  return data;
}
```

### b) Axios - validation

```ts
export async function approveWhatsAppOrder(orderUuid: string, decisionBy: string) {
  const { data } = await api.post(`/api/whatsapp/orders/${orderUuid}/approve`, {
    decisionBy
  });
  return data;
}
```

### c) Axios - refus

```ts
export async function rejectWhatsAppOrder(orderUuid: string, reason: string, decisionBy: string) {
  const { data } = await api.post(`/api/whatsapp/orders/${orderUuid}/reject`, {
    decisionBy,
    reason
  });
  return data;
}
```

---

## 6) Parcours UX minimal

1. Client envoie message.
2. Front appelle `/webhook` (ou recoit callback serveur selon votre architecture).
3. Afficher recu et total.
4. Afficher commande cote boutique avec actions.
5. Boutique valide/refuse.
6. Front affiche message final client (`clientMessage`).

---

## 7) Checklist integration frontend

- Gerer loading + retry sur appels reseau.
- Afficher erreurs backend (`message` si present).
- Conserver `orderUuid` pour les actions approve/reject.
- Logguer `sourceMessage` et `status` pour debug.
- Afficher `lines` + `total` dans un composant "Recu".

