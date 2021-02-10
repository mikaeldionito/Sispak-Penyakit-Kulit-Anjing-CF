package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface TambahPengetahuanContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getGejalaList(List<Gejala> gejalaList);
        void getPenyakitList(List<Penyakit> penyakitList);
        void getPengetahuan(Pengetahuan pengetahuan);

    }

    interface Presenter{
        void doGetGejalaList();
        void doGetPenyakitList();
        void doAddPengetahuan(Pengetahuan pengetahuan);
        void doUpdatePengetahuan(Pengetahuan pengetahuan);
        void doGetPengetahuanById(int id);
    }
}
