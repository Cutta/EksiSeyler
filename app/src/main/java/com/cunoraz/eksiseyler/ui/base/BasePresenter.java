package com.cunoraz.eksiseyler.ui.base;


import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class BasePresenter<V extends BaseView> {

    @Inject
    protected V view;

    public V getView() {
        return view;
    }

}