# Laporan Transparansi Penggunaan AI (AI_USAGE.md)

Dokumen ini disusun untuk memenuhi prinsip transparansi akademik sesuai pedoman penugasan Ujian Tengah Semester (UTS) mata kuliah Struktur Data.

---

## 1. Alat / Platform AI yang Digunakan
- **Alat 1**: AI JetBrains / GitHub Copilot (Bantuan autokomplesi di lingkungan IDE)
- **Alat 2**: Gemini / ChatGPT (penalaran dan diskusi konsep)

---

## 2. Tujuan Penggunaan
1. **Ekstraksi & Penataan Data CSV**: Membantu mempercepat pembacaan nilai atribut penting (seperti besaran nominal denda, lama vonis hukuman penjara, dan berat barang bukti) dari 50 berkas PDF putusan pengadilan agar terformat rapi ke dalam berkas `putusan-50.csv`.
2. **Boilerplate & Kerangka Kode**: Membantu menghasilkan struktur dasar kode Java (seperti class model data, deklarasi signature method interface, dan kerangka menu CLI).

---

## 3. Cara Verifikasi & Penyesuaian Mandiri

Seluruh saran dan keluaran yang dihasilkan oleh alat AI telah melalui proses verifikasi dan penyesuaian secara manual:

1. **Verifikasi Integritas Berkas CSV**:
   - Memeriksa secara manual seluruh 50 baris data agar tidak terdapat tanda koma di dalam kalimat yang dapat merusak proses  pembacaan.
   - Mengapit kalimat yang memuat tanda baca dengan tanda kutip ganda serta memvalidasi tipe data angka (`double`, `int`, `long`).
2. **Audit Kepatuhan Aturan Struktur Data**:
   - Memeriksa setiap baris kode Java untuk memastikan TIDAK ADA import atau penggunaan koleksi `java.util` yang dilarang
3. **Pengujian Fungsional & Invariant**:
   - Menjalankan skenario eksekusi program dan memastikan method `validateInvariant()` selalu mengembalikan nilai `true`.
   - Memastikan algoritma pengurutan Bubble Sort manual bekerja dengan benar tanpa merusak struktur tautan antar-node.
