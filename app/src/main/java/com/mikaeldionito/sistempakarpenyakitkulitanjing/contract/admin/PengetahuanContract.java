package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface PengetahuanContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getPengetahuanList(List<Pengetahuan> pengetahuanList);
    }

    interface Presenter{
        void doGetPengetahuanList();
        void deletePengetahuan(int id);

    }
}
