package com.sellnow.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import com.sellnow.R;
import com.sellnow.Controller;
import com.sellnow.dataset.DataSetNot;

public class AdapterNot extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSetNot> DataList;

    public AdapterNot(Activity activity, List<DataSetNot> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int location) {
        return DataList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_noti, null);


        TextView title = (TextView) convertView.findViewById(R.id.notTitle);
        TextView desc = (TextView) convertView.findViewById(R.id.notDescription);


        DataSetNot m = DataList.get(position);
        title.setText(m.getNotificationTitle());
        desc.setText(m.getNotificationDescription());


        return convertView;
    }

}
