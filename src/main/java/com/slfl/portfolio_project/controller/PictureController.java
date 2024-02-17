package com.slfl.portfolio_project.controller;

import com.slfl.portfolio_project.misc.errors.StorageFileNotFoundException;
import com.slfl.portfolio_project.model.requests.PictureCreateDTO;
import com.slfl.portfolio_project.model.response_factory.CustomResponse;
import com.slfl.portfolio_project.service.PictureService;
import com.slfl.portfolio_project.service.StorageService;
import com.slfl.portfolio_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/pictures")
@CrossOrigin("*")
public class PictureController {

    @Autowired
    private PictureService service;

    @GetMapping("/picture/read/{pictureId}")
    public CustomResponse readPicture(@PathVariable Integer pictureId) {
        return service.getSinglePicture(pictureId);
    }
}
