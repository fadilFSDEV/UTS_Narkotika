/**
 * Implementasi Struktur Data Sekunder: Stack untuk Audit Trail (Riwayat Aksi)
 * Menggunakan Array mentah (Object[]) dengan kapasitas dinamis (resize * 2 jika penuh).
 * Mengimplementasikan interface generic StrukturData<T>.
 *
 * @param <T> tipe elemen yang disimpan di stack
 */
public class RiwayatStack<T> implements StrukturData<T> {
    private Object[] elements;
    private int top; // menunjukkan jumlah elemen (size), elemen teratas di index top - 1
    private static final int INITIAL_CAPACITY = 10;

    public RiwayatStack() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.top = 0;
    }

    public RiwayatStack(int capacity) {
        if (capacity <= 0) capacity = INITIAL_CAPACITY;
        this.elements = new Object[capacity];
        this.top = 0;
    }

    /**
     * Memperbesar kapasitas array dua kali lipat saat penuh secara manual
     */
    private void resize() {
        int newCapacity = elements.length * 2;
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < top; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
    }

    /**
     * Menambahkan elemen ke puncak Stack (Push)
     */
    public void push(T value) {
        if (top == elements.length) {
            resize();
        }
        elements[top++] = value;
    }

    /**
     * Mengambil dan menghapus elemen dari puncak Stack (Pop)
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong! Tidak dapat melakukan pop.");
        }
        T item = (T) elements[--top];
        elements[top] = null; // Menghindari memory leak
        return item;
    }

    /**
     * Melihat elemen di puncak Stack tanpa menghapusnya (Peek)
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong!");
        }
        return (T) elements[top - 1];
    }

    /**
     * Menampilkan riwayat aksi dari yang paling baru ke paling lama (LIFO)
     */
    public void cetakRiwayat() {
        if (isEmpty()) {
            System.out.println("Riwayat aktivitas masih kosong.");
            return;
        }

        System.out.println("\n========== AUDIT TRAIL / LOG RIWAYAT (LIFO) ==========");
        System.out.println("No | Waktu / Detail Aksi");
        System.out.println("-------------------------------------------------------");
        int nomor = 1;
        for (int i = top - 1; i >= 0; i--) {
            System.out.printf("%2d | %s%n", nomor++, elements[i]);
        }
        System.out.println("=======================================================\n");
    }

    // --- Implementasi StrukturData<T> ---

    @Override
    public void add(T value) {
        push(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int position) {
        if (position < 0 || position >= top) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position + ", ukuran: " + top);
        }
        return (T) elements[position];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int position) {
        if (position < 0 || position >= top) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position + ", ukuran: " + top);
        }
        T removed = (T) elements[position];
        for (int i = position; i < top - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--top] = null;
        return removed;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public boolean contains(T value) {
        for (int i = 0; i < top; i++) {
            if (value == null) {
                if (elements[i] == null) return true;
            } else if (value.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < top; i++) {
            elements[i] = null;
        }
        top = 0;
    }
}
