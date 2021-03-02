package com.sellnow.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sellnow.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    static  boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CardView btnRes = findViewById(R.id.resBtnCard);
        TextView btnLogin = findViewById(R.id.resLogin);

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                volleyPost();

                if(login){
                    Intent myIntent = new Intent(view.getContext(),  LoginActivity.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Ocurrio un error al registrarse", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myIntent);

            }
        });

    }

    public void volleyPost(){
        String postUrl = "https://alejandro-loaiza.herokuapp.com/api/v1/users";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        EditText email =  findViewById(R.id.resEmail);
        EditText passw =  findViewById(R.id.resPass);
        EditText name =  findViewById(R.id.resUserName);
        EditText last =  findViewById(R.id.resLastName);
        EditText cell =  findViewById(R.id.resCell);

        JSONObject postData = new JSONObject();
        try {

            postData.put("userEmail", email);
            postData.put("userPassword", passw);
            postData.put("userName", name);
            postData.put("userLast", last);
            postData.put("userCellphone", cell);
            postData.put("userURLPricture", "https://i.ibb.co/FzwbZwr/Avatar-user.jpg");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    login = response.getBoolean("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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