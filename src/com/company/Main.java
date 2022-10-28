package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Tree AVL_TREE = new Tree();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true)
            menu();
    }

    public static void menu(){
        System.out.println("""
                1-add
                2-search
                3-remove
                4-exit
                """);
        int choice = input.nextInt();
        if(choice == 1)
            add();
        else if(choice == 2)
            search();
        else if (choice == 3)
            remove();
        else if (choice == 4)
            System.exit(1);

    }
    public static void add(){
        System.out.println("Введите число которое хотите добавить");
        int value = input.nextInt();
        AVL_TREE.add(value);
    }
    public static void search(){
        System.out.println("Введите искомое число");
        int value = input.nextInt();

        System.out.println(AVL_TREE.search(value));
    }
    public static void remove(){
        System.out.println("Введите число которое хоотите удалить");
        int value = input.nextInt();

        AVL_TREE.remove(value);
    }



}
