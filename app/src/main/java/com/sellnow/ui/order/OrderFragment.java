package com.sellnow.ui.order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.JsonArrayRequest;
import com.sellnow.Controller;
import com.sellnow.R;
import com.sellnow.adapter.AdapterNot;
import com.sellnow.adapter.AdapterOrder;
import com.sellnow.dataset.DataSetNot;
import com.sellnow.dataset.DataSetOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private static final String url = "https://alejandro-loaiza.herokuapp.com/api/v1/orders";
    private List<DataSetOrder> list = new ArrayList<DataSetOrder>();
    private ListView listView;
    private AdapterOrder adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                new ViewModelProvider(this).get(OrderViewModel.class);
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        listView = (ListView) view.findViewById(R.id.listOrder);
        adapter = new AdapterOrder(getActivity(), list);
        listView.setAdapter(adapter);

        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            DataSetOrder dataSetOrder = new DataSetOrder();

                            dataSetOrder.setOrderTitle(obj.getString("orderTitle"));
                            dataSetOrder.setOrderDescription(obj.getString("orderDescription"));
                            dataSetOrder.setOrderStatus(obj.getString("orderStatus"));

                            list.add(dataSetOrder);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    adapter.notifyDataSetChanged();
                }, error -> {
            AlertDialog.Builder add = new AlertDialog.Builder(getActivity());
            // AlertDialog.Builder add = new AlertDialog.Builder(this);
            add.setMessage(error.getMessage()).setCancelable(true);
            AlertDialog alert = add.create();
            alert.setTitle("Error!!!");
            alert.show();
        });

        Controller.getPermission().addToRequestQueue(jsonreq);

        return view;
    }
}