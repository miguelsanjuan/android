package com.sellnow.ui.notification;

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
import com.sellnow.adapter.AdapterProduct;
import com.sellnow.dataset.DataSetNot;
import com.sellnow.dataset.DataSetProduct;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;

    private static final String url = "https://alejandro-loaiza.herokuapp.com/api/v1/notifications";
    private List<DataSetNot> list = new ArrayList<DataSetNot>();
    private ListView listView;
    private AdapterNot adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                new ViewModelProvider(this).get(NotificationViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        listView = (ListView) view.findViewById(R.id.listNot);
        adapter = new AdapterNot(getActivity(), list);
        listView.setAdapter(adapter);

        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            DataSetNot dataSetNot = new DataSetNot();

                            dataSetNot.setNotificationTitle(obj.getString("notificationTitle"));
                            dataSetNot.setNotificationDescription(obj.getString("notificationDescription"));

                            list.add(dataSetNot);
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