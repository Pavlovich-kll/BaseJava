package com.basejava.webapp;

import com.basejava.webapp.model.SectionType;

public class TestSingleton {
    private static final TestSingleton OUR_INSTANCE = new TestSingleton();

    public static TestSingleton getInstance() {
        return OUR_INSTANCE;
    }

    private TestSingleton() {

    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name());
        System.out.println(instance.ordinal());
        for(SectionType type: SectionType.values()) {
            System.out.println(type.getTitle());
        }
    }

    /**
     * String называется так же, как и enum;
     */
    public enum Singleton {
        INSTANCE
    }
}
