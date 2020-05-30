import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;
    int ind;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (ind = 0; ind < size; ind++) {
            if (storage[ind].uuid.equals(uuid)) {
                return storage[ind];
            }
        }
        return null;
    }

    void delete(String uuid) {
        size--;
        storage[getInd(uuid)] = storage[size];
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    int getInd(String uuid) {
        get(uuid);
        return ind;
    }
}
