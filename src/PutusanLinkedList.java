/**
 * Implementasi Struktur Data Utama: Singly Linked List buatan sendiri (Custom)
 * Mengimplementasikan interface generic StrukturData<T>.
 *
 * @param <T> tipe data elemen list
 */
public class PutusanLinkedList<T> implements StrukturData<T> {

    /**
     * Inner class Node untuk Singly Linked List
     */
    public static class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public PutusanLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Menambahkan elemen ke akhir linked list
     */
    @Override
    public void add(T value) {
        addLast(value);
    }

    /**
     * Menambahkan elemen di awal (head)
     */
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Menambahkan elemen di akhir (tail)
     */
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Menyisipkan elemen pada posisi tertentu (0-indexed)
     */
    public void insert(int position, T value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position + ", ukuran: " + size);
        }
        if (position == 0) {
            addFirst(value);
            return;
        }
        if (position == size) {
            addLast(value);
            return;
        }

        Node<T> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }

        Node<T> newNode = new Node<>(value);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }

    /**
     * Mengambil elemen pada indeks posisi tertentu
     */
    @Override
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position + ", ukuran: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Menghapus elemen pada posisi tertentu dan mengembalikan datanya
     */
    @Override
    public T remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position + ", ukuran: " + size);
        }

        T removedData;
        if (position == 0) {
            removedData = head.data;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = head;
            for (int i = 0; i < position - 1; i++) {
                prev = prev.next;
            }
            Node<T> target = prev.next;
            removedData = target.data;
            prev.next = target.next;
            if (target == tail) {
                tail = prev;
            }
        }
        size--;
        return removedData;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (value == null) {
                if (current.data == null) return true;
            } else if (value.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    /**
     * Validasi invariant struktur Singly Linked List:
     * 1. Jika list kosong: head == null, tail == null, size == 0.
     * 2. Jika tidak kosong: head != null, tail != null.
     * 3. Jumlah node yang ditelusuri dari head harus sama dengan size.
     * 4. Node terakhir yang ditelusuri harus identik dengan tail.
     * 5. Node terakhir (tail.next) harus menunjuk ke null.
     *
     * @return true jika invariant terpenuhi/sehat, false jika tidak.
     */
    public boolean validateInvariant() {
        if (head == null) {
            return tail == null && size == 0;
        }

        if (tail == null || size <= 0) {
            return false;
        }

        int count = 0;
        Node<T> current = head;
        Node<T> last = null;

        while (current != null) {
            count++;
            last = current;
            current = current.next;
            // Pencegahan loop tak hingga (siklus)
            if (count > size + 1) {
                return false;
            }
        }

        return count == size && last == tail && tail.next == null;
    }

    /**
     * Mengurutkan data pada Singly Linked List berdasarkan vonisBulan dari kecil ke besar.
     * Menggunakan algoritma Bubble Sort manual (in-place swap data) tanpa Collections.sort.
     */
    public void sortByVonisBulan() {
        if (size <= 1 || head == null) {
            return;
        }

        boolean swapped = true;
        while (swapped) {
            swapped = false;
            Node<T> current = head;
            while (current != null && current.next != null) {
                if (current.data instanceof Putusan && current.next.data instanceof Putusan) {
                    Putusan p1 = (Putusan) current.data;
                    Putusan p2 = (Putusan) current.next.data;

                    if (p1.vonisBulan > p2.vonisBulan) {
                        // Swap data pada node
                        T temp = current.data;
                        current.data = current.next.data;
                        current.next.data = temp;
                        swapped = true;
                    }
                }
                current = current.next;
            }
        }
    }
}
