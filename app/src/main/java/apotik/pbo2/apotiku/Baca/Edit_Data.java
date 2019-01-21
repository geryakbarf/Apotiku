package apotik.pbo2.apotiku.Baca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Hapus_Request;
import apotik.pbo2.apotiku.Request.Update_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;
import apotik.pbo2.apotiku.UserActivity;

public class Edit_Data extends AppCompatActivity implements View.OnClickListener {

    Button bt_Ubah;
    Button bt_Hapus;
    EditText et_Nama;
    EditText et_Stok;
    EditText et_Harga;
    EditText et_Zat;
    EditText et_Khasiat;
    String namaAwal, zat, khasiat;
    int harga, stok;
    public String idApotik;
    ProgressDialog progressDialog;
    Session1 session1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Ubah Data Obat");
        setContentView(R.layout.activity_edit__data);
        et_Nama=findViewById(R.id.et_Nama);
        et_Harga=findViewById(R.id.et_Harga);
        et_Stok=findViewById(R.id.et_Stok);
        et_Zat=findViewById(R.id.et_Zat);
        et_Khasiat=findViewById(R.id.et_Khasiat);
        bt_Ubah=findViewById(R.id.bt_Ubah);
        bt_Hapus=findViewById(R.id.bt_Hapus);
        session1 = new Session1(getApplicationContext());
        HashMap<String,String> user=session1.getUserDetails();
        idApotik = user.get(session1.KEY_IDAPOTIK);
        progressDialog = new ProgressDialog(this);
        bt_Ubah.setOnClickListener(this);
        bt_Hapus.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle= intent.getExtras();
        namaAwal = (String)bundle.get("nama");
         zat = (String)bundle.get("zat");
         khasiat = (String)bundle.get("khasiat");
         harga = (Integer)bundle.get("harga");
         stok = (Integer)bundle.get("stok");
        et_Nama.setText(namaAwal);
        et_Harga.setText(harga+"");
        et_Stok.setText(stok+"");
        et_Khasiat.setText(khasiat);
        et_Zat.setText(zat);
    }

    private void update(){
        String namaObat = et_Nama.getText().toString().trim();
        if(TextUtils.isEmpty(namaObat)){
            Toast.makeText(getApplicationContext(),"Nama Obat Harus Diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        String zatObat = et_Zat.getText().toString().trim();
        if(TextUtils.isEmpty(zatObat)){
            Toast.makeText(getApplicationContext(),"Zat Aktif Obat Harap Diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        String khasiatObat = et_Khasiat.getText().toString().trim();
        if(TextUtils.isEmpty(khasiatObat)){
            Toast.makeText(getApplicationContext(),"Khasiat Obat Harap Diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        String hargaAwal = et_Harga.getText().toString().trim();
        if(TextUtils.isEmpty(hargaAwal)){
            Toast.makeText(getApplicationContext(),"Harga Obat Harap Diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        int hargaObat = Integer.parseInt(hargaAwal);

        String stokAwal = et_Stok.getText().toString().trim();
        if(TextUtils.isEmpty(stokAwal)){
            Toast.makeText(getApplicationContext(),"Stok Obat Harap Diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        int stokObat = Integer.parseInt(stokAwal);

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Data.this);
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
        Update_Request update_request = new Update_Request(namaAwal, namaObat, hargaObat, stokObat, zatObat, khasiatObat, idApotik, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Edit_Data.this);
        requestQueue.add(update_request);
    }

    private void hapus(){
        progressDialog.setTitle("Sedang Menghapus Data....");
        progressDialog.show();
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Data Terhapus",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Data.this);
                        builder.setMessage("Gagal Menghapus Data")
                                .setNegativeButton("Coba Lagi", null)
                                .create()
                                .show();
                    }
                }catch (JSONException a){
                    a.printStackTrace();
                }

            }
        };
        Hapus_Request hapus_request = new Hapus_Request(namaAwal, idApotik, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Edit_Data.this);
        requestQueue.add(hapus_request);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_Ubah){
            update();
        }else if(v == bt_Hapus){
            hapus();
        }
    }
}
