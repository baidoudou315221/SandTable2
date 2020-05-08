package com.example.sandtable2.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sandtable2.MainPageActivity;
import com.example.sandtable2.R;
import com.example.sandtable2.adapter.ListViewAdapter;
import com.example.sandtable2.bean.ListBean;
import com.example.sandtable2.infomation.ItemName;
import com.example.sandtable2.table.LineChartActivity;

import java.util.ArrayList;
import java.util.List;

public class SeekFragment extends Fragment {
    private View view;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<ListBean> myBeanList = new ArrayList<>();
    private Activity activity;
    private Intent intent;

    public Context getContext(){
        if (activity == null){
            return MainPageActivity.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.seek_fragment,container,false);
        listView = view.findViewById(R.id.listView);
        init();
        listViewAdapter = new ListViewAdapter(activity,R.layout.list_view,myBeanList);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("------>", "点击listview"+i);
                Log.d("------>","进入linechart");
                intent = new Intent(getActivity(), LineChartActivity.class);
                intent.putExtra("项目代号",Integer.toString(i));
                startActivity(intent);
            }
        });
        return view;
    }

    private void init() {
        ListBean bean0 = new ListBean(ItemName.SmallIcon[0],ItemName.SmallItem[0],ItemName.ConstantData[0]);
        myBeanList.add(bean0);

        ListBean bean1 = new ListBean(ItemName.SmallIcon[1],ItemName.SmallItem[1],ItemName.ConstantData[1]);
        myBeanList.add(bean1);

        ListBean bean2 = new ListBean(ItemName.SmallIcon[2],ItemName.SmallItem[2],ItemName.ConstantData[2]);
        myBeanList.add(bean2);

        ListBean bean3 = new ListBean(ItemName.SmallIcon[3],ItemName.SmallItem[3],ItemName.ConstantData[3]);
        myBeanList.add(bean3);
    }


}
