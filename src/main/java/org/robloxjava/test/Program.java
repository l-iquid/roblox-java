package org.robloxjava.test;

class Animal<T> {
    private String animalName;
    private int animalAge;

    public Animal(String stringA, int x) {
        this.animalName = stringA;
        this.animalAge = x;
        // pretend we're printing
    }

    public String getAnimalName() {
        return this.animalName;
    }

    public int getAnimalAge() {
        return this.animalAge;
    }

}

public class Program {
    public int r, y = 3;
    public static int SomeStaticInt = 6;
    public static String aTHing, b;

    private static Object hi(int exampleValue) {
        if (3 + 156 / 7 > exampleValue || 703 < exampleValue) {
            return 69;
        } else if (exampleValue == 3) {
            int t = 3;
        } else {
            return "Screw you!";
        }
        return 999;
    }

    public void a() {
        hi(3);
    }

    public static void main(String[] args) {
        Animal<Integer> x = new Animal<>("Woof", 3);
        SomeStaticInt = 6;
        x.getAnimalAge();
    }
}
