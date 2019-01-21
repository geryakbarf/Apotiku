package apotik.pbo2.apotiku.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Cari_Request extends StringRequest {
    private static final String CARI_REQUEST_URL = "http://debruyne.000webhostapp.com/cari_obat.php";
    private Map<String, String> params;

    public Cari_Request(String idApotik, String searchQuery, Response.Listener<String> listener){
        super(Request.Method.POST, CARI_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("IdApotik", idApotik);
        params.put("searchQuery", searchQuery);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
