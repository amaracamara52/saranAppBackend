/**
 *
 */
package org.sid.saranApp.enume;

/**
 *
 */
public enum TypeUniteEnum {
 PIECE("PIECE"),
  BOITE("BOITE"),
  FLACON("FLACON"),
  BOUTEILLE("BOUTEILLE"),
  SACHET("SACHET"),
  PAQUET("PAQUET"),
  CARTON("CARTON"),
  PACK("PACK"),
  LOT("LOT"),
  SAC("SAC"),
  BIDON("BIDON"),
  FUT("FUT"),
  GRAMME("GRAMME"),
  KILOGRAMME("KILOGRAMME"),
  LITRE("LITRE"),
  MILLILITRE("MILLILITRE"),
  COMPRIME("COMPRIME"),
  GELULE("GELULE"),
  AMPOULE("AMPOULE"),
  PLAQUETTE("PLAQUETTE")
  ;
  private String label;

  TypeUniteEnum(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
};
