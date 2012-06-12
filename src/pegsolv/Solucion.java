package pegsolv;

import java.util.*;

public class Solucion {

	public int coordvacia;
	public int solucionable;
	public int[] moves;
	public int[] board;
	public int[][] pinlist;
	public int[][] movedirs;
	public int moveNr = 0;
	public int xmove = 0;
	public int p1;
	public int p2;
	public int p3;
	public int topmoveNr;
	public int bottommoveNr = 1;
	public int sol = 1;
	public int[][] PMStacks = new int[49][31];
	public int[] PMHeight = new int[49];
	public long ti;

	public Solucion(int[] Tablero, int Tipo) {

		board = new int[Tablero.length];
		System.arraycopy(Tablero, 0, board, 0, Tablero.length);
		
		for (int i = 0; i < board.length; i++){
			if (board[i] == 0){
				coordvacia = i;
				break;
			}
		}

		if (Tipo == 1) {			
			// {arriba, izquierda, abajo, derecha}
			int[][] allowedMoves = { {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 1, 0},
					{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 0}, {0, 1, 1, 0},
					{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 1}, {0, 0, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1},
					{0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1},
					{0, 1, 0, 0}, {0, 1, 0, 0}, {1, 0, 0, 1}, {1, 0, 0, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1},
					{1, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 1}, {1, 0, 0, 0}, {1, 1, 0, 0},
					{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 1}, {1, 0, 0, 0}, {1, 1, 0, 0},
					{0, 0, 0, 0}, {0, 0, 0, 0}};
			movedirs = new int[allowedMoves.length][4];
			System.arraycopy(allowedMoves, 0, movedirs, 0, allowedMoves.length);
		}
		
		pinlist = new int[76][3];
		
		moves = new int[31];
		Arrays.fill(PMHeight, 0);
		for(int i = 0; i <= 48; i++){
			Arrays.fill(PMStacks[i], 0);
		}
	}
	
	public int resolver(){
		
		makeMovePinlist();
		ti = System.currentTimeMillis();
		
		while(true){
			testmove();
			move();
			//System.out.println(moveNr);
			if(resuelto()){
				return solucionable = 1;
			} else if(noresolv()){
				return solucionable = 0;
			}
			xmove = 0;
		}
	}
	
	public int[] tablero(){
		return board;
	}
	
	public int[][] movimientos(){
		return pinlist;
	}
	
	void makeMovePinlist(){		
		int n = -1;
		for(int m = 0; m <= 48; m++){
			if(movedirs[m][0] == 1){
				n++;
				pinlist[n][0] = m;
				pinlist[n][1] = pinlist[n][0] - 7;
				pinlist[n][2] = pinlist[n][1] - 7;
			} 
			if(movedirs[m][1] == 1){
				n++;
				pinlist[n][0] = m;
				pinlist[n][1] = pinlist[n][0] - 1;
				pinlist[n][2] = pinlist[n][1] - 1;
			} 
			if(movedirs[m][2] == 1){
				n++;
				pinlist[n][0] = m;
				pinlist[n][1] = pinlist[n][0] + 7;
				pinlist[n][2] = pinlist[n][1] + 7;
			} 
			if(movedirs[m][3] == 1){
				n++;
				pinlist[n][0] = m;
				pinlist[n][1] = pinlist[n][0] + 1;
				pinlist[n][2] = pinlist[n][1] + 1;
			}
		}	
	}
	
	void testmove(){
		int pm;
		p1 = pinlist[xmove][0];
		p2 = pinlist[xmove][1];
		p3 = pinlist[xmove][2];
		
		if(board[p1] == 0){
			nextmove();
		} else if(board[p2] == 0){
			nextmove();
		} else if(board[p3] == 1){
			nextmove();
		}
		
		pm = lastPmove(xmove) + 1;
		while(pm < moveNr){
			if(moves[pm] > xmove){
				nextmove();
			}
			pm++;
		}
		
	}

	void move(){
		board[p1] = 0;
		board[p2] = 0;
		board[p3] = 1;
		moves[moveNr] = xmove;
		PMPush(xmove, moveNr);
		moveNr++;
	}
	
	void nextmove(){
		xmove++;
		if(xmove < 76){
			testmove();
		} else {
			moveback();
		}
	}
	
	void moveback(){
		if(moveNr < bottommoveNr){
			sol = 0;
			topmoveNr = moveNr - 1;
			return;
		}
		
		moveNr--;
		int m = moves[moveNr];
		p1 = pinlist[m][0];
		p2 = pinlist[m][1];
		p3 = pinlist[m][2];
		board[p1] = 1;
		board[p2] = 1;
		board[p3] = 0;
		xmove = moves[moveNr];
		PMPop(xmove);
		//System.out.println("Moving back MoveNr: "+ (moveNr+1));
		nextmove();
	}
	
	boolean resuelto(){
		if((System.currentTimeMillis() - ti) <= 30000){
			if((moveNr == 31) && (board[coordvacia] == 1)){
				sol = 1;
				topmoveNr = moveNr - 1;
				return true;
			}
		} else{
			if(moveNr == 31){
				sol = 1;
				topmoveNr = moveNr - 1;
				return true;
			}
		}
		
		return false;
	}
	
	boolean noresolv(){
		if(sol == 0){
			return true;
		} else { return false; }
	}
	
	void PMPush(int m, int nr) {
		
		int pin; int h;
		for(int i = 0; i <= 2; i++){
			pin = pinlist[m][i];
			h = PMHeight[pin] + 1;
			PMStacks[pin][h] = nr;
			PMHeight[pin] = h;
			
		}
	}
	
	void PMPop(int m){
		int pin;
		for(int i = 0; i <= 2; i++){
			pin = pinlist[m][i];
			PMHeight[pin]--;			
		}
	}
	
	int lastPmove(int m){
		int pin; int x; int result = 0;
		for(int i = 0; i <= 2; i++){
			pin = pinlist[m][i];
			x = PMStacks[pin][PMHeight[pin]];
			if((x != 0) && (x > result)){
				result = x;
			}
		}
		return result;
	}
}
