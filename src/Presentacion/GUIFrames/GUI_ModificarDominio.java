package Presentacion.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.TransferDominio;
import Presentacion.Lenguajes.Lenguaje;
import Utilidades.ImagePath;
import Utilidades.TipoDominio;

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
public class GUI_ModificarDominio extends javax.swing.JDialog implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private TransferDominio dominio;
	// Variables declaration - do not modify
	private JTextField cajaValores;
	private JTextPane explicacion;
	private JTextPane textoTipo;
	private JComboBox comboDominios;
	private JLabel labelIcono;
	private JButton botonEditar;
	private JButton botonCancelar;
	// End of variables declaration
	
	
	
	public GUI_ModificarDominio() {
		initComponents();
	}

	private void initComponents() {

		setTitle(Lenguaje.getMensaje(Lenguaje.EDIT_DOMAIN));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		getContentPane().add(getBotonCancelar());
		getContentPane().add(getBotonEditar());
		getContentPane().add(getCajaValores());
		getContentPane().add(getComboDominios());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getExplicacion());
		getContentPane().add(getTextoTipo());
		this.setSize(380, 220);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.cajaValores.setText(this.getValores());
		this.actualizaComboDominios();		
		this.centraEnPantalla();
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);

	}

	private Runnable doFocus = new Runnable() {
	     public void run() {
	         cajaValores.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Oyentes de los botones
	 */

	@SuppressWarnings("unchecked")
	private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {
		// Obtenemos los nuevos valores
		Vector nuevosValores = this.listaValores();
		TipoDominio nuevoDominio = (TipoDominio) this.comboDominios.getSelectedItem();
		
		// Mandamos el dominio, con sus nuevos valores
		Vector<Object> v = new Vector<Object>();
		v.add(this.getDominio());
		v.add(nuevosValores);
		v.add(nuevoDominio);
		/*if(!nuevoTamano.isEmpty())
			v.add(nuevoTamano);*/
		
		controlador.mensajeDesde_GUI(TC.GUIModificarDominio_Click_BotonEditar, v);
	}

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setVisible(false);
	}

	private void comboDominiosItemStateChanged(java.awt.event.ItemEvent evt) {                                               
		
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonEditarActionPerformed(null);
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
			if(e.getKeyCode()==10){botonEditarActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Getters y Setters
	 */

	public TransferDominio getDominio() {
		return dominio;
	}

	public void setDominio(TransferDominio dominio) {
		this.dominio = dominio;
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}                

	/*
	 * Metodos privados
	 */

	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(280, 152, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonEditar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}

	private JButton getBotonEditar() {
		if(botonEditar == null) {
			botonEditar = new JButton();
			botonEditar.setText(Lenguaje.getMensaje(Lenguaje.EDIT));
			botonEditar.setBounds(189, 152, 80, 25);
			botonEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonEditarActionPerformed(evt);
				}
			});
			botonEditar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonEditarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonEditar.setMnemonic(Lenguaje.getMensaje(Lenguaje.EDIT).charAt(0));
		}
		return botonEditar;
	}

	private String getValores(){
		String valores = "";
		for (int i=0;i< dominio.getListaValores().size();i++){
			valores =valores.concat((String)dominio.getListaValores().get(i));
			if(i<dominio.getListaValores().size()-1) valores = valores.concat(", ");
		} 
		
		return valores;
	}

	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(18, 49, 102, 107);
		}
		return labelIcono;
	}
	
	private JTextField getCajaValores() {
		if(cajaValores == null) {
			cajaValores = new JTextField();
			getContentPane().add(cajaValores);
			cajaValores.setBounds(124, 85, 236, 20);
		}
		cajaValores.addKeyListener(general);
		return cajaValores;
	}

	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.VALUES));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(124, 65, 353, 37);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}
	
	private JTextPane getTextoTipo() {
		if(textoTipo == null) {
			textoTipo = new JTextPane();
			textoTipo.setText(Lenguaje.getMensaje(Lenguaje.TYPE));
			textoTipo.setEditable(false);
			textoTipo.setOpaque(false);
			textoTipo.setBounds(124, 15, 353, 37);
			textoTipo.setFocusable(false);
		}
		return textoTipo;
	}

	
	private JComboBox getComboDominios() {
		if(comboDominios == null) {
			comboDominios = new JComboBox();
			//.setModel(new javax.swing.DefaultComboBoxModel(TipoDominio.values()));
			comboDominios.setBounds(124, 35, 236, 20);
			comboDominios.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					comboDominiosItemStateChanged(evt);
				}
			});
			comboDominios.addKeyListener(general);
		}
		return comboDominios;
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

	private void actualizaComboDominios() {
		Object[] items = Utilidades.TipoDominio.values();
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
		this.comboDominios.setModel(new javax.swing.DefaultComboBoxModel(items2));
		// Si se puede, ponemos el dominio que tiene en el combo y tamano en caso de tenerlo
		String dominio = this.getDominio().getTipoBase().toString();
		// Si el dominio es null por defecto seleccionamos el tipo INTEGER
		if (dominio.equals("null")){
			this.comboDominios.setSelectedItem(TipoDominio.INTEGER);
		}
		else{
			this.comboDominios.setSelectedItem(TipoDominio.valueOf(dominio));
		}
	}
	
	@SuppressWarnings("unchecked")
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

}
