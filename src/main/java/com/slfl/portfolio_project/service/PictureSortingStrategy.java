package com.slfl.portfolio_project.service;

import com.slfl.portfolio_project.model.Picture;

import java.util.List;

public interface PictureSortingStrategy {
    List<Picture> sortPhotos(List<Picture> photos);
}