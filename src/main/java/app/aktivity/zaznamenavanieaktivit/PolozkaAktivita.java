package app.aktivity.zaznamenavanieaktivit;

import androidx.annotation.NonNull;

public class PolozkaAktivita {
    private String id;
    private String nazov, pocet, farba;

    public PolozkaAktivita(String id, String nazov, String pocet, String farba) {
        this.id = id;
        this.nazov = nazov;
        this.pocet = pocet;
        this.farba = farba;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getPocet() {
        return pocet;
    }

    public void setPocet(String pocet) {
        this.pocet = pocet;
    }

    public String getFarba() {
        return farba;
    }

    public void setFarba(String farba) {
        this.farba = farba;
    }

    @NonNull
    @Override
    public String toString() {
        return nazov + "\n" + pocet;
    }
}
