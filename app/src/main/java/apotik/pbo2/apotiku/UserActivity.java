package apotik.pbo2.apotiku;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.HashMap;

import apotik.pbo2.apotiku.Baca.Cari_Obat;
import apotik.pbo2.apotiku.Pegawai.Lihat_Pegawai;
import apotik.pbo2.apotiku.Pegawai.Pegawai;
import apotik.pbo2.apotiku.Pengaturan.Main_Settings;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {


    Session1 session1;
    LinearLayout bt_Tambah;
    LinearLayout bt_Daftar_Obat;
    LinearLayout bt_Keluar;
    LinearLayout bt_Pegawai;
    LinearLayout bt_Settings, bt_Cari;
    TextView t_Pegawai;

    public int hak;
    public String akses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Menu Utama");
        setContentView(R.layout.activity_user);
        session1 = new Session1(getApplicationContext());
        bt_Daftar_Obat=findViewById(R.id.bt_Daftar_Obat);
        bt_Cari=findViewById(R.id.bt_Cari);
        t_Pegawai=findViewById(R.id.t_Pegawai);
        bt_Tambah=findViewById(R.id.bt_Tambah);
        bt_Keluar=findViewById(R.id.bt_Keluar);
        bt_Pegawai=findViewById(R.id.bt_Pegawai);
        bt_Settings=findViewById(R.id.bt_Settings);
        bt_Settings.setOnClickListener(this);
        bt_Pegawai.setOnClickListener(this);
        bt_Tambah.setOnClickListener(this);
        bt_Cari.setOnClickListener(this);
        bt_Daftar_Obat.setOnClickListener(this);
        bt_Keluar.setOnClickListener(this);
        HashMap<String,String> user=session1.getUserDetails();
        String NamaPegawai = user.get(session1.KEY_NAMA_PEGAWAI);
        akses = user.get(session1.KEY_HAK_AKSES);
        hak = Integer.parseInt(akses);
        t_Pegawai.setText(NamaPegawai);
        t_Pegawai.setOnClickListener(this);

    }

    private void pegawai(){
        if(hak > 1) {
            Intent intent = new Intent(getApplicationContext(), Lihat_Pegawai.class);
            startActivity(intent);
        }else startActivity(new Intent(getApplicationContext(), Pegawai.class));
    }

    private void daftar() {
        Intent intent = new Intent(getApplicationContext(), Daftar_Obat.class);
        startActivity(intent);
    }

    private void tambah() {
        Intent intent = new Intent(getApplicationContext(), Tambah_Activity.class);
        startActivity(intent);
    }

    private void settings(){
        startActivity(new Intent(getApplicationContext(), Main_Settings.class));
    }

    private void cari(){
        startActivity(new Intent(getApplicationContext(), Cari_Obat.class));
    }

    private void akun(){
        startActivity(new Intent(getApplicationContext(), Akun_Activity.class));
    }

    public void onClick(View view) {

        if(view == bt_Tambah){
            tambah();
        } else if(view == bt_Daftar_Obat){
            daftar();
        } else if(view == bt_Pegawai){
            pegawai();
        }else if(view == bt_Settings){
            settings();
        } else if(view == t_Pegawai){
            akun();
        } else if(view == bt_Cari){
            cari();
        }
    }
}

