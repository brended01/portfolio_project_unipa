package com.slfl.portfolio_project.model.strategy_pattern;

import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.service.PictureSortingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PictureSortingContext {

    private PictureSortingStrategy pictureSortingStrategy;

    @Autowired
    public PictureSortingContext(@Qualifier("dateSortingStrategy") PictureSortingStrategy pictureSortingStrategy) {
        this.pictureSortingStrategy = pictureSortingStrategy;
    }

    public void setPictureSortingStrategy(PictureSortingStrategy pictureSortingStrategy) {
        this.pictureSortingStrategy = pictureSortingStrategy;
    }

    public List<Picture> sortPictures(List<Picture> photos) {
        return pictureSortingStrategy.sortPhotos(photos);
    }
}