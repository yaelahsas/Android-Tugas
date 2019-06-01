package tugas.besar.tugasku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Daftar extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mNoteRef;
    private  EditText editEmail, editPass,namanya,telpon,editPassRe;
    private Button tblDaftar;
    private TextView textDaftar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private CheckBox lk,pr;
    private String nama, jk,tlp,emailnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mNoteRef = FirebaseDatabase.getInstance().getReference("Pengguna");
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);



        namanya = findViewById(R.id.daftarNama);
        telpon = findViewById(R.id.daftarHp);
        editEmail = findViewById(R.id.daftarEmail);
        editPass = findViewById(R.id.daftarPass);
        editPassRe = findViewById(R.id.daftarPassRe);
        tblDaftar = findViewById(R.id.tombolDaftar);
        textDaftar = findViewById(R.id.tulisanPunyaAkun);
        lk = findViewById(R.id.daftarLaki);
        pr = findViewById(R.id.daftarPerempuan);
        tblDaftar.setOnClickListener(this);
        textDaftar.setOnClickListener(this);

    }


    private void daftarUser(){

       final String key = mNoteRef.push().getKey();
        nama = namanya.getText().toString().trim();
        tlp = telpon.getText().toString().trim();
        emailnya = editEmail.getText().toString().trim();
        String passnya = editPass.getText().toString().trim();
        String repassnya = editPassRe.getText().toString().trim();

        if (TextUtils.isEmpty(nama)){

            Toast.makeText(this,"Masukkan Nama ",Toast.LENGTH_SHORT).show();
            return;
        }

        if (lk.isChecked()){

            jk = "Laki-Laki";

        }
        if (pr.isChecked()){

            jk = "Perempuan";

        }
        if(!lk.isChecked() && !pr.isChecked()){

            Toast.makeText(this,"Masukkan Jenis Kelamin",Toast.LENGTH_SHORT).show();
            return;
        }
        if(lk.isChecked() && pr.isChecked()){

            Toast.makeText(this,"Pilih Satu Jenis Kelamin",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(tlp)){

            Toast.makeText(this,"Masukkan Telpon ",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(emailnya)){

            //Pesan Email Kosong
            Toast.makeText(this,"Masukkan Email ",Toast.LENGTH_SHORT).show();
        return;
        }

        if (TextUtils.isEmpty(passnya)){
            //pesan error Password
            Toast.makeText(this,"Masukkan Password",Toast.LENGTH_SHORT).show();
              return;
        }


        if (!repassnya.equals(passnya)){
            Toast.makeText(this,"Password Tidak Sama",Toast.LENGTH_SHORT).show();
            return;
        }

        //jika lolos validasi

        progressDialog.setMessage("Sedang Mendaftar...");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(emailnya, passnya)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        //User berhasil dibuat
                        progressDialog.hide();
                        Pengguna pengguna = new Pengguna(key,nama,jk,tlp);
                        mNoteRef.child(key).setValue(pengguna);
                        Toast.makeText(Daftar.this,"Daftar Berhasil",Toast.LENGTH_SHORT).show();
                        editEmail.setText("");
                        editPass.setText("");
                        namanya.setText("");
                        telpon.setText("");
                        editPassRe.setText("");
                        lk.setChecked(false);
                        pr.setChecked(false);
                        Intent login = new Intent(Daftar.this, Login.class);
                        startActivity(login);
                    } else {
                        progressDialog.hide();
                        Toast.makeText(Daftar.this,"Daftar Gagal.. Coba Lagi",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }


    @Override
    public void onClick(View v) {

        if (v == tblDaftar){
            daftarUser();
        }

        if (v == textDaftar){
            //Buka Login activity
            finish();
            Intent login = new Intent(Daftar.this, Login.class);
            startActivity(login);

        }
    }
}
