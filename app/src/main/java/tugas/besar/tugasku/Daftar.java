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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Daftar extends AppCompatActivity implements View.OnClickListener {

    private  EditText editEmail, editPass;
    private Button tblDaftar;
    private TextView textDaftar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


        editEmail = (EditText)findViewById(R.id.daftarEmail);
        editPass = (EditText)findViewById(R.id.daftarPass);
        tblDaftar = (Button)findViewById(R.id.tombolDaftar);
        textDaftar = (TextView)findViewById(R.id.tulisanPunyaAkun);

        tblDaftar.setOnClickListener(this);
        textDaftar.setOnClickListener(this);

    }


    private void daftarUser(){

        String emailnya = editEmail.getText().toString().trim();
        String passnya = editPass.getText().toString().trim();

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
                        Toast.makeText(Daftar.this,"Daftar Berhasil",Toast.LENGTH_SHORT).show();
                        editEmail.setText("");
                        editPass.setText("");

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
