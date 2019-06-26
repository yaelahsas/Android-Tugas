package tugas.besar.tugasku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class menu1 extends AppCompatActivity implements View.OnClickListener{

    private TextView namaEmail;
    Button tblLogout,tblCari,tblSaldo,tblAbout;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()==null){
            finish();

            startActivity(new Intent(this,Login.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();
        tblSaldo = findViewById(R.id.isisaldo);
        tblAbout = findViewById(R.id.tombolAbout);
        tblCari = findViewById(R.id.tombolCari);
        namaEmail = findViewById(R.id.tempatEmail);
        tblLogout = findViewById(R.id.tombolLogout);
        namaEmail.setText("Selamat Datang "+user.getEmail());

        tblLogout.setOnClickListener(this);
        tblSaldo.setOnClickListener(this);
        tblAbout.setOnClickListener(this);
        tblCari.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == tblLogout){
            finish();
            mAuth.signOut();
            startActivity(new Intent(this,Login.class));
        }else if (view == tblSaldo){
            startActivity(new Intent(this,Saldo.class));
        }else if (view == tblAbout){
            startActivity(new Intent(this,About.class));
        }else if (view == tblCari){

            startActivity(new Intent(this,MapsActivity.class));

        }

    }
}
