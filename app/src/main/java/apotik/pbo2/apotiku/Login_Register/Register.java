package apotik.pbo2.apotiku.Login_Register;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Register_Request;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //Inisialisasi Button dan EditText yang di Layout
    private Button bt_Register_Register;
    private EditText et_Nama;
    private EditText et_Alamat;
    private TextView t_Register_Login;
    private EditText et_Id;
    //Inisialisasi variabel
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mengubah Nama Action Bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Register Apotik");
        setContentView(R.layout.activity_register);
        //Inisialisasi pada OnCreate
        t_Register_Login = findViewById(R.id.t_Register_Login);
        t_Register_Login.setOnClickListener(this);
        progressBar = new ProgressDialog(this);
        bt_Register_Register = findViewById(R.id.bt_Register_Register);
        bt_Register_Register.setOnClickListener(this);
        et_Nama=findViewById(R.id.et_Nama);
        et_Alamat=findViewById(R.id.et_Alamat);
        et_Id=findViewById(R.id.et_Id);

    }

    //Method Register
    private void register(){
        String NamaApotik = et_Nama.getText().toString().trim();
        String AlamatApotik = et_Alamat.getText().toString().trim();
        final String IdApotik = et_Id.getText().toString().trim();

        //Verifikasi Jika User tidak mengisi Nama Apotik
        if(TextUtils.isEmpty(NamaApotik)){
            et_Nama.setError("Nama Apotik harap diisi!");
            return;
        }
        //Verifikasi Jika User tidak mengisi IDApotik
        if(TextUtils.isEmpty(IdApotik)){
            et_Id.setError("Id Apotik harap diisi!");
            return;
        }

        //Verifikasi Jika User tidak mengisi Alamat Apotik
        if(TextUtils.isEmpty(AlamatApotik)){
            et_Alamat.setError("Alamat Apotik harap diisi!");
            return;
        }

        //ProgressBar
        progressBar.setMessage("Harap Tunggu, sedang mendaftarkan..");
        progressBar.show();

        //Mengirim data ke Database(Proses Register)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsresponse = new JSONObject(response);
                    boolean success = jsresponse.getBoolean("success");
                    if(success){
                        progressBar.dismiss();
                        Intent intent = new Intent(getApplicationContext(), Register_Pengguna.class);
                        intent.putExtra("IdApotik", IdApotik);
                        startActivity(intent);
                    }else{
                        progressBar.dismiss();
                        String data = jsresponse.getString("data");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setMessage(data)
                                .setNegativeButton("Coba Lagi", null)
                                .create()
                                .show();
                    }
                } catch (JSONException a){
                    a.printStackTrace();
                }
            }
        };
        //Konek ke Databases
        Register_Request register_request = new Register_Request(IdApotik, NamaApotik, AlamatApotik, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(register_request);
    }

    //Pindah ke Menu Login
    private void login(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view){
        if(view == bt_Register_Register){
            register();
        }else if(view == t_Register_Login){
            login();
        }
    }

}
