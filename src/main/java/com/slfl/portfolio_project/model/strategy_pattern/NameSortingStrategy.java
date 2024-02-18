package com.slfl.portfolio_project.model.strategy_pattern;

import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.service.PictureSortingStrategy;

import java.util.Comparator;
import java.util.List;

public class NameSortingStrategy implements PictureSortingStrategy {
    public List<Picture> sortPhotos(List<Picture> photos) {
        photos.sort(Comparator.comparing(Picture::getTitle));
        return photos;
    }
}