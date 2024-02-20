package com.slfl.portfolio_project.model.backup_picture;

import com.slfl.portfolio_project.model.Picture;
import com.slfl.portfolio_project.repository.PictureMementoRepository;

public class PictureCareTaker {
    private PictureMemento postMemento;
    private PictureMementoRepository pictureMementoRepository;

    // Metodo per salvare lo stato del post
    public void save(Picture post) {

        this.postMemento = post.saveToMemento();
        postMemento = pictureMementoRepository.save(postMemento);

    }

    // Metodo per ripristinare lo stato del post all'ultimo salvataggio
    public Picture undo(Picture post) {
        postMemento = pictureMementoRepository.findById(post.getPictureId()).get();
        if (postMemento.getSavedPictureId() != null) {
            post.restoreFromMemento(postMemento);
        }
        return post;
    }
}
