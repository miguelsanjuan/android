package com.sellnow.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.sellnow.ui.CartActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    static boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CardView btnLogin = findViewById(R.id.loginBtnCard);
        TextView btnRes = findViewById(R.id.loginRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                volleyPost();
                System.out.println("LOGIN_AFTER ===> "+login);

                if(login){
                    Intent myIntent = new Intent(view.getContext(), RegisterActivity.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }

            }
        });



        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(myIntent);

            }
        });
    }


    public void volleyPost(){
        String postUrl = "https://alejandro-loaiza.herokuapp.com/api/v1/user/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        EditText emailF =  findViewById(R.id.loginTextUser);
        EditText passwF =  findViewById(R.id.loginTextPass);

        String email =  emailF.getText().toString();
        String passw =  passwF.getText().toString();

        JSONObject postData = new JSONObject();
        try {
            System.out.println("CORREO ===> "+email);
            System.out.println("CONTRA ===> "+passw);
            postData.put("userEmail", email);
            postData.put("userPassword", passw);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    login = response.getBoolean("status");
                    System.out.println("LOGIN_RES ===> "+login);
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