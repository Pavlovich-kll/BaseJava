package com.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams {
    public static int[] values = {1, 2, 3, 2, 4, 5, 3, 4, 5, 6, 1, 4, 7, 9, 8};
    public static List<Integer> integers = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 4, 5, 6, 1, 4, 7, 9, 8);

    public static void main(String[] args) {
        System.out.println(minValue(values));
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        long count = Arrays.stream(values).distinct().count();
        final int distinct = Arrays.stream(values)
                .sorted()
                .distinct()//удаление дубликатов
                .reduce(0, (a, b) -> 10 * a + b);//принимает лямбда- выражение (Accumulator), которое сворачивает данные в одну кучу
        System.out.println("В массиве: " + Arrays.toString(values) + " кол-во уникальных чисел: " + count);
        return distinct;
    }

    /**
     * https://ru.stackoverflow.com/a/1005668
     */
    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .reduce(0, Integer::sum) % 2 == 0 ? filter(integers, p -> p % 2 == 0) : filter(integers, p -> p % 2 != 0);
    }

    private static List<Integer> filter(List<Integer> integers, Predicate<Integer> predicate) {
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }
}