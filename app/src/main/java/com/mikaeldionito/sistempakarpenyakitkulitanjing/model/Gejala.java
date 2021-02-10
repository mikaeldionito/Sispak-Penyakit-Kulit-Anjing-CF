package com.mikaeldionito.sistempakarpenyakitkulitanjing.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Gejala {
    @SerializedName("kode_gejala")
    private String kodeGejala;
    @SerializedName("gejala")
    private String gejala;
    @SerializedName("bobot")
    private String bobot;

    public String getKodeGejala() {
        return kodeGejala;
    }

    public void setKodeGejala(String kodeGejala) {
        this.kodeGejala = kodeGejala;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public static final Comparator<Gejala> BY_NAME_ALPHABETICAL = new Comparator<Gejala>() {
        @Override
        public int compare(Gejala gejala, Gejala t1) {
            return gejala.getGejala().compareTo(t1.getGejala());
        }
    };
}
