package com.slfl.portfolio_project.model.backup_picture;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.slfl.portfolio_project.model.Album;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "pictures_memento")
@EnableAutoConfiguration
public class PictureMemento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "picture_id")
    private Integer savedPictureId;

    @Column
    private String savedTitle;

    @Column
    private String savedDescription;

    @Column
    private String savedPath;

    @Column
    private String savedCategory;

    @Column
    private Date savedShootDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album savedAlbum;

    public PictureMemento(Integer pictureId, String title, String description, String category, Date date, Album album, String path) {
        this.savedPictureId = pictureId;
        this.savedTitle = title;
        this.savedDescription = description;
        this.savedCategory = category;
        this.savedShootDate = date;
        this.savedAlbum = album;
        this.savedPath = path;
    }
}
