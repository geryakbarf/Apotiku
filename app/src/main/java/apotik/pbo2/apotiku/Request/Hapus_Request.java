package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Hapus_Request extends StringRequest {
    private static final String HAPUS_REQUEST_URL = "https://debruyne.000webhostapp.com/hapus_obat.php";
    private Map<String, String> params;

    public Hapus_Request(String namaObat, String idApotik, Response.Listener<String> listener){
        super(Method.POST, HAPUS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("NamaObat",namaObat);
        params.put("IdApotik",idApotik);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
