import java.time.LocalDate;

/**
 * Class Putusan
 * Model data representasi putusan perkara narkotika di Pengadilan Negeri.
 * Semua atribut diatur sebagai public.
 */
public class Putusan {
    public String caseId;
    public String nomorPerkara;
    public String pengadilan;
    public LocalDate tanggalPutusan;
    public String terdakwaKode;
    public String jenisNarkotika;
    public double beratBarangBuktiGram;
    public String pasalDakwaan;
    public String pasalTerbukti;
    public int tuntutanBulan;
    public int vonisBulan;
    public long dendaRupiah;
    public String barangBukti;
    public String keadaanMemberatkan;
    public String keadaanMeringankan;
    public String sourcePages;

    /**
     * Constructor default
     */
    public Putusan() {
    }

    /**
     * Constructor lengkap
     */
    public Putusan(String caseId, String nomorPerkara, String pengadilan, LocalDate tanggalPutusan,
                   String terdakwaKode, String jenisNarkotika, double beratBarangBuktiGram,
                   String pasalDakwaan, String pasalTerbukti, int tuntutanBulan, int vonisBulan,
                   long dendaRupiah, String barangBukti, String keadaanMemberatkan,
                   String keadaanMeringankan, String sourcePages)
    {
        this.caseId = caseId;
        this.nomorPerkara = nomorPerkara;
        this.pengadilan = pengadilan;
        this.tanggalPutusan = tanggalPutusan;
        this.terdakwaKode = terdakwaKode;
        this.jenisNarkotika = jenisNarkotika;
        this.beratBarangBuktiGram = beratBarangBuktiGram;
        this.pasalDakwaan = pasalDakwaan;
        this.pasalTerbukti = pasalTerbukti;
        this.tuntutanBulan = tuntutanBulan;
        this.vonisBulan = vonisBulan;
        this.dendaRupiah = dendaRupiah;
        this.barangBukti = barangBukti;
        this.keadaanMemberatkan = keadaanMemberatkan;
        this.keadaanMeringankan = keadaanMeringankan;
        this.sourcePages = sourcePages;
    }

    /**
     * Constructor yang menerima String[] hasil split CSV baris data.
     * Mengisi otomatis ke-16 atribut secara aman.
     */
    public Putusan(String[] data) {
        if (data != null && data.length >= 16) {
            this.caseId = clean(data[0]);
            this.nomorPerkara = clean(data[1]);
            this.pengadilan = clean(data[2]);

            // Parsing tanggalPutusan secara aman ke tipe LocalDate
            try {
                this.tanggalPutusan = LocalDate.parse(clean(data[3]));
            } catch (Exception e) {
                this.tanggalPutusan = null;
            }

            this.terdakwaKode = clean(data[4]);
            this.jenisNarkotika = clean(data[5]);
            this.beratBarangBuktiGram = parseDoubleSafe(data[6]);
            this.pasalDakwaan = clean(data[7]);
            this.pasalTerbukti = clean(data[8]);
            this.tuntutanBulan = parseIntSafe(data[9]);
            this.vonisBulan = parseIntSafe(data[10]);
            this.dendaRupiah = parseLongSafe(data[11]);
            this.barangBukti = clean(data[12]);
            this.keadaanMemberatkan = clean(data[13]);
            this.keadaanMeringankan = clean(data[14]);
            this.sourcePages = clean(data[15]);
        }
    }

    private static String clean(String s) {
        if (s == null) return "";
        String trimmed = s.trim();
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"") && trimmed.length() >= 2) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }
        return trimmed;
    }

    private static double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(clean(s));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(clean(s));
        } catch (Exception e) {
            return 0;
        }
    }

    private static long parseLongSafe(String s) {
        try {
            return Long.parseLong(clean(s));
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * Konversi objek putusan ke format 1 baris CSV dengan format tanggal yang aman
     */
    public String toCsvLine() {
        String tglStr = (tanggalPutusan != null) ? tanggalPutusan.toString() : "";
        return String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%d,%d,%d,\"%s\",\"%s\",\"%s\",%s",
                caseId, nomorPerkara, pengadilan, tglStr, terdakwaKode, jenisNarkotika,
                beratBarangBuktiGram, pasalDakwaan, pasalTerbukti, tuntutanBulan, vonisBulan,
                dendaRupiah, barangBukti, keadaanMemberatkan, keadaanMeringankan, sourcePages);
    }

    @Override
    public String toString() {
        String tglStr = (tanggalPutusan != null) ? tanggalPutusan.toString() : "-";
        return String.format(
            "[%s] No: %s | Tgl: %s | Pengadilan: %s | Terdakwa: %s | Narkotika: %s (%.2f gr) | Terbukti: %s | Vonis: %d bln | Denda: Rp %,d",
            caseId, nomorPerkara, tglStr, pengadilan, terdakwaKode, jenisNarkotika, beratBarangBuktiGram,
            pasalTerbukti, vonisBulan, dendaRupiah
        );
    }
}
