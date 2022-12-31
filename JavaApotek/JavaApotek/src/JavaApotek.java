import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author VNX
 */
public class JavaApotek {

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;

    public static void main(String[] args) {
        // TODO code application logic here
        conn = connection.getConnection();
        Scanner scMenu = new Scanner(System.in);
        int pilihan = 0;
        while (pilihan != 6) {
            System.out.println("\n===== Selamat Datang di Java Apotek =====");
            System.out.println("1. Input Data Obat");
            System.out.println("2. Update Data Obat");
            System.out.println("3. Show Data Obat");
            System.out.println("4. Transaksi");
            System.out.println("5. Show Transaksi");
            System.out.println("6. Exit");
            System.out.print("Masukan Pilihan : ");
            pilihan = scMenu.nextInt();

            switch (pilihan) {
                case 1:
                    inputData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    listObat();
                    break;
                case 4:
                    transaksiObat();
                    break;
                case 5:
                    showTransaksi();
                    break;
                case 6:
                    System.out.println("Program Selesai!");
                    System.exit(0);
                    break;
            }
        }
    }

    public static void inputData() {
        Scanner scInput = new Scanner(System.in);
        // Input data ke variable String
        System.out.println("======= Input Data Obat =======");
        System.out.print("ID Obat\t\t: ");
        String idObat = scInput.nextLine();
        System.out.print("Nama Obat\t: ");
        String namaObat = scInput.nextLine();
        System.out.print("Jenis Obat\t: ");
        String jenisObat = scInput.nextLine();
        System.out.print("Harga Obat\t: ");
        String hargaObat = scInput.nextLine();

        // Input data menggunakan query mysql
        // dengan parameter data string yang sudah diinput user diatas
        try {
            String myQuery;
            myQuery = "insert into data_obat (id_obat,nama_obat,jenis_obat,harga) values (?,?,?,?)";
            pstmt = conn.prepareStatement(myQuery);
            pstmt.setString(1, idObat);
            pstmt.setString(2, namaObat);
            pstmt.setString(3, jenisObat);
            pstmt.setString(4, hargaObat);
            System.out.println("Tambah Data Obat Berhasil!");
            pstmt.executeUpdate();
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public static void updateData() {
        Scanner scUpdate = new Scanner(System.in);

        System.out.println("======= Update Data Obat =======");
        System.out.print("ID Obat\t\t: ");
        String idObat = scUpdate.nextLine();

        try {
            pstmt = conn.prepareStatement("select * from data_obat where id_obat=?");
            pstmt.setString(1, idObat);
            rs = pstmt.executeQuery();
            String myQuery;
            if (rs.next()) {
                System.out.println("Data Obat Ditemukan!");
                System.out.println("======= Update Data =======");
                System.out.print("Nama Obat\t: ");
                String namaObat = scUpdate.nextLine();
                System.out.print("Jenis Obat\t: ");
                String jenisObat = scUpdate.nextLine();
                System.out.print("Harga Obat\t: ");
                String hargaObat = scUpdate.nextLine();

                myQuery = "update data_obat set nama_obat=?, jenis_obat=?, harga=? where id_obat=?";

                pstmt = conn.prepareStatement(myQuery);
                pstmt.setString(1, namaObat);
                pstmt.setString(2, jenisObat);
                pstmt.setString(3, hargaObat);
                pstmt.setString(4, idObat);
                pstmt.executeUpdate();
                System.out.println("Berhasil Update Data Obat!");
            } else {
                System.out.println("Data Obat Tidak Ditemukan!");
            }
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    public static void listObat() {
        System.out.println("List Obat : ");
        // Read Data Obat Dari Database MySql
        int i = 1;
        try {
            pstmt = conn.prepareStatement("select * from data_obat");
            rs = pstmt.executeQuery();
            String myQuery;
            while (rs.next()) {
                System.out.println(i + ". ID Obat\t: " + rs.getString("id_obat"));
                System.out.println("   Nama Obat\t: " + rs.getString("nama_obat"));
                System.out.println("   Jenis Obat\t: " + rs.getString("jenis_obat"));
                System.out.println("   Harga Obat\t: Rp. " + rs.getString("harga"));
                System.out.println("");
                i++;
            }

        } catch (SQLException se) {
            System.out.println(se);
        }

        System.out.println("=========================================");
    }

    public static void transaksiObat() {

        Scanner scTransaksi = new Scanner(System.in);
        Scanner scJumlah = new Scanner(System.in);
        System.out.println("=============== Transaksi Pembelian Obat ===============");
        System.out.print("Nama Pelanggan : ");
        String namaPelanggan = scTransaksi.nextLine();
        System.out.print("Masukkan ID Obat : ");
        String id = scTransaksi.nextLine();

        int jumlah = 0;
        int harga = 0;
        int total = 0;
        // Get Data Obat dari Database
        try {
            pstmt = conn.prepareStatement("select * from data_obat where id_obat=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            String myQuery;
            if (rs.next()) {
                System.out.println("\nData Obat Ditemukan!");
                // Menyimpan data obat kedalam class obat
                Obat ob = new Obat(rs.getString("id_obat"), rs.getString("nama_obat"), rs.getString("jenis_obat"),
                        rs.getString("harga"));
                // Print Details Obat Menggunakan Interface
                ob.printObat();
                // Bila Data Obat Ditemukan, maka trasaksi dapat dilanjut

                System.out.print("Masukkan Jumlah Obat : ");
                jumlah = scJumlah.nextInt();

                Transaksi tr = new Transaksi(namaPelanggan, ob.getIdObat(), ob.getNamaObat(),
                        Integer.parseInt(ob.getHargaObat()), jumlah);
                total = tr.hitungTotal();

                // Print Total Transaksi Menggunakan interface pada class Obat
                ob.printTotal(tr.getNamaPelanggan(), tr.getJumlah(), total);

                System.out.print("Masukkan Jumlah Bayar : ");
                int bayar = scJumlah.nextInt();
                while (bayar < total) {
                    System.out.println("Pembayaran Kurang!");
                    System.out.print("Masukkan Jumlah Bayar : ");
                    bayar = scJumlah.nextInt();
                }

                int kembalian = bayar - total;
                System.out.println("Kembalian : " + String.valueOf(kembalian));

                // Input data Transaksi ke Database
                myQuery = "insert into transaksi (nama_pelanggan,id_obat,nama_obat,jumlah, total, bayar, kembalian, tanggal) values (?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(myQuery);
                pstmt.setString(1, tr.getNamaPelanggan());
                pstmt.setString(2, tr.getIdObat());
                pstmt.setString(3, tr.getNamaObat());
                pstmt.setString(4, String.valueOf(tr.getJumlah()));
                pstmt.setString(5, String.valueOf(total));
                pstmt.setString(6, String.valueOf(bayar));
                pstmt.setString(7, String.valueOf(kembalian));

                // Method Date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                pstmt.setString(8, dtf.format(now));
                System.out.println("Transaksi Berhasil Di Input!");
                pstmt.executeUpdate();

            } else {
                System.out.println("\nData Obat Tidak Ditemukan!");
            }
        }
        // Exception
        catch (SQLException se) {
            System.out.println(se);
        }
    }

    public static void showTransaksi() {
        System.out.println(
                "================================================= List Transaksi =================================================");
        // Read Data Transaksi Dari Database MySql
        try {
            pstmt = conn.prepareStatement("select * from transaksi");
            rs = pstmt.executeQuery();
            String myQuery;
            System.out.println(
                    "ID Transaksi\tNama Pelanggan\t  ID Obat\t  Nama Obat\tJumlah\tTotal\tBayar\tKembalian\tTanggal");
            while (rs.next()) {
                String idtransaksi = rs.getString("id_transaksi");
                String nama = rs.getString("nama_pelanggan");
                String idobat = rs.getString("id_obat");
                String namaobat = rs.getString("nama_obat");
                String jumlah = rs.getString("jumlah");
                String total = rs.getString("total");
                String bayar = rs.getString("bayar");
                String kembalian = rs.getString("kembalian");
                String tanggal = rs.getString("tanggal");
                if (namaobat.length() < 6) {
                    System.out.println(
                            idtransaksi + "\t\t" + nama + "\t\t  " + idobat + "\t\t  " + namaobat + "\t\t" + jumlah
                                    + "\t" + total + "\t" + bayar + "\t" + kembalian + "\t\t" + tanggal);
                } else {
                    System.out.println(
                            idtransaksi + "\t\t" + nama + "\t\t  " + idobat + "\t\t  " + namaobat + "\t" + jumlah
                                    + "\t" + total + "\t" + bayar + "\t" + kembalian + "\t\t" + tanggal);
                }
                
            }

        } catch (SQLException se) {
            System.out.println(se);
        }

        System.out.println(
                "==================================================================================================================");
    }

}
