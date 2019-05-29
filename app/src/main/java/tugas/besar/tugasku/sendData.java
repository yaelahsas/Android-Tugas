package tugas.besar.tugasku;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendData extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mNoteRef;
    private EditText namanya,telpon;
    private Button kirimData;
    private CheckBox lk,pr;
    private String nama, jk,tlp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        mNoteRef = FirebaseDatabase.getInstance().getReference("Pengguna");
        progressDialog = new ProgressDialog(this);
        init();

        //init();
    }

    public void init(){

        namanya = findViewById(R.id.sendNama);
        telpon = findViewById(R.id.sendTelpon);
        kirimData = findViewById(R.id.sendKirimData);
        lk = findViewById(R.id.sendLaki);
        pr = findViewById(R.id.sendPerempuan);

        kirimData.setOnClickListener(this);


    }


    public void masukData(){

        String key = mNoteRef.push().getKey();
        nama = namanya.getText().toString();
        tlp = telpon.getText().toString();

        if (lk.isChecked()){

            jk = "Laki-Laki";

        }
        if (pr.isChecked()){

            jk = "Perempuan";

        }

        if (TextUtils.isEmpty(nama)){

            Toast.makeText(this,"Masukkan Nama",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!lk.isChecked() && !pr.isChecked()){

            Toast.makeText(this,"Masukkan Jenis Kelamin",Toast.LENGTH_SHORT).show();
            return;
        }
        if(lk.isChecked() && pr.isChecked()){

            Toast.makeText(this,"Pilih Satu Jenis Kelamin",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Sedang Memasukkan Data ..");
        progressDialog.show();

        Pengguna pengguna = new Pengguna(key,nama,jk,tlp);
        mNoteRef.child(key).setValue(pengguna)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.hide();
                            namanya.setText("");
                            telpon.setText("");
                            lk.setChecked(false);
                            pr.setChecked(false);
                            Toast.makeText(sendData.this,"Masuk Berhasil",Toast.LENGTH_SHORT).show();


                        }else{
                            progressDialog.hide();
                            Toast.makeText(sendData.this,"Gagal ..",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {

        if (view == kirimData){

            masukData();
        }

    }
}
