/**
 *
 */
package org.sid.saranApp.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.EtagereDto;
import org.sid.saranApp.dto.EtagereRayonDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.RayonDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Etagere;
import org.sid.saranApp.model.EtagereRayon;
import org.sid.saranApp.model.Rayon;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.EtagereRayonRepository;
import org.sid.saranApp.repository.EtagereRepository;
import org.sid.saranApp.repository.RayonRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.EtagereRayonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@Service
public class EtagereRayonServiceImpl implements EtagereRayonService {

	@Autowired
	private EtagereRayonRepository etagereRayonRepository;
	@Autowired
	private EtagereRepository etagereRepository;
	@Autowired
	private RayonRepository rayonRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;

	Logger logger = LoggerFactory.getLogger(EtagereRayonServiceImpl.class);

	@Override
	public EtagereDto addEtagere(EtagereDto etagereDto) {
		// TODO Auto-generated method stub
		Etagere etagereStock = new Etagere();
		etagereStock.setCapacite(etagereDto.getCapacite());
		etagereStock.setFull(false);
		etagereStock.setLibelle(etagereDto.getLibelle());
		Etagere etagereStockSave = etagereRepository.save(etagereStock);
		return Mapper.toEtagereStockDto(etagereStockSave);
	}

	@Override
	public EtagereRayonDto addEtagereRayon(EtagereRayonDto etagereRayonDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		EtagereRayon etagereRayon = new EtagereRayon();
		etagereRayon.setBoutique(utilisateur.getBoutique());
		etagereRayon.setEtagere(etagereRayonDto.getLibelle());
		etagereRayon.setRayon(etagereRayonDto.getRayon());
		etagereRayon.setUtilisateur(utilisateur);
		etagereRayon = etagereRayonRepository.save(etagereRayon);
		return Mapper.toEtagereRayonDto(etagereRayon);
	}

	@Override
	public RayonDto addRayon(RayonDto rayonDto) {
		// TODO Auto-generated method stub
		Rayon rayon = new Rayon();
		rayon.setCode(rayonDto.getCode());
		rayon.setLibelle(rayonDto.getLibelle());
		rayon = rayonRepository.save(rayon);
		return Mapper.toRayonDto(rayon);
	}

	private String getCellValueAsString(Cell cell) {

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	@Override
	public EtagereRayonDto getEtagereRayon(String uuid) {
		// TODO Auto-generated method stub
		EtagereRayon etagereRayon = etagereRayonRepository.findById(uuid).orElseThrow(null);
		return Mapper.toEtagereRayonDto(etagereRayon);
	}

	@Override
	public List<String[]> importationEtagere(MultipartFile file) {
		// TODO Auto-generated method stub
		List<String[]> rows = new ArrayList<>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		List<EtagereRayonDto> emplacements = new ArrayList<>();
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
				EtagereRayonDto emplacement = new EtagereRayonDto();
				emplacement.setCode(row.getCell(0).getStringCellValue());
				emplacement.setLibelle(row.getCell(1).getStringCellValue());
				emplacement.setRayon(row.getCell(2).getStringCellValue());
				logger.info("value {}", emplacement.getLibelle() + " " + emplacement.getRayon());

				EtagereRayon etagereRayon = new EtagereRayon();
				etagereRayon.setBoutique(utilisateur.getBoutique());
				etagereRayon.setUtilisateur(utilisateur);
				etagereRayon.setCode(emplacement.getCode());
				etagereRayon.setEtagere(emplacement.getLibelle());
				etagereRayon.setRayon(emplacement.getRayon());
				etagereRayonRepository.save(etagereRayon);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rows;

	}

	@Override
	public List<String[]> importationRayon(MultipartFile file) {
		// TODO Auto-generated method stub
		List<String[]> rows = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				String[] cellValues = new String[row.getPhysicalNumberOfCells()];
				for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
					Cell cell = row.getCell(i);
					cellValues[i] = getCellValueAsString(cell);
				}
				rows.add(cellValues);
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Iterator iterator = rows.iterator(); iterator.hasNext();) {
			String[] strings = (String[]) iterator.next();
			logger.info("value {}", strings);
		}

		return rows;
	}

	@Override
	public List<EtagereDto> listeEtagere() {
		// TODO Auto-generated method stub
		List<Etagere> etageres = etagereRepository.findAll();
		List<EtagereDto> etagereDtos = new ArrayList<EtagereDto>();
		etageres.forEach(etagere -> etagereDtos.add(Mapper.toEtagereStockDto(etagere)));
		return etagereDtos;
	}

	@Override
	public List<EtagereRayonDto> listeEtagereRayon() {
		// TODO Auto-generated method stub
		List<EtagereRayon> etageres = etagereRayonRepository.findAll();
		List<EtagereRayonDto> etagereDtos = new ArrayList<EtagereRayonDto>();
		etageres.forEach(etagere -> etagereDtos.add(Mapper.toEtagereRayonDto(etagere)));
		return etagereDtos;
	}

	@Override
	public List<RayonDto> listeRayons() {
		// TODO Auto-generated method stub
		List<Rayon> rayons = rayonRepository.findAll();
		List<RayonDto> rayonDtos = new ArrayList<RayonDto>();
		rayons.forEach(rayon -> rayonDtos.add(Mapper.toRayonDto(rayon)));
		return rayonDtos;
	}

	@Override
	public EtagereDto updateEtagere(EtagereDto etagereDto, String uuid) {
		// TODO Auto-generated method stub
		Etagere etagereStock = etagereRepository.findById(uuid).orElseThrow(null);
		etagereStock.setCapacite(etagereDto.getCapacite());
		etagereStock.setFull(false);
		etagereStock.setLibelle(etagereDto.getLibelle());
		Etagere etagereStockSave = etagereRepository.save(etagereStock);
		return Mapper.toEtagereStockDto(etagereStockSave);
	}

	@Override
	public EtagereRayonDto updateEtagereRayon(EtagereRayonDto etagereRayonDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		EtagereRayon etagereRayon = etagereRayonRepository.findById(uuid).orElseThrow(null);
		etagereRayon.setBoutique(utilisateur.getBoutique());
		etagereRayon.setEtagere(etagereRayonDto.getLibelle());
		etagereRayon.setRayon(etagereRayonDto.getRayon());
		etagereRayon.setUtilisateur(utilisateur);
		etagereRayon = etagereRayonRepository.save(etagereRayon);
		return Mapper.toEtagereRayonDto(etagereRayon);
	}

	@Override
	public RayonDto updateRayon(RayonDto rayonDto, String uuid) {
		// TODO Auto-generated method stub
		Rayon rayon = rayonRepository.findById(uuid).orElseThrow(null);
		rayon.setCode(rayonDto.getCode());
		rayon.setLibelle(rayonDto.getLibelle());
		rayon = rayonRepository.save(rayon);
		return Mapper.toRayonDto(rayon);
	}

	@Override
	public PageDataDto<EtagereRayonDto> listeEtagereRayons(int page, int size, String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<EtagereRayonDto> pageDataDto = new PageDataDto<EtagereRayonDto>();
		List<EtagereRayonDto> etagereRayonDtos = new ArrayList<EtagereRayonDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<EtagereRayon> etagereRayons = null;
		
		if(key != null) {
			etagereRayons = etagereRayonRepository.listeEtagereRayonByLibelle(key, key, key, pageable);
			etagereRayons.forEach(etagere -> etagereRayonDtos.add(Mapper.toEtagereRayonDto(etagere)));
		}
		
		if(key == null ) {
			
			etagereRayons = etagereRayonRepository.listeEtagereRayon(pageable);
			etagereRayons.forEach(etagere -> etagereRayonDtos.add(Mapper.toEtagereRayonDto(etagere)));
		}
		
		
		pageDataDto.setData(etagereRayonDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(etagereRayons.getTotalElements());
		pageDataDto.getPage().setTotalPages(etagereRayons.getTotalPages());
		return pageDataDto;
	}
}
