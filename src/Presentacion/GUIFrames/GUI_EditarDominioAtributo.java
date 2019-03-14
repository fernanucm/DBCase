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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.TransferAtributo;
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
@SuppressWarnings({"rawtypes" ,"unchecked"})
public class GUI_EditarDominioAtributo extends javax.swing.JDialog implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	// End of variables declaration
	private Controlador controlador;
	private TransferAtributo atributo;
	private JComboBox comboDominios;
	private JTextPane explicacion;
	private JLabel labelTamano;
	private JTextField cajaTamano;
	private JLabel labelIcono;
	private JButton botonEditar;
	private JButton botonCancelar;
	private Vector<TransferDominio> listaDominios;

	public GUI_EditarDominioAtributo() {
		initComponents();
	}

	private void initComponents() {

		setTitle(Lenguaje.getMensaje(Lenguaje.EDIT_DOMAIN_ATTRIBUTE));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		getContentPane().add(getBotonCancelar());
		getContentPane().add(getBotonEditar());
		getContentPane().add(getComboDominios());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getExplicacion());
		getContentPane().add(getCajaTamano());
		getContentPane().add(getLabelTamano());
		this.setSize(380, 220);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		controlador.mensajeDesde_GUI(TC.GUIEditarDominioAtributo_ActualizameLaListaDeDominios, null);
		TipoDominio[] basicos = Utilidades.TipoDominio.values();
		Object[] nuevos = new Object[this.listaDominios.size()];
		this.generaItems(nuevos);
		Object[] items = new Object[this.listaDominios.size()+basicos.length];
		//creamos la lista ordenada por tipo base
		int i=0;//el basico por el que voy
		int j=0;//la posicion de items por la que voy
		while (i<basicos.length){
			items[j]=basicos[i];
			j++;
			for(int h=0; h<listaDominios.size();h++){
				if (listaDominios.get(h).getTipoBase().toString().equals(basicos[i].toString())){
					items[j]=nuevos[h];
					j++;
				}
			}
			i++;
			
		}
		this.comboDominios.setModel(new javax.swing.DefaultComboBoxModel(items));
		this.comboDominios.setSelectedIndex(0);
		
		this.centraEnPantalla();
		
		this.comboDominios.setModel(new javax.swing.DefaultComboBoxModel(items));
		
		// Si el atributo es compuesto, su dominio es null y no se puede editar
		if (this.getAtributo().getCompuesto()){
			JOptionPane.showMessageDialog(
					null,
					(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
					(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_EDIT_DOMAIN))+"\""+this.getAtributo().getNombre()+"\"\n" +
					(Lenguaje.getMensaje(Lenguaje.COMPLEX_ATTRIBUTE))+"\n",
					(Lenguaje.getMensaje(Lenguaje.EDIT_DOMAIN_ATTRIBUTE)),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
			return;
		}
		// Si se puede, ponemos el dominio que tiene en el combo y tamano en caso de tenerlo
		String dominio = this.getAtributo().getDominio();
		
		// Si el dominio es null es que antes era un atributo compuesta y ahora ya no lo es.
		// Por defecto seleccionamos el tipo INTEGER
		if (dominio.equals("null")){
			this.comboDominios.setSelectedItem(TipoDominio.INTEGER);
			this.cajaTamano.setText("");
			this.cajaTamano.setEnabled(false);
			this.cajaTamano.setEditable(false);
		}
		else{
			try{// Si el dominio tiene un parentesis al final es que tiene tamano
				if(dominio.charAt(dominio.length()-1)==')'){
					String tipo = dominio.substring(0,dominio.indexOf("("));
					String tam = dominio.substring(dominio.indexOf("(")+1,dominio.indexOf(")"));
					this.comboDominios.setSelectedItem(TipoDominio.valueOf(tipo));
					this.cajaTamano.setText(tam);
					this.cajaTamano.setEnabled(true);
					this.cajaTamano.setEditable(true);
				}
				// Si es un dominio simple
				else{
					this.comboDominios.setSelectedItem(TipoDominio.valueOf(dominio));
					this.cajaTamano.setText("");
					this.cajaTamano.setEnabled(false);
					this.cajaTamano.setEditable(false);
				}
			}catch(Exception e){
				this.comboDominios.setSelectedItem(dominio);
			}
		}
		
		this.centraEnPantalla();
		boolean b= this.activarTamano();
		this.cajaTamano.setEditable(b);
		this.cajaTamano.setEnabled(b);
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);

	}

	private Runnable doFocus = new Runnable() {
	     public void run() {
	        comboDominios.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Oyentes de los botones
	 */

	private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {
		String dominioCadena;
		String nuevoTamano = "";
		try{
			// Obtenemos el nuevo dominio
			TipoDominio nuevoDominio = TipoDominio.valueOf(this.comboDominios.getSelectedItem().toString());
			dominioCadena = nuevoDominio.toString();
			
			// Si es dominio con tamano
			
			if (((TipoDominio)nuevoDominio).equals(TipoDominio.CHAR) ||
				((TipoDominio)nuevoDominio).equals(TipoDominio.VARCHAR) ||
				((TipoDominio)nuevoDominio).equals(TipoDominio.TEXT) ||
				((TipoDominio)nuevoDominio).equals(TipoDominio.INTEGER) ||
				((TipoDominio)nuevoDominio).equals(TipoDominio.DECIMAL) ||
				((TipoDominio)nuevoDominio).equals(TipoDominio.FLOAT)){
					// Obtenemos el tamano de la caja
					nuevoTamano = this.cajaTamano.getText();
					// Si no se ha especificado tamano ponemos un 1 por defecto
					if (nuevoTamano.isEmpty()){
						nuevoTamano = "10";
					}
					// Formamos la cadena
					dominioCadena += "("+nuevoTamano+")";
			}
		}catch(Exception e){//Dominio definido por el usuario
			dominioCadena = this.comboDominios.getSelectedItem().toString();
			//ta.setDominio(dominio.toString());
		}
		
		// Mandamos la entidad, el nuevo atributo y si hay tamano tambien
		Vector<Object> v = new Vector<Object>();
		v.add(this.getAtributo());
		v.add(dominioCadena);
		if(!nuevoTamano.isEmpty())
			v.add(nuevoTamano);
		
		controlador.mensajeDesde_GUI(TC.GUIEditarDominioAtributo_Click_BotonEditar, v);	
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
	 * Oyente del cambio en el comboDominios
	 */

	private void comboDominiosItemStateChanged(java.awt.event.ItemEvent evt) {                                               
		if (this.activarTamano()){
			this.cajaTamano.setText("");
			this.cajaTamano.setEnabled(true);
			this.cajaTamano.setEditable(true);
		}
		else{
			this.cajaTamano.setText("");
			this.cajaTamano.setEnabled(false);
			this.cajaTamano.setEditable(false);
		}
	}

	/*
	 * Getters y Setters
	 */

	public TransferAtributo getAtributo() {
		return atributo;
	}

	public void setAtributo(TransferAtributo atributo) {
		this.atributo = atributo;
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

	private boolean activarTamano(){
		boolean activo = false;
		try{
			TipoDominio dominio = TipoDominio.valueOf(this.comboDominios.getSelectedItem().toString());
			if (((TipoDominio)dominio).equals(TipoDominio.CHAR) ||
					((TipoDominio)dominio).equals(TipoDominio.VARCHAR) ||
					((TipoDominio)dominio).equals(TipoDominio.TEXT) ||
					((TipoDominio)dominio).equals(TipoDominio.INTEGER) ||
					((TipoDominio)dominio).equals(TipoDominio.DECIMAL) ||
					((TipoDominio)dominio).equals(TipoDominio.FLOAT))
					activo = true;
			return activo;
		}catch(Exception e){
			return false;
			
		}
	}

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
			botonEditar.setText(Lenguaje.getMensaje(Lenguaje.ACCEPT));
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
			botonEditar.setMnemonic(Lenguaje.getMensaje(Lenguaje.ACCEPT).charAt(0));
		}
		return botonEditar;
	}

	private JComboBox getComboDominios() {
		if(comboDominios == null) {
			comboDominios = new JComboBox();
			//.setModel(new javax.swing.DefaultComboBoxModel(TipoDominio.values()));
			comboDominios.setBounds(132, 59, 228, 21);
			comboDominios.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					comboDominiosItemStateChanged(evt);
				}
			});
			comboDominios.addKeyListener(general);
		}
		return comboDominios;
	}

	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(18, 49, 102, 107);
		}
		return labelIcono;
	}

	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.SELECT_DOMAIN_FOR_ATTRIBUTE));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(12, 10, 353, 37);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}

	private JTextField getCajaTamano() {
		if(cajaTamano == null) {
			cajaTamano = new JTextField();
			cajaTamano.setBounds(198, 88, 162, 21);
		}
		cajaTamano.addKeyListener(general);
		return cajaTamano;
	}

	private JLabel getLabelTamano() {
		if(labelTamano == null) {
			labelTamano = new JLabel();
			labelTamano.setText(Lenguaje.getMensaje(Lenguaje.SIZE_ATTRIBUTE));
			labelTamano.setBounds(132, 91, 66, 14);
		}
		return labelTamano;
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
private Object[] generaItems(Object[] items){
		
		// Generamos los items
		int cont = 0;
		//String[] items = new String[this.listaDominios.size()];
		while (cont<this.listaDominios.size()){
			TransferDominio td = this.listaDominios.get(cont);
			items[cont] = td.getNombre();
			cont++;
		}
		return items;
	}
public Vector<TransferDominio> getListaDominios() {
	return listaDominios;
}

public void setListaDominios(Vector<TransferDominio> listaDominios) {
	this.listaDominios = listaDominios;
}

}
