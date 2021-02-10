package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface GejalaContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getGejalaList(List<Gejala> gejalaList);
    }

    interface Presenter{
        void doGetGejalaList();
        void deleteGejala(String kode);
    }
}
