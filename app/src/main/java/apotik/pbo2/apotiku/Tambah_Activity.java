package apotik.pbo2.apotiku;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import apotik.pbo2.apotiku.Request.Tambah_Request;
import apotik.pbo2.apotiku.SharedPreffrence.Session1;

public class Tambah_Activity extends AppCompatActivity implements View.OnClickListener{

    Button bt_Tambah;
    Button bt_Gambar;
    EditText et_Nama;
    EditText et_Stok;
    EditText et_Harga;
    EditText et_Zat;
    EditText et_Khasiat;
    Spinner spinner;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    final int PICK_IMAGE_REQUEST = 1;
    Uri filePath;
    Session1 session1;
    public String idApotik;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tambah Obat");
        setContentView(R.layout.tambah_obat);
        et_Nama=(EditText)findViewById(R.id.et_Nama);
        et_Harga=(EditText)findViewById(R.id.et_Harga);
        et_Stok=(EditText)findViewById(R.id.et_Stok);
        et_Zat=(EditText)findViewById(R.id.et_Zat);
        et_Khasiat=(EditText)findViewById(R.id.et_Khasiat);
        bt_Tambah=(Button)findViewById(R.id.bt_Tambah);
        bt_Gambar=(Button)findViewById(R.id.bt_Gambar);
        spinner=(Spinner)findViewById(R.id.spinner);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        bt_Tambah.setOnClickListener(this);
        bt_Gambar.setOnClickListener(this);
        session1 = new Session1(getApplicationContext());
        HashMap<String,String> user=session1.getUserDetails();
        idApotik = user.get(session1.KEY_IDAPOTIK);
        progressDialog = new ProgressDialog(this);
    }

    private void gambar(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                 && data != null && data.getData() != null)
        {
            filePath= data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Toast.makeText(getApplicationContext(),"Gambar Berhasil Ditambahkan",Toast.LENGTH_SHORT).show();
                bt_Gambar.setText("Gantikan Gambar");
            }
            catch (IOException a){a.printStackTrace();}
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void tambah(){
        if(filePath != null) {
            final String namaObat = et_Nama.getText().toString().trim();
            if (TextUtils.isEmpty(namaObat)) {
                Toast.makeText(this, "Nama Obat Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            String harga = et_Harga.getText().toString().trim();
            if (TextUtils.isEmpty(harga)) {
                Toast.makeText(this, "Harga Obat Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }
            final int hargaObat = Integer.parseInt(harga);

            String stok = et_Stok.getText().toString().trim();
            if (TextUtils.isEmpty(stok)) {
                Toast.makeText(this, "Stok Obat Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }
            final int stokObat = Integer.parseInt(stok);

            final String zatAktif = et_Zat.getText().toString().trim();
            if (TextUtils.isEmpty(zatAktif)) {
                Toast.makeText(this, "Zat Aktif Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            final String khasiat = et_Khasiat.getText().toString().trim();
            if (TextUtils.isEmpty(khasiat)) {
                Toast.makeText(this, "Khasiat Obat Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            final String jenis = spinner.getSelectedItem().toString().trim();
            if (jenis.equals("Jenis Obat")) {
                Toast.makeText(this, "Harap Pilih Jenis Obat !", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setTitle("Sedang Menambahkan...");
            progressDialog.show();
            //
            final StorageReference sref = storageReference.child("Obat/"+System.currentTimeMillis() + "." + getFileExtension(filePath));

            Task<Uri> uriTask = sref.putFile(filePath)  .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return sref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    Uri url = task.getResult();
                    String idGambar = url.toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsresponse = new JSONObject(response);
                                boolean success = jsresponse.getBoolean("success");
                                if(success){
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Data Terkirim",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Tambah_Activity.this);
                                    String data = jsresponse.getString("data");
                                    builder.setMessage(data)
                                            .setNegativeButton("Coba Lagi", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException a){
                                a.printStackTrace();
                            }
                        }
                    };
                    //
                    Tambah_Request tambah_request = new Tambah_Request(namaObat, hargaObat, stokObat, zatAktif, khasiat, jenis, idGambar, idApotik, responseListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(Tambah_Activity.this);
                    requestQueue.add(tambah_request);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Harap Pilih Gambar!",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if(v == bt_Tambah){
            tambah();
        }else if(v ==  bt_Gambar){
            gambar();
        }
    }
}
