package apotik.pbo2.apotiku.Data;

public class Data {
    String idGambar;
    String namaPegawai;
    String nomorKontak;

    public Data(String idGambar, String namaPegawai, String nomorKontak){
        this.idGambar=idGambar;
        this.namaPegawai=namaPegawai;
        this.nomorKontak=nomorKontak;
    }

    public String getIdGambar() {
        return idGambar;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public String getNomorKontak() {
        return nomorKontak;
    }
}
