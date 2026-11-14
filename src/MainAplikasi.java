import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Class MainAplikasi
 * Program utama CLI (Command Line Interface) Aplikasi Putusan Narkotika Pengadilan Negeri.
 */

public class MainAplikasi {

    // Variabel statis untuk struktur data utama dan audit trail
    private static final PutusanLinkedList<Putusan> daftarPutusan = new PutusanLinkedList<>();
    private static final RiwayatStack<String> auditLog = new RiwayatStack<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static int dummyCounter = 51;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("         APLIKASI PUTUSAN PENGADILAN NEGERI (NARKOTIKA)        ");
        System.out.println("                    UTS STRUKTUR DATA INDIVIDU                 ");
        System.out.println("===============================================================");

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenu();
            System.out.print("Pilih menu [0-10]: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    menuImporCsv();
                    break;
                case "2":
                    menuTampilkanSemua();
                    break;
                case "3":
                    menuTambahDataDummy();
                    break;
                case "4":
                    menuHapusViaIndex();
                    break;
                case "5":
                    menuCariCaseId();
                    break;
                case "6":
                    menuFilterJenisNarkotika();
                    break;
                case "7":
                    menuAgregasi();
                    break;
                case "8":
                    menuUrutkanVonis();
                    break;
                case "9":
                    menuEksporCsv();
                    break;
                case "10":
                    menuCetakAuditLog();
                    break;
                case "0":
                    System.out.println("\nKeluar dari program. Terima kasih!");
                    berjalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Silakan masukkan angka 0 sampai 10.\n");
                    break;
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n------------------------- MENU UTAMA -------------------------");
        System.out.println("1.  Impor CSV dari \"data/putusan-50.csv\" (skip header)");
        System.out.println("2.  Tampilkan semua data (sekaligus panggil dan cetak hasil validateInvariant)");
        System.out.println("3.  Tambah data dummy");
        System.out.println("4.  Hapus data via index");
        System.out.println("5.  Cari data berdasarkan caseId (Sequential search & hitung iterasi)");
        System.out.println("6.  Filter berdasarkan jenisNarkotika");
        System.out.println("7.  Agregasi (Total perkara, total denda, rata-rata vonis)");
        System.out.println("8.  Urutkan berdasarkan Vonis (Bubble Sort manual Singly Linked List)");
        System.out.println("9.  Ekspor ke CSV");
        System.out.println("10. Cetak Audit Log dari Stack");
        System.out.println("0.  Keluar");
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * Menu 1: Impor CSV dari data/putusan-50.csv (skip header)
     */
    private static void menuImporCsv() {
        System.out.println("\n--- [1. Impor Data dari CSV] ---");
        String filePath = "data/putusan-50.csv";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File tidak ditemukan di: " + filePath);
            return;
        }

        int count = 0;
        int failed = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine(); // Skip header baris pertama
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = parseCsvLine(line);
                if (parts != null && parts.length >= 16) {
                    Putusan p = new Putusan(parts);
                    daftarPutusan.add(p);
                    count++;
                } else {
                    failed++;
                }
            }

            System.out.printf("Impor berhasil! Total data masuk: %d (Baris invalid: %d)%n", count, failed);
            System.out.println("Jumlah data dalam Singly Linked List saat ini: " + daftarPutusan.size());

            // Catat aktivitas ke Stack
            auditLog.push("[MENU 1 - IMPOR] Berhasil mengimpor " + count + " data dari " + filePath);

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan I/O saat membaca berkas: " + e.getMessage());
        }
    }

    /**
     * Menu 2: Tampilkan semua data & panggil validateInvariant()
     */
    private static void menuTampilkanSemua() {
        System.out.println("\n--- [2. Tampilkan Semua Data & Validasi Invariant] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong! Silakan pilih Menu 1 terlebih dahulu.");
            return;
        }

        System.out.println("DAFTAR DATA PUTUSAN NARKOTIKA:");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan p = daftarPutusan.get(i);
            System.out.printf("%3d. %s%n", (i + 1), p.toString());
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total elemen: " + daftarPutusan.size());

        // Panggil dan cetak hasil validateInvariant()
        boolean statusInvariant = daftarPutusan.validateInvariant();
        System.out.println("Hasil Uji Invariant Singly Linked List: " + (statusInvariant ? "TRUE (Invariant Valid & Konsisten)" : "FALSE (Invariant Rusak!)"));
        System.out.println("Keterangan: Head dan tail sinkron, tail.next == null, dan jumlah traversal sama persis dengan size.");

        // Catat aktivitas ke Stack
        auditLog.push("[MENU 2 - TAMPIL] Menampilkan " + daftarPutusan.size() + " data | Invariant: " + statusInvariant);
    }

    /**
     * Menu 3: Tambah data dummy
     */
    private static void menuTambahDataDummy() {
        System.out.println("\n--- [3. Tambah Data Dummy] ---");
        String caseId = String.format("P%03d", dummyCounter);
        String nomorPerkara = String.format("%03d/Pid.Sus/2026/PN Sby", dummyCounter);
        String pengadilan = "PN Surabaya";
        LocalDate tanggalPutusan = LocalDate.parse("2026-11-15");
        String terdakwaKode = String.format("TDK-%03d", dummyCounter);
        String jenisNarkotika = (dummyCounter % 2 == 0) ? "Sabu-sabu" : "Ganja";
        double berat = 2.50 + (dummyCounter % 5);
        String pasalDakwaan = "Pasal 112 ayat (1) UU RI No 35 Tahun 2009";
        String pasalTerbukti = "Pasal 112 ayat (1)";
        int tuntutan = 48;
        int vonis = 36 + (dummyCounter % 12);
        long denda = 10000000L;
        String barangBukti = berat + " gram " + jenisNarkotika;
        String keadaanMemberatkan = "Merusak masa depan generasi muda";
        String keadaanMeringankan = "Terdakwa berjanji tidak mengulangi perbuatannya";
        String sourcePages = "hal 1-16";

        Putusan dummy = new Putusan(caseId, nomorPerkara, pengadilan, tanggalPutusan, terdakwaKode,
                jenisNarkotika, berat, pasalDakwaan, pasalTerbukti, tuntutan, vonis, denda,
                barangBukti, keadaanMemberatkan, keadaanMeringankan, sourcePages);

        daftarPutusan.add(dummy);
        dummyCounter++;

        System.out.println("Data dummy berhasil ditambahkan:");
        System.out.println(dummy.toString());
        System.out.println("Total elemen sekarang: " + daftarPutusan.size());

        // Catat aktivitas ke Stack
        auditLog.push("[MENU 3 - TAMBAH DUMMY] Menambahkan data dummy: " + caseId + " (" + nomorPerkara + ")");
    }

    /**
     * Menu 4: Hapus data via index
     */
    private static void menuHapusViaIndex() {
        System.out.println("\n--- [4. Hapus Data via Index] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong! Tidak ada data yang bisa dihapus.");
            return;
        }

        System.out.printf("Masukkan indeks data yang ingin dihapus (0 s.d. %d): ", daftarPutusan.size() - 1);
        String inputIdx = scanner.nextLine().trim();

        try {
            int index = Integer.parseInt(inputIdx);
            if (index < 0 || index >= daftarPutusan.size()) {
                System.out.println("Indeks di luar rentang valid!");
                return;
            }

            Putusan terhapus = daftarPutusan.remove(index);
            System.out.println("Data pada index " + index + " berhasil dihapus:");
            System.out.println(terhapus.toString());
            System.out.println("Sisa data: " + daftarPutusan.size());

            // Catat aktivitas ke Stack
            auditLog.push("[MENU 4 - HAPUS] Menghapus data index " + index + " (CaseID: " + terhapus.caseId + ")");

        } catch (NumberFormatException e) {
            System.out.println("Format input salah! Masukkan angka bulat.");
        }
    }

    /**
     * Menu 5: Cari data berdasarkan caseId (Sequential search & hitung iterasi)
     * Menggunakan looping daftarPutusan.get(i) secara aman tanpa akses class private/static.
     */
    private static void menuCariCaseId() {
        System.out.println("\n--- [5. Cari Data Berdasarkan Case ID] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong!");
            return;
        }

        System.out.print("Masukkan Case ID yang dicari (contoh: TRG-001 atau P051): ");
        String targetCaseId = scanner.nextLine().trim();

        int langkah = 0;
        boolean ditemukan = false;

        for (int i = 0; i < daftarPutusan.size(); i++) {
            langkah++; // Menghitung setiap iterasi langkah penelusuran
            Putusan p = daftarPutusan.get(i);
            if (p != null && p.caseId != null && p.caseId.equalsIgnoreCase(targetCaseId)) {
                System.out.println("\n== HASIL PENCARIAN ==");
                System.out.printf("Status: DITEMUKAN pada langkah/iterasi ke-%d (Indeks ke-%d)!%n", langkah, i);
                System.out.println("Data Putusan:");
                System.out.println(p.toString());
                System.out.println("Detail Lengkap: ");
                System.out.println("- Barang Bukti         : " + p.barangBukti);
                System.out.println("- Dakwaan & Terbukti   : " + p.pasalDakwaan + " | Terbukti: " + p.pasalTerbukti);
                System.out.println("- Tuntutan vs Vonis    : Tuntutan " + p.tuntutanBulan + " bln vs Vonis " + p.vonisBulan + " bln");
                System.out.println("- Keadaan Memberatkan  : " + p.keadaanMemberatkan);
                System.out.println("- Keadaan Meringankan  : " + p.keadaanMeringankan);
                ditemukan = true;
                break;
            }
        }

        if (!ditemukan) {
            System.out.printf("Data dengan Case ID '%s' TIDAK DITEMUKAN setelah %d langkah penelusuran.%n", targetCaseId, langkah);
        }

        // Catat aktivitas ke Stack
        auditLog.push("[MENU 5 - CARI] CaseID: " + targetCaseId + " | Status: " + (ditemukan ? "Ketemu" : "Tidak Ketemu") + " | " + langkah + " iterasi");
    }

    /**
     * Menu 6: Filter berdasarkan jenisNarkotika
     */
    private static void menuFilterJenisNarkotika() {
        System.out.println("\n--- [6. Filter Berdasarkan Jenis Narkotika] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong!");
            return;
        }

        System.out.print("Masukkan kata kunci jenis narkotika (misal: Sabu, Ganja, Ekstasi, Heroin): ");
        String jenis = scanner.nextLine().trim();

        int cocok = 0;
        System.out.println("\nHasil Filter Jenis: \"" + jenis + "\"");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan p = daftarPutusan.get(i);
            if (p != null && p.jenisNarkotika != null && p.jenisNarkotika.toLowerCase().contains(jenis.toLowerCase())) {
                cocok++;
                System.out.printf("%3d. %s%n", cocok, p.toString());
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("Total data yang cocok: %d perkara.%n", cocok);

        // Catat aktivitas ke Stack
        auditLog.push("[MENU 6 - FILTER] Jenis: \"" + jenis + "\" | Hasil: " + cocok + " perkara");
    }

    /**
     * Menu 7: Agregasi (Total perkara, total denda, rata-rata vonis)
     */
    private static void menuAgregasi() {
        System.out.println("\n--- [7. Agregasi Data Putusan] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong!");
            return;
        }

        int totalPerkara = daftarPutusan.size();
        long totalDenda = 0;
        long totalVonis = 0;

        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan p = daftarPutusan.get(i);
            if (p != null) {
                totalDenda += p.dendaRupiah;
                totalVonis += p.vonisBulan;
            }
        }

        double rataRataVonis = (double) totalVonis / totalPerkara;

        System.out.println("==================================================");
        System.out.println("             RINGKASAN AGREGASI DATA              ");
        System.out.println("==================================================");
        System.out.printf("Total Perkara Terdata : %d perkara%n", totalPerkara);
        System.out.printf("Total Denda Rupiah    : Rp %,d%n", totalDenda);
        System.out.printf("Total Akumulasi Vonis : %d bulan%n", totalVonis);
        System.out.printf("Rata-rata Vonis Bulan : %.2f bulan%n", rataRataVonis);
        System.out.println("==================================================");

        // Catat aktivitas ke Stack
        auditLog.push(String.format("[MENU 7 - AGREGASI] Total: %d, Denda: Rp %,d, Rata-rata: %.2f bln", totalPerkara, totalDenda, rataRataVonis));
    }

    /**
     * Menu 8: Urutkan berdasarkan Vonis (Bubble Sort manual Singly Linked List)
     */
    private static void menuUrutkanVonis() {
        System.out.println("\n--- [8. Urutkan Berdasarkan Vonis (Bubble Sort Manual)] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong!");
            return;
        }

        System.out.println("Menjalankan Bubble Sort manual pada rantai node Singly Linked List...");
        daftarPutusan.sortByVonisBulan();
        System.out.println("Pengurutan SELESAI! Data berhasil diurutkan berdasarkan vonisBulan (dari kecil ke besar).");

        System.out.println("\n5 Putusan dengan vonis terendah setelah sorting:");
        int limit = Math.min(5, daftarPutusan.size());
        for (int i = 0; i < limit; i++) {
            System.out.printf("%d. %s%n", (i + 1), daftarPutusan.get(i).toString());
        }

        // Catat aktivitas ke Stack
        auditLog.push("[MENU 8 - SORT] Mengurutkan list dengan Bubble Sort manual berdasarkan vonisBulan");
    }

    /**
     * Menu 9: Ekspor ke CSV
     * Menulis seluruh data dari daftarPutusan ke file data/ekspor_hasil.csv menggunakan BufferedWriter & FileWriter.
     */
    private static void menuEksporCsv() {
        System.out.println("\n--- [9. Ekspor ke CSV] ---");
        if (daftarPutusan.isEmpty()) {
            System.out.println("Daftar putusan kosong! Tidak ada data yang bisa diekspor.");
            return;
        }

        String path = "data/ekspor_hasil.csv";
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Header CSV standar
            writer.write("caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages");
            writer.newLine();

            // Looping seluruh data pada daftarPutusan
            for (int i = 0; i < daftarPutusan.size(); i++) {
                Putusan p = daftarPutusan.get(i);
                if (p != null) {
                    writer.write(p.toCsvLine());
                    writer.newLine();
                }
            }

            System.out.printf("Sukses mengekspor %d data putusan ke file: %s%n", daftarPutusan.size(), path);

            // Catat aktivitas ke Stack
            auditLog.push("[MENU 9 - EKSPOR] Berhasil mengekspor " + daftarPutusan.size() + " data ke " + path);

        } catch (IOException e) {
            System.out.println("Gagal mengekspor data ke file CSV: " + e.getMessage());
        }
    }

    /**
     * Menu 10: Cetak Audit Log dari Stack
     */
    private static void menuCetakAuditLog() {
        System.out.println("\n--- [10. Cetak Audit Log dari Stack] ---");
        auditLog.cetakRiwayat();
        // Catat aktivitas ke Stack
        auditLog.push("[MENU 10 - AUDIT] Melihat daftar riwayat aktivitas dari Stack");
    }

    /**
     * Parser CSV baris tunggal toleran tanda kutip ganda tanpa koleksi java.util
     */
    private static String[] parseCsvLine(String line) {
        if (line == null) return null;

        String[] temp = new String[25];
        int colIndex = 0;
        StringBuilder sb = new StringBuilder();
        boolean insideQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ',' && !insideQuote) {
                if (colIndex < temp.length) {
                    temp[colIndex++] = sb.toString();
                }
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        if (colIndex < temp.length) {
            temp[colIndex++] = sb.toString();
        }

        String[] result = new String[colIndex];
        for (int i = 0; i < colIndex; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}
