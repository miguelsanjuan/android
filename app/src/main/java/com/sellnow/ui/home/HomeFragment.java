package com.sellnow.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.toolbox.JsonArrayRequest;
import com.sellnow.MainActivity;
import com.sellnow.R;

import java.util.ArrayList;
import java.util.List;

import com.sellnow.Controller;
import com.sellnow.dataset.DataSetProduct;
import com.sellnow.adapter.AdapterProduct;
import com.sellnow.ui.ProductActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private static final String url = "https://alejandro-loaiza.herokuapp.com/api/v1/products";
    private List<DataSetProduct> list = new ArrayList<DataSetProduct>();
    private ListView listView;
    private AdapterProduct adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        adapter = new AdapterProduct(getActivity(), list);
        // adapter = new AdapterProduct(this, list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int item = position + 1;

                //Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                Intent myIntent = new Intent(view.getContext(), ProductActivity.class);
                myIntent.putExtra("id", item); //Optional parameters
                getActivity().startActivity(myIntent);
                // startActivity(myIntent);
                // finish();

                /*Toast.makeText(getActivity().getApplicationContext(),
                        "You click on position:"+item, Toast.LENGTH_SHORT).show();*/
            }
        });

        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            DataSetProduct dataSetProduct = new DataSetProduct();
                            dataSetProduct.setProductID(obj.getInt("productID"));
                            dataSetProduct.setCategoryID(obj.getInt("categoryID"));
                            dataSetProduct.setProductName(obj.getString("productName"));
                            dataSetProduct.setProductDescription(obj.getString("productDescription"));
                            dataSetProduct.setUnitPrice(obj.getDouble("unitPrice"));
                            dataSetProduct.setProductURLPicture(obj.getString("productURLPicture"));
                            list.add(dataSetProduct);
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


        /*homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
         final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return view;
    }
}