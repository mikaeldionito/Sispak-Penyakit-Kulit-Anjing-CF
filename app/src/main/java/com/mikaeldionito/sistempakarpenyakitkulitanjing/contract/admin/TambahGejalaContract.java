package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface TambahGejalaContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getGejala(Gejala gejala);
    }

    interface Presenter{
        void doAddGejala(Gejala gejala);
        void doUpdateGejala(Gejala gejala);
        void doGetGejala(String kodeGejala);
    }
}
