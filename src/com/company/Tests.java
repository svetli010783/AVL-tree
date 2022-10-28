package com.company;

import java.util.Scanner;

public class Tests {
    static Tree AVL_TREE = new Tree();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // тест поиска по дереву
//              AVL_TREE.add(5);
//              AVL_TREE.add(3);
//              AVL_TREE.add(7);
//              AVL_TREE.add(10);
//              AVL_TREE.add(6);
//              System.out.println( AVL_TREE.search(7));


//        // тест левого малого поворота
//                AVL_TREE.add(5);
//                AVL_TREE.add(3);
//                AVL_TREE.add(9);
//                AVL_TREE.add(10);
//                AVL_TREE.add(7);
//                AVL_TREE.add(11);
//                System.out.println(AVL_TREE.getTop().toString());


//        // тест правого малого поворота
//                 AVL_TREE.add(9);
//                 AVL_TREE.add(10);
//                 AVL_TREE.add(5);
//                 AVL_TREE.add(6);
//                 AVL_TREE.add(3);
//                 AVL_TREE.add(1);
//                 System.out.println(AVL_TREE.getTop().toString());

//          // тест левого большего поворота
//                   AVL_TREE.add(5);
//                   AVL_TREE.add(9);
//                   AVL_TREE.add(3);
//                   AVL_TREE.add(10);
//                   AVL_TREE.add(7);
//                   AVL_TREE.add(6) ;
//                   System.out.println(AVL_TREE.getTop().toString());
//
//
        AVL_TREE.add(7);
        AVL_TREE.add(10);
        AVL_TREE.add(6);
        AVL_TREE.add(5);
        AVL_TREE.add(9);
        AVL_TREE.add(12);
        AVL_TREE.add(8);
        AVL_TREE.add(11);
        AVL_TREE.remove(10);



    }
}
