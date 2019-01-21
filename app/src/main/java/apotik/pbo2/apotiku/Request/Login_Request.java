package apotik.pbo2.apotiku.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login_Request extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://debruyne.000webhostapp.com/login.php";
    private Map<String, String> params;

    public Login_Request(String UserName, String Password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("UserName",UserName);
        params.put("Password",Password);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }

}
