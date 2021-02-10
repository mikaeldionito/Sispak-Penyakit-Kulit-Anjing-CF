package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface PenyakitContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getPenyakitList(List<Penyakit> penyakitList);
        void getResponse(Responses responses);
    }

    interface Presenter{
        void doGetPenyakitList();
        void deletePenyakit(String id);
    }
}
