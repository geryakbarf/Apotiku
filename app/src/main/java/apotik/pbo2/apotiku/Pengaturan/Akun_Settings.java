package apotik.pbo2.apotiku.Pengaturan;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apotik.pbo2.apotiku.R;

public class Akun_Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Pengaturan Akun");
        setContentView(R.layout.activity_akun__settings);
    }
}
