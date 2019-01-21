package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Ubah_Apotik_Request  extends StringRequest {
    private static final String UBAH_REQUEST_URL = "https://debruyne.000webhostapp.com/update_Apotik.php";
    private Map<String, String> params;

    public Ubah_Apotik_Request(String idApotik, String namaApotik, String alamatApotik, Response.Listener<String> listener){
        super(Method.POST, UBAH_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("IdApotik",idApotik);
        params.put("NamaApotik",namaApotik);
        params.put("AlamatApotik",alamatApotik);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
