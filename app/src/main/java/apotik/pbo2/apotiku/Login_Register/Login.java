package apotik.pbo2.apotiku.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Login_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;
import apotik.pbo2.apotiku.UserActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Inisialisasi Button dan EditText yang di Layout
    private Button bt_Login_Login;
    private EditText et_Login_Email;
    private  EditText et_Login_Password;
    private TextView t_Login_Register;
    //Inisialisasi Session dan variabel
    private ProgressDialog progressBar;
    Session1 session1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mengubah Nama Action Bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Login");
        setContentView(R.layout.activity_login);
        //Inisialiasasi pada Oncreate
        t_Login_Register = findViewById(R.id.t_Login_Register);
        t_Login_Register.setOnClickListener(this);
        bt_Login_Login=findViewById(R.id.bt_Register_Register);
        bt_Login_Login.setOnClickListener(this);
        et_Login_Email = findViewById(R.id.et_Login_Email);
        et_Login_Password = findViewById(R.id.et_Login_Password);
        session1 = new Session1(getApplicationContext());
        progressBar = new ProgressDialog(this);

    }

    //Method Login
    private void login(){
        final String userName = et_Login_Email.getText().toString().trim();
        String password = et_Login_Password.getText().toString().trim();

        //Verifikasi Jika Username tidak diisi
        if(TextUtils.isEmpty(userName)) {
            et_Login_Email.setError("Username Harap Diisi!");
            return;
        }
        //Verifikasi Jika Password tidak diisi
        if(TextUtils.isEmpty(password)) {
            et_Login_Password.setError("Password Harap Diisi");
            return;
        }
        //ProgressBar
        progressBar.setMessage("Harap Tunggu, sedang login..");
        progressBar.show();
        //Login Progress
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        progressBar.dismiss();
                        //Mengambil variabel dari Databases untuk dijadikan Session
                        String namaPegawai = jsonObject.getString("NamaPegawai");
                        String hakAkses = jsonObject.getString("HakAkses");
                        String idApotik = jsonObject.getString("IdApotik");
                        session1.createLoginSession(namaPegawai,hakAkses,idApotik,userName);
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        progressBar.dismiss();
                        Toast.makeText(getApplicationContext(),"Login Gagal, Coba lagi",Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException a){
                    a.printStackTrace();
                }
            }
        };
        //Konek ke Databases
        Login_Request login_request = new Login_Request(userName,password, response);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(login_request);
            }

    public void onClick(View view){
        if(view == bt_Login_Login){
            login();
        }else if(view == t_Login_Register){
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
        }
    }
}
