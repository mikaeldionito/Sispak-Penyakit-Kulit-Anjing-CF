package com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.AdminLoginContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.AdminPreference;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.main.MainActivity;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Admin;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin.AdminLoginPresenter;

public class AdminLoginActivity extends AppCompatActivity implements AdminLoginContract.View {
    private AdminLoginPresenter mPresenter;
    private ApiInterface mApi;

    private EditText etUsername,etPassword;
    private Button btnLogin;

    private ProgressDialog progressDialog;
    private AdminPreference mAdminPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Halaman Login Admin");

        etUsername = findViewById(R.id.login_etUsername);
        etPassword = findViewById(R.id.login_etPassword);
        btnLogin = findViewById(R.id.login_btnLogin);

        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new AdminLoginPresenter(this,mApi);
        progressDialog = Global.setupProgressDialog(this);
        mAdminPref = new AdminPreference(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void getResponse(Responses responses) {
        Toast.makeText(this, responses.getMessage(), Toast.LENGTH_SHORT).show();

        if (responses.getStatus() == 1){
            mAdminPref.setAdminLogin(new Admin(etUsername.getText().toString(),etPassword.getText().toString()));
            startActivity(new Intent(this,AdminMainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void checkLogin(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Isi username dan password", Toast.LENGTH_SHORT).show();
        }else{
            mPresenter.doLogin(new Admin(username,password));
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
