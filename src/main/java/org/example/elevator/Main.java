package org.example.elevator;

public class Main {

    public static void main(String[] args) {

        User p1 = new User("Ivan");
        User p2 = new User("Igor");
        User p3 = new User("Jake");
        p1.start();
        p2.start();
        p3.start();

    }
}
