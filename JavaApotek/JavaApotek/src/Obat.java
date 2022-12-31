public class Obat extends JavaApotek implements printMethod {
    String idObat;
    String namaObat;
    String jenisObat;
    String hargaObat;
    //Constructor
    public Obat(String idObat, String namaObat, String jenisObat, String hargaObat) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.jenisObat = jenisObat;
        this.hargaObat = hargaObat;
    }

    // Method String
    public String getIdObat() {
        return idObat;
    }

    public void setIdObat(String idObat) {
        this.idObat = idObat;
    }

    // Method String
    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    // Method String
    public String getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }

    // Method String
    public String getHargaObat() {
        return hargaObat;
    }

    public void setHargaObat(String hargaObat) {
        this.hargaObat = hargaObat;
    }

    @Override
    public void printObat() {
        System.out.println("========= Details Obat ========= ");
        System.out.println("ID Obat : " + getIdObat());
        System.out.println("Nama Obat : " + getNamaObat());
        System.out.println("Jenis Obat : " + getJenisObat());
        System.out.println("Harga Obat : " + getHargaObat());
        System.out.println("================================");
    }

    @Override
    public void printTotal(String nama, int jumlah, int total) {
        System.out.println("\n========= Total Transaksi ========= ");
        System.out.println("Nama Pelanggan : " + nama);
        System.out.println("ID Obat : " + getIdObat());
        System.out.println("Nama Obat : " + getNamaObat());
        System.out.println("Jumlah Obat : " + String.valueOf(jumlah));
        System.out.println("Total : " + String.valueOf(total));
    }

}
