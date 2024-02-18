package com.slfl.portfolio_project.model.validation_image;

import com.slfl.portfolio_project.model.ImageFile;
import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.repository.ImageHandler;
import org.springframework.web.multipart.MultipartFile;

abstract class BaseImageHandler implements ImageHandler {
    private ImageHandler nextHandler;

    @Override
    public void setNextHandler(ImageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean handleImage(ImageFile file) {
        if (nextHandler != null) {
           return nextHandler.handleImage(file);
        }
        return true;
    }

    protected abstract boolean doHandle(ImageFile file);

}