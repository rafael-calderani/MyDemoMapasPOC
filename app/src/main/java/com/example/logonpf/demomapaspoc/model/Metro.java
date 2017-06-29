package com.example.logonpf.demomapaspoc.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Metro implements Parcelable {

    private String cor;
    private String numero;
    private String urlImagem;

    protected Metro(Parcel in) {
        cor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cor);
    }

    public static final Creator<Metro> CREATOR = new Creator<Metro>() {
        @Override
        public Metro createFromParcel(Parcel in) {
            return new Metro(in);
        }

        @Override
        public Metro[] newArray(int size) {
            return new Metro[size];
        }
    };

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUrlImagem() {
        return urlImagem;
    }
    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
