package com.slfl.portfolio_project.repository;

import com.slfl.portfolio_project.model.ImageFile;
import com.slfl.portfolio_project.model.Picture;
import org.springframework.web.multipart.MultipartFile;

public interface ImageHandler {
    void setNextHandler(ImageHandler nextHandler);
    boolean handleImage(ImageFile file);
}
