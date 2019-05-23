package tugas.besar.tugasku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    private TextView namaEmail;
    Button tblLogout;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()==null){
            finish();

            startActivity(new Intent(this,Login.class));
        }

            FirebaseUser user = mAuth.getCurrentUser();
        namaEmail = (TextView)findViewById(R.id.tempatEmail);
        tblLogout = (Button)findViewById(R.id.tombolLogout);
        namaEmail.setText("Selamat Datang "+user.getEmail());

        tblLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tblLogout){
            finish();
            startActivity(new Intent(this,Login.class));
        }
    }
}
