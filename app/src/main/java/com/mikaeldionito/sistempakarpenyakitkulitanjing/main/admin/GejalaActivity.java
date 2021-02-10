package com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.adapter.GejalaRecyclerAdapter;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.GejalaContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin.GejalaPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GejalaActivity extends AppCompatActivity implements GejalaContract.View {
    private RecyclerView recyclerView;
    private Button btnTambah;

    private GejalaPresenter mPresenter;
    private ApiInterface mApi;
    private ProgressDialog progressDialog;

    private GejalaRecyclerAdapter mAdapter;
    private List<Gejala> mGejalaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gejala);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Gejala");

        recyclerView = findViewById(R.id.gejala_rv);
        btnTambah = findViewById(R.id.gejala_btnTambah);

        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new GejalaPresenter(this,mApi);
        progressDialog = Global.setupProgressDialog(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new GejalaRecyclerAdapter(this,mGejalaList,mPresenter);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        mPresenter.doGetGejalaList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TambahGejalaActivity.class);
                intent.putExtra("status","new");
                startActivityForResult(intent,1);
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
            for (Gejala gejala: mGejalaList){
                if (gejala.getKodeGejala().equals(responses.getKode())){
                    mGejalaList.remove(gejala);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void getGejalaList(List<Gejala> gejalaList) {
        mGejalaList.addAll(gejalaList);
        Collections.sort(mGejalaList, new Comparator<Gejala>() {
            @Override
            public int compare(Gejala o1, Gejala o2) {
                return o1.getKodeGejala().compareTo(o2.getKodeGejala());
            }
        });
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                mGejalaList.clear();
                mAdapter.notifyDataSetChanged();
                mPresenter.doGetGejalaList();
            }
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
