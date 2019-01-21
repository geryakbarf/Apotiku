package apotik.pbo2.apotiku;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;

import apotik.pbo2.apotiku.Login_Register.Login;
import apotik.pbo2.apotiku.Login_Register.Register;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_Register;
    private Button bt_Login;
    Session1 session1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Selamat Datang");
        setContentView(R.layout.activity_main);
        bt_Login = (Button)findViewById(R.id.bt_Login);
        bt_Register=(Button)findViewById(R.id.bt_Register);
        bt_Register.setOnClickListener(this);
        bt_Login.setOnClickListener(this);
        session1 = new Session1(getApplicationContext());
        session1.checkLogin();


    }

    private void registerForm(){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    private void loginForm(){
        Intent intent1 = new Intent(MainActivity.this, Login.class);
        startActivity(intent1);
    }

    public void onClick(View view){
        if(view == bt_Login ){
            loginForm();
        }else if(view == bt_Register){
            registerForm();
        }
    }
}
