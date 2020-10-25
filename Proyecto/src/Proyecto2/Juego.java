package Proyecto2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Clase Juego.
 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
 *
 */
public class Juego {
	
	private static final float probRandom = 0.7f;
	private Tablero tablero, estadoInicial;
	private String rutaSudoku;
	private boolean sudokuValido;
	
	/**
	 * Inicializa un nuevo juego.
	 */
	public Juego() {	
		try {
			tablero = new Tablero();
			rutaSudoku = "Archivo/sudokuValido.txt";
			if(validarTablero()) {
				sudokuValido = true;
				eliminarRandom();
			}
			else
				sudokuValido = false;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna el tablero actual.
	 * @return Tablero actual.
	 */
	public Tablero getTablero() {
		return tablero;
	}
	
	/**
	 * Verifica la validez del sudoku.
	 * @return Verdadero si es un sudoku válido, falso en caso contrario.
	 */
	public boolean sudokuValido() {
		return sudokuValido;
	}
	
	/**
	 * Reinicia el tablero a su estado inicial.
	 */
	public void reiniciar() {
		tablero = estadoInicial;
		guardarCopiaInicial();
	}
	
	/**
	 * Verifica si el archivo asociado al tablero contiene un sudoku válido.
	 * @return Verdadero si el archivo contiene un sudoku válido, falso en caso contrario.
	 * @throws FileNotFoundException Si el archivo no fue encontrado.
	 */
	private boolean validarTablero() throws FileNotFoundException {
		int posCaracter = 0, fila = 0, columna = 0;
		boolean esValido = true;
		BufferedReader archivo;
		String linea, caracter;
		String [] numeros;
		try {
			archivo = new BufferedReader(new FileReader (rutaSudoku));
			linea = archivo.readLine();
			while(linea != null && esValido) {
				numeros = linea.split(" ");
				while(posCaracter < numeros.length && esValido) {
					caracter = numeros[posCaracter];
					tablero.insertar(Integer.parseInt(caracter), fila, columna);
					posCaracter++;
					columna++;
				}
				columna = 0;
				fila++;
				posCaracter = 0;
				linea = archivo.readLine();	
			}
			archivo.close();
			esValido = esValido && sudoku_valido() ? true : false;	
		} 
		catch (NumberFormatException | NullPointerException | IOException e) {
			e.printStackTrace();
		}
		return esValido;
	}
	
	/**
	 * Verifica si el sudoku contenido en el tablero es válido.
	 * @return Verdadero si el sudoku contenido en el tablero es válido, falso en caso contrario.
	 */
	private boolean sudoku_valido() {
		boolean esValido = true;
		int fila = 0, columna = 0, numero;
		int dimension = tablero.dimension();
		while(fila < dimension && esValido) {
			 while(columna < dimension && esValido) { 
				 numero = tablero.getCelda(fila, columna).getElemento();
				 esValido = esValido && tablero.insercionValida(fila, columna, numero);
				 columna++;
			 }
			 columna = 0;
			 fila++;
		 } 
		 return esValido;
	}
	
	/**
	 * Elimina aleatoriamente números del tablero.
	 */
	private void eliminarRandom() {
		Random numero = new Random();
		int dimension = tablero.dimension();
		for(int fila = 0; fila < dimension; fila++) {
			for(int columna = 0; columna < dimension; columna++) {
				if(probRandom > numero.nextFloat())
					tablero.eliminar(fila, columna);
				else
					tablero.getCelda(fila, columna).setEditable(false);
			}
		}
		guardarCopiaInicial();
	}
	
	/**
	 * Guarda una copia del tablero inicial.
	 */
	private void guardarCopiaInicial() {
		int elemento;
		estadoInicial = new Tablero();
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				elemento = tablero.getCelda(i, j).getElemento();
				if(elemento != 0)
					estadoInicial.insertar(elemento, i, j);
			}
		}
	}	
}
