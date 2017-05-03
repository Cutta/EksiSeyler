package com.cunoraz.eksiseyler.ui.content;

import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ContentContract {

    public interface View extends BaseView {

        void loadPosts(ArrayList<Post> posts);

        void openDetail(PostAdapter.PostViewHolder viewHolder, Post post);

    }

    interface Presenter {

        void onViewReady();

        void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post);

    }

}
