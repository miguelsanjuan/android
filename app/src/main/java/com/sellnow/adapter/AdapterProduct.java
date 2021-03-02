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
import com.sellnow.dataset.DataSetProduct;
import com.sellnow.Controller;

public class AdapterProduct extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSetProduct> DataList;
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public AdapterProduct(Activity activity, List<DataSetProduct> dataitem) {
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
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = Controller.getPermission().getImageLoader();
        NetworkImageView picture = (NetworkImageView) convertView
                .findViewById(R.id.productPicture);
        TextView name = (TextView) convertView.findViewById(R.id.productName);
        TextView desc = (TextView) convertView.findViewById(R.id.productDescription);
        TextView price = (TextView) convertView.findViewById(R.id.unitPrice);

        DataSetProduct m = DataList.get(position);
        picture.setImageUrl(m.getProductURLPicture(), imageLoader);
        name.setText(m.getProductName());
        desc.setText(String.valueOf(m.getProductDescription()));
        price.setText("$ " + String.valueOf(m.getUnitPrice()));

        return convertView;
    }

}


