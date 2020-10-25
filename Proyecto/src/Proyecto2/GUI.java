package Proyecto2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

/**
 * Clase GUI. 
 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
 *
 */
public class GUI extends JFrame {
	
	private static final int borrarCelda = 0, noRealizarAccion = 10; 
	private static final int insercionesTotales = 81;
	private JPanel contentPane, panelSudoku, panelEeveelutions, panelOpciones;
	private JLabel fondo;
	private JButton [][] botones;
	private JButton [] imagenesSeleccion;
	private JButton borrar, deshacer, reiniciar;
	private Juego juego;
	private boolean seleccionIncorrecta;
	private int seleccion, ultimaFila, ultimaColumna, ultimoNumero;

	private Timer t;
	private JLabel [] imTiempo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 773);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setFont(new Font("Consolas", Font.PLAIN, 13));
		setBackground(Color.DARK_GRAY);
		setTitle("Sudoku eeveelution");	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		juego = new Juego();
		inicializar_componentes();
		inicializar_juego();			
	}
	
	/**
	 * Inicializa los componentes de la GUI.
	 */
	public void inicializar_componentes() {
		
		//Inicializar botones del tablero
		OyenteBotonesSudoku oyenteTablero = new OyenteBotonesSudoku();
		int dimension = juego.getTablero().dimension();
		botones = new JButton[dimension][dimension];
		for(int fila = 0; fila < dimension; fila++) {
			for(int columna = 0; columna < dimension; columna++) {
				botones[fila][columna] = new JButton();
				botones[fila][columna].setActionCommand(""+fila+","+columna);
				botones[fila][columna].addActionListener(oyenteTablero);
				modificar_interfaz_botones(fila, columna);
			}
		}
		
		//Inicializar paneles del tablero
		
		imTiempo = new JLabel[8];
		int coordenada = 364; 
		for(int i = 0; i < imTiempo.length; i++) {
			imTiempo[i] = new JLabel();
			imTiempo[i].setBounds(coordenada+14, 47, 14, 27);
			coordenada = coordenada + 14;
			if(i == 2 || i == 5)
				imTiempo[i].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/dos_puntos.png")));
			else
				imTiempo[i].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/t0.png")));
			contentPane.add(imTiempo[i]);
		}
	
		panelSudoku = new JPanel();
		panelSudoku.setBorder(new LineBorder(null, 3));
		panelSudoku.setBounds(10, 84, 480, 480);
		panelSudoku.setBackground(new java.awt.Color(0,0,0,10));
		panelSudoku.setLayout(new GridLayout(3, 3, 0, 0));
		contentPane.add(panelSudoku);
		
		panelEeveelutions = new JPanel();
		panelEeveelutions.setBorder(null);
		panelEeveelutions.setBounds(10, 574, 483, 50);
		panelEeveelutions.setBackground(new java.awt.Color(0,0,0,10));
		contentPane.add(panelEeveelutions);
		panelEeveelutions.setLayout(new GridLayout(0, 9, 0, 0));

		panelOpciones = new JPanel();
		panelOpciones.setBounds(0, 626, 504, 132);
		panelOpciones.setBackground(new java.awt.Color(0,0,0,10));
		contentPane.add(panelOpciones);
		
		fondo = new JLabel("");
		fondo.setBounds(-71, -24, 582, 814);
		fondo.setIcon(new ImageIcon(GUI.class.getResource("/Imagenes/fondoKanto.png")));
		contentPane.add(fondo);	
		
		//Inicializar botones opciones
		deshacer = new JButton();
		deshacer.setBounds(101, 20, 59, 59);
		deshacer.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/deshacer.png")));
		deshacer.addActionListener(new OyenteDeshacer());
		deshacer.setBackground(Color.WHITE);
		panelOpciones.setLayout(null);
		panelOpciones.add(deshacer);
		
		borrar = new JButton();
		borrar.setBounds(222, 20, 59, 59);
		borrar.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/borrar.png")));
		borrar.addActionListener(new OyenteBorrar());
		borrar.setBackground(Color.WHITE);
		panelOpciones.add(borrar);
				
		reiniciar = new JButton();
		reiniciar.setBounds(339, 20, 59, 59);
		reiniciar.setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/reiniciar.png")));
		reiniciar.addActionListener(new OyenteReiniciar());
		reiniciar.setBackground(Color.WHITE);
		panelOpciones.add(reiniciar);

		//Inicializar botones eeveelutions
		OyenteBotonesEeveelutions oInferior = new OyenteBotonesEeveelutions();
		imagenesSeleccion = new JButton[9];
		for(int i = 1; i < 10; i++) {
			imagenesSeleccion[i-1] = new JButton("");
			imagenesSeleccion[i-1].addActionListener(oInferior);
			imagenesSeleccion[i-1].setActionCommand(""+i);
			imagenesSeleccion[i-1].setBorder(BorderFactory.createRaisedBevelBorder());
			panelEeveelutions.add(imagenesSeleccion[i-1]);
			imagenesSeleccion[i-1].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+i+".png")));
		}	
			
		//Agregar botones a paneles
		JPanel panel_areas;
		for (int i = 1; i < 10; i++) {
			panel_areas = new JPanel();
			panel_areas.setBorder(new LineBorder(null, 2));
			panel_areas.setBackground(new java.awt.Color(0,0,0,10));
			panelSudoku.add(panel_areas);
			panel_areas.setLayout(new GridLayout(3, 3, 0, 0));	
			switch(i) {	
				case 1:
					agregar_botones(0, 0,panel_areas);
					break;
				case 2:
					agregar_botones(0, 3, panel_areas);
					break;
				case 3:
					agregar_botones(0, 6, panel_areas);
					break;
				case 4:
					agregar_botones(3, 0, panel_areas);
					break;
				case 5:
					agregar_botones(3, 3, panel_areas);
					break;
				case 6:
					agregar_botones(3, 6, panel_areas);
					break;
				case 7:
					agregar_botones(6, 0, panel_areas);
					break;
				case 8:
					agregar_botones(6, 3, panel_areas);
					break;
				case 9:
					agregar_botones(6, 6, panel_areas);
					break;	
			}
		}	
	}
	
	/**
	 * Inicializa una nueva instancia de juego.
	 */
	public void inicializar_juego() {
		
		panelEeveelutions.setVisible(true);
		panelOpciones.setVisible(true);
		habilitar_panel_inferior(false);
		inicializar_atributos();
		int dimension = juego.getTablero().dimension();		
		juego = new Juego();
		for(int fila = 0; fila < dimension; fila++) {
			for(int columna = 0; columna < dimension; columna++) {
				modificar_interfaz_botones(fila, columna);
			}
		}
		
		iniciar_cronometro();
	}
	
	//Metodos auxiliares
	
	/**
	 * Inicializa el tiempo de juego.
	 */
	private void iniciar_cronometro() {
		LocalTime start = LocalTime.now( );
	    t = new Timer();
	    TimerTask tt = new TimerTask() {	
	        @Override
	        public void run() {
	            LocalTime stop = LocalTime.now( );
	            Duration d = Duration.between(start, stop);
	            
	            String hms = String.format("%02d:%02d:%02d", 
	                            d.toHours(), 
	                            d.toMinutesPart(), 
	                            d.toSecondsPart());
	            for(int i = 0; i < hms.length(); i++) {
	            	if(hms.charAt(i) != ':')
	            		imTiempo[i].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/t"+hms.charAt(i)+".png")));
	            }
	        };
	    };
	    t.schedule(tt, 0, 1000);
	}
	
	/**
	 * Inicializa los atributos del juego.
	 */
	private void inicializar_atributos() {
		seleccionIncorrecta = false;
		seleccion = 10;
		actualizar_ultimo_movimiento(10,10,10);		
	}

	/**
	 * Actualiza la última jugada realizada.
	 * @param fila Fila donde se interactuó por última vez.
	 * @param columna Columna donde se interactuó por última vez.
	 * @param numero Último número insertado.
	 */
	private void actualizar_ultimo_movimiento(int fila, int columna, int numero) {
		ultimoNumero = numero;
        ultimaFila = fila;
        ultimaColumna = columna;
	}
	
	/**
	 * Agrega los botones del sudoku a sus respectivas sub-áreas.
	 * @param posFila Fila en la que comienza la inserción de botones.
	 * @param posColumna Columna en la que comienza la inserción de botones.
	 * @param panel Panel al que se agregan los botones correspondientes.
	 */
	private void agregar_botones(int posFila, int posColumna, JPanel panel) {
		int iniCaux = posColumna;
		int finFila = posFila + 2, finColumna = posColumna + 2;
		while(posFila <= finFila) {
			while(posColumna <= finColumna) {
				panel.add(botones[posFila][posColumna]);
				posColumna++;
			}
			posFila++;
			posColumna = iniCaux;
		}
	}
	
	/**
	 * Habilita/deshabilita los botones del sudoku.
	 * @param estado Indica la acción a realizar, habilitar si su valor es <true>, deshabilitar si es <false>.
	 */
	private void habilitar_botones_sudoku(boolean estado) {	
		for (int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(juego.getTablero().getCelda(i, j).esEditable())
					botones[i][j].setEnabled(estado);
			}
		}
	}
	
	/**
	 * Marca/desmarca una fila parametrizada.
	 * @param fila Fila a marcar/desmarcar.
	 * @param estado Indica la acción a realizar, marcar si su valor es <true>, desmarcar si es <false>.
	 */
	private void marcar_filas(int fila, boolean estado) {
		for(int columna = 0; columna < 9; columna++) {
			if(estado == true)
				botones[fila][columna].setBorder(BorderFactory.createLineBorder(Color.red));
			else
				botones[fila][columna].setBorder(null);
		}
	}
	
	/**
	 * Marca/desmarca una columna parametrizada.
	 * @param columna Columna a marcar/desmarcar.
	 * @param estado Indica la acción a realizar, marcar si su valor es <true>, desmarcar si es <false>.
	 */
	private void marcar_columnas(int columna, boolean estado) {
		for(int fila = 0; fila < 9; fila++) {
			if(estado == true)
				botones[fila][columna].setBorder(BorderFactory.createLineBorder(Color.red));
			else
				botones[fila][columna].setBorder(null);
		}
	}
	
	/**
	 * Habilita/deshabilita el panel inferior de opciones.
	 * @param estado Indica la acción a realizar, habilitar si su valor es <true>, deshabilitar si es <false>.
	 */
	private void habilitar_panel_inferior(boolean estado) {	
		reiniciar.setEnabled(estado);
		deshacer.setEnabled(estado);
		borrar.setEnabled(estado);
	}
	
	/**
	 * Modifica el botón del sudoku contenido en la fila y columna parametrizadas.
	 * @param fila Número de fila en la que se encuentra el botón a modificar.
	 * @param columna Número de columna en la que se encuentra el botón a modificar.
	 */
	private void modificar_interfaz_botones(int fila, int columna) {
		if(juego.getTablero().getCelda(fila, columna).getElemento() == 0) {
			botones[fila][columna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0.png")));
			botones[fila][columna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0.png")));
			botones[fila][columna].setEnabled(true);
		}
		else {
			botones[fila][columna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+juego.getTablero().getCelda(fila, columna).getElemento()+".png")));
			botones[fila][columna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+juego.getTablero().getCelda(fila, columna).getElemento()+".png")));
			botones[fila][columna].setEnabled(false);
		}
	}

	/**
	 * Deshabilita las opciones correspondientes y muestra un mensaje indicando que el juego fue ganado.
	 */
	private void juego_ganado() { 	
		t.cancel();	
		imagenesSeleccion[seleccion-1].setBorder(BorderFactory.createRaisedBevelBorder());	
		panelEeveelutions.setVisible(false);
		panelOpciones.setVisible(false); 
		String [] botones = {"Jugar otra vez"};
		int opcionElegida = JOptionPane.showOptionDialog(null, "Felicitaciones! Capturaste todas las eevelutions!", "Juego ganado", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE,  new ImageIcon(this.getClass().getResource("/Imagenes/pokeball.png")), botones, botones[0]);
		if(opcionElegida == 0) 
			inicializar_juego();	
	}
	
	/**
	 * Clase OyenteBotonesSudoku.
	 * Implementa la interfaz ActionListener y su método.
	 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
	 *
	 */
	private class OyenteBotonesSudoku implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String indices = e.getActionCommand();
            String[] iyj = indices.split(",");
            int fila = Integer.parseInt(iyj[0]);
            int columna = Integer.parseInt(iyj[1]);
            int numero = juego.getTablero().getCelda(fila, columna).getElemento();   
            if(seleccion != noRealizarAccion) {
            	if(seleccion != borrarCelda && seleccion != numero) {
            		juego.getTablero().insertar(seleccion, fila, columna);
	        		if(juego.getTablero().insercionValida(fila, columna, seleccion)) {
	        			botones[fila][columna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+seleccion+".png")));
	        			botones[fila][columna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+seleccion+".png")));
	        			
	        			if(juego.getTablero().cantidadInserciones() == insercionesTotales)
	        				juego_ganado();
	        		}
	        		else {
	        			seleccionIncorrecta = true;
	        			botones[fila][columna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/error.png")));
	        			botones[fila][columna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/error.png")));
	        			marcar_filas(fila, true);
	        			marcar_columnas(columna, true);
	        			habilitar_botones_sudoku(false);
	        			borrar.setEnabled(false);
	        			reiniciar.setEnabled(true);
	        		}
            	}
            	else {
                	botones[fila][columna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0.png")));
                	botones[fila][columna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/0.png")));
                	juego.getTablero().eliminar(fila,  columna);
                }
            	actualizar_ultimo_movimiento(fila, columna, numero);
            	habilitar_panel_inferior(true);
            }
        }     
		
	}
	
	/**
	 * Clase OyenteBotonesEeveelutions.
	 * Implementa la interfaz ActionListener y su método.
	 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
	 *
	 */
	private class OyenteBotonesEeveelutions implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String indice = e.getActionCommand();
			int numero = Integer.parseInt(indice);	
			if(numero == seleccion) {
				seleccion = noRealizarAccion;
				imagenesSeleccion[numero-1].setBorder(BorderFactory.createRaisedBevelBorder());
			}
			else {
				if(seleccion != borrarCelda && seleccion != noRealizarAccion)
					imagenesSeleccion[seleccion-1].setBorder(BorderFactory.createRaisedBevelBorder());
				else if (seleccion == borrarCelda)
					borrar.setBorder(BorderFactory.createRaisedBevelBorder());
				imagenesSeleccion[numero-1].setBorder(BorderFactory.createLoweredBevelBorder());
				seleccion = numero;
			}		
		}		
	}
	
	/**
	 * Clase OyenteDeshacer.
	 * Implementa la interfaz ActionListener y su método.
	 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
	 *
	 */
	private class OyenteDeshacer implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if (seleccionIncorrecta) {
				marcar_filas(ultimaFila, false);
    			marcar_columnas(ultimaColumna, false);
    			habilitar_botones_sudoku(true);
			}
			if(seleccion != noRealizarAccion) {
				if(seleccion == borrarCelda) 
					borrar.setBorder(BorderFactory.createRaisedBevelBorder());
				else
					imagenesSeleccion[seleccion-1].setBorder(BorderFactory.createRaisedBevelBorder());
				seleccion = noRealizarAccion;
			}
			juego.getTablero().eliminar(ultimaFila, ultimaColumna);
			juego.getTablero().getCelda(ultimaFila, ultimaColumna).setElemento(ultimoNumero);
			botones[ultimaFila][ultimaColumna].setIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+ultimoNumero+".png")));
			botones[ultimaFila][ultimaColumna].setDisabledIcon(new ImageIcon(this.getClass().getResource("/Imagenes/"+ultimoNumero+".png")));
			borrar.setEnabled(true);
			deshacer.setEnabled(false);
		}	
	}
	
	/**
	 * Clase OyenteBorrar.
	 * Implementa la interfaz ActionListener y su método.
	 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
	 *
	 */
	private class OyenteBorrar implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(seleccion == borrarCelda) {
				seleccion = 10;
				borrar.setBorder(BorderFactory.createRaisedBevelBorder());	
			}
			else {
				if(seleccion != noRealizarAccion)
					imagenesSeleccion[seleccion-1].setBorder(BorderFactory.createRaisedBevelBorder());
				seleccion = borrarCelda;
				borrar.setBorder(BorderFactory.createLoweredBevelBorder());
			}	
		}
		
	}
	
	/**
	 * Clase OyenteReiniciar.
	 * Implementa la interfaz ActionListener y su método.
	 * @author Victoria Berti, alumna de la Universidad Nacional del Sur.
	 *
	 */
	private class OyenteReiniciar implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			t.cancel();
			int dimension = juego.getTablero().dimension();
			if(seleccion != noRealizarAccion && seleccion != borrarCelda)
				imagenesSeleccion[seleccion-1].setBorder(BorderFactory.createRaisedBevelBorder());
			else if (seleccion == borrarCelda)
				borrar.setBorder(BorderFactory.createRaisedBevelBorder());
			habilitar_panel_inferior(false);
			if(seleccionIncorrecta) {
				marcar_filas(ultimaFila, false);
				marcar_columnas(ultimaColumna, false);
			}	
			juego.reiniciar();
			inicializar_atributos();
			for(int fila = 0; fila < dimension; fila++) {
				for(int columna = 0; columna < dimension; columna++) {
					modificar_interfaz_botones(fila, columna);
				}
			}
			iniciar_cronometro();
		}		
	}
}