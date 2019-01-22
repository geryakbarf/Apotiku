package apotik.pbo2.apotiku.Data;

public class Data {
    String idGambar;
    String namaPegawai;
    String nomorKontak;
    String username;

    public Data(String idGambar, String namaPegawai, String nomorKontak, String username){
        this.idGambar=idGambar;
        this.namaPegawai=namaPegawai;
        this.nomorKontak=nomorKontak;
        this.username=username;
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

    public String getUsername() {
        return username;
    }
}
