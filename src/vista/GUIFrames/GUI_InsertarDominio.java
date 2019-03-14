package vista.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import controlador.TC;
import modelo.tools.ImagePath;
import modelo.tools.TipoDominio;
import modelo.transfers.TransferDominio;
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
@SuppressWarnings({"rawtypes" ,"unchecked"})
public class GUI_InsertarDominio extends javax.swing.JDialog  implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JTextField cajaNombre;
	private JTextField cajaValores;
	private JComboBox comboTipo;
	private JTextPane explicacion;
	private JTextPane textType;
	private JTextPane textValues;
	private JLabel labelIcono;
	private JButton botonInsertar;
	// End of variables declaration

	public GUI_InsertarDominio() {
		initComponents();
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(Lenguaje.getMensaje(Lenguaje.INSERT_NEW_DOMAIN));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		this.setSize(380, 230);
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(280, 162, 80, 25);
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
			botonInsertar.setBounds(195, 162, 80, 25);
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
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 70, 100, 100);
		}
		{
			explicacion = new JTextPane();
			getContentPane().add(explicacion);
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.NAME));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(124, 10, 348, 20);
			explicacion.setFocusable(false);
		}
		{
			textType = new JTextPane();
			getContentPane().add(textType);
			textType.setText(Lenguaje.getMensaje(Lenguaje.TYPE));
			textType.setEditable(false);
			textType.setOpaque(false);
			textType.setBounds(124, 55, 348, 20);
			textType.setFocusable(false);
		}
		{
			textValues = new JTextPane();
			getContentPane().add(textValues);
			textValues.setText(Lenguaje.getMensaje(Lenguaje.VALUES));
			textValues.setEditable(false);
			textValues.setOpaque(false);
			textValues.setBounds(124, 100, 348, 20);
			textValues.setFocusable(false);
		}
		{
			cajaNombre = new JTextField();
			getContentPane().add(cajaNombre);
			cajaNombre.setBounds(124, 30, 236, 20);
			cajaNombre.addKeyListener(general);
		}
		{
			comboTipo = new JComboBox();
			getContentPane().add(comboTipo);
			comboTipo.setBounds(124, 75, 236, 20);
			//Creamos lista de tipos básicos
			Object[] items = modelo.tools.TipoDominio.values();
			Object[] items2 = new Object[items.length-1];
			//quitamos BLOB
			int i=0;
			while (i<items.length && !items[i].toString().equals("BLOB")){ 
				items2[i]=items[i];
				i++;
			}
			for (int j=i+1; j<items.length;j++){
				items2[j-1]=items[j];
			}
			comboTipo.setModel(new javax.swing.DefaultComboBoxModel(items2));
			comboTipo.addKeyListener(general);
		}
		{
			cajaValores = new JTextField();
			getContentPane().add(cajaValores);
			cajaValores.setBounds(124, 120, 236, 20);
			cajaValores.addKeyListener(general);
		}
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Oyentes de los botones 
	 */
	private void botonInsertarActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// Generamos el transfer que mandaremos al controlador
		TransferDominio td = new TransferDominio();
		td.setNombre(this.cajaNombre.getText());
		td.setTipoBase((TipoDominio)this.comboTipo.getSelectedItem());
		td.setListaValores(listaValores());
		// Mandamos mensaje + datos al controlador
		this.getControlador().mensajeDesde_GUI(TC.GUIInsertarDominio_Click_BotonInsertar, td);
		
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
		this.cajaValores.setText("");
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
	
//TODO Nota: deberiamos controlar repeticiones y más casos raros
	private Vector listaValores(){
		Vector v = new Vector();
		String s = this.cajaValores.getText();
		int pos0 = 0;
		int comilla1 = s.indexOf("'");
		int comilla2 = s.indexOf("'", comilla1+1);
		int pos1;
		if (comilla2!= -1){//tener en cuenta las comas que no esten entre comillas
			pos1 = s.indexOf(",", comilla2);
		}
		else
			pos1 = s.indexOf(",");
		while(pos0 != -1 ){
			String subS;
			if (pos1 !=-1){ 
				subS = s.substring(pos0, pos1);
			}
			else{
				subS = s.substring(pos0, s.length());
			}
			pos0 = pos1;
			pos1 = s.indexOf (",",pos1+1);
			if (subS.contains("'")){
				//eliminamos todos lo que este fuera de las comillas
				int primeraComilla = subS.indexOf("'");
				int segundaComilla= subS.indexOf("'", primeraComilla+1);
				if (segundaComilla!= -1){
				subS = subS.substring(primeraComilla, segundaComilla+1);
				}
			}else{
				subS = subS.replaceAll(" ","");
				subS = subS.replaceAll(",","");
			}
			
			v.add(subS);
		}
		return v;
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

}
