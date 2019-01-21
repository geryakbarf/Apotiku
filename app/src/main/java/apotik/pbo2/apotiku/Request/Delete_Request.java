package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Delete_Request extends StringRequest {
    private static final String DELETE_REQUEST_URL = "https://debruyne.000webhostapp.com/delete.php";
    private Map<String, String> params;

    public Delete_Request(String idApotik, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("IdApotik",idApotik);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}

