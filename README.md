# UTS Struktur Data: Aplikasi Putusan Narkotika Pengadilan Negeri

Proyek aplikasi berbasis Java berorientasi objek untuk manajemen dan pengolahan data 50 putusan perkara tindak pidana narkotika di Pengadilan Negeri. Proyek ini dikembangkan secara murni menggunakan implementasi struktur data linier (Custom Data Structures) dari nol guna memenuhi capaian pembelajaran mata kuliah Struktur Data.

> [!NOTE]
> Proyek ini dikerjakan secara **INDIVIDU**.

---

## 1. Arsitektur Struktur Data

1. **Struktur Data Utama (`PutusanLinkedList`)**:
   - Berbasis **Singly Linked List** dengan node dinamis (`Node<T>`).
   - Dilengkapi fungsi pemeriksaan `validateInvariant()` untuk memverifikasi konsistensi `head`, `tail`, dan jumlah elemen aktif.
   - Dilengkapi algoritma **Bubble Sort manual** (`sortByVonisBulan()`) untuk mengurutkan node berdasarkan vonis.
2. **Struktur Data Sekunder (`RiwayatStack`)**:
   - Berbasis **Stack** menggunakan array mentah (`Object[]`).
   - Memiliki mekanisme alokasi dinamis manual (`resize * 2` saat kapasitas penuh).
   - Berfungsi sebagai **Audit Trail** untuk merekam seluruh riwayat aktivitas operasi pengguna secara LIFO (Last-In, First-Out).
3. **Interface Generic (`StrukturData<T>`)**:
   - Menyediakan kontrak operasi dasar: `add`, `get`, `remove`, `size`, `isEmpty`, `contains`, dan `clear`.

---

## 2. Susunan Berkas Proyek

```
uts-narkotika/
├── data/
│   └── putusan-50.csv               # Dataset 50 putusan pengadilan narkotika
├── docs/
│   └── adr/
│       └── 001-struktur-data.md     # Architectural Decision Record
├── src/
│   ├── StrukturData.java            # Interface generic struktur data
│   ├── Putusan.java                 # Class model data putusan (16 atribut)
│   ├── PutusanLinkedList.java       # Singly Linked List custom
│   ├── RiwayatStack.java            # Stack custom berbasis Object[]
│   └── MainAplikasi.java            # Entry point & CLI Menu aplikasi
├── AI_USAGE.md                      # Dokumentasi transparansi penggunaan AI
├── CONTRIBUTIONS.md                 # Bukti kontribusi pengerjaan mandiri
└── README.md                        # Dokumentasi utama proyek
```

---

## 3. Cara Menjalankan MainAplikasi.java

### Prasyarat
- Java Development Kit (JDK) versi 11 atau lebih baru telah terpasang.
- Terminal / Command Prompt / PowerShell.

### Cara Kompilasi

1. **Buka terminal** pada direktori root proyek (`uts-narkotika`).
2. **Kompilasi kode program**:
   ```bash
   javac -d out/production/uts-narkotika src/*.java
   ```
3. **Jalankan aplikasi**:
   ```bash
   java -cp out/production/uts-narkotika MainAplikasi
   ```

---

## 4. Panduan Fitur Menu CLI

Aplikasi menyediakan 10 menu navigasi interaktif:

| No | Menu | Keterangan |
|:---:|:---|:---|
| **1** | **Impor CSV** | Membaca berkas `data/putusan-50.csv`, melewati baris header, dan memuat 50 data putusan ke dalam `PutusanLinkedList`. |
| **2** | **Tampilkan Semua & Validasi Invariant** | Menampilkan seluruh record putusan dan mengeksekusi method `validateInvariant()` untuk memastikan struktur list sehat. |
| **3** | **Tambah Data Dummy** | Menambahkan satu data putusan dummy baru ke dalam list. |
| **4** | **Hapus Data via Index** | Menghapus record pada indeks tertentu dengan pergeseran pointer yang aman. |
| **5** | **Cari Data via Case ID** | Pencarian sekuensial (*Sequential Search*) berbasis `caseId` dan menampilkan jumlah langkah/iterasi yang ditempuh. |
| **6** | **Filter Jenis Narkotika** | Menyaring dan menampilkan daftar perkara berdasarkan jenis zat narkotika (Sabu, Ganja, Ekstasi, dll). |
| **7** | **Agregasi Data** | Menghitung total perkara, akumulasi denda rupiah, dan rata-rata vonis hukuman penjara dalam bulan. |
| **8** | **Urutkan Berdasarkan Vonis** | Menjalankan algoritma Bubble Sort manual pada `PutusanLinkedList` dari vonis terendah ke tertinggi. |
| **9** | **Ekspor ke CSV** | Menulis seluruh data putusan dari `PutusanLinkedList` ke berkas `data/ekspor_hasil.csv`. |
| **10** | **Cetak Audit Log dari Stack** | Menampilkan seluruh catatan riwayat eksekusi menu pengguna dari struktur data `RiwayatStack`. |
| **0** | **Keluar** | Mengakhiri program secara aman. |
