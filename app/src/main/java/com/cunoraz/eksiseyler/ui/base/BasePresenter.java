package com.cunoraz.eksiseyler.ui.base;


/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class BasePresenter<V extends BaseView> {

    protected V view;

    public V getView() {
        return view;
    }
}
