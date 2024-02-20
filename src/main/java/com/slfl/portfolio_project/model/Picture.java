package com.slfl.portfolio_project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slfl.portfolio_project.model.backup_picture.PictureMemento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "pictures")
@EnableAutoConfiguration
@JsonIgnoreProperties("album")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "picture_id")
    private Integer pictureId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String path;

    @Column
    private String category;

    @Column
    private Date shootDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(mappedBy = "likedPictures")
    private List<User> usersWhoLikes;

    public Picture(String title, String description, String category, Date date, Album album) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.shootDate = date;
        this.album = album;
    }

    public Picture(Integer pictureId, String title, String description, String category, Date date, Album album) {
        this.pictureId = pictureId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.shootDate = date;
        this.album = album;
    }

    public Picture(Integer pictureId, String title, String description, String category, Album album) {
        this.pictureId = pictureId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.shootDate = new Date();
        this.album = album;
    }

    public Picture(Integer pictureId, String title, String description, String category, Date date, Album album, String path) {
        this.pictureId = pictureId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.shootDate = date;
        this.album = album;
        this.path = path;
    }

    public PictureMemento saveToMemento() {
        return new PictureMemento(pictureId,title,description,category,shootDate,album,path);
    }

    // Metodo per ripristinare lo stato da un Memento
    public void restoreFromMemento(PictureMemento memento) {
        this.pictureId = memento.getSavedPictureId();
        this.title = memento.getSavedTitle();
        this.description = memento.getSavedDescription();
        this.category = memento.getSavedCategory();
        this.shootDate = memento.getSavedShootDate();
        this.album = memento.getSavedAlbum();
        this.path = memento.getSavedPath();
    }
}
