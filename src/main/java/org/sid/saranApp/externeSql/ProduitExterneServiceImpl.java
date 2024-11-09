package org.sid.saranApp.externeSql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.sid.saranApp.dto.StatistiqueBoutiqueDto;
import org.sid.saranApp.dto.TopVenteDTO;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class ProduitExterneServiceImpl {
	
	@PersistenceContext
    private EntityManager entityManager;
	Logger logger = LoggerFactory.getLogger(ProduitExterneServiceImpl.class);
	
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	
	
	
	public List<TopVenteDTO> topVenteProduit(int limit){
		
		 String sql ="SELECT \r\n"
		 		+ "    a.libelle,\r\n"
		 		+ "    a.description, \r\n"
		 		+ "    p.\"uuid\" ,\r\n"
		 		+ "    SUM(lc.quantite) AS total_vendu,\r\n"
		 		+ "    er.code,\r\n"
		 		+ "    er.etagere,\r\n"
		 		+ "    er.rayon\r\n"
		 		+ "FROM \r\n"
		 		+ "    produit  p\r\n"
		 		+ "JOIN \r\n"
		 		+ "    ligne_commande lc ON p.\"uuid\" = lc.produit_uuid \r\n"
		 		+ "JOIN   \r\n"
		 		+ "   livraison_commande_fournisseur lv on p.livraison_commande_fournisseur_uuid  = lv.\"uuid\" \r\n"
		 		+ "JOIN   \r\n"
		 		+ "    detail_commande_fournisseur dcf on lv.detail_commande_fournisseur_uuid = dcf.\"uuid\" \r\n"
		 		+ "JOIN\r\n"
		 		+ "	article a on a.\"uuid\" = dcf.article_uuid \r\n"
		 		+ "JOIN	\r\n"
		 		+ "	etagere_rayon er on p.emplacement_uuid = er.\"uuid\"	\r\n"
		 		+ "	\r\n"
		 		+ "where p.boutique_uuid =:boutique_uuid	\r\n"
		 		+ "	\r\n"
		 		+ "GROUP BY \r\n"
		 		+ "    p.\"uuid\",a.libelle,a.description ,  er.code,\r\n"
		 		+ "    er.etagere,\r\n"
		 		+ "    er.rayon\r\n"
		 		+ "ORDER BY \r\n"
		 		+ "    total_vendu DESC\r\n"
		 		+ "LIMIT :limit";
		
		       
		Query query = entityManager.createNativeQuery(sql);
		 query.setParameter("boutique_uuid", utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
         query.setParameter("limit", limit);
      

       List<Object[]> results = query.getResultList();
       List<TopVenteDTO> topVentes = new ArrayList<>();

       for (Object[] row : results) {
           String libelle = (String) row[0];
           String description = (String) row[1];
           String uuid = (String) row[2];
           Integer totalVendu = ((Number) row[3]).intValue();
           String code = (String) row[4];
           String etagere = (String) row[5];
           String rayon = (String) row[6];
         
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
	           double montant = Double.parseDouble(String.valueOf(row[1]));
     		  
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
	           double montant = Double.parseDouble(String.valueOf(row[1]));
     		  
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
	           double montant = Double.parseDouble(String.valueOf(row[1]));
	        		  
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
	           double montant = Double.parseDouble(String.valueOf(row[1]));
	        		  
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
           double montant = Double.parseDouble(String.valueOf(row[1]));
        		  
           String libelle = (String) row[0];
        
         
           //topVentes = new StatistiqueBoutiqueDto(libelle, montant, nombre);
           topVentes.add(new StatistiqueBoutiqueDto(libelle, montant, 0));
       }
       
       //topVentes.setLibelle("CMD vendu Aujourd'hui");

       return topVentes;
	
}


}
