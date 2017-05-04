package com.cunoraz.eksiseyler.ui.favourites;

import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseView;
import com.cunoraz.eksiseyler.ui.content.PostAdapter;

import java.util.ArrayList;

/**
 * Created by cuneytcarikci on 04/05/2017.
 */

public class FavouritiesContract {

    public interface View extends BaseView {

        void showSavingModeActiveDialog();

        void showSavingModeNotActiveDialog();

        void updateSavingModeMenuItem(boolean isActive);

        void loadPosts(ArrayList<Post> posts);

        void openDetail(PostAdapter.PostViewHolder viewHolder, Post post);

    }

    interface Presenter {

        void onViewReady();

        void onResume();

        void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post);

        boolean isSavingModeActive();

        void onClickSavingModeMenuItem();

    }

}
