package com.tammo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            GameBoard gb = new GameBoard(10, 10);
            gb.togglePosition(2,0);
            gb.togglePosition(0,1);
            gb.togglePosition(2,1);
            gb.togglePosition(1,2);
            gb.togglePosition(2,2);

            Scanner inputScanner = new Scanner(System.in);
            String tmp;
            do {
                System.out.println(gb);
                gb = gb.updatedBoard();
                System.out.print("Press Enter for the next iteration or enter a character and enter to quit:");
                tmp = inputScanner.nextLine();
                System.out.print(tmp);
            } while(tmp.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
