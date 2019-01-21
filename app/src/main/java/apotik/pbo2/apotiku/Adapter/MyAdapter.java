package apotik.pbo2.apotiku.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import apotik.pbo2.apotiku.Data.Data;
import apotik.pbo2.apotiku.Pegawai.Edit_Pegawai;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Data> listdata;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdapter(Context context, List<Data> listdata){
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
        this.listdata=listdata;
        Activity activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posisition){
        Data listPegawai=listdata.get(posisition);
        Glide.with(context).load(listPegawai.getIdGambar()).into(holder.thumbnail);
        holder.nama.setText(listPegawai.getNamaPegawai());
        holder.nomor.setText(listPegawai.getNomorKontak());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nama,nomor;
        private ImageView thumbnail;
        private Session1 session1;
        private String hakAkes;
        public ViewHolder(View itemView){
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            nama=(TextView)itemView.findViewById(R.id.nama);
            nomor=(TextView)itemView.findViewById(R.id.nomor);
            session1 = new Session1(context);
            HashMap<String,String> user=session1.getUserDetails();
            hakAkes = user.get(session1.KEY_HAK_AKSES);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(hakAkes.equals("2")){
                return;
            } else {
                Intent intent = new Intent(context, Edit_Pegawai.class);
                String nama1 = nama.getText().toString().trim();
                String nomor1 = nomor.getText().toString().trim();
                intent.putExtra("nama",nama1);
                intent.putExtra("nomor",nomor1);
                context.startActivity(intent);
            }
        }
    }
}

