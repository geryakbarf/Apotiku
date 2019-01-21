package apotik.pbo2.apotiku.Login_Register;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import apotik.pbo2.apotiku.R;
import apotik.pbo2.apotiku.Request.Delete_Request;
import apotik.pbo2.apotiku.Request.Register_Pengguna_Request;

public class Register_Pengguna extends AppCompatActivity implements View.OnClickListener {

    //Inisialisasi Button dan EditText yang di Layout
    EditText et_Pengguna;
    EditText et_Nomor;
    EditText et_Register_Password;
    EditText et_Register_Password2;
    EditText et_Username;
    Button bt_Foto;
    Button bt_Register;
    //Inisialisasi variabel
    final int PICK_IMAGE_REQUEST = 1;
    Uri filePath;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    public String idApotik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Mengubah Nama Action Bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Register Admin");
        setContentView(R.layout.activity_register__pengguna);
        //Inisialisasi pada OnCreate
            et_Pengguna=findViewById(R.id.et_Pengguna);
            et_Nomor=findViewById(R.id.et_Nomor);
            et_Username=findViewById(R.id.et_Username);
            et_Register_Password=findViewById(R.id.et__Register_Password);
            et_Register_Password2=findViewById(R.id.et__Register_Password2);
            bt_Foto=findViewById(R.id.bt_Foto);
            bt_Register=findViewById(R.id.bt_Register);
            bt_Foto.setOnClickListener(this);
            bt_Register.setOnClickListener(this);
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference= firebaseStorage.getReference();
            idApotik = getIntent().getStringExtra("IdApotik");

    }

    //Membuka Gallery untuk memilih Gambar
    private void gambar(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    //Method Ketika Gambar berhasil dipilih
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
                bt_Foto.setText("Gantikan Foto");
            }
            catch (IOException a){a.printStackTrace();}
        }
    }

    //Method untuk mendapatkan url Gambar yang akan diupload
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //Mengupload gambar ke Firebase Storage, dan informasi pengguna ke Databases
    private void tambah(){
        if(filePath != null) {
            final String HakAkses = "1";
            final String NamaPegawai = et_Pengguna.getText().toString().trim();
            //Verifikasi Jika User tidak mengisi Nama
            if(TextUtils.isEmpty(NamaPegawai)){
                Toast.makeText(this, "Nama Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }


            final String NomorKontak = et_Nomor.getText().toString().trim();
            //Verifikasi Jika User tidak mengisi Nomor Kontak
            if(TextUtils.isEmpty(NomorKontak)){
                Toast.makeText(this, "Nomor Kontak Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            final String Password = et_Register_Password.getText().toString().trim();
            //Verifikasi Jika User tidak mengisi Password
            if(TextUtils.isEmpty(Password)){
                Toast.makeText(this, "Password Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            String Konfirmasi = et_Register_Password2.getText().toString().trim();
            //Verifikasi Jika User tidak mengisi Konformasi Password
            if(TextUtils.isEmpty(Konfirmasi)){
                Toast.makeText(this, "Konfirmasi Password Harap Diisi !", Toast.LENGTH_SHORT).show();
                return;
            }

            //Verifikasi Jika Password dan konfirmasi Password tidak sama
            if(!Password.equals(Konfirmasi)){
                Toast.makeText(this,"Password dan Konfirmasi Password tidak sama !",Toast.LENGTH_SHORT).show();
                return;
            }

            final String UserName = et_Username.getText().toString().trim();
            //Verifikasi Jika User tidak mengisi Username
            if(TextUtils.isEmpty(UserName)){
                Toast.makeText(this,"Username harap diisi !",Toast.LENGTH_SHORT).show();
                return;
            }

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Sedang Menambahkan...");
            progressDialog.show();

            //Inisialisasi Direktori Penyimpanan Online
            final StorageReference sref = storageReference.child("Gambar/"+System.currentTimeMillis() + "." + getFileExtension(filePath));

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

                    //Mendapatkan URL Gambar
                    Uri url = task.getResult();
                    String IdGambar = url.toString();

                    //Mengirimkan Data ke Databases
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsresponse = new JSONObject(response);
                                boolean success = jsresponse.getBoolean("success");
                                if(success){
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else{
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Register_Pengguna.this);
                                    builder.setMessage("Register Gagal")
                                            .setNegativeButton("Coba Lagi", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException a){
                                a.printStackTrace();
                            }
                        }
                    };
                    //Konek ke Databases
                    Register_Pengguna_Request register_request = new Register_Pengguna_Request(UserName, NamaPegawai, NomorKontak, Password, HakAkses, IdGambar, idApotik, responseListener);
                    RequestQueue requestQueue = Volley.newRequestQueue(Register_Pengguna.this);
                    requestQueue.add(register_request);
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

        //Otomatis menghapus Data dari Databases jika User meng-cancel
    @Override
    public void onBackPressed() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsresponse = new JSONObject(response);
                    boolean success = jsresponse.getBoolean("success");
                    if(success){
                        Intent intent = new Intent(getApplicationContext(), Register.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Terjadi Kesalahan",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException a){
                    a.printStackTrace();
                }
            }
        };
        Delete_Request delete_request = new Delete_Request(idApotik, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Register_Pengguna.this);
        requestQueue.add(delete_request);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_Foto){
            gambar();
        } else if(v == bt_Register){
            tambah();
        } else onBackPressed();
    }
}
