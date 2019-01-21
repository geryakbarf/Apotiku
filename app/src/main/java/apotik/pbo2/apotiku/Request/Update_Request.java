package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Update_Request extends StringRequest {
    private static final String UPDATE_REQUEST_URL = "https://debruyne.000webhostapp.com/update_obat.php";
    private Map<String, String> params;

    public Update_Request(String namaAwal, String namaObat, int hargaObat, int stokObat, String zatAktif, String khasiatObat, String idApotik, Response.Listener<String> listener){
        super(Method.POST, UPDATE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("NamaAwal",namaAwal);
        params.put("NamaObat",namaObat);
        params.put("HargaObat",hargaObat+"");
        params.put("StokObat",stokObat+"");
        params.put("ZatAktif",zatAktif);
        params.put("KhasiatObat",khasiatObat);
        params.put("IdApotik",idApotik);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
