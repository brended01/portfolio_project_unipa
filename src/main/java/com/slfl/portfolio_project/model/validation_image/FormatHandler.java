package com.slfl.portfolio_project.model.validation_image;

import com.slfl.portfolio_project.model.ImageFile;
import com.slfl.portfolio_project.model.Picture;

public class FormatHandler extends BaseImageHandler {
    @Override
    protected boolean doHandle(ImageFile file) {
        String[] validFormats = {"jpg", "jpeg", "png"};
        String format = file.getFormat().toLowerCase();
        for (String validFormat : validFormats) {
            if (format.equals(validFormat)) {
                return true; // Valid format
            }
        }
        return false; // Not valid format
    }
}