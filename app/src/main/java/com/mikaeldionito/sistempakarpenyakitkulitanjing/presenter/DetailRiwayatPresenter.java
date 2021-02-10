package com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter;

import android.os.AsyncTask;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.DetailRiwayatContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async.GejalaListTask;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async.PenyakitListTask;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatPresenter implements DetailRiwayatContract.Presenter {
    private DetailRiwayatContract.View mView;
    private ApiInterface mApi;
    private GejalaListTask mGejalaListTask;
    private PenyakitListTask mPenyakitListTask;

    public DetailRiwayatPresenter(DetailRiwayatContract.View mView, ApiInterface mApi) {
        this.mView = mView;
        this.mApi = mApi;
    }

    @Override
    public void doGetPenggunaById(int id) {
        mView.showLoading();
        Call<Pengguna> penggunaCall = mApi.getPenggunaById(id);
        penggunaCall.enqueue(new Callback<Pengguna>() {
            @Override
            public void onResponse(Call<Pengguna> call, Response<Pengguna> response) {
//                mView.hideLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        mView.getPengguna(response.body());
                        doGetPenyakitList();
                        doGetGejalaList();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pengguna> call, Throwable t) {
                mView.hideLoading();
                Responses responses = new Responses();
                responses.setStatus(0);
                responses.setMessage(t.getMessage());
                mView.getResponse(responses);
            }
        });
    }

    @Override
    public void doGetGejalaList() {
        if (mGejalaListTask == null || mGejalaListTask.getStatus() != AsyncTask.Status.RUNNING) {
            mGejalaListTask = new GejalaListTask(mView, mApi);
            mGejalaListTask.execute();
        }
    }

    @Override
    public void doGetPenyakitList() {
        if (mPenyakitListTask == null || mPenyakitListTask.getStatus() != AsyncTask.Status.RUNNING) {
            mPenyakitListTask = new PenyakitListTask(mView, mApi);
            mPenyakitListTask.execute();
        }
    }

    @Override
    public void doGetPengetahuanByGejala(String kodeGejalas) {
        Call<List<Pengetahuan>> pengetahuanCall = mApi.getPengetahuanByGejala(kodeGejalas);
        pengetahuanCall.enqueue(new Callback<List<Pengetahuan>>() {
            @Override
            public void onResponse(Call<List<Pengetahuan>> call, Response<List<Pengetahuan>> response) {
                mView.hideLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        mView.getPengetahuanByGejala(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pengetahuan>> call, Throwable t) {
                mView.hideLoading();
                Responses responses = new Responses();
                responses.setStatus(0);
                responses.setMessage(t.getMessage());
                mView.getResponse(responses);
            }
        });
    }
}
