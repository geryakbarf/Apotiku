package apotik.pbo2.apotiku.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Baca_Request_Jenis extends StringRequest {
    private static final String LIHAT_REQUEST_URL = "http://debruyne.000webhostapp.com/get_obat_jenis.php";
    private Map<String, String> params;

    public Baca_Request_Jenis(String idApotik, String jenis, Response.Listener<String> listener){
        super(Request.Method.POST, LIHAT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("IdApotik", idApotik);
        params.put("Jenis",jenis);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
