package com.cunoraz.eksiseyler.domain.favourites;

import com.cunoraz.eksiseyler.model.rest.entity.Post;

import java.util.List;


/**
 * Created by cuneytcarikci on 04/05/2017.
 */

public interface FavouritesUsecase {

    boolean isSavingModeActive();

    void storeSavingModeStatus(boolean willDisplayImages);

    List<Post> getPostListFromSharedPreference();
}
