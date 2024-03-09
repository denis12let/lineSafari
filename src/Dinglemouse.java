import java.util.ArrayList;
import java.util.Arrays;

public class Dinglemouse {
    public static int linesCounter;
    public static void main(String[] args) {
        Dinglemouse.linesCounter = 0;
        System.out.println("Hello world!");
        final char grid[][] = makeGrid(new String[] {
                "      +------+",
                "      |      |",
                "X-----+------+",
                "      |       ",
                "      X       ",
        });
        System.out.println(line(grid));
    }

    public static boolean line(final char [][] grid) {
        Pair coordX = new Pair(); //коорды первого найденного X
//        ArrayList<Boolean> linesCorrects = new ArrayList<>(); //счетчик X и осмотр правильности направлений
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
               if (grid[i][j] == 'X'){
                   coordX.setX(i);
                   coordX.setY(j);
                   Pair.firstX = coordX;
                   boolean result = lineSearch(grid, coordX, new Pair(-1, -1, '0'));
                   return result;
//                   linesCorrects.add(result);
               }
            }
        }
//        if (linesCorrects.size() % 2 != 0) return false;
//        long trueCount = linesCorrects.stream()
//                .filter(el -> el == true)
//                .count();
//        if (trueCount < linesCorrects.size() / 2) return false;
        return true;
    }

    public static boolean lineSearch(char [][] grid, Pair currentPos, Pair oldPos){
        int xCoord = currentPos.getX(), yCoord = currentPos.getY(), count = 0;
        int oldXDiff = oldPos.getX() - xCoord, oldYDiff = oldPos.getY() - yCoord;
        char currentChar = grid[xCoord][yCoord], oldChar = oldPos.getX() != -1 ? oldPos.getC() : 'X';
        if (currentChar == ' ') return false;
        else if (currentChar == '-') {
            if (oldChar == '|') return false;
            if ((oldChar == '-' || oldChar == '+' || oldChar == 'X') && oldYDiff == 0) return false;
        }else if (currentChar == '|') {
            if (oldChar == '-') return false;
            if ((oldChar == '|' || oldChar == '+' || oldChar == 'X') && oldXDiff == 0) return false;
        }else if (currentChar == '+') {
            if (oldChar == '-' && oldYDiff == 0) return false;
            if (oldChar == '|' && oldXDiff == 0) return false;
        }else if (currentChar == 'X'){
            if (oldPos.getX() != -1){
                if (oldChar == '-' && oldYDiff == 0) return false;
                if (oldChar == '|' && oldXDiff == 0) return false;
                if (oldChar == 'X') return true;
//                if (yCoord > 0){
//                    if (grid[xCoord][yCoord - 1] != ' ') return false;
//                }
//                if (yCoord < grid[0].length - 1){
//                    if (grid[xCoord][yCoord + 1] != ' ') return false;
//                }
//                if (xCoord > 0){
//                    if (grid[xCoord - 1][yCoord] != ' ') return false;
//                }
//                if (yCoord < grid.length){
//                    if (grid[xCoord + 1][yCoord] != ' ') return false;
//                }
//                return true;
                return true;
//                if (charArrayExamination(grid)) {
//                    return true;
//                } else {
//                    return false;
//                }
            }
        }
        grid[xCoord][yCoord] = ' ';
        if (yCoord > 0 && oldYDiff != -1){
            Pair newPos = new Pair(xCoord, yCoord - 1, currentChar);
            if (currentChar != '+' || oldXDiff != 0){
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
        }
        if (yCoord < grid[0].length - 1 && oldYDiff != 1){
            Pair newPos = new Pair(xCoord, yCoord + 1, currentChar);
            if (currentChar != '+' || oldXDiff != 0) {
                if (lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
        }
        if (xCoord > 0 && oldXDiff != -1){
            Pair newPos = new Pair(xCoord - 1, yCoord, currentChar);
            if (currentChar != '+' || oldYDiff != 0) {
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
        }
        if (xCoord < grid.length - 1 && oldXDiff != 1){
            Pair newPos = new Pair(xCoord + 1, yCoord, currentChar);
            if (currentChar != '+' || oldYDiff != 0) {
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
        }
        return count != 1 ? false : true;
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
    static Pair firstX;
    private int x;
    private int y;
    private char c;

    public Pair(int x, int y, char c) {
        this.x = x;
        this.y = y;
        this.c = c;
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

    public void setC(char c) {
        this.c = c;
    }
}