package Proyecto2;

/**
 * Clase Tablero.
 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
 *
 */
public class Tablero {
	
	protected Celda [][] matriz;
	protected static final int MAX = 9;
	protected int cantInsertados;
	
	/**
	 * Inicializa un nuevo tablero.
	 */
	public Tablero() {
		matriz = new Celda[MAX][MAX];
		for(int fila = 0; fila < MAX; fila++) {
			for(int columna = 0; columna < MAX; columna++) {
				matriz[fila][columna] = new Celda(0);
			}
		}
	}
	
	/**
	 * Retorna la celda contenida en la fila y columna parametrizadas.
	 * @param fila Fila correspondiente a la celda solicitada.
	 * @param columna Columna correspondiente a la celda solicitada.
	 * @return Celda solicitada.
	 */
	public Celda getCelda(int fila, int columna) {
		return matriz[fila][columna];
	}
	
	/**
	 * Inserta el n�mero num en la posici�n parametrizada.
	 * @param num N�mero a insertar.
	 * @param fila Fila donde se insertar� el nuevo n�mero. 
	 * @param columna Columna donde se insertar� el nuevo n�mero.
	 */
	public void insertar(int num, int fila, int columna) {
		if(matriz[fila][columna].getElemento() == 0)
			cantInsertados++;
		matriz[fila][columna].setElemento(num);
	}
	
	/**
	 * Elimina el n�mero num de la posici�n parametrizada.
	 * @param fila Fila donde se encuentra el n�mero a eliminar.
	 * @param columna Columna donde se encuentra el n�mero a eliminar.
	 */
	public void eliminar(int fila, int columna) {
		matriz[fila][columna].setElemento(0);
		cantInsertados--;;
	}
	
	/**
	 * Retorna la cantidad de n�meros insertados actualmente en el tablero.
	 * @return Cantidad de inserciones en el tablero.
	 */
	public int cantidadInserciones() {
		return cantInsertados;
	}
	
	/**
	 * Verifica si la inserci�n hecha permite que el tablero siga siendo v�lido.
	 * @param fila Fila donde se verificar� que el n�mero no haya sido insertado.
	 * @param columna Columna donde se verificar� que el n�mero no haya sido insertado.
	 * @param num N�mero a verificar.
	 * @return Verdadero si el tablero sigue siendo v�lido luego de la inserci�n, falso en caso contrario.
	 */
	public boolean insercionValida(int fila, int columna, int num) {
		return filaValida(fila, columna, num) && columnaValida(fila, columna, num) && areaValida(fila, columna, num);
	}
	
	/**
	 * Retorna la dimensi�n de las filas y columnas del tablero.
	 * @return Dimensi�n de las filas y columnas del tablero.
	 */
	public int dimension() {
		return MAX;
	}
	
	/**
	 * Verifica que el n�mero num no se encuentre en la fila parametrizada.
	 * @param fila Fila donde se verificar� que el n�mero no haya sido insertado.
	 * @param num N�mero a verificar.
	 * @return Verdadero si la fila sigue siendo v�lida luego de la inserci�n, falso en caso contrario.
	 */
	private boolean filaValida(int fila, int columna, int num) {
		boolean esValida = true;
		int posicion = 0;
		while(posicion < MAX && esValida) {
			if(posicion != columna)
				if(matriz[fila][posicion].getElemento() == num)
					esValida = false;
			posicion++;
		}	
		return esValida;
	}
	
	/**
	 * Verifica que el n�mero num no se encuentre en la columna parametrizada.
	 * @param columna Columna donde se verificar� que el n�mero no haya sido insertado.
	 * @param num N�mero a verificar.
	 * @return Verdadero si la columna sigue siendo v�lida luego de la inserci�n, falso en caso contrario.
	 */
	private boolean columnaValida(int fila, int columna, int num) {
		boolean esValida = true;
		int posicion = 0;
		while(posicion < MAX && esValida) {
			if(posicion != fila)
				if(matriz[posicion][columna].getElemento() == num)
					esValida = false;
			posicion++;
		}
		return esValida;
	}
	
	/**
	 * Verifica que el n�mero num no se encuentre en el �rea correspondiente a una matriz de 3x3 a partir de la fila y columna parametrizadas.
	 * @param fActual Fila donde se verificar� que el n�mero no haya sido insertado.
	 * @param cActual Columna donde se verificar� que el n�mero no haya sido insertado.
	 * @param num N�mero a verificar.
	 * @return Verdadero si el �rea sigue siendo v�lida luego de la inserci�n del n�mero, falso en caso contrario.
	 */
	private boolean areaValida(int fila, int columna, int num) {
		int fInicial = buscarInicio(fila), cInicial = buscarInicio(columna);
		return validarMatrizP(fInicial, cInicial, num, fila, columna);
	}
	
	/**
	 * A partir de una fila/columna parametrizada, busca el punto de inicio de la matriz de 3x3 asociada. 
	 * @param inicial Fila/columna pr�xima a verificar.
	 * @return Inicio de la matriz de 3x3 donde se encuentra la fila/columna buscada.
	 */
	private int buscarInicio(int inicial) {
		int valorRetorno;
		if(inicial < 3)
			valorRetorno = 0;
		else if( inicial >= 3 && inicial < 6)
			valorRetorno = 3;
		else
			valorRetorno = 6;
		return valorRetorno;
	}

	/**
	 * Verifica si una matriz de 3x3 no contiene el n�mero buscado.
	 * @param filaActual Fila inicial de la matriz de 3x3.
	 * @param columnaActual Columna inicial de la matriz de 3x3.
	 * @param num N�mero a buscar.
	 * @param filaNumero Fila donde se encuentra ubicado el n�mero a buscar.
	 * @param columnaNumero Columna donde se encuentra ubicado el n�mero a buscar.
	 * @return Verdadero si el n�mero no fue encontrado en la matriz, falso en caso contrario.
	 */
	private boolean validarMatrizP(int filaActual, int columnaActual, int num, int filaNumero, int columnaNumero) {
		boolean esValida = true;
		int finF = filaActual + 2, finC = columnaActual + 2;
		while(filaActual <= finF && esValida) {
			while(columnaActual <= finC && esValida) {
				if(filaActual != filaNumero && columnaActual != columnaNumero)
					if(matriz[filaActual][columnaActual].getElemento() == num)
						esValida = false;
				columnaActual++;
			}
			columnaActual = finC - 2;
			filaActual++;
		}
		return esValida;
	}	
}
