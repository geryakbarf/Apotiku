package apotik.pbo2.apotiku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import apotik.pbo2.apotiku.Baca.Edit_Data;
import apotik.pbo2.apotiku.Data.List_Data;
import apotik.pbo2.apotiku.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<List_Data> list_data;
    private Context context;
    private LayoutInflater inflater;

    public Adapter(Context context, List<List_Data> list_data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List_Data listObat = list_data.get(position);
        Glide.with(context).load(listObat.getIdGambar()).into(holder.image);
        holder.t_Nama.setText(listObat.getObatNama());
        holder.t_Jenis.setText(listObat.getObatJenis());
        holder.t_Zat.setText(listObat.getObatZat());
        holder.t_Khasiat.setText(listObat.getObatKhasiat());
        holder.t_Stok.setText(listObat.getObatStok() + "");
        holder.t_Harga.setText(listObat.getObatHarga() + "");
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView t_Nama, t_Jenis, t_Zat, t_Khasiat, t_Stok, t_Harga, bt_Ubah;
        private ImageView image,cart;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            t_Nama =  itemView.findViewById(R.id.t_Nama);
            t_Jenis = itemView.findViewById(R.id.t_Jenis);
            t_Zat =  itemView.findViewById(R.id.t_Zat);
            t_Khasiat = itemView.findViewById(R.id.t_Khasiat);
            t_Stok =  itemView.findViewById(R.id.t_Stok);
            t_Harga = itemView.findViewById(R.id.t_Harga);
            bt_Ubah=itemView.findViewById(R.id.bt_Ubah);
            cart=itemView.findViewById(R.id.cart);
            cart.setOnClickListener(this);
            bt_Ubah.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(v == bt_Ubah){
                String nama = t_Nama.getText().toString();
                String zat = t_Zat.getText().toString();
                String khasiat = t_Khasiat.getText().toString();
                int stok = Integer.parseInt(t_Stok.getText().toString());
                int harga = Integer.parseInt(t_Harga.getText().toString());
                Intent intent = new Intent(context, Edit_Data.class);
                intent.putExtra("nama",nama);
                intent.putExtra("zat",zat);
                intent.putExtra("khasiat",khasiat);
                intent.putExtra("stok",stok);
                intent.putExtra("harga",harga);
                context.startActivity(intent);
            }
        }
    }
}
