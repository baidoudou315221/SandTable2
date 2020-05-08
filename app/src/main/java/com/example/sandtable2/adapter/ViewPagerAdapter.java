package com.example.sandtable2.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sandtable2.R;
import com.example.sandtable2.infomation.ItemName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.sandtable2.infomation.ItemName.Item;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private Context context;

    public void setFragments(List<Fragment> fragments,Context context) {
        this.fragments = fragments;
        this.context = context;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return Item.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Item[position];
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab, null);
        TextView tv = view.findViewById(R.id.tab_word);
        tv.setText(ItemName.Item[position]);
        ImageView img = view.findViewById(R.id.tab_image);
        img.setImageResource(ItemName.ItemIcon0[position]);
        return view;
    }

}
