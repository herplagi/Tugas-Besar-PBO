public class Transaksi extends JavaApotek {
    String namaPelanggan;
    String idObat;
    String namaObat;
    int harga;
    int jumlah;

    int total;

    public Transaksi(String namaPelanggan, String idObat, String namaObat, int harga, int jumlah) {

        this.namaPelanggan = namaPelanggan;
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.harga = harga;
        this.jumlah = jumlah;

    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getIdObat() {
        return idObat;
    }

    public void setIdObat(String idObat) {
        this.idObat = idObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    // Proses Matematika
    public int hitungTotal() {
        total = jumlah * harga;
        return total;
    }

}
