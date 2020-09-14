package com.company;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.company.DniTygodnia.dniTygodnia.*;

public class Main {


    public static void main(String[] args) {

        DniTygodnia.dniTygodnia poniedzialek = PONIEDZIALEK;
        DniTygodnia.dniTygodnia wtork = WTOREK;


        Tv telewizor = new Tv("jazda",1345);
        Tv telewizor1  = new Tv("samsung",  634318, 138);
//        System.out.println(poniedzialek);
        Set<String> strings = new HashSet<>();
        strings.add("Jeden");
        strings.add("Dwa");
        strings.add("Dwa");
        strings.add("Trzy");
       

        for (String string:strings
             ) {
            System.out.println(string);
            System.out.println(strings.size());

        }

    }
}
