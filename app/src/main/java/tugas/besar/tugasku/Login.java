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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.xml.transform.dom.DOMLocator;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText loginEmail,loginPass;
    private Button tblLogin;
    private TextView tulisanDaftar;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        loginEmail = (EditText)findViewById(R.id.LoginEmail);
        loginPass = (EditText)findViewById(R.id.LoginPass);
        tblLogin = (Button)findViewById(R.id.tombolLogin);
        tulisanDaftar = (TextView)findViewById(R.id.tulisanBelumPunyaAkun);

        mAuth = FirebaseAuth.getInstance();
    if (  mAuth.getCurrentUser() != null){

        Intent prof = new Intent(Login.this,menu1.class);
        startActivity(prof);
        //buka Profile Activity
    }
        tblLogin.setOnClickListener(this);
        tulisanDaftar.setOnClickListener(this);

    }

    private void loginUser(){
        String emailnya = loginEmail.getText().toString().trim();
        String passnya = loginPass.getText().toString().trim();

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

        progressDialog.setMessage("Sedang Login..");
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(emailnya, passnya)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            finish();
                         Intent prof = new Intent(Login.this,menu1.class);
                         startActivity(prof);

                        } else {
                            progressDialog.hide();
                            Toast.makeText(Login.this, "Login Gagal.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

    @Override
    public void onClick(View view) {
        if (view == tblLogin){

            loginUser();
        }
        if (view == tulisanDaftar){
            finish();
            Intent login = new Intent(Login.this, Daftar.class);
            startActivity(login);


        }

    }
}
