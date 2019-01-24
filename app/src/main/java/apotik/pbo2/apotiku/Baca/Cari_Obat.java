package apotik.pbo2.apotiku.Baca;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apotik.pbo2.apotiku.Adapter.Adapter;
import apotik.pbo2.apotiku.Data.List_Data;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Baca_Request_Jenis;
import apotik.pbo2.apotiku.Request.Cari_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Cari_Obat extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    EditText et_Cari;
    ImageView bt_CariObat;
    private List<List_Data> list_data;
    private RecyclerView rv;
    private Adapter adapter;
    Session1 session1;
    public String idApotik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Cari Obat");
        ab.hide();
        setContentView(R.layout.activity_cari__obat);
        et_Cari=findViewById(R.id.et_Cari);
        bt_CariObat=findViewById(R.id.bt_CariObat);
        bt_CariObat.setOnClickListener(this);
        rv= findViewById(R.id.recyclerviewCari);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter= new Adapter(getApplicationContext(),list_data);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        idApotik = user.get(session1.KEY_IDAPOTIK);
        progressDialog = new ProgressDialog(this);
    }

    private void cari(){
        String query= et_Cari.getText().toString().trim();
        if(TextUtils.isEmpty(query)){
            et_Cari.setError("Masukkan Nama Obat Yang Dicari!");
            return;
        }
        list_data.clear();
        progressDialog.setTitle("Mencari...");
        progressDialog.show();
        Response.Listener<String> listener =    new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    progressDialog.dismiss();
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
        Cari_Request cari_request = new Cari_Request(idApotik, query, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Cari_Obat.this);
        requestQueue.add(cari_request);
    }


    @Override
    public void onClick(View v) {
        cari();
    }
}
