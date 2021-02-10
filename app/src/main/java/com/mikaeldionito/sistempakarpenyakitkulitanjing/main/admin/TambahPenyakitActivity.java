package com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.TambahPenyakitContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin.TambahPenyakitPresenter;

public class TambahPenyakitActivity extends AppCompatActivity implements TambahPenyakitContract.View {
    private EditText etId, etPenyakit;
    private Button btnSimpan;

    private ProgressDialog progressDialog;
    private ApiInterface mApi;
    private TambahPenyakitPresenter mPresenter;

    private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_tambah_penyakit);

        etId = findViewById(R.id.tambahPenyakit_etKode);
        etPenyakit = findViewById(R.id.tambahPenyakit_etPenyakit);
        btnSimpan = findViewById(R.id.tambahPenyakit_btnSimpan);

        mStatus = getIntent().getStringExtra("status");

        progressDialog = Global.setupProgressDialog(this);
        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new TambahPenyakitPresenter(this,mApi);

        getSupportActionBar().setTitle("Tambah Penyakit");
        if (mStatus.equals("edit")){
            String idPenyakit = getIntent().getStringExtra("id_penyakit");
            mPresenter.doGetPenyakit(idPenyakit);
            getSupportActionBar().setTitle("Edit Penyakit");
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForm();
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
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void getPenyakit(Penyakit penyakit) {
        etId.setText(penyakit.getIdPenyakit());
        etId.setEnabled(false);
        etId.setBackgroundColor(getResources().getColor(R.color.gray));
        etId.setTextColor(getResources().getColor(R.color.dark_gray));
        etPenyakit.setText(penyakit.getNamaPenyakit());
    }

    protected void checkForm(){
        String idPenyakit = etId.getText().toString();
        String namaPenyakit = etPenyakit.getText().toString();

        if (idPenyakit.isEmpty() || namaPenyakit.isEmpty()){
            Toast.makeText(this, "Lengkapi kolom inputan yang kosong", Toast.LENGTH_SHORT).show();
        }else {
            Penyakit penyakit = new Penyakit();
            penyakit.setIdPenyakit(idPenyakit);
            penyakit.setNamaPenyakit(namaPenyakit);
            if (mStatus.equals("edit")){
                mPresenter.doUpdatePenyakit(penyakit);
            }else if (mStatus.equals("new")){
                mPresenter.doAddPenyakit(penyakit);
            }
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
