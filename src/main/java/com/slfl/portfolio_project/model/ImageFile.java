package com.slfl.portfolio_project.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageFile implements MultipartFile {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public long getSize() {
        long fileSizeInBytes = this.getSize();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }

    public String getFormat() {
        String fileName = this.getOriginalFilename();
        String[] fileNameParts = Objects.requireNonNull(fileName).split("\\.");
        String fileExtension = fileNameParts[fileNameParts.length - 1];

        return fileExtension;
    }
}
