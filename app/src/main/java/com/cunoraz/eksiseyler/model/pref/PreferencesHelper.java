package com.cunoraz.eksiseyler.model.pref;

import com.cunoraz.eksiseyler.model.rest.entity.Post;

import java.util.List;

/**
 * Created by andanicalik on 03/05/17.
 */

public interface PreferencesHelper {

    List<Post> getFavouritePosts();

    void addToFavouritePosts(Post post);

    void removeFromFavouritePosts(Post post);

    boolean isOneOfFavouritePosts(Post post);

    void storeSavingModeStatus(boolean isActive);

    boolean isSavingModeActive();

}