package com.example.my_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword, editTextEmail,nik,telpon,nama;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        nik = (EditText) findViewById(R.id.nik);
        telpon = (EditText) findViewById(R.id.telpNumber);
        nama = (EditText) findViewById(R.id.name);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser(){

        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String no_induk_ke = nik.getText().toString().trim();
        final String telfon = telpon.getText().toString().trim();
        final String nama_lengkap = nama.getText().toString().trim();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Connections.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("pesan"), Toast.LENGTH_LONG).show();
                            if( (!username.isEmpty()) && (!password.isEmpty()) && (!email.isEmpty())  && (!no_induk_ke.isEmpty())  && (!nama_lengkap.isEmpty())  && (!telfon.isEmpty()) ) {
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("nama_lengkap", nama_lengkap);
                params.put("telpon", telfon);
                params.put("nik", no_induk_ke);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            registerUser();
//        if (view == textViewLogin)
//            startActivity(new Intent(this,LoginActivity.class));
    }
}
