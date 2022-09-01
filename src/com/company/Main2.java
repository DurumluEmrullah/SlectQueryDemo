package com.company;

public class Main2 {
    public static void main(String[] args) {


        int ornek = ornek();


    }


    public static int ornek(){
        try {


            return 2;
        }
        catch (Exception e){

        }finally {
            System.out.println("Çalıştı");
        }
        return 1;
    }
}
