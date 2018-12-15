package apotik.pbo2.apotiku.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.UserActivity;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button bt_Register_Register;
    private EditText et_Nama;
    private EditText et_Alamat;
    private TextView t_Register_Login;
    private EditText et_Id;

    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        t_Register_Login = (TextView)findViewById(R.id.t_Register_Login);
        t_Register_Login.setOnClickListener(this);
        progressBar = new ProgressDialog(this);
        bt_Register_Register = (Button)findViewById(R.id.bt_Register_Register);
        bt_Register_Register.setOnClickListener(this);
        et_Nama=(EditText)findViewById(R.id.et_Nama);
        et_Alamat=(EditText)findViewById(R.id.et_Alamat);
        et_Id=(EditText)findViewById(R.id.et_Id);

    }

    private void register(){
        String NamaApotik = et_Nama.getText().toString().trim();
        String AlamatApotik = et_Alamat.getText().toString().trim();
        final String IdApotik = et_Id.getText().toString().trim();

        //Jika User tidak mengisi Email
        if(TextUtils.isEmpty(NamaApotik)){
            Toast.makeText(this, "Nama Apotik harap diisi!",Toast.LENGTH_SHORT).show();
            return;
        }
        //Jika User tidak mengisi Password
        if(TextUtils.isEmpty(AlamatApotik)){
            Toast.makeText(this, "ALamat Apotik harap diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        //Jika User tidak mengisi ID
        if(TextUtils.isEmpty(IdApotik)){
            Toast.makeText(this, "ID Apotik harap diisi!",Toast.LENGTH_SHORT).show();
            return;
        }

        //ProgressBar
        progressBar.setMessage("Harap Tunggu, sedang mendaftarkan..");
        progressBar.show();

        //Mengirim data ke Database
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
        //
        Register_Request register_request = new Register_Request(IdApotik, NamaApotik, AlamatApotik, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(register_request);
    }

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
