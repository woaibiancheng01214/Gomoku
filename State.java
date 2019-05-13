class State {
    Type state;
    private int coorRow;
    private int coorCol;
    private int HLIMIT;
    private int WLIMIT;
    private char[][] b;

    State(int hlimit, int wlimit){
        state = Type.blackTurn;
        HLIMIT = hlimit;
        WLIMIT = wlimit;
        b = new char[HLIMIT][WLIMIT];
    }

    public char[][] getBoard(){
        return b;
    }

    public Type currentState(){
        return state;
    }

    public void setCoor(int x, int y){
        coorRow = x;
        coorCol = y;
    }
    
    public void setChesspiece()
    {
        if(state == Type.blackTurn){
            b[coorCol][coorRow] = 'X';
        }
        else{
            b[coorCol][coorRow] = 'O';
        }
    }

    public void initBoard()
    {
        for(int i = 0; i < HLIMIT; i++){
            for(int j = 0; j < WLIMIT; j++){
                b[i][j] = '_';
            }
        }
    }

    public boolean checkExistAndOutOfBoard(int x, int y) {
        if(y < HLIMIT && x < WLIMIT && b[y][x] == '_'){
            return true;
        }
        return false;
    }

    // Check whether the game is finished
    public void nextState(){
        if(checkGameFinish() == true){
            return;
        }
        switchPlayer();
    }
    
    // two pairs include coorRow and coorCol AND player1win and player2win 
    // are both confusing consider revise!!!
    private boolean checkGameFinish(){
        int count = 0;
        char currentChess = (b[coorCol][coorRow] == 'X') ? 'X' : 'O';
        if (checkWinRow(coorCol, coorRow, currentChess, count) == true){
            return true;
        }
        if(checkWinColumn(coorCol, coorRow, currentChess, count) == true){
            return true;
        }
        if(checkDiagonalTopleft(coorCol, coorRow, currentChess, count) == true){
            return true;
        }
        if(checkDiagonalTopright(coorCol, coorRow, currentChess, count) == true){
            return true;
        }
        // check board full of chessspieces
        for(int i = 0; i < HLIMIT; i++){
            for(int j = 0; j < WLIMIT; j++){
                if(b[i][j] == '_'){return false;}
            }
        }
        state = Type.draw;
        return true;
    }

    // checkWinColumn and checkWinRow maybe could be combined by setting an extra parameter
    private boolean checkWinColumn(int i,int j,char currentChess,int count){
        while(i >= 0 && b[i][j] == currentChess){
            i--;
            count++;
        }
        i = coorCol + 1;
        while(i < WLIMIT && b[i][j] == currentChess){
            i++;
            count++;
        }
        if(count == 5){
            state = (currentChess == 'X') ? Type.blackWin : Type.whiteWin;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkWinRow(int i,int j,char currentChess,int count){
        while(j >= 0 && b[i][j] == currentChess){
            j--;
            count++;
        }
        j = coorRow + 1;
        while(j < HLIMIT && b[i][j] == currentChess){
            j++;
            count++;
        }
        if(count == 5){
            state = (currentChess == 'X') ? Type.blackWin : Type.whiteWin;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkDiagonalTopleft(int i,int j,char currentChess,int count){
        while(i >= 0 && j >= 0 && b[i][j] == currentChess){
            i--;
            j--;
            count++;
        }
        i = coorCol + 1;
        j = coorRow + 1;
        while(i < WLIMIT && j < HLIMIT && b[i][j] == currentChess){
            i++;
            j++;
            count++;
        }
        if(count == 5){
            state = (currentChess == 'X') ? Type.blackWin : Type.whiteWin;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkDiagonalTopright(int i,int j,char currentChess,int count){
        while(i >= 0 && j < HLIMIT && b[i][j] == currentChess){
            i--;
            j++;
            count++;
        }
        i = coorCol + 1;
        j = coorRow - 1;
        while(i < WLIMIT && j >= 0 && b[i][j] == currentChess){
            i++;
            j--;
            count++;
        }
        if(count == 5){
            state = (currentChess == 'X') ? Type.blackWin : Type.whiteWin;
            return true;
        }
        else{
            return false;
        }
    }
    private void switchPlayer(){
        if(state == Type.blackTurn){
            state = Type.whiteTurn;
        }
        else{
            state = Type.blackTurn;
        }
    }

    /* reconstructed program, need review test functions */
    /* -----------testing------------ */
    /*
    public static void main(String[] args)
    {
        State program = new State(3, 3);
        program.test();
    }

    private void test()
    {
        boolean testing = false;
        assert(testing = true);
        if(!testing) throw new Error("Use java -ea Board");
        test_all();
    }

    private char[][] getNewBoard()
    {
        HLIMIT = 3;
        WLIMIT = 3;
        char[][] b = new char[HLIMIT][WLIMIT];
        for(int i = 0; i < HLIMIT; i++){
            for(int j = 0; j < WLIMIT; j++){
                b[i][j] = '_';
            }
        }
        return b;
    }

    private void test_all()
    {
        test_init();
        test_parseCoor();
        char[][] b = getNewBoard();
        test_checkExistAndOutOfBoard(b);
        b = getNewBoard();
        test_game_finish(b);
        b = getNewBoard();
        test_validateMove(b);
        b = getNewBoard();
        test_setChesspiece(b);
        System.out.println("All State tests pass");
    }

      Dependencies:
    
        current_state(too simple, no test)
        get_coor
        validateMove->parseCoordinate
                    ->checkExistAndOutOfBoard
        nextState->switchPlayer(too simple, no test)
                ->game_finish
    

    private void test_init()
    {
        char[][] b = new char[3][3];
        initBoard();
        assert(b[0][0] == '_');
        assert(b[0][2] == '_');
        assert(b[1][1] == '_');
        assert(b[2][1] == '_');
    }

    private void test_parseCoor()
    {
        parseCoordinate("1a");
        assert(coorRow == 0);
        assert(coorCol == 0);
        parseCoordinate("c3");
        assert(coorRow == 2);
        assert(coorCol == 2);
    }

    private void test_checkExistAndOutOfBoard(char[][] b)
    {
        coorRow = -1; coorCol = 0;
        assert(checkExistAndOutOfBoard(b) == CheckInfo.outOfBound);
        coorRow = 4; coorCol = 0;
        assert(checkExistAndOutOfBoard(b) == CheckInfo.outOfBound);
        coorRow = 1; coorCol = -1;
        assert(checkExistAndOutOfBoard(b) == CheckInfo.outOfBound);
        coorRow = 1; coorCol = 10;
        assert(checkExistAndOutOfBoard(b) == CheckInfo.outOfBound);
        coorCol = 1;
        // Now x == 1, y == 1;
        b[coorCol][coorRow] = 'X';
        assert(checkExistAndOutOfBoard(b) == CheckInfo.alreadyExist);
        coorRow = 2; coorCol = 2;
        b[coorCol][coorRow] = 'O';
        assert(checkExistAndOutOfBoard(b) == CheckInfo.alreadyExist);
        coorRow = 0; coorCol = 0;
        b[coorCol][coorRow] = '_';
        assert(checkExistAndOutOfBoard(b) == CheckInfo.valid);
    }

    private void test_game_finish(char[][] b)
    {
        b[0][0] = b[0][1] = b[0][2] = 'X';
        assert(game_finish(b) == true);
        b[0][2] = b[1][2] = b[2][2] = 'O';
        assert(game_finish(b) == true);
        b[0][0] = b[1][1] = b[2][2] = 'X';
        assert(game_finish(b) == true);
        b[2][0] = b[1][1] = b[0][2] = 'O';
        assert(game_finish(b) == true);
    }

    private void test_validateMove(char[][] b)
    {
        assert(validateMove(b, "a11", 3, 3) == CheckInfo.invalidLength);
        assert(validateMove(b, "3c", 3, 3) == CheckInfo.valid);
        assert(validateMove(b, "5c", 3, 3) == CheckInfo.outOfBound);
        assert(validateMove(b, "3s", 3, 3) == CheckInfo.outOfBound);
        assert(validateMove(b, "*+", 3, 3) == CheckInfo.invalidType);
    }

    private void test_setChesspiece(char[][] b)
    {
        state = Type.XTurn;
        coorCol = 1; coorRow = 0;
        setChesspiece(b);
        assert(b[1][0] == 'X');
        coorCol = 0; coorRow = 1;
        state = Type.OTurn;
        setChesspiece(b);
        assert(b[0][1] == 'O');
        coorCol = 1; coorRow = 2;
        state = Type.XTurn;
        setChesspiece(b);
        assert(b[1][2] == 'X');
    }*/
}