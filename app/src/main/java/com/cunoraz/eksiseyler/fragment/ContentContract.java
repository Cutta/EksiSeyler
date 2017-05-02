package com.cunoraz.eksiseyler.fragment;

import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.ui.base.BaseView;

import java.util.ArrayList;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ContentContract {

    public interface View extends BaseView {
        void loadPosts(ArrayList<Post> posts);

    }

    interface Presenter {
        void onViewReady();

    }
}
