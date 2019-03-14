package vista.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import controlador.TC;
import modelo.tools.ImagePath;
import modelo.transfers.TransferEntidad;
import modelo.transfers.TransferRelacion;
import vista.Lenguajes.Lenguaje;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("rawtypes")
public class GUI_InsertarRelacion extends javax.swing.JDialog  implements KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private Point2D posicionRelacion;
	private Controlador controlador;
	// Variables declaration - do not modify
	private JTextPane explicacion;
	private JLabel labelIcono;
	private JTextField cajaNombre;
	private JButton botonInsertar;
	private JButton botonCancelar;
	// End of variables declaration
	

	public GUI_InsertarRelacion() {
		initComponents();
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(Lenguaje.getMensaje(Lenguaje.INSERT_RELATION));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setModal(true);
		setResizable(false);
		getContentPane().setLayout(null);
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(249, 106, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonInsertar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		{
			botonInsertar = new JButton();
			getContentPane().add(botonInsertar);
			botonInsertar.setText(Lenguaje.getMensaje(Lenguaje.INSERT));
			botonInsertar.setBounds(164, 106, 80, 25);
			botonInsertar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonInsertarActionPerformed(evt);
				}
			});
			botonInsertar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonInsertarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonInsertar.setMnemonic(Lenguaje.getMensaje(Lenguaje.INSERT).charAt(0));
		}
		{
			cajaNombre = new JTextField();
			getContentPane().add(cajaNombre);
			cajaNombre.setBounds(82, 18, 230, 20);
			cajaNombre.addKeyListener(general);
		}
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(20, 44, 96, 91);
		}
		{
			explicacion = new JTextPane();
			getContentPane().add(explicacion);
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.WRITE_RELATION_NAME));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(26, 17, 67, 21);
			explicacion.setFocusable(false);
		}
		this.setSize(357, 178);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}
	
	/*//@SuppressWarnings("unchecked")
	private String[] generaItems(){
		// Generamos los items
		int cont = 0;
		String[] items = new String[this.listaEntidades.size()];
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			items[cont] = te.getNombre();
			cont++;
		}
		return items;
	}*/
	

	/*
	 * Oyentes de los botones
	 */

	private void botonInsertarActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// Generamos el transfer que mandaremos al controlador
		TransferRelacion tr = new TransferRelacion();
		tr.setPosicion(this.getPosicionRelacion());
		tr.setNombre(this.cajaNombre.getText());
		tr.setListaAtributos(new Vector());
		tr.setListaEntidadesYAridades(new Vector());
		tr.setListaRestricciones(new Vector());
		tr.setListaUniques(new Vector());
		tr.setTipo("Normal");
		// Mandamos mensaje + datos al controlador
		this.getControlador().mensajeDesde_GUI(TC.GUIInsertarRelacion_Click_BotonInsertar, tr);
	}                                           

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setInactiva();
	}                   
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonInsertarActionPerformed(null);
				break;
			}
		}
	} 
	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseEntered( MouseEvent e ) {} 
	
	public void mouseClicked(MouseEvent arg0) {
		this.requestFocus();
	}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
	
	//Oyente para todos los elementos
	private KeyListener general = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==10){botonInsertarActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.centraEnPantalla();
		this.cajaNombre.setText("");
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         cajaNombre.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Getters y Setters
	 */

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public Point2D getPosicionRelacion() {
		return posicionRelacion;
	}

	public void setPosicionRelacion(Point2D posicionRelacion) {
		this.posicionRelacion = posicionRelacion;
	}
	
	public void setListaEntidades(Vector<TransferEntidad> listaEntidades) {
	}

	 /*
	 * Utilidades
	 */
	private void centraEnPantalla(){
		// Tamano de la pantalla
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		// Alto
		String altoString = String.valueOf(this.getSize().getWidth());
		altoString = altoString.substring(0,altoString.indexOf("."));
		int altoInt = Integer.parseInt(altoString);
		// Ancho
		String anchoString = String.valueOf(this.getSize().getHeight());
		anchoString = anchoString.substring(0,anchoString.indexOf("."));
		int anchoInt = Integer.parseInt(anchoString);

		setBounds((screenSize.width-altoInt)/2, (screenSize.height-anchoInt)/2, altoInt, anchoInt);
	}



}
