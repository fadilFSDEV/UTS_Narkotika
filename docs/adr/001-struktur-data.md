# Architectural Decision Record (ADR)

## 001. Pemilihan Struktur Data Utama dan Mekanisme Pengurutan

- **Tanggal** : 2026-11-15
- **Penulis** : Muhammad Habib Fadillah
- **NIM**     : 202510370110034
---

## 1. Konteks

Pada penugasan Ujian Tengah Semester (UTS) mata kuliah Struktur Data, mahasiswa diwajibkan membangun aplikasi Java berorientasi objek untuk menyimpan dan mengolah 50 data putusan perkara narkotika. Terdapat batasan ketat di mana mahasiswa dilarang keras menggunakan implementasi struktur data koleksi bawaan dari `java.util` (seperti `ArrayList`, `LinkedList`, `Stack`, `Queue`, dan `Collections`). 

Mahasiswa diberikan kebebasan memilih satu struktur data linier utama (antara Array Dinamis, Singly Linked List, Stack, atau Queue) dan struktur data sekunder untuk kebutuhan audit riwayat.

---

## 2. Keputusan Arsitektural

Kami memutuskan untuk:
1. Memilih **Custom Singly Linked List (`PutusanLinkedList`)** sebagai **struktur data utama** untuk menampung seluruh objek data `Putusan`.
2. Memilih **Custom Stack Berbasis Array Mentah (`RiwayatStack`)** sebagai **struktur data sekunder** untuk merekam riwayat audit aktivitas pengguna (*Audit Trail*).
3. Mengimplementasikan algoritma **Bubble Sort manual** secara *in-place* langsung pada rantai node `PutusanLinkedList` untuk fitur pengurutan data berdasarkan vonis hukuman.

---

## 3. Alasan dan Pertimbangan

1. **Efisiensi Memori (Alokasi Dinamis)**:
   - Singly Linked List mengalokasikan memori secara dinamis untuk setiap node saat elemen ditambahkan (`addLast` / `addFirst`). Tidak terdapat pemborosan kapasitas memori yang tidak terpakai seperti pada alokasi awal array tetap.
2. **Kemudahan Operasi Penyisipan dan Penghapusan**:
   - Operasi penambahan atau penghapusan elemen di awal (*head*) atau akhir (*tail*) dapat dilakukan secara efisien dengan pembaruan pointer tanpa perlu melakukan *shifting* (pergeseran fisik) semua elemen seperti pada array.
3. **Penyederhanaan Algoritma Pengurutan Manual (Bubble Sort)**:
   - Pengurutan pada Singly Linked List dapat diimplementasikan dengan menukar referensi nilai data (`data swap`) antar-node bertetangga (`current.data` dan `current.next.data`) selama proses iterasi. Pendekatan ini menjaga invariant rantai pointer (`head`, `tail`, dan `next`) tetap konsisten tanpa merusak struktur tautan list.
4. **Kepatuhan Terhadap Aturan Akademik**:
   - Struktur ini dibangun 100% dari nol menggunakan `class Node<T>` independen, sehingga bebas dari ketergantungan library `java.util.Collections.sort()` maupun wrapper bawaan lainnya.

---

## 4. Konsekuensi

- **Keuntungan**:
  - Implementasi murni berorientasi objek yang transparan dan mudah diinspeksi.
  - Memori bersifat elastis mengikuti jumlah record yang diimpor.
  - Operasi traversal sequential search memungkinkan pencatatan metrik langkah/iterasi secara akurat.
- **Batasan**:
  - Akses data berdasarkan indeks tertentu membutuhkan penelusuran sekuensial dari node `head` dengan kompleksitas waktu $O(n)$, berbeda dengan akses langsung $O(1)$ pada array. Namun, untuk ukuran dataset 50 putusan, waktu akses ini dapat diabaikan dan tetap berkinerja sangat cepat.
