package com.slfl.portfolio_project.repository;

import com.slfl.portfolio_project.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Optional<Picture> findPictureByTitle(String title);

    List<Picture> findPictureByAlbum_AlbumId(Integer album_albumId);

    @Query("SELECT p FROM Picture p ORDER BY SIZE(p.usersWhoLikes) DESC")
    List<Picture> findMostLikedPictures();
}
