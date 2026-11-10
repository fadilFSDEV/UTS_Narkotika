/**
 * Interface generic StrukturData<T>
 * Menyediakan kontrak dasar operasi struktur data linier.
 * @param <T> tipe elemen yang disimpan
 */
public interface StrukturData<T> {
    void add(T value);
    T get(int position);
    T remove(int position);
    int size();
    boolean isEmpty();
    boolean contains(T value);
    void clear();
}
