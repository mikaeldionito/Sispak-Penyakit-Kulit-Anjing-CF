package com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.TambahPenyakitContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPenyakitPresenter implements TambahPenyakitContract.Presenter {
    private TambahPenyakitContract.View mView;
    private ApiInterface mApi;

    public TambahPenyakitPresenter(TambahPenyakitContract.View mView, ApiInterface mApi) {
        this.mView = mView;
        this.mApi = mApi;
    }

    @Override
    public void doAddPenyakit(Penyakit penyakit) {
        mView.showLoading();
        Call<Responses> responsesCall = mApi.tambahPenyakit(penyakit);
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

    @Override
    public void doUpdatePenyakit(Penyakit penyakit) {
        mView.showLoading();
        Call<Responses> responsesCall = mApi.updatePenyakit(penyakit);
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

    @Override
    public void doGetPenyakit(String idPenyakit) {
        mView.showLoading();
        Call<Penyakit> penyakitCall = mApi.getPenyakitById(idPenyakit);
        penyakitCall.enqueue(new Callback<Penyakit>() {
            @Override
            public void onResponse(Call<Penyakit> call, Response<Penyakit> response) {
                mView.hideLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        mView.getPenyakit(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Penyakit> call, Throwable t) {
                mView.hideLoading();
                Responses responses = new Responses();
                responses.setStatus(0);
                responses.setMessage(t.getMessage());
                mView.getResponse(responses);
            }
        });
    }
}
