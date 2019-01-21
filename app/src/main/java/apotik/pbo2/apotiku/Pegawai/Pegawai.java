package apotik.pbo2.apotiku.Pegawai;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import apotik.pbo2.apotiku.R;

public class Pegawai extends AppCompatActivity implements View.OnClickListener {

    CardView bt_Lihat;
    CardView bt_Tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Menu Pegawai");
        setContentView(R.layout.activity_pegawai);
        bt_Lihat=(CardView)findViewById(R.id.bt_Lihat);
        bt_Tambah=(CardView)findViewById(R.id.bt_Tambah);
        bt_Tambah.setOnClickListener(this);
        bt_Lihat.setOnClickListener(this);
    }

    private void lihat(){
        Intent intent = new Intent(getApplicationContext(),Lihat_Pegawai.class);
        startActivity(intent);
    }

    private void tambah(){
        Intent intent = new Intent(getApplicationContext(), Tambah_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_Lihat){
            lihat();
        } else if(v == bt_Tambah){
            tambah();
        }
    }
}
