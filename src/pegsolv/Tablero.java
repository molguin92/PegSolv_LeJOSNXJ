/*	Class "Tablero" for solving a Peg Solitaire board.
    Copyright (C) 2012  Manuel Olguin <manuel.olguin@ug.uchile.cl>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package pegsolv;
import java.util.*;

public class Tablero {
	
	public int[] board;
	
	public Tablero(String tipo){
		
		if(tipo == "Cruz"){
			board = new int[49];
			Arrays.fill(board, 1);
			board[0] = 2;
			board[1] = 2;
			board[5] = 2;
			board[6] = 2;
			board[7] = 2;
			board[8] = 2;
			board[12] = 2;
			board[13] = 2;
			board[35] = 2;
			board[36] = 2;
			board[40] = 2;
			board[41] = 2;
			board[42] = 2;
			board[43] = 2;
			board[47] = 2;
			board[48] = 2;
		
		}
		
	}
	
	public void asignar(int coord, int valor){
		if((coord != 0) && (coord != 1) && (coord != 5) && (coord != 6) && (coord != 7) && (coord != 8) && (coord != 12) && (coord != 13) && (coord != 35) && (coord != 36) && (coord != 40) && (coord != 41) && (coord != 42) && (coord != 43) && (coord != 47) && (coord != 48) ){
			board[coord] = valor;
		} else {
			System.out.println("Empty pin index out of bounds.");
			System.exit(0);
		}
	}
	
	public int[] mapa() {
		return board;
	}

}
