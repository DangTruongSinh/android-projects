package com.example.doan2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;



public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String url = "http://smarthome.j.layershift.co.uk/account-api";
    Button btnLogin;
    EditText edtTaiKhoan;
    EditText edtMatKhau;
    TextView txtThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        txtThongBao = findViewById(R.id.txtThongBao);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        final TaiKhoan taiKhoan = new TaiKhoan(edtTaiKhoan.getText().toString(),edtMatKhau.getText().toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            final String json = mapper.writeValueAsString(taiKhoan);
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("tag",response);
                    if(response.equals("true"))
                    {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",taiKhoan.getUsername());
                        intent.putExtra("password",taiKhoan.getPassword());
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                    else
                        txtThongBao.setVisibility(View.VISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("tag","error");
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json;charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return json.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;

                    }
                }
            };
            requestQueue.add(stringRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
