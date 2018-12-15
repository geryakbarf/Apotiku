package apotik.pbo2.apotiku.Login_Register;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;
import apotik.pbo2.apotiku.UserActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button bt_Login_Login;
    private EditText et_Login_Email;
    private  EditText et_Login_Password;
    private TextView t_Login_Register;

    private ProgressDialog progressBar;
    Session1 session1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        t_Login_Register = (TextView)findViewById(R.id.t_Login_Register);
        t_Login_Register.setOnClickListener(this);
        bt_Login_Login=(Button)findViewById(R.id.bt_Register_Register);
        bt_Login_Login.setOnClickListener(this);
        et_Login_Email = (EditText)findViewById(R.id.et_Login_Email);
        et_Login_Password = (EditText)findViewById(R.id.et_Login_Password);
        session1 = new Session1(getApplicationContext());
        progressBar = new ProgressDialog(this);

    }

    private void login(){
        final String userName = et_Login_Email.getText().toString().trim();
        String password = et_Login_Password.getText().toString().trim();

        //Jika Email tidak diisi
        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Username harap diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Jika Password tidak diisi
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password harap diisi!", Toast.LENGTH_SHORT).show();
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
                        String namaPegawai = jsonObject.getString("NamaPegawai");
                        String hakAkses = jsonObject.getString("HakAkses");
                        String idApotik = jsonObject.getString("IdApotik");
                        session1.createLoginSession(namaPegawai,hakAkses,idApotik);
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
