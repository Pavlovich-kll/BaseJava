package com.basejava.webapp;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final Resume resume_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume resume_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume resume_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume resume_4 = new Resume(UUID_4);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(resume_1);
        collection.add(resume_2);
        collection.add(resume_3);

        Iterator<Resume> iterator = collection.iterator();
        /**
         * Использование итератора для модификации коллекции (в данном примере удаление элемента):
         * iterator.hasNext() - проверяет, что нет следующего элемента;
         * затем первый элемент удаляется и остается 2 элемента в коллекции;
         */
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (Objects.equals((r.getUuid()), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());

        /**
         * Реализация коллекции:
         * Мар - есть объект и ключ. Например, есть массив событий,
         * и "ключем" к событию будет конкретный день.
         *
         * put(ключ, значение);
         * keySet()- коллекция уникальных ключей;
         * entrySet() - выдает пару значений;
         * getValue() - берет значение из пары, не нужно постоянно обращаться к Map (это ошибка);
         */
        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, resume_1);
        map.put(UUID_2, resume_2);
        map.put(UUID_3, resume_3);

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

    }
}