package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

public interface TambahPenyakitContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getPenyakit(Penyakit penyakit);
    }

    interface Presenter{
        void doAddPenyakit(Penyakit penyakit);
        void doUpdatePenyakit(Penyakit penyakit);
        void doGetPenyakit(String idPenyakit);
    }
}
