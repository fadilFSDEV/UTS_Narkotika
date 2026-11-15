# Laporan Kontribusi Individu (CONTRIBUTIONS.md)

**Mata Kuliah** : Struktur Data  
**Penugasan**   : Ujian Tengah Semester
**Status Tim**  : Pengerjaan **INDIVIDU**

---

## 1. Identitas Pengembang

- **Nama Mahasiswa** : Muhammad Habib Fadillah
- **NIM**            : 202510370110034
- **Peran**          : Full-Stack Developer & Data Extractor (Seluruh peran dikerjakan secara individual)

---

## 2. Rincian Kontribusi Mandiri

Seluruh komponen tugas UTS Struktur Data ini dirancang, diekstraksi, dan diimplementasikan secara mandiri oleh saya sendiri:

### A. Ekstraksi dan Validasi Dataset
- Mengekstrak data dari 50 dokumen putusan peradilan narkotika (PDF Mahkamah Agung) ke dalam format terstruktur `data/putusan-50.csv` secara mandiri.
- Melakukan pembersihan data, normalisasi satuan gram barang bukti, standardisasi denda, dan penyelarasan 16 kolom atribut sesuai format penugasan.
- Memastikan anonimisasi kode terdakwa (`TDK-xxx`) dan verifikasi kebenaran nomor perkara peradilan.

### B. Perancangan & Implementasi Struktur Data Custom
- Merancang dan membuat interface generic `StrukturData<T>` dari nol.
- Membangun struktur data utama **Singly Linked List** (`PutusanLinkedList.java`) menggunakan inner class `Node<T>` tanpa menggunakan koleksi bawaan `java.util`.
- Mengimplementasikan method `validateInvariant()` untuk memverifikasi konsistensi pointer `head`, `tail`, dan jumlah node list.
- Membangun struktur data sekunder **Stack** (`RiwayatStack.java`) dari nol berbasis array mentah `Object[]` dengan algoritma penggandaan kapasitas manual (`resize * 2`).

### C. Algoritma dan Logika Aplikasi
- Mengimplementasikan algoritma **Bubble Sort manual** (`sortByVonisBulan()`) yang bekerja langsung pada struktur data berantai (Singly Linked List) tanpa dependensi `Collections.sort`.
- Mengimplementasikan algoritma **Sequential Search** dengan pencacah langkah (*step counter*) untuk menganalisis kompleksitas waktu pencarian secara empiris.
- Mengimplementasikan modul filter, agregasi statistik matematika, dan sistem pencatatan jejak audit (*Audit Trail*) ke dalam `RiwayatStack`.
- Membangun antarmuka terminal interaktif Command Line Interface (CLI) pada `MainAplikasi.java` yang bebas dari library koleksi Yang dilarang.
