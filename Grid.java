class Grid{
	private char X='X', O='O',S=' ';
	private char[][] cells = {{S,S,S},{S,S,S},{S,S,S}};
	private char turn = X;
	
	char get(int x,int y){return cells[x][y];};
	public void move(int x,int y){
		if(cells[x][y] == S){
			cells[x][y]=turn;
			turn = (turn==X) ?O:X;
		}
	}

	public void reset(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				cells[i][j] = S;
			}
		}
		turn = X;
	}
	public char getTurn(){
		return turn;
	}

	public boolean Tie(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(cells[i][j] == S){
					return false;
				}
			}
		}
		return true;
	}
	

	public boolean playable(){
		for(int i = 0; i < 3; i++){
			if(cells[i][0] == cells[i][1] &&
				cells[i][1] == cells[i][2] &&
				cells[i][0] != S){
				return false;
			}
		}
		for(int j = 0; j < 3; j++){
			if(cells[0][j] == cells[1][j] &&
				cells[1][j] == cells[2][j] &&
				cells[0][j] != S){
				return false;
			}
		}
		if(cells[0][0] == cells[1][1] &&
			cells[1][1] == cells[2][2] &&
			cells[1][1] != S){
				return false;
			}
		if(cells[2][0] == cells[1][1] &&
			cells[1][1] == cells[0][2] &&
			cells[1][1] != S){
				return false;
		}
		return true;
	}
}