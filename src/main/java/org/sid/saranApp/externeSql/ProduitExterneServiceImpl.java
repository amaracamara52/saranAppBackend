package org.sid.saranApp.externeSql;

import org.sid.saranApp.dto.StatistiqueBoutiqueDto;
import org.sid.saranApp.dto.TopVenteDTO;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitExterneServiceImpl {
	
	@PersistenceContext
    private EntityManager entityManager;
	Logger logger = LoggerFactory.getLogger(ProduitExterneServiceImpl.class);
	
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	
	
	
	public List<TopVenteDTO> topVenteProduit(int limit){
		
		 String sql ="SELECT \n" +
				 "\t\t \t\t    a.libelle,\n" +
				 "\t\t \t\t    a.description, \n" +
				 "\t\t \t\t    p.uuid ,\n" +
				 "\t\t \t\t    SUM(lc.quantite) AS total_vendu,\n" +
				 "\t\t \t\t    er.code,\n" +
				 "\t\t \t\t    er.etagere,\n" +
				 "\t\t \t\t    er.rayon\n" +
				 "\t\t \t\tFROM \n" +
				 "\t\t \t\t    produit  p\n" +
				 "\t\t \t\tJOIN \n" +
				 "\t\t \t\t    ligne_commande lc ON p.uuid = lc.produit_uuid \n" +
				 "\t\t \t\t--JOIN   \n" +
				 "\t\t \t\t--   livraison_commande_fournisseur lv on p.livraison_commande_fournisseur_uuid  = lv.uuid \n" +
				 "\t\t \t\t--JOIN   \n" +
				 "\t\t \t\t--    detail_commande_fournisseur dcf on lv.detail_commande_fournisseur_uuid = dcf.uuid \n" +
				 "\t\t \t\tJOIN\n" +
				 "\t\t \t\t\tarticle a on a.uuid = p.article_uuid \n" +
				 "\t\t \t\tLEFT JOIN\t\n" +
				 "\t\t \t\t\tetagere_rayon er on p.emplacement_uuid = er.uuid\t\n" +
				 "\t\t \t\t\t\n" +
				 "\t\t \t\twhere p.boutique_uuid =:boutique_uuid and lc.is_delete = false\t\n" +
				 "\t\t \t\t\t\n" +
				 "\t\t \t\tGROUP BY \n" +
				 "\t\t \t\t    p.uuid,a.libelle,a.description ,  er.code,\n" +
				 "\t\t \t\t    er.etagere,\n" +
				 "\t\t \t\t    er.rayon\n" +
				 "\t\t \t\tORDER BY \n" +
				 "\t\t \t\t    total_vendu DESC\n" +
				 "\t\t \t\tLIMIT :limit";
		
		       
		Query query = entityManager.createNativeQuery(sql);
		 query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
         query.setParameter("limit", limit);
      

       List<Object[]> results = query.getResultList();
       List<TopVenteDTO> topVentes = new ArrayList<>();

       for (Object[] row : results) {
           String libelle = (String) row[0];
           String description = (String) row[1];
           String uuid = (String) row[2];
           int totalVendu = row[3] != null ? ((Number) row[3]).intValue() : 0;
           String code = row[4] != null ? String.valueOf(row[4]) : null;
           String etagere = row[5] != null ? String.valueOf(row[5]) : null;
           String rayon = row[6] != null ? String.valueOf(row[6]) : null;
         
           topVentes.add(new TopVenteDTO(libelle, description, code, etagere, rayon, totalVendu, uuid));
       }

       return topVentes;
		
	}
	
	public StatistiqueBoutiqueDto getStockARecouvrer() {
		
		 String sql ="select count(p) as nombre,sum(p.prix_vente * p.quantite) as stock from produit p "
		 		+ "where p.boutique_uuid=:boutique_uuid";
			
			       
			Query query = entityManager.createNativeQuery(sql);
			 query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
	       
	       List<Object[]> results = query.getResultList();
	       StatistiqueBoutiqueDto topVentes = null;

	       for (Object[] row : results) {
	           String libelle = "Stock à recouvrer";
	           double montant = row[1] != null ? Double.parseDouble(String.valueOf(row[1])) : 0.0;
     		  
	           int nombre = ((Number) row[0]).intValue();
	        
	         
	           topVentes = new StatistiqueBoutiqueDto(libelle, montant, nombre);
	       }

	       return topVentes;
		
	}

	
	public StatistiqueBoutiqueDto getStockResteARecouvrer() {
		
		 String sql ="select count(p) as nombre,sum(p.prix_vente * p.quantite_image) as stock from produit p"
		 		+ "where p.boutique_uuid=:boutique_uuid";
			
			       
			Query query = entityManager.createNativeQuery(sql);
			 query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
	       
	       List<Object[]> results = query.getResultList();
	       StatistiqueBoutiqueDto topVentes = null;

	       for (Object[] row : results) {
	           String libelle = "Reste stock à recouvrer";
	           double montant = row[1] != null ? Double.parseDouble(String.valueOf(row[1])) : 0.0;
     		  
	           int nombre = ((Number) row[0]).intValue();
	        
	         
	           topVentes = new StatistiqueBoutiqueDto(libelle, montant, nombre);
	       }

	       return topVentes;
		
	}
	
	
	public StatistiqueBoutiqueDto getCommandeVenduAujourdhui() {
		
		LocalDate date = LocalDate.now();
		 String sql ="select count(cv) as nombre, sum(cv.montant_commade) as somme from commande_vente cv \r\n"
		 		+ "where cv.date_paiement = :dateVente and cv.boutique_uuid=:boutique_uuid";
			
			       
		    Query query = entityManager.createNativeQuery(sql);
	        query.setParameter("dateVente", date);
	        query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
	       
	       List<Object[]> results = query.getResultList();
	       StatistiqueBoutiqueDto topVentes = null;

	       for (Object[] row : results) {
	           String libelle = "CMD vendu Aujourd'hui";
	           double montant = row[1] != null ? Double.parseDouble(String.valueOf(row[1])) : 0.0;
	        		  
	           int nombre = ((Number) row[0]).intValue();
	        
	         
	           topVentes = new StatistiqueBoutiqueDto(libelle, montant, nombre);
	       }
	       
	       //topVentes.setLibelle("CMD vendu Aujourd'hui");
	     
	       return topVentes;
		
	}
	
	
public StatistiqueBoutiqueDto getTotaProduitVendu() {
		
		LocalDate date = LocalDate.now();
		 String sql ="select count(cv) as nombre, sum(cv.montant_commade) as somme from commande_vente cv\r\n"
		 		+ "where cv.boutique_uuid=:boutique_uuid";
			
			       
		    Query query = entityManager.createNativeQuery(sql);
		    query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
	       // query.setParameter("dateVente", date);
	       
	       List<Object[]> results = query.getResultList();
	       StatistiqueBoutiqueDto topVente = null;

	       for (Object[] row : results) {
	           String libelle = "Total produit vendu";
	           double montant = row[1] != null ? Double.parseDouble(String.valueOf(row[1])) : 0.0;
	        		  
	           int nombre = ((Number) row[0]).intValue();
	        
	         
	           topVente = new StatistiqueBoutiqueDto(libelle, montant, nombre);
	       }
	       
	       //topVentes.setLibelle("CMD vendu Aujourd'hui");

	       return topVente;
		
	}



public List<StatistiqueBoutiqueDto> getSituationVenteDesMois() {
	
	//LocalDate date = LocalDate.now();
	 String sql =" SELECT TO_CHAR(c.date_paiement, 'TMMonth YYYY') AS mois, SUM(c.montant_commade) AS totalVente  \r\n"
	 		+ "   FROM  commande_vente c where c.boutique_uuid=:boutique_uuid \r\n"
	 		+ "   GROUP BY mois  \r\n"
	 		+ "   ORDER BY mois ASC  ";
		
		       
	    Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
      //  query.setParameter("dateVente", date);
       
       List<Object[]> results = query.getResultList();
       List<StatistiqueBoutiqueDto> topVentes = new ArrayList<StatistiqueBoutiqueDto>();

       for (Object[] row : results) {
          // String libelle = "CMD vendu Aujourd'hui";
           double montant = row[1] != null ? Double.parseDouble(String.valueOf(row[1])) : 0.0;
        		  
           String libelle = (String) row[0];
        
         
           //topVentes = new StatistiqueBoutiqueDto(libelle, montant, nombre);
           topVentes.add(new StatistiqueBoutiqueDto(libelle, montant, 0));
       }
       
       //topVentes.setLibelle("CMD vendu Aujourd'hui");

       return topVentes;
	
}


}
