package com.cunoraz.eksiseyler.di.search;

import com.cunoraz.eksiseyler.di.PerActivity;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.ui.search.SearchActivity;

import dagger.Component;

/**
 * Created by cuneytcarikci on 08/05/2017.
 */

@PerActivity
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {

    void inject(SearchActivity searchActivity);

}
