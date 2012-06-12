package pegsolv;
public class test {
	public static void main(String[] args) throws InterruptedException {
		// Clase para probar Solucion.java y Tablero.java
		
		Tablero tablero = new Tablero("Cruz");		
		tablero.asignar(21, 0);
		int[] map = tablero.mapa();
		String m = "";
		for(int n = 0; n < map.length; n++){
			if( n%7 == 0 ){
				m = m + "\n";
			}
			m = m + ("" + map[n]);
		}
		
		System.out.println(m);
		
		Solucion sol = new Solucion(tablero.mapa(), 1);
		
		System.out.println(sol.resolver());
		
		int[] solucion = sol.tablero();
		String s = "";
		for(int n = 0; n < solucion.length; n++){
			if( n%7 == 0 ){
				s = s + "\n";
			}
			s = s + ("" + solucion[n]);
		}
		
		System.out.println(s);
		
		int[][] moves = sol.movimientos();
		String d = "";
		
		for(int n = 0; n < moves.length; n++){
			d = d + ("" + moves[n]);
		}
		
		System.out.println(d);
	}

}
