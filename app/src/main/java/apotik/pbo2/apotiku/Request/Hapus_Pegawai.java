package apotik.pbo2.apotiku.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Hapus_Pegawai extends StringRequest {
    private static final String HAPUS_REQUEST_URL = "https://debruyne.000webhostapp.com/hapus_Pegawai.php";
    private Map<String, String> params;

    public Hapus_Pegawai(String username, String idApotik, Response.Listener<String> listener){
        super(Method.POST, HAPUS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Username",username);
        params.put("IdApotik",idApotik);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
