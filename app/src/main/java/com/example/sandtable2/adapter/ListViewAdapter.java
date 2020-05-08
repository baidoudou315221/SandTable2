package com.example.sandtable2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sandtable2.R;
import com.example.sandtable2.bean.ListBean;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {

    private final int ImageId;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<ListBean> objects) {
        super(context, resource, objects);
        ImageId = resource;//传入自定义的界面
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListBean myBean = (ListBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(ImageId,parent,false);
        LinearLayout linearLayout = view.findViewById(R.id.list_adapter_view);

        ImageView headImage = view.findViewById(R.id.list_adapter_image);
        TextView text = view.findViewById(R.id.list_adapter_text);//标题
        TextView text2= view.findViewById(R.id.list_adapter_text2);//结果
        headImage.setImageResource(myBean.getImageID());
        text.setText(myBean.getText());
        text2.setText(myBean.getInfo());

        return view;
    }
}
