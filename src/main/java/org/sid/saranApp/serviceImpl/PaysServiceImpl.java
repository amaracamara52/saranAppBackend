package org.sid.saranApp.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sid.saranApp.dto.PaysDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaysDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.Pays;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.PaysRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.PaysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaysServiceImpl implements PaysService {
	
	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(PaysServiceImpl.class);
	 
	@Override
	public PaysDto addPays(PaysDto paysDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Pays pays = new Pays();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		pays.setLibelle(paysDto.getLibelle());
	//	pays.setBoutique(utilisateur.getBoutique());
		pays.setUtilisateur(utilisateur);
		Pays paysSave = paysRepository.save(pays);
		return Mapper.toPaysDto(paysSave);
	}

	@Override
	public PaysDto upadtePays(PaysDto paysDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Pays pays = paysRepository.findById(uuid).orElseThrow(null);
		pays.setLibelle(paysDto.getLibelle());
		//pays.setBoutique(utilisateur.getBoutique());
		pays.setUtilisateur(utilisateur);
		Pays paysSave = paysRepository.save(pays);
		return Mapper.toPaysDto(paysSave);
	}

	@Override
	public List<PaysDto> findAll() {
		// TODO Auto-generated method stub
		List<Pays> pays = paysRepository.findAll();
		List<PaysDto> paysDtos = new ArrayList<PaysDto>();
		pays.forEach(paysValue -> paysDtos.add(Mapper.toPaysDto(paysValue)));
		return paysDtos;
	}

	@Override
	public PaysDto getPays(String uuid) {
		// TODO Auto-generated method stub
		Pays pays = paysRepository.findById(uuid).orElseThrow(null);
		return Mapper.toPaysDto(pays);
	}

	@Override
	public void deletePays(String uuid) {
		// TODO Auto-generated method stub
		
		paysRepository.deleteById(uuid);
		
	}

	@Override
	public PageDataDto<PaysDto> listePays(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void importationPays(MultipartFile file) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		

		List<PaysDto> paysDtos = new ArrayList<>();
		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					// skip header row
					continue;
				}
				PaysDto dto = new PaysDto();
				
				logger.info("value {}", row.getCell(0).getStringCellValue());
				
				String libelle = row.getCell(0).getStringCellValue();
				String capital = row.getCell(1).getStringCellValue();
				String monnaie = row.getCell(2).getStringCellValue();
				dto.setLibelle(libelle);
				dto.setCapitale(capital);
				dto.setMonnaie(monnaie);
			
				//PaysDto.setQuantiteDansCarton((int) row.getCell(2).getNumericCellValue());

				

				Pays pays = new Pays();

				pays.setMonnaie(dto.getMonnaie());
				pays.setUtilisateur(utilisateur);
				pays.setCapitale(dto.getCapitale());
				pays.setLibelle(dto.getLibelle());
				pays.setNombreVille(0);
				paysRepository.save(pays);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
