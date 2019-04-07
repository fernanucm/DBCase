package vista.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class GUI_QuitarEntidadPadre extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	

	private Controlador controlador;
	private TransferRelacion relacion;
	// Variables declaration - do not modify
	private JTextPane pregunta;
	private JButton botonNo;
	private JButton botonSi;
	private JTextPane explicacion;
	private JLabel labelIcono;
	// End of variables declaration

	public GUI_QuitarEntidadPadre() {
		initComponents();
	}

	private void initComponents() {

		getContentPane().setLayout(null);
		setTitle(Lenguaje.text(Lenguaje.QUIT_PARENT_ENTITY));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setBounds(12, 24, 64, 69);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.WARNING)));
		}
		{
			explicacion = new JTextPane();
			getContentPane().add(explicacion);
			explicacion.setText(Lenguaje.text(Lenguaje.EXPLICATION_QUIT_PARENT));
			explicacion.setBounds(88, 12, 228, 50);
			explicacion.setOpaque(false);
			explicacion.setEditable(false);
			explicacion.setFocusable(false);
		}
		{
			pregunta = new JTextPane();
			getContentPane().add(pregunta);
			pregunta.setText(Lenguaje.text(Lenguaje.DO_YOU_WISH_QUIT_PARENT));
			pregunta.setBounds(88, 68, 183, 20);
			pregunta.setEditable(false);
			pregunta.setOpaque(false);
			pregunta.setFocusable(false);
		}
		{
			botonSi = new JButton();
			getContentPane().add(botonSi);
			botonSi.setText(Lenguaje.text(Lenguaje.YES));
			botonSi.setBounds(106, 105, 80, 25);
			botonSi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonSiActionPerformed(evt);
				}
			});
			botonSi.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonSiActionPerformed(null);}
					else if(e.getKeyCode()==27){botonNoActionPerformed(null);}
					else if(e.getKeyCode()==39){botonNo.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonSi.setMnemonic(Lenguaje.text(Lenguaje.YES).charAt(0));
		}
		{
			botonNo = new JButton();
			getContentPane().add(botonNo);
			botonNo.setText(Lenguaje.text(Lenguaje.NO));
			botonNo.setBounds(202, 105, 80, 25);
			botonNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonNoActionPerformed(evt);
				}
			});
			botonNo.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonNoActionPerformed(null);}
					else if(e.getKeyCode()==27){botonNoActionPerformed(null);}
					else if(e.getKeyCode()==37){botonSi.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonNo.setMnemonic(Lenguaje.text(Lenguaje.NO).charAt(0));
		}

		this.setSize(349, 182);
		this.addMouseListener(this);
		this.addKeyListener(this);

	}

	/*
	 * Oyentes de los botones
	 */
	
	private void botonNoActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
	}

	private void botonSiActionPerformed(java.awt.event.ActionEvent evt) {
		// Mandamos el mensaje al controlador con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIQuitarEntidadPadre_ClickBotonSi, this.getRelacion());
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonSiActionPerformed(null);
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
	
	/*
	 * Activar y desactivar el dialogo
	 */
	
	public void setActiva(){
		// Si no esta establecida la entidad padre lanzamos el error 
		if(this.getRelacion().getListaEntidadesYAridades().isEmpty())
			JOptionPane.showMessageDialog(
				null,
				(Lenguaje.text(Lenguaje.ERROR))+"\n" +
				(Lenguaje.text(Lenguaje.IMPOSIBLE_QUIT_PARENT))+"\n" +
				(Lenguaje.text(Lenguaje.NO_FATHER))+"\n",
				Lenguaje.text(Lenguaje.QUIT_PARENT_ENTITY),
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.centraEnPantalla();
			SwingUtilities.invokeLater(doFocus);
			this.setVisible(true);
			
		}	
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	        botonSi.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
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
	
	/*
	 * Getters y setters
	 */
	
	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public TransferRelacion getRelacion() {
		return relacion;
	}

	public void setRelacion(TransferRelacion relacion) {
		this.relacion = relacion;
	}

}

