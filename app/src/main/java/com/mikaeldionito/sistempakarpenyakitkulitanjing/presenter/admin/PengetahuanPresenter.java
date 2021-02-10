package com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin;

import android.os.AsyncTask;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.PengetahuanContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async.GejalaListTask;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async.PengetahuanListTask;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async.PenyakitListTask;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengetahuanPresenter implements PengetahuanContract.Presenter {
    private PengetahuanContract.View mView;
    private ApiInterface mApi;;
    private PengetahuanListTask mPengetahuanListTask;

    public PengetahuanPresenter(PengetahuanContract.View mView, ApiInterface mApi) {
        this.mView = mView;
        this.mApi = mApi;
    }

    @Override
    public void doGetPengetahuanList() {
        if (mPengetahuanListTask == null || mPengetahuanListTask.getStatus() != AsyncTask.Status.RUNNING) {
            mPengetahuanListTask = new PengetahuanListTask(mView, mApi);
            mPengetahuanListTask.execute();
        }
    }

    @Override
    public void deletePengetahuan(int id) {
        mView.showLoading();
        Call<Responses> responsesCall = mApi.deletePengetahuan(id);
        responsesCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                mView.hideLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        mView.getResponse(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                mView.hideLoading();
                Responses responses = new Responses();
                responses.setStatus(0);
                responses.setMessage(t.getMessage());
                mView.getResponse(responses);
            }
        });
    }
}
