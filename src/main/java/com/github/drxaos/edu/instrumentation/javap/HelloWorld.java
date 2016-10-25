package com.github.drxaos.edu.instrumentation.javap;


public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public String ab(String in) {
        if (in == null) {
            return "a";
        } else {
            return "b";
        }
    }
}
