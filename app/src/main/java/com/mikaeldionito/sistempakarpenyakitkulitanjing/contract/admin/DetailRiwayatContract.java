package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface DetailRiwayatContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getPengguna(Pengguna pengguna);
        void getGejalaList(List<Gejala> gejalaList);
        void getResponse(Responses responses);
        void getPengetahuanByGejala(List<Pengetahuan> pengetahuanList);
        void getPenyakitList(List<Penyakit> penyakitList);
    }

    interface Presenter{
        void doGetPenggunaById(int id);
        void doGetGejalaList();
        void doGetPenyakitList();
        void doGetPengetahuanByGejala(String kodeGejalas);
    }
}
