package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Update_Pass_Request extends StringRequest {
    private static final String PASS_REQUEST_URL = "https://debruyne.000webhostapp.com/update_Akun.php";
    private Map<String, String> params;

    public Update_Pass_Request(String passlama, String passbaru, String idUser, Response.Listener<String> listener){
        super(Method.POST, PASS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Passlama",passlama);
        params.put("Passbaru",passbaru);
        params.put("Username",idUser);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
