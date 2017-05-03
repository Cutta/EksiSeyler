package com.cunoraz.eksiseyler.di.content;

import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.ui.content.ContentFragment;
import com.cunoraz.eksiseyler.di.PerFragment;

import dagger.Component;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */


@PerFragment
@Component(modules = ContentModule.class, dependencies = AppComponent.class)
public interface ContentComponent {

    void inject(ContentFragment contentFragment);
}
