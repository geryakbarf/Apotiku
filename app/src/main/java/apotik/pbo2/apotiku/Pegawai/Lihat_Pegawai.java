package apotik.pbo2.apotiku.Pegawai;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

import apotik.pbo2.apotiku.Adapter.MyAdapter;
import apotik.pbo2.apotiku.Data.Data;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Lihat_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Lihat_Pegawai extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Data> list;
    GridLayoutManager gridLayoutManager;
    public String idApotik;
    Session1 session1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Lihat Pegawai");
        setContentView(R.layout.activity_lihat__pegawai);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(!false);
        gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        list=new ArrayList<>();
        adapter=new MyAdapter(Lihat_Pegawai.this,list);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        idApotik = user.get(session1.KEY_IDAPOTIK);
        lihatPegawai();
    }

    private void lihatPegawai(){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i< array.length(); i++){
                        JSONObject ob = array.getJSONObject(i);
                        Data data = new Data(ob.getString("IdGambar"),ob.getString("Nama"),ob.getString("Nomor"));
                        list.add(data);
                    }
                    recyclerView.setAdapter(adapter);
                } catch (JSONException a){
                    a.printStackTrace();
                }
            }
        };
        Lihat_Request lihat_request = new Lihat_Request(idApotik, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Lihat_Pegawai.this);
        requestQueue.add(lihat_request);
    }
}
