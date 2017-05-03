package com.cunoraz.eksiseyler.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.di.main.DaggerMainComponent;
import com.cunoraz.eksiseyler.di.main.MainModule;
import com.cunoraz.eksiseyler.model.rest.entity.Channel;
import com.cunoraz.eksiseyler.model.rest.entity.Tags;
import com.cunoraz.eksiseyler.ui.base.BaseActivity;
import com.cunoraz.eksiseyler.ui.content.ContentFragment;
import com.cunoraz.eksiseyler.ui.detail.DetailActivity;
import com.cunoraz.eksiseyler.ui.favourites.BookMarkListActivity;
import com.cunoraz.eksiseyler.util.DialogBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.spinner_nav)
    Spinner spinner;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    MainPresenter mPresenter;

    private Uri data;
    private Menu mMenu;

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        getExtras();
        setupToolbar();
        setupViewPager();
        setupSpinner();
        mPresenter.onViewReady();
    }


    //<editor-fold desc="SetUpUi">
    private void setupSpinner() {

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("BİLİM");
        categories.add("EDEBİYAT");
        categories.add("EĞLENCE");
        categories.add("HABER");
        categories.add("İLİŞKİLER");
        categories.add("KÜLTÜR");
        categories.add("MAGAZİN");
        categories.add("MODA");
        categories.add("MÜZİK");
        categories.add("OTOMOTİV");
        categories.add("OYUN");
        categories.add("SAĞLIK");
        categories.add("SANAT");
        categories.add("SİNEMA");
        categories.add("SİYASET");
        categories.add("SPOR");
        categories.add("TARİH");
        categories.add("TEKNOLOJİ");
        categories.add("YAŞAM");
        categories.add("YEME İÇME");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewPager.setCurrentItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.BILIM, "BİLİM")), "BİLİM");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.EDEBIYAT, "EDEBİYAT")), "EDEBİYAT");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.EGLENCE, "EĞLENCE")), "EĞLENCE");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.HABER, "HABER")), "HABER");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.ILISKILER, "İLİŞKİLER")), "İLİŞKİLER");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.KULTUR, "KÜLTÜR")), "KÜLTÜR");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.MAGAZIN, "MAGAZİN")), "MAGAZİN");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.MODA, "MODA")), "MODA");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.MOTOSIKLET,"MOTOSİKLET")),"MOTOSİKLET");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.MUZIK, "MÜZİK")), "MÜZİK");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.OTOMOTIV, "OTOMOTİV")), "OTOMOTİV");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.OYUN, "OYUN")), "OYUN");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.PROGRAMLAMA,"PROGRAMLAMA")),"PROGRAMLAMA");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.SAGLIK, "SAĞLIK")), "SAĞLIK");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.SANAT, "SANAT")), "SANAT");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.SINEMA, "SİNEMA")), "SİNEMA");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.SIYASET, "SİYASET")), "SİYASET");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SPOILER,"SPOILER")),"SPOILER");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.SPOR, "SPOR")), "SPOR");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.TARIH, "TARİH")), "TARİH");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.TEKNOLOJI, "TEKNOLOJİ")), "TEKNOLOJİ");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.YASAM, "YAŞAM")), "YAŞAM");
        adapter.addFrag(ContentFragment.newInstance(new Channel(Tags.YEME_ICME, "YEME İÇME")), "YEME İÇME");

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                spinner.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        if (data != null)
            mPresenter.handleDeepLink();
    }
    //</editor-fold>

    private void getExtras() {
        Intent intent = getIntent();
        data = intent.getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerMainComponent.builder()
                .appComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        this.mMenu = menu;
        updateSavingModeMenuItem(mPresenter.isSavingModeActive());

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMenu != null)
            updateSavingModeMenuItem(mPresenter.isSavingModeActive());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_menu_show_image:
                mPresenter.onClickSavingModeMenuItem();
                return true;
            case R.id.ic_menu_show_bookmark_list://// TODO: 03/05/2017
                startActivity(new Intent(MainActivity.this, BookMarkListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showSavingModeActiveDialog() {
        DialogBuilder.infoDialog(MainActivity.this,
                R.string.dialog_title_saving_mode_active,
                R.string.dialog_message_saving_mode_active)
                .show();
    }

    @Override
    public void showSavingModeNotActiveDialog() {
        DialogBuilder.infoDialog(MainActivity.this,
                R.string.dialog_title_saving_mode_passive,
                R.string.dialog_message_saving_mode_passive)
                .show();
    }

    @Override
    public void updateSavingModeMenuItem(boolean isActive) {
        if (isActive)
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_photo_gray_24dp));
        else
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_photo_white_24dp));

    }

    @Override
    public void openDetailFromDeepLink() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("extra_url", data.toString());
                startActivity(intent);
            }
        }, 50);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String name) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(name);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
