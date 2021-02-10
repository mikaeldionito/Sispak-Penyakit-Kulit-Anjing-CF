package com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin;

import android.app.ProgressDialog;

import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.adapter.DaftarRiwayatPenggunaanRecyclerAdapter;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.DaftarRiwayatPenggunaanContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin.DaftarRiwayatPenggunaanPresenter;

import java.util.ArrayList;
import java.util.List;

public class DaftarRiwayatPenggunaanActivity extends AppCompatActivity implements DaftarRiwayatPenggunaanContract.View {
    private RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    private List<Pengguna> mPenggunaList = new ArrayList<>();
    private DaftarRiwayatPenggunaanPresenter mPresenter;
    private ApiInterface mApi;
    private DaftarRiwayatPenggunaanRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Pengguna");
        setContentView(R.layout.activity_daftar_riwayat_penggunaan);

        recyclerView = findViewById(R.id.riwayat_rv);

        progressDialog = Global.setupProgressDialog(this);
        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new DaftarRiwayatPenggunaanPresenter(this,mApi);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new DaftarRiwayatPenggunaanRecyclerAdapter(this,mPenggunaList,mPresenter);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        mPresenter.doGetDaftarRiwayat();
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

        if  (responses.getStatus() == 1){
            mPenggunaList.remove(responses.getId());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDaftarRiwayat(List<Pengguna> penggunaList) {
        mPenggunaList.addAll(penggunaList);
        mAdapter.notifyDataSetChanged();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
