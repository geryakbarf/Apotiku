package apotik.pbo2.apotiku.Baca;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apotik.pbo2.apotiku.Adapter.Adapter;
import apotik.pbo2.apotiku.Data.List_Data;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Baca_Request_Jenis;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Baca_Lain extends AppCompatActivity {
    private List<List_Data> list_data;
    private RecyclerView rv;
    private Adapter adapter;
    Session1 session1;
    public String idApotik;
    public String jenis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Lain - Lain");
        setContentView(R.layout.activity_baca__obat);
        rv=(RecyclerView) findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter= new Adapter(getApplicationContext(),list_data);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        idApotik = user.get(session1.KEY_IDAPOTIK);
        jenis = "Lain - Lain";
        getObat();
    }

    private void getObat(){ Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i=0; i< array.length(); i++){
                    JSONObject ob = array.getJSONObject(i);
                    List_Data data = new List_Data(ob.getString("IdGambar"),ob.getString("obatNama")
                            ,ob.getString("obatJenis")
                            ,ob.getString("obatZat")
                            ,ob.getString("obatKhasiat")
                            ,ob.getInt("obatStok")
                            ,ob.getInt("obatHarga"));
                    list_data.add(data);
                }
                rv.setAdapter(adapter);
            } catch (JSONException a){
                a.printStackTrace();
            }
        }
    };
        Baca_Request_Jenis baca_request = new Baca_Request_Jenis(idApotik, jenis, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Baca_Lain.this);
        requestQueue.add(baca_request);

    }

}
