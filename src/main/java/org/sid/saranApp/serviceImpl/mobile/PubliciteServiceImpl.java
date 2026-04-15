package org.sid.saranApp.serviceImpl.mobile;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PageDataMobileDto;
import org.sid.saranApp.dto.mobile.MountedDto;
import org.sid.saranApp.dto.mobile.PubliciteDto;
import org.sid.saranApp.dto.mobile.PubliciteOperationDto;
import org.sid.saranApp.dto.mobile.PubliciteParamDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.PubliciteOperationRepository;
import org.sid.saranApp.repository.PubliciteParametreRepository;
import org.sid.saranApp.repository.PubliciteRepository;
import org.sid.saranApp.service.mobile.PubliciteService;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PubliciteServiceImpl implements PubliciteService {

    @Autowired
    private PubliciteOperationRepository publiciteOperationRepository;
    @Autowired
    private PubliciteParametreRepository publiciteParametreRepository;
    @Autowired
    private PubliciteRepository publiciteRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private UtilisateurServiceImpl utilisateurService;
    @Override
    public PubliciteDto add(PubliciteDto publiciteDto) {
        Publicite publicite = new Publicite();
        Categorie categorie = categorieRepository.findById(publiciteDto.getUuidCategorie()).orElseThrow(null);
        publicite.setDescription(publiciteDto.getDescription());
        publicite.setCategorie(categorie);
        publicite.setPrice(publiciteDto.getPrice());
        publicite.setLibelle(publiciteDto.getLibelle());
        publicite.setQuantite(publiciteDto.getQuantite());

        Publicite publiciteSave = publiciteRepository.save(publicite);
        List<PubliciteOperationDto> publiciteOperationDtos = publiciteDto.getPubliciteOperationDtos();
        publiciteOperationDtos.forEach(p -> {
            p.setUuidPublicite(publiciteSave.getUuid());
            addPublishOperation(p);
        });

        publiciteDto.getPubliciteParamDtos().forEach(p -> {
            p.setUuidPublicite(publiciteSave.getUuid());
            addPublishParametre(p);
        });
        return null;
    }

    @Override
    public PageDataMobileDto<PubliciteDto> findAllPublishByCategory(String uuidCategorie, String name, double minPrice, double maxPrice, int page, int size) {
        String uuidBoutique = utilisateurService.getCurentUtilisateur().getBoutique().getUuid();
        PageDataMobileDto<PubliciteDto> pageDataDto = new PageDataMobileDto<PubliciteDto>();
        List<PubliciteDto> publiciteDtos = new ArrayList<PubliciteDto>();
        Pageable pageable = PageRequest.of(page, size);
        MountedDto mountedDto = new MountedDto();
        Page<Publicite> articles = null;
        articles = publiciteRepository.findAllPublishByCategory(name,uuidCategorie,minPrice,maxPrice,uuidBoutique,pageable);
        mountedDto = Mapper.toMountedDto(utilisateurService.getCurentUtilisateur(),categorieRepository.findAll());
        pageDataDto.setMountedDto(mountedDto);
        pageDataDto.setData(publiciteDtos);
        pageDataDto.getPage().setPageNumber(page);
        pageDataDto.getPage().setSize(size);
        pageDataDto.getPage().setTotalElements(articles.getTotalElements());
        pageDataDto.getPage().setTotalPages(articles.getTotalPages());
        return pageDataDto;
    }

    @Override
    public PubliciteDto update(PubliciteDto publiciteDto, String uuid) {
        return null;
    }

    @Override
    public PubliciteDto get(String uuidProduct) {
        Publicite publicite = publiciteRepository.findById(uuidProduct).orElseThrow(null);
        return Mapper.toPublicite(publicite);
    }

    public PubliciteOperationDto addPublishOperation(PubliciteOperationDto publiciteOperationDto){
        Optional<PubliciteOperation> publiciteOperationOptional = publiciteOperationRepository.findById(publiciteOperationDto.getUuid());
        PubliciteOperation publiciteOperation = null;
        if(publiciteOperationOptional.isPresent()){
            publiciteOperation = publiciteOperationOptional.get();
            publiciteOperation.setPublicite(publiciteOperation.getPublicite());
            publiciteOperation.setInterested(publiciteOperationDto.isInterested());
            publiciteOperation.setFavorite(publiciteOperationDto.isFavorite());
            publiciteOperation.setLike(publiciteOperationDto.isLike());
            publiciteOperation.setEnumStatutOnlineOrder(publiciteOperationDto.getEnumStatutOnlineOrder());

            publiciteOperation = publiciteOperationRepository.save(publiciteOperation);

            if(!publiciteOperation.getUuid().isEmpty()){
                publiciteOperationDto.setCode(200);
                publiciteOperationDto.setLoad(true);
                publiciteOperationDto.setMessage("updated");

            }else{
                publiciteOperationDto.setCode(400);
                publiciteOperationDto.setLoad(true);
                publiciteOperationDto.setMessage("not updated");
            }
        }else{
            publiciteOperation = new PubliciteOperation();
            Publicite publicite = publiciteRepository.findById(publiciteOperationDto.getUuidPublicite()).orElseThrow(null);
            publiciteOperation.setPublicite(publicite);
            publiciteOperation.setInterested(publiciteOperationDto.isInterested());
            publiciteOperation.setFavorite(publiciteOperationDto.isFavorite());
            publiciteOperation.setLike(publiciteOperationDto.isLike());
            publiciteOperation.setEnumStatutOnlineOrder(publiciteOperationDto.getEnumStatutOnlineOrder());

            publiciteOperation = publiciteOperationRepository.save(publiciteOperation);

            if(!publiciteOperation.getUuid().isEmpty()){
                publiciteOperationDto.setCode(200);
                publiciteOperationDto.setLoad(true);
                publiciteOperationDto.setMessage("updated");

            }else{
                publiciteOperationDto.setCode(400);
                publiciteOperationDto.setLoad(false);
                publiciteOperationDto.setMessage("not updated");
            }
        }
        return Mapper.toPubliciteOperation(publiciteOperation);
    }

    public PubliciteParamDto addPublishParametre(PubliciteParamDto publiciteParamDto){

        Optional<PubliciteParametre> publiciteParametreOptional = publiciteParametreRepository.findById(publiciteParamDto.getUuid());
        PubliciteParametre publiciteParametre = null;

        if(publiciteParametreOptional.isPresent()){
            publiciteParametre = publiciteParametreOptional.get();
            publiciteParametre.setLibelle(publiciteParamDto.getLibelle());
            publiciteParametre.setValue(publiciteParamDto.getValue());
            publiciteParametre.setPublicite(publiciteParametre.getPublicite());

            publiciteParametre = publiciteParametreRepository.save(publiciteParametre);

            if(!publiciteParametre.getUuid().isEmpty()){
                publiciteParamDto.setCode(200);
                publiciteParamDto.setLoad(true);
                publiciteParamDto.setMessage("updated");
            }else{
                publiciteParamDto.setCode(400);
                publiciteParamDto.setLoad(false);
                publiciteParamDto.setMessage("not updated");
            }
        }else{
            publiciteParametre = new PubliciteParametre();
            Publicite publicite = publiciteRepository.findById(publiciteParamDto.getUuidPublicite()).orElseThrow(null);
            publiciteParametre.setPublicite(publicite);
            publiciteParametre.setValue(publiciteParamDto.getValue());
            publiciteParametre.setLibelle(publiciteParamDto.getLibelle());
            publiciteParametre = publiciteParametreRepository.save(publiciteParametre);
        }

        return Mapper.toPubliciteParam(publiciteParametre);


    }
}
