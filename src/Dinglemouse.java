import java.util.ArrayList;
import java.util.Arrays;

public class Dinglemouse {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        final char grid[][] = makeGrid(new String[] {
                "           ",
                "X---------X",
                "           ",
                "           "
        });
        System.out.println(line(grid));
    }

    public static boolean line(final char [][] grid) {
        Pair coordX = new Pair(); //коорды первого найденного X
        ArrayList<Boolean> lines = new ArrayList<>();
         for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
               if (grid[i][j] == 'X'){
                   coordX.setX(i);
                   coordX.setY(j);
                   boolean result = lineSearch(grid, coordX, new Pair(-1, -1, '0'));
                   lines.add(result);
               }
            }
        }
        if (lines.stream()
                .filter(el -> el == true)
                .count() > 0)
            return true;
        return false;
    }

    public static boolean lineSearch(char [][] grid, Pair currentPos, Pair oldPos){
        int xCoord = currentPos.getX(), yCoord = currentPos.getY(), count = 0;
        int oldXDiff = oldPos.getX() - xCoord, oldYDiff = oldPos.getY() - yCoord, flag = 0, check;
        char currentChar = grid[xCoord][yCoord], oldChar = oldPos.getX() != -1 ? oldPos.getC() : 'X';

        //flag - кол-во возможных путей с одной точки
        //count - кол-во путей, имеющих конец X
        //check - смотрит можно ли проложить путь через след эл. с помощью функции charsCheck
        //oldXdiff и oldYDiff - на каком уровне прошлая и нынешняя точки, т. е. 0 0 или 0
        //                                                                              0

        if (currentChar == 'X' && oldPos.getX() != -1){
            if (charArrayExamination(grid)) return true;
        }

        if (yCoord > 0 && oldYDiff != -1){ //право
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord - 1, currentChar);
            check = charsCheck(currentChar, grid[xCoord][yCoord - 1], 1);
            if (check == 1){
                if (!(currentChar == '+' && oldXDiff == 0 && (oldChar == 'X' || oldChar == '-' || oldChar == '+'))) { //условие, чтоб не было +++ или X++ или -++
                    flag++;
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
                }
            }else if (check == 2){
                flag++; count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }

        if (yCoord < grid[0].length - 1 && oldYDiff != 1){ //лево
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord + 1, currentChar);
            check = charsCheck(currentChar, grid[xCoord][yCoord + 1], 1);
            if (check == 1){
                if (!(currentChar == '+' && oldXDiff == 0 && (oldChar == 'X' || oldChar == '-' || oldChar == '+'))) {
                    flag++;
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
                }
            }else if (check == 2){
                flag++; count++;
            }

            grid[xCoord][yCoord] = currentChar;
        }

        if (xCoord > 0 && oldXDiff != -1){ //верх
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord - 1, yCoord, currentChar);
            check = charsCheck(currentChar, grid[xCoord - 1][yCoord], 2);
            if (check == 1){
                if (!(currentChar == '+' && oldYDiff == 0 && (oldChar == 'X' || oldChar == '|' || oldChar == '+'))) {
                    flag++;
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
                }
            }else if (check == 2){
                flag++; count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }

        if (xCoord < grid.length - 1 && oldXDiff != 1){ //низ
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord + 1, yCoord, currentChar);
            check = charsCheck(currentChar, grid[xCoord + 1][yCoord], 2);
            if (check == 1){
                if (!(currentChar == '+' && oldYDiff == 0 && (oldChar == 'X' || oldChar == '|' || oldChar == '+'))) {
                    flag++;
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
                }
            }else if (check == 2){
                flag++; count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }

        return count == 1 && flag == 1 ? true : false;
    }

    public static char[][] makeGrid(String[] strings){ //Перевод массива стрингов в чары
        char[][] grid = new char[strings.length][strings[0].length()];
        for (int i = 0; i < strings.length; i++){
            for (int j = 0; j < strings[0].length(); j++){
                grid[i][j] = strings[i].charAt(j);
            }
        }
        return grid;
    }

    public static int charsCheck(char charCurrent, char charNext, int direction){
        int flag = 0;
        switch (charCurrent){
            case '-' -> {
                if ((charNext == '-' || charNext == '+' || charNext == 'X') && direction == 1) flag = 1;
            }
            case '|' -> {
                if ((charNext == '|' || charNext == '+' || charNext == 'X') && direction == 2) flag = 1;
            }
            case '+', 'X' -> {
                if ((charNext == '|' && direction == 2) || (charNext == '-' && direction == 1) || charNext == '+' || charNext == 'X') flag = 1;
            }
        }
        if (charCurrent == 'X' && charNext == 'X') flag = 2;
        return flag;
    }

    public static boolean charArrayExamination(char[][] grid){
        int count = 0;
        for (char[] item : grid){
            for (char el : item){
                count = count + (el == ' ' ? 1 : 0);
            }
        }
        return count == grid.length * grid[0].length - 1;
    }
}

class Pair{
    private int x;
    private int y;
    private char c;

    public Pair(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getC() {
        return c;
    }
}

