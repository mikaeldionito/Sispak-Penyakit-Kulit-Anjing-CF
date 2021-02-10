package com.mikaeldionito.sistempakarpenyakitkulitanjing.main.admin;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mikaeldionito.sistempakarpenyakitkulitanjing.R;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiInterface;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.api.ApiService;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.contract.admin.DetailRiwayatContract;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.helper.Global;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.main.SolusiPenyakitActivity;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Gejala;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengetahuan;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Pengguna;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Penyakit;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.model.Responses;
import com.mikaeldionito.sistempakarpenyakitkulitanjing.presenter.DetailRiwayatPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailRiwayatActivity extends AppCompatActivity implements DetailRiwayatContract.View {
    private TextView tvNama,tvUsia,tvJenisKelamin,tvPenyakitCF,tvKemungkinanCF,tvRumusCF;
    private TableLayout tlGejala;
    private Button btnKeSolusi;

    private ProgressDialog progressDialog;
    private ApiInterface mApi;
    private DetailRiwayatPresenter mPresenter;
    private Pengguna mPengguna;

    private List<Penyakit> mPenyakitList = new ArrayList<>();
    private List<Gejala> mGejalaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Pengguna");

        tvNama = findViewById(R.id.detail_tvNama);
        tvUsia = findViewById(R.id.detail_tvUsia);
        tvJenisKelamin = findViewById(R.id.detail_tvKelamin);
        tlGejala = findViewById(R.id.layout_headerGejala);
        tvPenyakitCF = findViewById(R.id.detail_tvPenyakitCF);
        tvKemungkinanCF = findViewById(R.id.detail_tvKemungkinanCF);
        tvRumusCF = findViewById(R.id.detail_tvRumusCF);
        btnKeSolusi = findViewById(R.id.btnKeSolusi);

        mApi = ApiService.getService().create(ApiInterface.class);
        mPresenter = new DetailRiwayatPresenter(this,mApi);
        progressDialog = Global.setupProgressDialog(this);

        if (getIntent() != null){
            int id = getIntent().getIntExtra("id",0);
            mPresenter.doGetPenggunaById(id);
        }
        btnKeSolusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SolusiPenyakitActivity.class));
            }
        });
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void getPengguna(Pengguna pengguna) {
        mPengguna = pengguna;
        tvNama.setText(pengguna.getNama());
        tvUsia.setText(pengguna.getUsia());
        tvJenisKelamin.setText(pengguna.getJenisKelamin());
    }

    @Override
    public void getGejalaList(List<Gejala> gejalaList) {
        TableRow trGejala;
        String[] kodeGejalas = mPengguna.getGejala().split(",");
        for (int i=0; i<gejalaList.size();i++){
            for (String kodeGejala: kodeGejalas){
                if (kodeGejala.equals(gejalaList.get(i).getKodeGejala())){
                    mGejalaList.add(gejalaList.get(i));
                    trGejala = (TableRow) getLayoutInflater().inflate(R.layout.row_layout_gejala,null);
                    TextView tvNo = trGejala.findViewById(R.id.row_tvNo);
                    TextView tvGejala = trGejala.findViewById(R.id.row_tvNamaGejala);
                    tvNo.setText(String.valueOf(i+1));
                    tvGejala.setText(gejalaList.get(i).getGejala());
                    tlGejala.addView(trGejala);
                }
            }
        }

        mPresenter.doGetPengetahuanByGejala(mPengguna.getGejala());
    }

    @Override
    public void getResponse(Responses responses) {
        Toast.makeText(this, responses.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPengetahuanByGejala(List<Pengetahuan> pengetahuanList) {
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


        Collections.sort(mPenyakitList, new Comparator<Penyakit>() {
            @Override
            public int compare(Penyakit o1, Penyakit o2) {
                return Float.compare(o2.getValueCF(), o1.getValueCF());
            }
        });

        tvRumusCF.setText(rumusCF);
        tvPenyakitCF.setText(mPenyakitList.get(0).getNamaPenyakit());
        tvKemungkinanCF.setText(mPenyakitList.get(0).getValueCF()+"%");

    }

    @Override
    public void getPenyakitList(List<Penyakit> penyakitList) {
        mPenyakitList.addAll(penyakitList);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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