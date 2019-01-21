package apotik.pbo2.apotiku;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Akun_Activity extends AppCompatActivity implements View.OnClickListener {

    Session1 session1;
    ProgressDialog progressDialog;
    Button bt_Logout;
    TextView txt_Username,txt_Nama, txt_Nokon;
    ImageView img_Gambar;
    String username,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Akun Anda");
        setContentView(R.layout.activity_akun_);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        username = user.get(session1.KEY_USERNAME);
        nama = user.get(session1.KEY_NAMA_PEGAWAI);
        bt_Logout=findViewById(R.id.bt_Logout);
        bt_Logout.setOnClickListener(this);
        txt_Username=findViewById(R.id.txt_Username);
        txt_Nama=findViewById(R.id.txt_Nama);
        txt_Nokon=findViewById(R.id.txt_Nomor);
        img_Gambar=findViewById(R.id.image_Foto);

        progressDialog= new ProgressDialog(this);
        getAkun();

    }

    private void logout() {
        session1.logoutUser();
    }

    private void getAkun(){
        progressDialog.setTitle("Memuat...");
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i< array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        String nokon = ob.getString("NomorKontak");
                        String idgambar = ob.getString("IdGambar");
                        txt_Nama.setText(nama);
                        txt_Nokon.setText(nokon);
                        txt_Username.setText(username);
                        Glide.with(getApplicationContext()).load(idgambar).into(img_Gambar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Akun_Request akun_request = new Akun_Request(username, listener);
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(akun_request);
    }

    public class Akun_Request extends StringRequest {
        private static final String AKUN_REQUEST_URL = "https://debruyne.000webhostapp.com/get_akun.php";
        private Map<String, String> params;

        public Akun_Request(String Username, Response.Listener<String> listener){
            super(Request.Method.POST, AKUN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("Username",Username);
        }
        @Override
        public Map<String, String> getParams(){
            return params;
        }

    }

    @Override
    public void onClick(View v) {
        logout();
    }
}
