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
	 * Inserta el número num en la posición parametrizada.
	 * @param num Número a insertar.
	 * @param fila Fila donde se insertará el nuevo número. 
	 * @param columna Columna donde se insertará el nuevo número.
	 */
	public void insertar(int num, int fila, int columna) {
		if(matriz[fila][columna].getElemento() == 0)
			cantInsertados++;
		matriz[fila][columna].setElemento(num);
	}
	
	/**
	 * Elimina el número num de la posición parametrizada.
	 * @param fila Fila donde se encuentra el número a eliminar.
	 * @param columna Columna donde se encuentra el número a eliminar.
	 */
	public void eliminar(int fila, int columna) {
		matriz[fila][columna].setElemento(0);
		cantInsertados--;;
	}
	
	/**
	 * Retorna la cantidad de números insertados actualmente en el tablero.
	 * @return Cantidad de inserciones en el tablero.
	 */
	public int cantidadInserciones() {
		return cantInsertados;
	}
	
	/**
	 * Verifica si la inserción hecha permite que el tablero siga siendo válido.
	 * @param fila Fila donde se verificará que el número no haya sido insertado.
	 * @param columna Columna donde se verificará que el número no haya sido insertado.
	 * @param num Número a verificar.
	 * @return Verdadero si el tablero sigue siendo válido luego de la inserción, falso en caso contrario.
	 */
	public boolean insercionValida(int fila, int columna, int num) {
		return filaValida(fila, columna, num) && columnaValida(fila, columna, num) && areaValida(fila, columna, num);
	}
	
	/**
	 * Retorna la dimensión de las filas y columnas del tablero.
	 * @return Dimensión de las filas y columnas del tablero.
	 */
	public int dimension() {
		return MAX;
	}
	
	/**
	 * Verifica que el número num no se encuentre en la fila parametrizada.
	 * @param fila Fila donde se verificará que el número no haya sido insertado.
	 * @param num Número a verificar.
	 * @return Verdadero si la fila sigue siendo válida luego de la inserción, falso en caso contrario.
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
	 * Verifica que el número num no se encuentre en la columna parametrizada.
	 * @param columna Columna donde se verificará que el número no haya sido insertado.
	 * @param num Número a verificar.
	 * @return Verdadero si la columna sigue siendo válida luego de la inserción, falso en caso contrario.
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
	 * Verifica que el número num no se encuentre en el área correspondiente a una matriz de 3x3 a partir de la fila y columna parametrizadas.
	 * @param fActual Fila donde se verificará que el número no haya sido insertado.
	 * @param cActual Columna donde se verificará que el número no haya sido insertado.
	 * @param num Número a verificar.
	 * @return Verdadero si el área sigue siendo válida luego de la inserción del número, falso en caso contrario.
	 */
	private boolean areaValida(int fila, int columna, int num) {
		int fInicial = buscarInicio(fila), cInicial = buscarInicio(columna);
		return validarMatrizP(fInicial, cInicial, num, fila, columna);
	}
	
	/**
	 * A partir de una fila/columna parametrizada, busca el punto de inicio de la matriz de 3x3 asociada. 
	 * @param inicial Fila/columna próxima a verificar.
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
	 * Verifica si una matriz de 3x3 no contiene el número buscado.
	 * @param filaActual Fila inicial de la matriz de 3x3.
	 * @param columnaActual Columna inicial de la matriz de 3x3.
	 * @param num Número a buscar.
	 * @param filaNumero Fila donde se encuentra ubicado el número a buscar.
	 * @param columnaNumero Columna donde se encuentra ubicado el número a buscar.
	 * @return Verdadero si el número no fue encontrado en la matriz, falso en caso contrario.
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
