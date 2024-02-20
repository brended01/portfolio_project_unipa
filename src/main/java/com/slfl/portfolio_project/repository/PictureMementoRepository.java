package com.slfl.portfolio_project.repository;

import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.model.backup_picture.PictureMemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureMementoRepository extends JpaRepository<PictureMemento, Integer> {
    Optional<Picture> findPictureBySavedPictureId(Integer picture_id);


}
