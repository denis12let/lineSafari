public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        final char grid[][] = makeGrid(new String[] {
                "     X-------+",
                "             |",
                "X------------+",
                "              ",
                "              ",
        });
        System.out.println(line(grid));
    }

    public static boolean line(final char [][] grid) {
        Pair coordX = new Pair(); //коорды первого найденного X
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
               if (grid[i][j] == 'X'){
                   coordX.setX(i);
                   coordX.setY(j);
                   Pair.firstX = coordX;
                   boolean result = lineSearch(grid, coordX, new Pair(-1, -1));
                   return result;
               }
            }
        }
        return false;
    }


    public static boolean lineSearch(char [][] grid, Pair currentPos, Pair oldPos){
        int xCoord = currentPos.getX(), yCoord = currentPos.getY(), count = 0;
        int oldXDiff = oldPos.getX() - xCoord, oldYDiff = oldPos.getY() - yCoord;
        if (grid[xCoord][yCoord] == ' ') return false;
        char currentChar = grid[xCoord][yCoord];
        if ((yCoord > 0 && oldYDiff != -1) && (currentChar != '+' || (currentChar == '+' && oldPos.getY() == currentPos.getY()))){
            // 2 условие - Чтоб предыдущий и следующий за + элементы не были на одном уровне
//            if (currentChar == '+' && oldPos.getX() == currentPos.getX()){
                grid[xCoord][yCoord] = ' ';
                Pair newPos = new Pair(xCoord, yCoord - 1);
                if (grid[xCoord][yCoord - 1] == 'X' && currentChar == '-') count++;
                else if (grid[xCoord][yCoord - 1] == currentChar || (grid[xCoord][yCoord - 1] == '+' && currentChar != '|') || currentChar == 'X' || currentChar == '+'){
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord))) count++;
                }
                grid[xCoord][yCoord] = currentChar;
//            }
        }
        if ((yCoord < grid[0].length - 1 && oldYDiff != 1) && (currentChar != '+' || (currentChar == '+' && oldPos.getY() == currentPos.getY()))){
                grid[xCoord][yCoord] = ' ';
                Pair newPos = new Pair(xCoord, yCoord + 1);
                if (grid[xCoord][yCoord + 1] == 'X' && currentChar == '-') count++;
                else if (grid[xCoord][yCoord + 1] == currentChar || (grid[xCoord][yCoord + 1] == '+' && currentChar != '|') || currentChar == 'X' || currentChar == '+'){
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord))) count++;
                grid[xCoord][yCoord] = currentChar;
            }
        }
        if ((xCoord > 0 && oldXDiff != -1) && (currentChar != '+' || (currentChar == '+' && oldPos.getX() == currentPos.getX()))){
//            if (currentChar == '+' && oldPos.getY() != currentPos.getX()) {
                grid[xCoord][yCoord] = ' ';
                Pair newPos = new Pair(xCoord - 1, yCoord);
                if (grid[xCoord - 1][yCoord] == 'X' && currentChar == '|') count++;
                else if (grid[xCoord - 1][yCoord] == currentChar || (grid[xCoord - 1][yCoord] == '+' && currentChar != '-') || currentChar == 'X' || currentChar == '+'){
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord))) count++;
                }
                grid[xCoord][yCoord] = currentChar;
//            }
        }
        if ((xCoord < grid.length - 1 && oldXDiff != 1) && (currentChar != '+' || (currentChar == '+' && oldPos.getX() == currentPos.getX()))){
                grid[xCoord][yCoord] = ' ';
                Pair newPos = new Pair(xCoord + 1, yCoord);
                if (grid[xCoord + 1][yCoord] == 'X' && currentChar == '|') count++;
                else if (grid[xCoord + 1][yCoord] == currentChar || (grid[xCoord + 1][yCoord] == '+' && currentChar != '-') || currentChar == 'X' || currentChar == '+'){
                    if(lineSearch(grid, newPos, new Pair(xCoord, yCoord))) count++;
            }
        }
        if (currentChar == 'X' && Pair.firstX.getX() != currentPos.getX() && Pair.firstX.getY() != currentPos.getY()) return true;
        if (count != 1) return false;
        return true;
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
}

class Pair{
    static Pair firstX;
    private int x;
    private int y;

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
}