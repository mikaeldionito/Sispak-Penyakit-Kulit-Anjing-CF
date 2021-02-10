package com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.async;

import android.os.AsyncTask;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.PengetahuanContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;

import java.util.List;

import retrofit2.Response;

public class PengetahuanListTask extends AsyncTask<Void, Integer, List<Pengetahuan>> {
    private PengetahuanContract.View mPengetahuanView;
    private ApiInterface mApi;

    public PengetahuanListTask(PengetahuanContract.View mPengetahuanView, ApiInterface mApi) {
        this.mPengetahuanView = mPengetahuanView;
        this.mApi = mApi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mPengetahuanView.showLoading();
    }

    @Override
    protected void onPostExecute(List<Pengetahuan> pengetahuanList) {
        super.onPostExecute(pengetahuanList);
        mPengetahuanView.hideLoading();
        if (pengetahuanList != null){
            mPengetahuanView.getPengetahuanList(pengetahuanList);
        }
    }

    @Override
    protected List<Pengetahuan> doInBackground(Void... voids) {
        try {
            Response<List<Pengetahuan>> response = mApi.getPengetahuanList().execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
