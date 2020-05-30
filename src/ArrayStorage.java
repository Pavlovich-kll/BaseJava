import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        for (int ind = 0; ind < size; ind++) {
            if (storage[ind].uuid.equals(uuid)) {
                return storage[ind];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int indGet = Arrays.asList(storage).indexOf(get(uuid));
        if (indGet >= 0) {
            size--;
            storage[indGet] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Резюме отсутсвует, удаление невозможно");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
