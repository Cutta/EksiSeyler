package com.cunoraz.eksiseyler.di.main.content;

import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.fragment.ContentFragment;
import com.cunoraz.eksiseyler.fragment.PerFragment;

import dagger.Component;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */


@PerFragment
@Component(modules = ContentModule.class, dependencies = AppComponent.class)
public interface ContentComponent {

    void inject(ContentFragment contentFragment);
}
