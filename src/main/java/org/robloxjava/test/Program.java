package org.robloxjava.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Animal<T> {
    private final String name;
    private final int age;
    private T specialInfo;

    public Animal(String name, int age, T specialInfo) {
        this.name = name;
        this.age = age;
        this.specialInfo = specialInfo;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public T getSpecialInfo() {
        return specialInfo;
    }

    public void setSpecialInfo(T newInfo) {
        specialInfo = newInfo;
    }

}


public class Program {
    public static void main(String[] args) {
        Animal<Object[]> x = new Animal<>("Barkson", 4,
                new Object[] {5, 7, 9, "poopies"});

        ArrayList<Integer> y = new ArrayList<>();

        x.setSpecialInfo(new Object[] {1, 2, 3, true});
    }
}
