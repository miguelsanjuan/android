package com.sellnow.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.sellnow.Controller;
import com.sellnow.R;
import com.sellnow.dataset.DataSetProduct;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {

    private String url = "https://alejandro-loaiza.herokuapp.com/api/v1/products";
    private String mal = "";
    private String nam = "";
    private String des = "";
    private double pri = 0;
    private int cat = 0;
    private String img = "";
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Getting the ProductID
        Intent intent = getIntent();
        String ID = intent.getStringExtra("id");
        url += "/" + ID;
        /*JsonObjectRequest jsonreq = new  JsonObjectRequest(url, response -> {

                        try {
                            VolleyLog.wtf(response);

                            //JSONObject obj = response.getJSONObject(1);
                            DataSetProduct dataSetProduct = new DataSetProduct();
                            dataSetProduct.setProductID(obj.getInt("productID"));
                            dataSetProduct.setCategoryID(obj.getInt("categoryID"));
                            dataSetProduct.setProductName(obj.getString("productName"));
                            dataSetProduct.setProductDescription(obj.getString("productDescription"));
                            dataSetProduct.setUnitPrice(obj.getDouble("unitPrice"));
                            dataSetProduct.setProductURLPicture(obj.getString("productURLPicture"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }, error -> {
            AlertDialog.Builder add = new AlertDialog.Builder(this);
            add.setMessage(error.getMessage()).setCancelable(true);
            AlertDialog alert = add.create();
            alert.setTitle("Error!!!");
            alert.show();
        });*/

        JsonObjectRequest jsonreq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.wtf(response.toString());

                try {

                    if (imageLoader == null)
                        imageLoader = Controller.getPermission().getImageLoader();

                    NetworkImageView picture = (NetworkImageView) findViewById(R.id.productPictureSingle);
                    TextView name = (TextView) findViewById(R.id.productNameSingle);
                    TextView desc = (TextView) findViewById(R.id.productDescriptionSingle);
                    TextView price = (TextView) findViewById(R.id.unitPriceSingle);

                    picture.setImageUrl(response.getString("productURLPicture"), imageLoader);
                    name.setText(response.getString("productName"));
                    desc.setText(response.getString("productDescription"));
                    price.setText("$ " + String.valueOf(response.getDouble("unitPrice")));

                    mal = "alejandro.loaiza@sellnow.com";
                    nam = response.getString("productName");
                    des = response.getString("productDescription");
                    pri = response.getDouble("unitPrice");
                    //cat = 1;
                    img = response.getString("productURLPicture");

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


        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(20);
        cat = np.getValue();


        // BUTTON CLICK
        Button button = (Button) findViewById(R.id.btn_single);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //int IDint = Integer.valueOf(ID);
                volleyPost();
                Toast.makeText(v.getContext(), "Se agrego el producto al carrito", Toast.LENGTH_LONG).show();

            }
        });
    }

    // Kill activity when back buttom is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void volleyPost(){
        String postUrl = "https://alejandro-loaiza.herokuapp.com/api/v1/cart";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            NumberPicker np = findViewById(R.id.numberPicker);
            cat = np.getValue();
            postData.put("productID", 1 );
            postData.put("userEmail", mal);
            postData.put("productName", nam);
            postData.put("productDescription", des);
            postData.put("unitPrice", pri);
            postData.put("quantity", cat);
            postData.put("productURLPicture", img);

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

}