public class Dinglemouse {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        final char grid[][] = makeGrid(new String[] {
                "    +-----+     ",
                "    |+---+|     ",
                "    ||+-+||     ",
                "    |||X+||     ",
                "    X|+--+|     ",
                "     +----+     "
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
                   boolean result = lineSearch(grid, coordX, new Pair(-1, -1, '0'));
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
        char currentChar = grid[xCoord][yCoord], oldChar = oldPos.getX() != -1 ? '0' : oldPos.getC();

        if (currentChar == '-') {
            if (oldChar == '|') return false;
            if ((oldChar == '-' || oldChar == '+' || oldChar == 'X') && oldYDiff == 0) return false;
        }else if (currentChar == '|') {
            if (oldChar == '-') return false;
            if ((oldChar == '|' || oldChar == '+' || oldChar == 'X') && oldXDiff == 0) return false;
        }else if (currentChar == '+') {
            if (oldChar == '-' && oldXDiff == 0) return false;
            if (oldChar == '|' && oldYDiff == 0) return false;
        }else if (currentChar == 'X'){
            if (oldPos.getX() != -1){
                if (charArrayExamination(grid)) {
                    return true;
                } else {
                    return false;
                }
            }else{
                if (oldChar == '-' && oldXDiff == 0) return false;
                if (oldChar == '|' && oldYDiff == 0) return false;
                if (oldChar == 'X') return true;
            }
        }

//        if (currentChar == 'X'){
//            if (Pair.firstX.getX() != currentPos.getX() || Pair.firstX.getY() != currentPos.getY()) {
//                if (charArrayExamination(grid)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//           else{
//                grid[xCoord][yCoord] = ' ';
//                if (yCoord > 0 && oldYDiff != -1) {
//                    if (grid[xCoord][yCoord - 1] == '-' || grid[xCoord][yCoord - 1] == '+'){
//                        return lineSearch(grid, new Pair(xCoord, yCoord - 1, currentChar), new Pair(xCoord, yCoord, currentChar));
//                    }
//                }
//                if (yCoord < grid[0].length - 1 && oldYDiff != 1){
//                    if (grid[xCoord][yCoord + 1] == '-' || grid[xCoord][yCoord + 1] == '+'){
//                        return lineSearch(grid, new Pair(xCoord, yCoord + 1, currentChar), new Pair(xCoord, yCoord, currentChar));
//                    }
//                }
//                if (xCoord > 0 && oldXDiff != -1) {
//                    if (grid[xCoord - 1][yCoord] == '|' || grid[xCoord - 1][yCoord] == '+'){
//                        return lineSearch(grid, new Pair(xCoord - 1, yCoord, currentChar), new Pair(xCoord, yCoord, currentChar));
//                    }
//                }
//                if (xCoord < grid.length - 1 && oldXDiff != 1) {
//                    if (grid[xCoord + 1][yCoord] == '|' || grid[xCoord + 1][yCoord] == '+'){
//                        return lineSearch(grid, new Pair(xCoord + 1, yCoord, currentChar), new Pair(xCoord, yCoord, currentChar));
//                    }
//                }
//            }
//        }
        if ((oldChar == '-' && currentChar == '|') || (oldChar == '|' && currentChar == '-')) return false;
        if ((yCoord > 0 && oldYDiff != -1) && (currentChar != '+' || (currentChar == '+' && oldPos.getY() == currentPos.getY()))){
            // 2 условие - Чтоб предыдущий и следующий за + элементы не были на одном уровне
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord - 1, currentChar);
            if (currentChar == '+' && grid[xCoord][yCoord - 1] == '|') count = Integer.MAX_VALUE;
            if (grid[xCoord][yCoord - 1] == 'X' && currentChar == '-') {
                if (charArrayExamination(grid)){ //а если плюсы над палкой типо //////   +---+
                    return true;//////////////////////////////////////////////////////   -X--+
                }
                return false; //заглушка потом убрать
               // count++;
            }
            else if ((grid[xCoord][yCoord - 1] == currentChar && currentChar == '-') || (grid[xCoord][yCoord - 1] == '+' && currentChar != '|') || currentChar == 'X' || currentChar == '+'){
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if ((yCoord < grid[0].length - 1 && oldYDiff != 1) && (currentChar != '+' || (currentChar == '+' && oldPos.getY() == currentPos.getY()))){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord, yCoord + 1, currentChar);
            if (currentChar == '+' && grid[xCoord][yCoord + 1] == '|') count = Integer.MAX_VALUE;
            if (grid[xCoord][yCoord + 1] == 'X' && currentChar == '-'){
                if (charArrayExamination(grid)){ //а если плюсы над палкой типо //////   +---+
                    return true;//////////////////////////////////////////////////////   -X--+
                }
                return false;
                //count++;
            }
            else if ((grid[xCoord][yCoord + 1] == currentChar  && currentChar == '-') || (grid[xCoord][yCoord + 1] == '+' && currentChar != '|') || currentChar == 'X' || currentChar == '+') {
                if (lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if ((xCoord > 0 && oldXDiff != -1) && (currentChar != '+' || (currentChar == '+' && oldPos.getX() == currentPos.getX()))){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord - 1, yCoord, currentChar);
            if (currentChar == '+' && grid[xCoord - 1][yCoord] == '-') count = Integer.MAX_VALUE;
            if (grid[xCoord - 1][yCoord] == 'X' && currentChar == '|') {
                if (charArrayExamination(grid)){ //а если плюсы над палкой типо //////   +---+
                    return true;//////////////////////////////////////////////////////   -X--+
                }
                return false;
                //count++;
            }
            else if ((grid[xCoord - 1][yCoord] == currentChar && currentChar == '|') || (grid[xCoord - 1][yCoord] == '+' && currentChar != '-') || currentChar == 'X' || currentChar == '+'){
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
            grid[xCoord][yCoord] = currentChar;
        }
        if ((xCoord < grid.length - 1 && oldXDiff != 1) && (currentChar != '+' || (currentChar == '+' && oldPos.getX() == currentPos.getX()))){
            grid[xCoord][yCoord] = ' ';
            Pair newPos = new Pair(xCoord + 1, yCoord, currentChar);
            if (currentChar == '+' && grid[xCoord + 1][yCoord] == '-') count = Integer.MAX_VALUE;
            if (grid[xCoord + 1][yCoord] == 'X' && currentChar == '|'){
                if (charArrayExamination(grid)){ //а если плюсы над палкой типо //////   +---+
                    return true;//////////////////////////////////////////////////////   -X--+
                }
                return false;
                //count++;
            }
            else if ((grid[xCoord + 1][yCoord] == currentChar && currentChar == '|') || (grid[xCoord + 1][yCoord] == '+' && currentChar != '-') || currentChar == 'X' || currentChar == '+'){
                if(lineSearch(grid, newPos, new Pair(xCoord, yCoord, currentChar))) count++;
            }
        }
        if (count != 1){
            return false;
        }
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