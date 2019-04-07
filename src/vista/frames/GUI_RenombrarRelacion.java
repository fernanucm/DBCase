package vista.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import controlador.TC;
import modelo.lenguaje.Lenguaje;
import modelo.tools.ImagePath;
import modelo.transfers.TransferRelacion;

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
public class GUI_RenombrarRelacion extends javax.swing.JDialog implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private TransferRelacion relacion;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JButton botonRenombrar;
	private JTextField cajaNombre;
	private JTextPane explicacion;
	private JLabel labelIcono;
	// End of variables declaration
	


	public GUI_RenombrarRelacion() {
		initComponents();
	}

	private void initComponents() {

		setTitle(Lenguaje.text(Lenguaje.RENAME_RELATION_DBDT));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		this.setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		this.setSize(382, 167);
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.text(Lenguaje.CANCEL));
			botonCancelar.setBounds(278, 96, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonRenombrar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.text(Lenguaje.CANCEL).charAt(0));
			
		}
		{
			botonRenombrar = new JButton();
			getContentPane().add(botonRenombrar);
			botonRenombrar.setText(Lenguaje.text(Lenguaje.RENAME));
			botonRenombrar.setBounds(193, 96, 80, 25);
			botonRenombrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonRenombrarActionPerformed(evt);
				}
			});
			botonRenombrar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonRenombrarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
					
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonRenombrar.setMnemonic(Lenguaje.text(Lenguaje.RENAME).charAt(0));
		}
		{
			cajaNombre = new JTextField();
			getContentPane().add(cajaNombre);
			cajaNombre.setText("");
			cajaNombre.setBounds(126, 42, 236, 20);
			cajaNombre.addKeyListener(general);
		}
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 12, 100, 100);
		}
		{
			explicacion = new JTextPane();
			getContentPane().add(explicacion);
			explicacion.setText(
					Lenguaje.text(Lenguaje.EXPLICATION_RENAME_RELATION1));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(124, 12, 236, 21);
			explicacion.setFocusable(false);
		}
		this.addMouseListener(this);
		this.addKeyListener(this);
	}
	
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.cajaNombre.setText(this.getRelacion().getNombre());
		SwingUtilities.invokeLater(doFocus);
		this.centraEnPantalla();
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
	 * Oyentes de los botones
	 */
	private void botonRenombrarActionPerformed(java.awt.event.ActionEvent evt) {                                               
		// Datos que mandamos al controlador
		Vector<Object> v = new Vector<Object>();
		v.add(this.getRelacion());
		v.add(this.cajaNombre.getText());
		// Mandamos mensaje + datos al controlador
		this.getControlador().mensajeDesde_GUI(TC.GUIRenombrarRelacion_Click_BotonRenombrar, v);
	}                                              

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setVisible(false);
	}
	
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonRenombrarActionPerformed(null);
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
			if(e.getKeyCode()==10){botonRenombrarActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Getters y Setters
	 */

	public TransferRelacion getRelacion() {
		return relacion;
	}

	public void setRelacion(TransferRelacion relacion) {
		this.relacion = relacion;
	}
	
	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
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
