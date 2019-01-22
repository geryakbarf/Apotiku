package apotik.pbo2.apotiku.Pengaturan;

import android.app.ProgressDialog;
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

import java.util.HashMap;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Update_Pass_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Akun_Settings extends AppCompatActivity implements View.OnClickListener {

    EditText txt_Namaku, txt_Kontak,txt_Passlama,txt_Passbaru;
    String idUser;
    Session1 session1;
    ProgressDialog progressDialog;
    Button bt_UbahPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Pengaturan Akun");
        setContentView(R.layout.activity_akun__settings);
        session1= new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        idUser=user.get(session1.KEY_USERNAME);
        txt_Namaku=findViewById(R.id.txt_Namaku);
        txt_Kontak=findViewById(R.id.txt_Kontak);
        txt_Passbaru=findViewById(R.id.txt_Passbaru);
        txt_Passlama=findViewById(R.id.txt_Passlama);
        progressDialog = new ProgressDialog(this);
        bt_UbahPass=findViewById(R.id.bt_UbahPass);
        bt_UbahPass.setOnClickListener(this);
    }


    private void ubahPassword(){
        String passLama = txt_Passlama.getText().toString().trim();
        if(TextUtils.isEmpty(passLama)){
            txt_Passlama.setError("Harap Masukkan Password lama anda!");
            return;
        }

        String passBaru = txt_Passbaru.getText().toString().trim();
        if(TextUtils.isEmpty(passBaru)){
            txt_Passbaru.setError("Harap Masukkan Password baru anda!");
            return;
        }

        progressDialog.setTitle("Harap Tunggu...");
        progressDialog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Password telah Diubah!",Toast.LENGTH_SHORT).show();
                        txt_Passbaru.setText("");
                        txt_Passlama.setText("");
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),jsonObject.getString("data"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Update_Pass_Request update_pass_request= new Update_Pass_Request(passLama,passBaru,idUser,listener);
        RequestQueue requestQueue=Volley.newRequestQueue(Akun_Settings.this);
        requestQueue.add(update_pass_request);


    }

    @Override
    public void onClick(View v) {
        if(v == bt_UbahPass){
            ubahPassword();
        }
    }
}
