package apotik.pbo2.apotiku.Data;

public class List_Data {
    String idGambar;
    String obatNama;
    int obatHarga;
    int obatStok;
    String obatZat;
    String obatKhasiat;
    String obatJenis;

    public List_Data(){

    }

    public List_Data(String idGambar, String obatNama, String obatJenis , String obatZat , String obatKhasiat , int obatStok, int obatHarga){
        this.idGambar=idGambar;
        this.obatNama=obatNama;
        this.obatHarga=obatHarga;
        this.obatStok=obatStok;
        this.obatZat=obatZat;
        this.obatKhasiat=obatKhasiat;
        this.obatJenis=obatJenis;
    }

    public String getIdGambar() {
        return idGambar;
    }

    public String getObatNama() {
        return obatNama;
    }

    public int getObatHarga() {
        return obatHarga;
    }

    public int getObatStok() {
        return obatStok;
    }

    public String getObatZat() {
        return obatZat;
    }

    public String getObatKhasiat() {
        return obatKhasiat;
    }

    public String getObatJenis() {
        return obatJenis;
    }

}
