package apotik.pbo2.apotiku.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import apotik.pbo2.apotiku.Data.Data;
import apotik.pbo2.apotiku.Pegawai.Edit_Pegawai;
import apotik.pbo2.apotiku.Pegawai.Lihat_Pegawai;
import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Hapus_Pegawai;
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
        holder.username.setText(listPegawai.getUsername());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nama,nomor,username;
        private ImageView thumbnail;
        private Session1 session1;
        private String hakAkes, id;
        public ViewHolder(View itemView){
            super(itemView);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            nama=itemView.findViewById(R.id.nama);
            nomor=itemView.findViewById(R.id.nomor);
            username=itemView.findViewById(R.id.txt_UN);
            session1 = new Session1(context);
            HashMap<String,String> user=session1.getUserDetails();
            hakAkes = user.get(session1.KEY_HAK_AKSES);
            id=user.get(session1.KEY_IDAPOTIK);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(hakAkes.equals("2")){
                return;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Perhatian!");
                builder.setMessage("Apakah Anda Ingin Menghapus Data Pegawai Ini ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String un = username.getText().toString().trim();
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Sedang Menghapus....");
                        progressDialog.show();
                        Response.Listener<String> listener= new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(context, Lihat_Pegawai.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                        Toast.makeText(context,"Data Terhapus",Toast.LENGTH_SHORT).show();
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"Gagal Menghapus Pegawai",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Hapus_Pegawai hapus_pegawai = new Hapus_Pegawai(un,id,listener);
                        RequestQueue requestQueue=Volley.newRequestQueue(context);
                        requestQueue.add(hapus_pegawai);
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                builder.show();
            }
        }
    }
}

