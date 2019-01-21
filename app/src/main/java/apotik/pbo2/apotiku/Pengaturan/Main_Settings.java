package apotik.pbo2.apotiku.Pengaturan;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Main_Settings extends AppCompatActivity implements View.OnClickListener {

    Session1 session1;
    LinearLayout bt_Akun;
    LinearLayout bt_Apotik;
    public int hak;
    public String akses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Pengaturan");
        setContentView(R.layout.activity_main__settings);
        bt_Apotik=findViewById(R.id.bt_Apotik);
        bt_Akun=findViewById(R.id.bt_Akun);
        bt_Apotik.setOnClickListener(this);
        bt_Akun.setOnClickListener(this);
        session1 = new Session1(this);
        HashMap<String,String> user=session1.getUserDetails();
        akses = user.get(session1.KEY_HAK_AKSES);
        hak = Integer.parseInt(akses);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_Akun){
            startActivity(new Intent(getApplicationContext(), Akun_Settings.class));
        } else {
            if(hak==1){
                startActivity(new Intent(getApplicationContext(),Apotik_Settings.class));
            }else Toast.makeText(getApplicationContext(),"Menu Ini Hanya Bisa Diakses oleh Admin!",Toast.LENGTH_SHORT).show();
        }
    }
}
