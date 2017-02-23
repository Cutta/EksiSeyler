package com.cunoraz.eksiseyler.activity;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.Utility.SharedPrefManager;
import com.cunoraz.eksiseyler.Utility.Tags;
import com.cunoraz.eksiseyler.app.MyApplication;
import com.cunoraz.eksiseyler.fragment.ContextFragment;
import com.cunoraz.eksiseyler.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Uri data;
    boolean showImage = true;
    private Menu menu;
    SharedPrefManager manager;
    public static final String IMAGE_SHOW = "showImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new SharedPrefManager(MainActivity.this);
        showImage = manager.getBoolean(IMAGE_SHOW);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        spinner = (Spinner) findViewById(R.id.spinner_nav);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        data = intent.getData();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewPager.setCurrentItem(i);
                Log.d("onItemSelected", "onItemSelected: ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                spinner.setSelection(position);
                Log.d("onPageSelected", "onPageSelected: ");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("BİLİM");
        categories.add("EDEBİYAT");
        categories.add("EĞLENCE");
        categories.add("HABER");
        categories.add("İLİŞKİLER");
        categories.add("KÜLTÜR");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_activity, menu);
        this.menu = menu;
        if (showImage)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_photo_white_24dp));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_photo_gray_24dp));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ic_menu_show_image:
                if (showImage) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("\u2713 " + "Tasarruf Modu Etkin")
                            .setMessage("Bundan sonraki içerik görüntülemenizde, resimler yüklenmez ve data paketinizden tasarruf edersiniz.")
                            .setPositiveButton("Tamam", null)
                            .create();
                    dialog.show();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_photo_gray_24dp));
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Tasarruf Modu Devredışı")
                            .setMessage("Bundan sonraki içerik görüntülemenizde, resimleri görebilirsiniz.")
                            .setPositiveButton("Tamam", null)
                            .create();
                    dialog.show();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_photo_white_24dp));

                }
                showImage = !showImage;
                manager.saveBoolean(IMAGE_SHOW, showImage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.BILIM, "BİLİM")), "BİLİM");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.EDEBIYAT, "EDEBİYAT")), "EDEBİYAT");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.EGLENCE, "EĞLENCE")), "EĞLENCE");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.HABER, "HABER")), "HABER");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.ILISKILER, "İLİŞKİLER")), "İLİŞKİLER");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.KULTUR, "KÜLTÜR")), "KÜLTÜR");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.MAGAZIN,"MAGAZİN")),"MAGAZİN");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.MODA, "MODA")), "MODA");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.MOTOSIKLET,"MOTOSİKLET")),"MOTOSİKLET");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.MUZIK, "MÜZİK")), "MÜZİK");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.OTOMOTIV, "OTOMOTİV")), "OTOMOTİV");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.OYUN, "OYUN")), "OYUN");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.PROGRAMLAMA,"PROGRAMLAMA")),"PROGRAMLAMA");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SAGLIK, "SAĞLIK")), "SAĞLIK");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SANAT, "SANAT")), "SANAT");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SINEMA, "SİNEMA")), "SİNEMA");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SIYASET, "SİYASET")), "SİYASET");
        //adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SPOILER,"SPOILER")),"SPOILER");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.SPOR, "SPOR")), "SPOR");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.TARIH, "TARİH")), "TARİH");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.TEKNOLOJI, "TEKNOLOJİ")), "TEKNOLOJİ");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.YASAM, "YAŞAM")), "YAŞAM");
        adapter.addFrag(ContextFragment.newInstance(new Channel(Tags.YEME_ICME, "YEME İÇME")), "YEME İÇME");

        viewPager.setAdapter(adapter);
        if (data != null)
            openUriFromIntent();
    }

    private void openUriFromIntent() {
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

    @Override
    public void onBackPressed() {
        MyApplication.getInstance().clearApplicationData();
        super.onBackPressed();
    }

    public boolean getShowImage() {
        return showImage;
    }
}
