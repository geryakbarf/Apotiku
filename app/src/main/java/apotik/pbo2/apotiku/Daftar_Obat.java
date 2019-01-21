package apotik.pbo2.apotiku;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import apotik.pbo2.apotiku.Baca.Baca_Batuk;
import apotik.pbo2.apotiku.Baca.Baca_Demam;
import apotik.pbo2.apotiku.Baca.Baca_Flu;
import apotik.pbo2.apotiku.Baca.Baca_Lain;
import apotik.pbo2.apotiku.Baca.Baca_Luar;
import apotik.pbo2.apotiku.Baca.Baca_Maag;
import apotik.pbo2.apotiku.Baca.Baca_Obat;
import apotik.pbo2.apotiku.Baca.Baca_Pusing;
import apotik.pbo2.apotiku.Baca.Baca_Sakit_Perut;

public class Daftar_Obat extends AppCompatActivity implements View.OnClickListener {

    CardView bt_Semua;
    CardView bt_Batuk;
    CardView bt_Demam;
    CardView bt_Flu;
    CardView bt_Pusing;
    CardView bt_Maag;
    CardView bt_Sakit_Perut;
    CardView bt_Luar;
    CardView bt_Lain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Daftar Obat");
        setContentView(R.layout.activity_daftar__obat);
        bt_Semua = (CardView)findViewById(R.id.bt_Semua);
        bt_Batuk=(CardView)findViewById(R.id.bt_Batuk);
        bt_Demam=(CardView)findViewById(R.id.bt_Demam);
        bt_Flu=(CardView)findViewById(R.id.bt_Flu);
        bt_Pusing=(CardView)findViewById(R.id.bt_Pusing);
        bt_Maag=(CardView)findViewById(R.id.bt_Maag);
        bt_Sakit_Perut=(CardView)findViewById(R.id.bt_Sakit_perut);
        bt_Luar=(CardView)findViewById(R.id.bt_Luar);
        bt_Lain=(CardView)findViewById(R.id.bt_Lain);
        bt_Batuk.setOnClickListener(this);
        bt_Semua.setOnClickListener(this);
        bt_Demam.setOnClickListener(this);
        bt_Flu.setOnClickListener(this);
        bt_Pusing.setOnClickListener(this);
        bt_Maag.setOnClickListener(this);
        bt_Sakit_Perut.setOnClickListener(this);
        bt_Luar.setOnClickListener(this);
        bt_Lain.setOnClickListener(this);
    }

    private void semua(){
        startActivity(new Intent(getApplicationContext(), Baca_Obat.class));
    }

    private void demam(){startActivity(new Intent(getApplicationContext(), Baca_Demam.class));}

    private void flu(){startActivity(new Intent(getApplicationContext(), Baca_Flu.class));}

    private void pusing(){startActivity(new Intent(getApplicationContext(), Baca_Pusing.class));}

    private void maag(){startActivity(new Intent(getApplicationContext(), Baca_Maag.class));}

    private void sakit_perut(){startActivity(new Intent(getApplicationContext(), Baca_Sakit_Perut.class));}

    private void luar(){startActivity(new Intent(getApplicationContext(), Baca_Luar.class));}

    private void lain(){startActivity(new Intent(getApplicationContext(), Baca_Lain.class));}

    private void batuk(){ startActivity(new Intent(getApplicationContext(), Baca_Batuk.class));}

    @Override
    public void onClick(View v) {

        if(v == bt_Semua){
         semua();
        }else if(v == bt_Batuk){
          batuk();
        } else if(v == bt_Demam){
            demam();
        } else if(v == bt_Flu){
            flu();
        } else if(v == bt_Lain){
            lain();
        } else if(v == bt_Luar){
            luar();
        } else if(v == bt_Pusing){
            pusing();
        } else if(v == bt_Maag){
            maag();
        } else if(v == bt_Sakit_Perut){
            sakit_perut();
        }
    }
}
