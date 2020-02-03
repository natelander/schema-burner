package com.natelander.schemaburner;

public class Main {
    public static void main(String[] args) {

        try {
            DiffResolver.calculateDiff(args[0], args[1], args[2]);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
