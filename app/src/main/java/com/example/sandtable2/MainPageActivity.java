package com.example.sandtable2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandtable2.adapter.ViewPagerAdapter;
import com.example.sandtable2.fragment.ChangeFragment;
import com.example.sandtable2.fragment.SeekFragment;
import com.example.sandtable2.infomation.ItemName;
import com.google.android.material.tabs.TabLayout;
import com.zhangke.websocket.WebSocketSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainPageActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextView titleBar;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<Drawable> icons = new ArrayList<>();

    private static MainPageActivity mInstance;
    public static Context getInstance(){
        if (mInstance == null){
            mInstance = new MainPageActivity();
        }
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        WebSocketSetting.setConnectUrl("ws://39.105.232.155:8099/productWebsocket/001");
        init();

        viewPagerAdapter.setFragments(fragments,this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(viewPagerAdapter.getTabView(i));
            }

        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        titleBar.setText(ItemName.Item[0]);
                        break;
                    case 1:
                        titleBar.setText(ItemName.Item[1]);
                        break;
                }
                Log.d("tabLayout------->", String.valueOf(tab.getPosition()));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_image).setFocusable(true);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void init() {
        titles.add(ItemName.Item[0]);
        titles.add(ItemName.Item[1]);

        icons.add(getResources().getDrawable(ItemName.ItemIcon0[0]));
        icons.add(getResources().getDrawable(ItemName.ItemIcon0[1]));

        fragments.clear();
        fragments.add(new SeekFragment());
        fragments.add(new ChangeFragment());


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        titleBar = findViewById(R.id.titleBar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }


}
