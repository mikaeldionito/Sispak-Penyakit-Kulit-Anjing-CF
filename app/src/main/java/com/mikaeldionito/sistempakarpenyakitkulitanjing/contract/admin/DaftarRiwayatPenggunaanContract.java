package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

import java.util.List;

public interface DaftarRiwayatPenggunaanContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
        void getDaftarRiwayat(List<Pengguna> penggunaList);
    }

    interface Presenter{
        void doGetDaftarRiwayat();
        void doDeleteDaftarRiwayat(int id);
    }
}
