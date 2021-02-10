package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface DiagnosaContract {
    interface View{
        void showLoading();
        void hideLoading();
        void retrieveGejalaList(List<Gejala> gejalaList);
        void retrievePengetahuanList(List<Pengetahuan> pengetahuanList);
        void retrievePenyakitList(List<Penyakit> penyakitList);
        void getResponse(Responses responses);
    }

    interface Presenter{
        void doRetrieveGejalaList();
        void doRetrievePengetahuanList(String gejalas);
        void doRetrievePenyakitList();
        void doSendDataPengguna(Pengguna pengguna);
    }
}
