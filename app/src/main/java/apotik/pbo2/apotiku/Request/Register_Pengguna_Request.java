package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Register_Pengguna_Request extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://debruyne.000webhostapp.com/get_register.php";
    private Map<String, String> params;

public Register_Pengguna_Request(String UserName, String NamaPegawai, String NomorKontak, String Password, String HakAkses, String IdGambar, String IdApotik, Response.Listener<String> listener){
    super(Method.POST, REGISTER_REQUEST_URL, listener, null);
    params = new HashMap<>();
    params.put("UserName",UserName);
    params.put("NamaPegawai",NamaPegawai);
    params.put("NomorKontak",NomorKontak);
    params.put("Password",Password);
    params.put("HakAkses",HakAkses);
    params.put("IdGambar",IdGambar);
    params.put("IdApotik",IdApotik);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}