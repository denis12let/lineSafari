import java.util.ArrayList;
import java.util.Arrays;

public class Dinglemouse {
    public static void main(String[] args) {
        //План
        // -- убрать условие return count != 1 ? false : true; и сделать if (count == 0) return false;
        // -- сделать так: Создать static flag в этом классе. Если возвращаешься по рекурсии от X, то flag = true, иначе - false (но подумать, что в рекурсии после X можно смотреть еще на другие символы и этот true станет false)
        //1. Считаем кол-во X отдельной функцией и получаем это как массив координат
        //2. Созддаем элемент класса ArrayList(чтоб добавлять из рекурсии можно было)
        //3. В методе line(где просто ищем X-ы и обходим их) создаем массив ArrayList-ов, размером с количество X-ов
        //4. Обходим X-ы -> добавляем при true координату в ArrayList -> делаем distinct этого массива, чтоб удалить повторы ->
        //-> добавляем эти ArrayList-ы координат в массив ArrayList-ов из метода line
        //5. Узнаем какой X к кому относится(придумать как)
        System.out.println("Hello world!");
        final char grid[][] = makeGrid(new String[] {
                "     ++      ",
                "    ++++     ",
                "    ++++     ",
                "   X-++-X    "
        });
        System.out.println(line(grid));
    }

    public static boolean line(final char [][] grid) {
        Pair coordX = new Pair(); //коорды первого найденного X
        ArrayList<Pair> sources = sourcesSearch(grid);
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
               if (grid[i][j] == 'X'){
                   coordX.setX(i);
                   coordX.setY(j);

                   boolean result = lineSearch(grid, coordX, new Pair(-1, -1, '0'), sources);
                   return result;
               }
            }
        }
        return true;
    }

    public static boolean lineSearch(char [][] grid, Pair currentPos, Pair oldPos, ArrayList<Pair> sources){
        int xCoord = currentPos.getX(), yCoord = currentPos.getY(), count = 0;
        int oldXDiff = oldPos.getX() - xCoord, oldYDiff = oldPos.getY() - yCoord;
        char currentChar = grid[xCoord][yCoord], oldChar = oldPos.getX() != -1 ? oldPos.getC() : 'X';
        if (sourceSearch(sources, oldPos) != -1) sources.remove(0);
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
            int xCIndex;
            if (oldPos.getX() != -1){
                if (oldChar == '-' && oldYDiff == 0) return false;
                if (oldChar == '|' && oldXDiff == 0) return false;
                if (oldChar == 'X') return true; //Исправить, чтобы при XX было и как новый путь, и как еще поиск дальше



                xCIndex = sourceSearch(sources, currentPos);
                sources.remove(xCIndex);
                if (sources.size() == 0){
                    sources.add(0, currentPos);
                    if (charArrayExamination(grid)) return true;
                    else return false;
                }
                grid[xCoord][yCoord] = ' ';
                if (lineSearch(grid,
                        new Pair(sources.get(0).getX(), sources.get(0).getY()),
                        new Pair(-1, -1, '0'), sources)){
                    grid[xCoord][yCoord] = 'X';
                    sources.add(0, currentPos);
                    return true;
                }
                grid[xCoord][yCoord] = 'X';
                sources.add(0, currentPos);
                return false;
            }
//            else{
//                sources.remove(0);
//            }
        }
        if (yCoord > 0 && oldYDiff != -1){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord - 1, currentChar);
            if (currentChar != '+' || oldXDiff != 0){
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar), sources)) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if (yCoord < grid[0].length - 1 && oldYDiff != 1){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord + 1, currentChar);
            if (currentChar != '+' || oldXDiff != 0) {
                if (lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar), sources)) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if (xCoord > 0 && oldXDiff != -1){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord - 1, yCoord, currentChar);
            if (currentChar != '+' || oldYDiff != 0) {
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar), sources)) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if (xCoord < grid.length - 1 && oldXDiff != 1){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord + 1, yCoord, currentChar);
            if (currentChar != '+' || oldYDiff != 0) {
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar), sources)) count++;
            }
            grid[xCoord][yCoord] = currentChar;
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

    public static ArrayList<Pair> sourcesSearch(char[][] grid){
        ArrayList<Pair> sourcesCoords = new ArrayList<>();
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                if (grid[i][j] == 'X') sourcesCoords.add(new Pair(i, j));
            }
        }
        return sourcesCoords;
    }

    public static int sourceSearch(ArrayList<Pair> sources, Pair currentCoord){
        for (int i = 0; i < sources.size(); i++){
            if (sources.get(i).getX() == currentCoord.getX() && sources.get(i).getY() == currentCoord.getY()) return i;
        }
        return -1;
    }

}

class Pair{
//    static Pair firstX;
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

    public void setC(char c) {
        this.c = c;
    }
}