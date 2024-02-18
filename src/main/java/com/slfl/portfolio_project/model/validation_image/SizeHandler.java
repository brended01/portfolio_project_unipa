package com.slfl.portfolio_project.model.validation_image;

import com.slfl.portfolio_project.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

public class SizeHandler extends BaseImageHandler {

    @Override
    protected boolean doHandle(ImageFile image) {
        int maxSize = 5 * 1024 * 1024; // 5MB
        long imageSize =  image.getSize();
        if (imageSize <= maxSize) {
            return true; // Valid size
        }
        return false; // Not valid size
    }
}