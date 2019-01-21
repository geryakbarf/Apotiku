package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Tambah_Request extends StringRequest {
    private static final String TAMBAH_REQUEST_URL = "https://debruyne.000webhostapp.com/send_obat.php";
    private Map<String, String> params;

    public Tambah_Request(String namaObat, int hargaObat, int stokObat, String zatAktif, String khasiatObat, String jenisObat, String idGambar, String idApotik, Response.Listener<String> listener){
        super(Method.POST, TAMBAH_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("NamaObat",namaObat);
        params.put("HargaObat",hargaObat+"");
        params.put("StokObat",stokObat+"");
        params.put("ZatAktif",zatAktif);
        params.put("KhasiatObat",khasiatObat);
        params.put("JenisObat",jenisObat);
        params.put("IdGambar",idGambar);
        params.put("IdApotik",idApotik);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
    }

