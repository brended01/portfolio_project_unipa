package com.slfl.portfolio_project.service;

import com.slfl.portfolio_project.model.Album;
import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.model.backup_picture.PictureCareTaker;
import com.slfl.portfolio_project.model.backup_picture.PictureMemento;
import com.slfl.portfolio_project.model.requests.PictureCreateDTO;
import com.slfl.portfolio_project.model.response_factory.CustomResponse;
import com.slfl.portfolio_project.model.response_factory.ResponseFactory;
import com.slfl.portfolio_project.model.response_factory.image.LoadedImageResponse;
import com.slfl.portfolio_project.model.response_factory.picture.PictureResponseFactory;
import com.slfl.portfolio_project.repository.AlbumRepository;
import com.slfl.portfolio_project.repository.PictureMementoRepository;
import com.slfl.portfolio_project.repository.PictureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PictureMementoRepository pictureMementoRepository;
    private final AlbumRepository albumRepository;
    private final ResponseFactory responseFactory;

    @Autowired
    PictureService(PictureRepository pictureRepository, PictureMementoRepository pictureMementoRepository, AlbumRepository albumRepository) {
        this.pictureRepository = pictureRepository;
        this.pictureMementoRepository = pictureMementoRepository;
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
            Picture specifiedPicture = pictureRepository.findById(pictureId).get();
            PictureCareTaker pictureCareTaker = new PictureCareTaker();
            if (specifiedPicture.getPictureId() == null) {
                return this.responseFactory.createCustomError("404", "Immagine non trovata.");
            }
            //salvataggio backup
            pictureCareTaker.save(specifiedPicture,pictureMementoRepository);
            pictureRepository.save(new Picture(pictureId, pictureCreateDTO.getTitle(), pictureCreateDTO.getDescription(), pictureCreateDTO.getCategory(), pictureCreateDTO.getDate(), specifiedPicture.getAlbum()));
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
            List<Picture> pictures = pictureRepository.findPictureByAlbum_AlbumId(albumId);
            return new LoadedImageResponse("200", "Immagini scaricate con successo.", pictures);
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }

    public CustomResponse restorePicture(Integer pictureId) {
        try {
            Picture specifiedPicture = pictureRepository.findById(pictureId).get();
            if (specifiedPicture.getPictureId() == null) {
                return this.responseFactory.createCustomError("404", "Immagine non trovata.");
            }
            PictureCareTaker pictureCareTaker = new PictureCareTaker();
            Picture restoredPicture = pictureCareTaker.undo(specifiedPicture, pictureMementoRepository);
            pictureRepository.save(new Picture(pictureId, restoredPicture.getTitle(), restoredPicture.getDescription(), restoredPicture.getCategory(), restoredPicture.getShootDate(), restoredPicture.getAlbum(), restoredPicture.getPath()));
            return this.responseFactory.updateSuccessfullyResponse();
        } catch (Exception e) {
            return this.responseFactory.createCustomError("404", e.getMessage());
        }
    }
}
