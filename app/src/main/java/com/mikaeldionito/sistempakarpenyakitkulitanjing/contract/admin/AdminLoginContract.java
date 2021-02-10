package com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Admin;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;

public interface AdminLoginContract {
    interface View{
        void showLoading();
        void hideLoading();
        void getResponse(Responses responses);
    }

    interface Presenter{
        void doLogin(Admin admin);
    }
}
