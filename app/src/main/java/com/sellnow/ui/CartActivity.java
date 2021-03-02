package com.sellnow.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.sellnow.R;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sellnow.Controller;
import com.sellnow.adapter.AdapterProduct;
import com.sellnow.dataset.DataSetCart;
import com.sellnow.adapter.AdapterCart;
import com.sellnow.dataset.DataSetProduct;
import com.sellnow.ui.ProductActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class CartActivity extends AppCompatActivity {

    private static final String url = "https://alejandro-loaiza.herokuapp.com/api/v1/cart";
    private List<DataSetCart> list = new ArrayList<DataSetCart>();
    private ListView listView;
    private AdapterCart adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        String CORREO = intent.getStringExtra("email");

        /*Toast.makeText(getApplicationContext(),
                "El correo es :"+CORREO, Toast.LENGTH_SHORT).show();*/


        listView = (ListView) findViewById(R.id.listCart);
        adapter = new AdapterCart(this, list);
        listView.setAdapter(adapter);

        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                response -> {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            DataSetCart dataSetCart = new DataSetCart();

                            double total = obj.getInt("quantity") * obj.getDouble("unitPrice");

                            //String ar[] = getProduct(obj.getInt("productID"));
                            //dataSetCart.setProductID(obj.getInt("productID"));
                            //dataSetCart.setUserEmail(obj.getString("userEmail"));
                            dataSetCart.setProductName(obj.getString("productName"));
                            dataSetCart.setProductDescription(obj.getString("productDescription"));
                            dataSetCart.setQuantity(obj.getInt("quantity"));
                            dataSetCart.setUnitPrice(total);
                            dataSetCart.setProductURLPicture(obj.getString("productURLPicture"));

                            list.add(dataSetCart);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    adapter.notifyDataSetChanged();
                }, error -> {
            // AlertDialog.Builder add = new AlertDialog.Builder(getActivity());
            AlertDialog.Builder add = new AlertDialog.Builder(this);
            add.setMessage(error.getMessage()).setCancelable(true);
            AlertDialog alert = add.create();
            alert.setTitle("Error!!!");
            alert.show();
        });

        Controller.getPermission().addToRequestQueue(jsonreq);


        // BUTTON CLICK
        Button button = (Button) findViewById(R.id.btn_cart);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //int IDint = Integer.valueOf(ID);
                volleyPost();
                Toast.makeText(v.getContext(), "Se realizo el pedido", Toast.LENGTH_LONG).show();

            }
        });

    }

    // Kill activity when back buttom is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void volleyPost(){
        String postUrl = "https://alejandro-loaiza.herokuapp.com/api/v1/orders";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {

            postData.put("orderTitle", "Pedido "+getDateTime() );
            postData.put("orderDescription", "La vendedora realizara tu envio pronto");
            postData.put("orderStatus", "En proceso");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    /*public void getProduct(int ID)
    {
        String url = "https://alejandro-loaiza.herokuapp.com/api/v1/products/1";
        //url += "/" + String.valueOf(ID);
        //final String ar[] = new String[3];
        static String ar[] = new String[3];

        JsonObjectRequest jsonreq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(getApplicationContext(),
                            "Product :"+response.getString("productName"), Toast.LENGTH_SHORT).show();
                    ar[0] = response.getString("productName");
                    ar[1] =  response.getString("productDescription");
                    ar[2] =  String.valueOf(response.getDouble("unitPrice"));
                    ar[3] =  response.getString("productURLPicture");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
        Controller.getPermission().addToRequestQueue(jsonreq);
        //return ar; //returning two values at once
    }*/
}