package tugas.besar.tugasku;

/**
 * Created by user on 28/05/2019.
 */

public class Pengguna {

   private String uid,nama,jk,tlp;

    public Pengguna() {
    }



    public Pengguna(String uid, String nama, String jk, String tlp) {
        this.uid = uid;
        this.nama = nama;
        this.jk = jk;
        this.tlp = tlp;
    }

    public String getUid() {
        return uid;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public String getTlp() {
        return tlp;
    }
}
