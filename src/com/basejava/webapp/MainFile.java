package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(".\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\com\\basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        /**
         * В блоке "try" создается ресурс, который наследует Autoclosable:
         * т.е. все ресурсы, которые мы моем поместить в блок "try" наследуют
         * "Autoclosable" и Java 7 обеспечивает автоматическое закрытие этих ресурсов.
         */
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        doRecursiveTraversal(dir, "  ");
    }

    /**
     * Рекурсивный обход (возможен только вертикальный (в глубину))
     * позволяет выполнить просмотр всех вершин дерева и получить общие характеристики всего дерева.
     * @param dir
     */
    public static void doRecursiveTraversal(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "This is directory: " + file.getName());
                    doRecursiveTraversal(file, indent + "  ");
                } else if (file.isFile()) {
                    System.out.println(indent + "  " +  "This is file: " + file.getName());
                }
            }
        }
    }
}