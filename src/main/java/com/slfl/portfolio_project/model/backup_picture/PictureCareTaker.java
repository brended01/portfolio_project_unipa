package com.slfl.portfolio_project.model.backup_picture;

import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.repository.PictureMementoRepository;

public class PictureCareTaker {
    private PictureMemento postMemento;

    // Metodo per salvare lo stato del post
    public void save(Picture post, PictureMementoRepository pictureMementoRepository) {
        this.postMemento = post.saveToMemento();
        pictureMementoRepository.save(postMemento);

    }

    // Metodo per ripristinare lo stato del post all'ultimo salvataggio
    public Picture undo(Picture post, PictureMementoRepository pictureMementoRepository) {
        postMemento = pictureMementoRepository.findById(post.getPictureId()).get();
        if (postMemento.getSavedPictureId() != null) {
            post.restoreFromMemento(postMemento);
        }
        return post;
    }
}
