package com.cunoraz.eksiseyler.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.Utility.Tags;
import com.cunoraz.eksiseyler.app.MyApplication;
import com.cunoraz.eksiseyler.fragment.ContextFragment;
import com.cunoraz.eksiseyler.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
}
