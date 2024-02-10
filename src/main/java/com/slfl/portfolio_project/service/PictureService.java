package com.slfl.portfolio_project.service;

import com.slfl.portfolio_project.model.Album;
import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.model.requests.PictureCreateDTO;
import com.slfl.portfolio_project.model.response_factory.CustomResponse;
import com.slfl.portfolio_project.model.response_factory.ResponseFactory;
import com.slfl.portfolio_project.model.response_factory.picture.PictureResponseFactory;
import com.slfl.portfolio_project.repository.AlbumRepository;
import com.slfl.portfolio_project.repository.PictureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PictureService {

    private final PictureRepository pictureRepository;
    private final AlbumRepository albumRepository;
    private final ResponseFactory responseFactory;

    @Autowired
    PictureService(PictureRepository pictureRepository, AlbumRepository albumRepository) {
        this.pictureRepository = pictureRepository;
        this.albumRepository = albumRepository;
        this.responseFactory = new PictureResponseFactory();
    }

    // Check how to upload actual picture
    public CustomResponse createPicture(PictureCreateDTO pictureCreateDTO, Integer albumId) {
        try {
            Optional<Picture> retrievedPicture = pictureRepository.findPictureByTitle(pictureCreateDTO.getTitle());
            if (retrievedPicture.isPresent()) {
                return this.responseFactory.createCustomError("404", "Immagine già presente.");
            }

            Optional<Album> matchedAlbum = albumRepository.findById(albumId);

            if(matchedAlbum.isEmpty()) {
                return this.responseFactory.createCustomError("404", "Album non trovato.");
            }

            pictureRepository.save(new Picture(
                    pictureCreateDTO.getTitle(),
                    pictureCreateDTO.getDescription(),
                    pictureCreateDTO.getCategory(),
                    pictureCreateDTO.getDate(),
                    matchedAlbum.get()
            ));

            return this.responseFactory.createSuccessfullyResponse();
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }

    public CustomResponse getSinglePicture(Integer pictureId) {
        try {
            Optional<Picture> specifiedPicture = pictureRepository.findById(pictureId);
            if(specifiedPicture.isEmpty()) {
                return this.responseFactory.createCustomError("404", "Immagine non trovata.");
            }
            return this.responseFactory.createDataResponse(specifiedPicture.get());
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }

    // Update picture info
    public CustomResponse updatePicture(Integer pictureId, PictureCreateDTO pictureCreateDTO) {
        try {
            Optional<Picture> specifiedPicture = pictureRepository.findById(pictureId);
            if (specifiedPicture.isEmpty()) {
                return this.responseFactory.createCustomError("404", "Immagine non trovata.");
            }

            pictureRepository.save(new Picture(pictureId, pictureCreateDTO.getTitle(), pictureCreateDTO.getDescription(), pictureCreateDTO.getCategory(), pictureCreateDTO.getDate(), specifiedPicture.get().getAlbum()));
            return this.responseFactory.updateSuccessfullyResponse();
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }

    public CustomResponse deletePicture(Integer pictureId) {
        try {
            Optional<Picture> specifiedPicture = pictureRepository.findById(pictureId);
            if (specifiedPicture.isEmpty()) {
                return this.responseFactory.createCustomError("404", "Immagine non trovata.");
            }
            pictureRepository.delete(specifiedPicture.get());
            return this.responseFactory.deleteSuccessfullyResponse();
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }
}