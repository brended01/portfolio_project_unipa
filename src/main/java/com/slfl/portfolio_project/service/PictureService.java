package com.slfl.portfolio_project.service;

import com.slfl.portfolio_project.model.Album;
import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.model.requests.PictureCreateDTO;
import com.slfl.portfolio_project.model.response_factory.CustomResponse;
import com.slfl.portfolio_project.model.response_factory.ResponseFactory;
import com.slfl.portfolio_project.model.response_factory.image.LoadedImageResponse;
import com.slfl.portfolio_project.model.response_factory.picture.PictureResponseFactory;
import com.slfl.portfolio_project.repository.AlbumRepository;
import com.slfl.portfolio_project.repository.PictureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class PictureService {

    private final PictureRepository pictureRepository;
    private final AlbumRepository albumRepository;
    private final ImageFileService imageFileService;
    private final ResponseFactory responseFactory;

    @Autowired
    PictureService(PictureRepository pictureRepository, AlbumRepository albumRepository, ImageFileService imageFileService) {
        this.pictureRepository = pictureRepository;
        this.albumRepository = albumRepository;
        this.imageFileService = imageFileService;
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

            if (matchedAlbum.isEmpty()) {
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
            if (specifiedPicture.isEmpty()) {
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

    public void updateFilePicture(Integer pictureId, String path) throws Exception {
        Optional<Picture> specifiedPicture = pictureRepository.findById(pictureId);
        if (specifiedPicture.isEmpty()) {
            throw new Exception("Immagine non trovata");
        }
        Picture retrievedPicture = specifiedPicture.get();
        pictureRepository.save(new Picture(pictureId, retrievedPicture.getTitle(), retrievedPicture.getDescription(), retrievedPicture.getCategory(), retrievedPicture.getShootDate(), retrievedPicture.getAlbum(), path));
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

    public CustomResponse loadFileImageByAlbum(Integer albumId) {
        try {
            Optional<Album> foundAlbum = albumRepository.findById(albumId);
            if (foundAlbum.isEmpty()) {
                return this.responseFactory.createCustomError("404", "Album non trovato in loadFileImageByAlbum");
            }
            Stream<Path> images = imageFileService.loadImagesOfAlbum(foundAlbum.get());
            return new LoadedImageResponse("200", "Immagini scaricate con successo.", images);
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }
}
