package apotik.pbo2.apotiku.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Baca_Apotik_Request extends StringRequest {
    private static final String APOTIK_REQUEST_URL = "http://debruyne.000webhostapp.com/get_Apotik.php";
    private Map<String, String> params;

    public Baca_Apotik_Request(String idApotik, Response.Listener<String> listener){
        super(Request.Method.POST, APOTIK_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("IdApotik", idApotik);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
