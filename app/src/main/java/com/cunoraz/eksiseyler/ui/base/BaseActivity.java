package com.cunoraz.eksiseyler.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cunoraz.eksiseyler.app.EksiSeylerApp;
import com.cunoraz.eksiseyler.di.app.AppComponent;
import com.cunoraz.eksiseyler.util.Utils;

import butterknife.ButterKnife;

/**
 * Created by andanicalik on 03/05/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState);
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState) {
        resolveDaggerDependency();
    }

    protected AppComponent getApplicationComponent() {
        return ((EksiSeylerApp) (getApplication())).getAppComponent();
    }

    protected void resolveDaggerDependency() {

    }
    public boolean isConnected(){
        return Utils.isConnected(BaseActivity.this);
    }

    @LayoutRes
    protected abstract int getLayoutId();

}