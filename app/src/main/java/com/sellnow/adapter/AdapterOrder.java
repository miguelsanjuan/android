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
import com.sellnow.dataset.DataSetOrder;

public class AdapterOrder extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSetOrder> DataList;

    public AdapterOrder(Activity activity, List<DataSetOrder> dataitem) {
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
            convertView = inflater.inflate(R.layout.list_order_row, null);


        TextView title = (TextView) convertView.findViewById(R.id.orderTitle);
        TextView desc = (TextView) convertView.findViewById(R.id.orderDescription);
        TextView stat = (TextView) convertView.findViewById(R.id.orderStatus);


        DataSetOrder m = DataList.get(position);
        title.setText(m.getOrderTitle());
        desc.setText(m.getOrderDescription());
        stat.setText(m.getOrderStatus());


        return convertView;
    }

}
