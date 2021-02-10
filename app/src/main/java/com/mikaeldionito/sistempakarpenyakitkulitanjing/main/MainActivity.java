package com.mikaeldionito.sistempakarpenyakitkulitanjing.main;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.AdminPreference;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin.AdminLoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView konsultasiMik, infoMik, solusiMik;
    private TextView txtadmin;
    private AdminPreference mAdminPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");

        konsultasiMik = findViewById(R.id.konsultasi_mik);
        infoMik =  findViewById(R.id.info_mik);
        solusiMik = findViewById(R.id.solusi_mik);
        txtadmin = findViewById(R.id.txtadmin);

        konsultasiMik.setOnClickListener(this);
        infoMik.setOnClickListener(this);
        solusiMik.setOnClickListener(this);
        txtadmin.setOnClickListener(this);

        mAdminPref = new AdminPreference(this);
    }

    @Override
    public void onClick(View v) {
        Intent mik ;
        switch (v.getId()){
            case R.id.konsultasi_mik : mik = new Intent(this,FormDataActivity.class); startActivity(mik); break;
            case R.id.info_mik : mik = new Intent(this,InfoPenyakitActivity.class); startActivity(mik); break;
            case R.id.solusi_mik : mik = new Intent(this,SolusiPenyakitActivity.class); startActivity(mik); break;
            case R.id.txtadmin : mik = new Intent(this, AdminLoginActivity.class); startActivity(mik); break;
            default: break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                finish();
            }
        }
    }
}
