package com.mikaeldionito.sistempakarpenyakitkulitanjing.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.adapter.GejalaRecyclerAdapter;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.DiagnosaContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.DiagnosaPresenter;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.admin.GejalaPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiagnosaActivity extends AppCompatActivity implements DiagnosaContract.View {
    private DiagnosaPresenter mPresenter;
    private ApiInterface mApi;

    private TextView tvPenyakitCF,tvKemungkinanCF,tvRumusCF,tvPenyakitDS,tvKemungkinanDS,tvRumusDS;
    private ScrollView svGejala,svHasil;
    private LinearLayout llGejala;
    private TableLayout tlGejala;
    private Button btnSubmit,btnUlang,btnKeSolusi;
    private ProgressDialog progressDialog;

    private List<Gejala> mGejalaList = new ArrayList<>();
    private List<Penyakit> mPenyakitList = new ArrayList<>();

    private String mNama,mJenisKelamin,mUsia,mGejala;
    private MenuItem menuExit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Gejala");

        llGejala = findViewById(R.id.diagnosa_llGejala);
        btnSubmit = findViewById(R.id.diagnosa_btnSubmit);
        btnUlang = findViewById(R.id.diagnosa_btnUlang);
        tlGejala = findViewById(R.id.layout_headerGejala);
        svGejala = findViewById(R.id.diagnosa_svGejala);
        svHasil = findViewById(R.id.diagnosa_svHasil);
        tvPenyakitCF = findViewById(R.id.diagnosa_tvPenyakitCF);
        tvKemungkinanCF = findViewById(R.id.diagnosa_tvKemungkinanCF);
        tvRumusCF = findViewById(R.id.diagnosa_tvRumusCF);
        btnKeSolusi = findViewById(R.id.btnSlsi);

        if (getIntent() != null){
            mNama = getIntent().getStringExtra("nama");
            mUsia = getIntent().getStringExtra("usia");
            mJenisKelamin = getIntent().getStringExtra("jenisKelamin");
        }

        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new DiagnosaPresenter(this,mApi);
        progressDialog = Global.setupProgressDialog(this);
        mPresenter.doRetrievePenyakitList();
        mPresenter.doRetrieveGejalaList();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPenyakit();
            }
        });

        btnUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doRetrieveGejalaList();
            }
        });
        Collections.sort(mGejalaList, new Comparator<Gejala>() {
            @Override
            public int compare(Gejala o1, Gejala o2) {
                return o1.getGejala().compareTo(o2.getGejala());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        AlertDialog.Builder homeDialog = Global.setupPopupDialog(this,"Apakah Anda yakin akan kembali ke halaman home?");
        homeDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (svGejala.getVisibility() == View.VISIBLE){
            homeDialog.show();
        }else if (svHasil.getVisibility() == View.VISIBLE){
            onBackPressed();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menuExit = menu.findItem(R.id.menu_exit);
        if (svHasil.getVisibility() != View.VISIBLE){
            menuExit.setVisible(false);
            menuExit.setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_exit:
                AlertDialog.Builder exitDialog = Global.setupPopupDialog(this,"Apakah Anda ingin keluar dari aplikasi?");
                exitDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                exitDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
// Fungsi memasukan pilihan gejala oleh user
    @Override
    public void retrieveGejalaList(final List<Gejala> gejalaList) {
        getSupportActionBar().setTitle("Gejala");
        mGejalaList.clear();
        llGejala.removeAllViews();
        menuExit.setVisible(false);
        menuExit.setEnabled(false);
        svHasil.setVisibility(View.GONE);
        btnUlang.setVisibility(View.GONE);
        svGejala.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
        int childCount = llGejala.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            llGejala.removeViews(1, childCount - 1);
        }

        for (int i = 0; i < gejalaList.size(); i++){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(gejalaList.get(i).getGejala());
            llGejala.addView(checkBox);

            final int finalI = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        mGejalaList.add(gejalaList.get(finalI));
                    }else{
                        mGejalaList.remove(gejalaList.get(finalI));
                    }
                }
            });
        }
        //Collections.sort(gejalaList, Gejala.BY_NAME_ALPHABETICAL);
        Collections.sort(mGejalaList, new Comparator<Gejala>() {
            @Override
            public int compare(Gejala o1, Gejala o2) {
                return o1.getGejala().compareTo(o2.getGejala());
            }
        });


    }
// Fungsi menampilkan Hasil
    @Override
    public void retrievePengetahuanList(List<Pengetahuan> pengetahuanList) {

        getSupportActionBar().setTitle("Hasil Diagnosa");
        menuExit.setVisible(true);
        menuExit.setEnabled(true);
        svGejala.setVisibility(View.GONE);
        svHasil.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        btnUlang.setVisibility(View.VISIBLE);
        btnKeSolusi.setVisibility(View.VISIBLE);

        btnKeSolusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SolusiPenyakitActivity.class));
            }
        });

        int childCountGejala = tlGejala.getChildCount();
        // Remove all rows except the first one
        if (childCountGejala > 1) {
            tlGejala.removeViews(1, childCountGejala - 1);
        }
        TableRow trGejala;
        for (int i=0; i<mGejalaList.size();i++){
            trGejala = (TableRow) getLayoutInflater().inflate(R.layout.row_layout_gejala,null);
            TextView tvNo = trGejala.findViewById(R.id.row_tvNo);
            TextView tvGejala = trGejala.findViewById(R.id.row_tvNamaGejala);
            tvNo.setText(String.valueOf(i+1));
            tvGejala.setText(mGejalaList.get(i).getGejala());
            tlGejala.addView(trGejala);
        }

        for (int i = 0; i < mPenyakitList.size();i++){
            float count = 0;
            for (Pengetahuan pengetahuan: pengetahuanList){
                if (pengetahuan.getIdPenyakit().equals(mPenyakitList.get(i).getIdPenyakit())){
                    count+=Float.valueOf(pengetahuan.getBobot());
                    mPenyakitList.get(i).setTotalBobot(count);
                }
            }
        }

        //CF
        float bobotOld=0;
        String rumusCF = "";
        float valueCF = 0;
        for (int y=0;y<mPenyakitList.size();y++){
            int x = 1;
            String penyakit = mPenyakitList.get(y).getNamaPenyakit();
            rumusCF = rumusCF + "----- "+ penyakit.toUpperCase() +" -----\n";
            for (int i=0;i<pengetahuanList.size();i++){
                if (pengetahuanList.get(i).getIdPenyakit().equals(mPenyakitList.get(y).getIdPenyakit())){
                    if (x == 1){
                        bobotOld = Float.valueOf(pengetahuanList.get(i).getBobot());
                        x++;
                    }else if ( x == 2){
                        rumusCF =  rumusCF + "CFcombine CF[H,E]1,2 = "+bobotOld+" + "+pengetahuanList.get(i).getBobot()+" * (1-"+bobotOld+")\n";
                        bobotOld = countWithMethodCF(bobotOld,Float.valueOf(pengetahuanList.get(i).getBobot()));
                        rumusCF =  rumusCF + "CFcombine CF[H,E]1,2 = "+bobotOld+"\n\n";
                        x++;
                    }else{
                        rumusCF =  rumusCF + "CFcombine CF[H,E]old,"+x+" = "+bobotOld+" + "+pengetahuanList.get(i).getBobot()+" * (1-"+bobotOld+")\n";
                        bobotOld = countWithMethodCF(bobotOld,Float.valueOf(pengetahuanList.get(i).getBobot()));
                        rumusCF =  rumusCF + "CFcombine CF[H,E]old,"+x+" = "+bobotOld+"\n\n";
                        x++;
                    }
                }
            }
            valueCF = countFinalValueCF(bobotOld);
            mPenyakitList.get(y).setValueCF(valueCF);
            rumusCF = rumusCF + "CF[H,E]old * 100% = " + bobotOld + " * 100% = " + valueCF + "\n\n";
            valueCF = 0;
            bobotOld = 0;
        }
        Pengguna pengguna = new Pengguna();


        Collections.sort(mPenyakitList, new Comparator<Penyakit>() {
            @Override
            public int compare(Penyakit o1, Penyakit o2) {
                return Float.compare(o2.getValueCF(), o1.getValueCF());
            }
        });

        tvRumusCF.setText(rumusCF);
        tvPenyakitCF.setText(mPenyakitList.get(0).getNamaPenyakit());
        tvKemungkinanCF.setText(mPenyakitList.get(0).getValueCF()+"%");
        pengguna.setKemungkinanCf(mPenyakitList.get(0).getNamaPenyakit()+":"+mPenyakitList.get(0).getValueCF());

        pengguna.setNama(mNama);
        pengguna.setUsia(mUsia);
        pengguna.setJenisKelamin(mJenisKelamin);
        pengguna.setGejala(mGejala);
        mPresenter.doSendDataPengguna(pengguna);
    }

    @Override
    public void retrievePenyakitList(List<Penyakit> penyakitList) {
        mPenyakitList.addAll(penyakitList);
    }

    @Override
    public void getResponse(Responses responses) {
        Toast.makeText(this, responses.getMessage(), Toast.LENGTH_SHORT).show();
    }

    protected void checkPenyakit(){
        Collections.sort(mGejalaList, new Comparator<Gejala>() {
            @Override
            public int compare(Gejala o1, Gejala o2) {
                return o1.getKodeGejala().compareTo(o2.getKodeGejala());
            }
        });

        if (mGejalaList.isEmpty()){
            Toast.makeText(this, "Tidak ada gejala yang dipilih!", Toast.LENGTH_SHORT).show();
        }else{
            String strGejala = "";
            for (int i = 0; i < mGejalaList.size(); i++){
                if (i == 0) {
                    strGejala = mGejalaList.get(i).getKodeGejala();
                }else{
                    strGejala = new StringBuilder(strGejala).append(",").append(mGejalaList.get(i).getKodeGejala()).toString();
                }
            }

//            Toast.makeText(this, strGejala, Toast.LENGTH_SHORT).show();
            mGejala = strGejala;
            mPresenter.doRetrievePengetahuanList(strGejala);
        }
    }

    protected float countWithMethodCF(float bobotOld, float bobotNew){
        return bobotOld + (bobotNew*(1-bobotOld));
    }

    protected float countBelief(float bobot){
        return 1-bobot;
    }

    protected float countMCombine(float bobot1, float bobot2, float belief2){
        return ((bobot1*bobot2)+(bobot1*belief2))/(1-0);
    }

    protected float countBeliefMCombine(float belief1, float belief2){
        return (belief1*belief2)/(1-0);
    }

    protected float countFinalValueDS(float bobot, float belief){
        return (bobot+belief) * 100;
    }

    protected float countFinalValueCF(float bobot){
        return bobot * 100;
    }

}