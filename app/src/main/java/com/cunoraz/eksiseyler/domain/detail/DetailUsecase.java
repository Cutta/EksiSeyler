package com.cunoraz.eksiseyler.domain.detail;

import com.cunoraz.eksiseyler.model.rest.entity.Post;

/**
 * Created by andanicalik on 03/05/17.
 */

public interface DetailUsecase {

    boolean isSavingModeActive();

    void storeSavingModeStatus(boolean willDisplayImages);

    boolean isOneOfFavouritePosts(Post post);

    void addToFavouritePosts(Post post);

    void removeFromFavouritePosts(Post post);

}