package apotik.pbo2.apotiku.Pengaturan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import apotik.pbo2.apotiku.Baca.Edit_Data;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Baca_Apotik_Request;
import apotik.pbo2.apotiku.Request.Ubah_Apotik_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;
import apotik.pbo2.apotiku.UserActivity;

public class Apotik_Settings extends AppCompatActivity implements View.OnClickListener {

    Session1 session1;
    TextView txt_NamaApotik,txt_AlamatApotik;
    Button bt_UbahApotik;
    String id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Pengaturan Apotik");
        setContentView(R.layout.activity_apotik__settings);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        id=user.get(session1.KEY_IDAPOTIK);
        txt_NamaApotik=findViewById(R.id.txt_NamaApotik);
        txt_AlamatApotik=findViewById(R.id.txt_AlamatApotik);
        bt_UbahApotik=findViewById(R.id.bt_UbahApotik);
        bt_UbahApotik.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        getData();
    }

    private void getData(){
        progressDialog.setTitle("Tunggu....");
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    progressDialog.dismiss();
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i< array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        String nama = ob.getString("nama");
                        String alamat = ob.getString("alamat");
                        txt_NamaApotik.setText(nama);
                        txt_AlamatApotik.setText(alamat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Baca_Apotik_Request  baca_apotik_request = new Baca_Apotik_Request(id,listener);
        RequestQueue requestQueue=Volley.newRequestQueue(Apotik_Settings.this);
        requestQueue.add(baca_apotik_request);
    }

    private void ubahData(){
        String nama = txt_NamaApotik.getText().toString().trim();
        if(TextUtils.isEmpty(nama)){
            txt_NamaApotik.setError("Harap mengisi nama Apotik!");
            return;
        }
        String alamat = txt_AlamatApotik.getText().toString().trim();
        if(TextUtils.isEmpty(alamat)){
            txt_AlamatApotik.setError("Harap mengisis alamat Apotik");
            return;
        }

        progressDialog.setTitle("Sedang Mengubah data...");
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Data Terubah",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Apotik_Settings.this);
                        builder.setMessage("Gagal Mengubah Data")
                                .setNegativeButton("Coba Lagi", null)
                                .create()
                                .show();
                    }
                }catch (JSONException a){
                    a.printStackTrace();
                }
            }
        };
        Ubah_Apotik_Request ubah_apotik_request = new Ubah_Apotik_Request(id,nama,alamat,listener);
        RequestQueue requestQueue =Volley.newRequestQueue(Apotik_Settings.this);
        requestQueue.add(ubah_apotik_request);

    }

    @Override
    public void onClick(View v) {
        if(v == bt_UbahApotik){
            ubahData();
        }
    }
}
