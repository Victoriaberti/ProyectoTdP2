package Proyecto2;

/**
 * Clase Celda.
 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
 *
 */
public class Celda {
	
	private int elemento;
	private boolean esEditable;
	
	/**
	 * Inicializa una nueva celda.
	 * @param elemento Elemento de la nueva celda.
	 */
	public Celda(int elemento) {
		this.elemento = elemento;
		esEditable = true;
	}

	/**
	 * Retorna el elemento contenido en la celda.
	 * @return Elemento contenido en la celda.
	 */
	public int getElemento() {
		return elemento;
	}

	/**
	 * Reemplaza el elemento en la celda por el elemento parametrizado.
	 * @param elemento Nuevo elemento de la celda.
	 */
	public void setElemento(int elemento) {
		this.elemento = elemento;
	}

	/**
	 * Verifica si el contenido de la celda es editable o no.
	 * @return Verdadero si el contenido de la celda es editable, falso en caso contrario.
	 */
	public boolean esEditable() {
		return esEditable;
	}

	/**
	 * Modifica la editabilidad de la celda.
	 * @param esEditable Establece si la celda será editable o no.
	 */
	public void setEditable(boolean esEditable) {
		this.esEditable = esEditable;
	}
}
