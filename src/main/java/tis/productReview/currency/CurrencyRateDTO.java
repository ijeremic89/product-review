package tis.productReview.currency;

public class CurrencyRateDTO {

    private String broj_tecajnice;
    private String datum_primjene;
    private String drzava;
    private String drzava_iso;
    private String kupovni_tecaj;
    private String prodajni_tecaj;
    private String sifra_valute;
    private String srednji_tecaj;
    private String valuta;

    public String getBroj_tecajnice() {
        return broj_tecajnice;
    }

    public void setBroj_tecajnice(String broj_tecajnice) {
        this.broj_tecajnice = broj_tecajnice;
    }

    public String getDatum_primjene() {
        return datum_primjene;
    }

    public void setDatum_primjene(String datum_primjene) {
        this.datum_primjene = datum_primjene;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getDrzava_iso() {
        return drzava_iso;
    }

    public void setDrzava_iso(String drzava_iso) {
        this.drzava_iso = drzava_iso;
    }

    public String getKupovni_tecaj() {
        return kupovni_tecaj;
    }

    public void setKupovni_tecaj(String kupovni_tecaj) {
        this.kupovni_tecaj = kupovni_tecaj;
    }

    public String getProdajni_tecaj() {
        return prodajni_tecaj;
    }

    public void setProdajni_tecaj(String prodajni_tecaj) {
        this.prodajni_tecaj = prodajni_tecaj;
    }

    public String getSifra_valute() {
        return sifra_valute;
    }

    public void setSifra_valute(String sifra_valute) {
        this.sifra_valute = sifra_valute;
    }

    public String getSrednji_tecaj() {
        return srednji_tecaj;
    }

    public void setSrednji_tecaj(String srednji_tecaj) {
        this.srednji_tecaj = srednji_tecaj;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }
}
