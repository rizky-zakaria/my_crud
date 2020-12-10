package com.example.my_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends AppCompatActivity {

    private Button logout;
    private TextView textViewUsername, textViewUserEmail, fullname, notlp, nik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));

        }

        logout = findViewById(R.id.bt_logout);
        nik = (TextView) findViewById(R.id.nik);
        fullname = (TextView) findViewById(R.id.fullname);
        notlp = (TextView) findViewById(R.id.notlp);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);


        nik.setText(SharedPrefManager.getInstance(this).getNik());
        fullname.setText(SharedPrefManager.getInstance(this).getNama());
        notlp.setText(SharedPrefManager.getInstance(this).getTelpon());
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this,"Menu ini belum tersedia", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
