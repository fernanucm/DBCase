package Presentacion.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import Controlador.Controlador;
import Controlador.TC;
import LogicaNegocio.Transfers.Transfer;
import LogicaNegocio.Transfers.TransferEntidad;
import LogicaNegocio.Transfers.TransferRelacion;
import Presentacion.Lenguajes.Lenguaje;
import Utilidades.ImagePath;

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
public class GUI_EstablecerEntidadPadre extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	
	// End of variables declaration
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private JComboBox comboEntidades;
	private JTextPane textoExplicacion;
	private JLabel labelIcono;
	private JButton botonCancelar;
	private JButton botonAceptar;
	private TransferRelacion relacion;


	public GUI_EstablecerEntidadPadre() {
		initComponents();
	}

	private void initComponents() {
		this.setTitle((Lenguaje.getMensaje(Lenguaje.SET_PARENT_ENTITY)));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		getContentPane().setLayout(null);
		setModal(true);
		this.setResizable(false);
		getContentPane().add(getBotonAceptar());
		getContentPane().add(getBotonCancelar());
		getContentPane().add(getComboEntidades());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getTextoExplicacion());
		this.setSize(385, 179);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Activar y desactivar el dialogo
	 */
	
	public void setActiva(){
		// Si ya tiene establecida la entidad padre, lanzamos un error
		if(!this.getRelacion().getListaEntidadesYAridades().isEmpty())
			JOptionPane.showMessageDialog(
					null,
					(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
					(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_SET_PARENT))+"\n" +
					(Lenguaje.getMensaje(Lenguaje.OTHER_PARENT))+"\n",
					Lenguaje.getMensaje(Lenguaje.SET_PARENT_ENTITY),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.controlador.mensajeDesde_GUI(TC.GUIEstablecerEntidadPadre_ActualizameListaEntidades, null);
			// Generamos los items
			String[] items = this.generaItems();
			if(items.length == 0)
				JOptionPane.showMessageDialog(
						null,
						(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
						(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_SET_PARENT_IN_ISA))+"\n" +
						(Lenguaje.getMensaje(Lenguaje.NO_ENTITIES_AVAILABLES))+"\n",
						Lenguaje.getMensaje(Lenguaje.SET_PARENT_ENTITY),
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
			else{
				comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(items));
				comboEntidades.setSelectedIndex(0);
				this.centraEnPantalla();
				SwingUtilities.invokeLater(doFocus);
				this.setVisible(true);
			}
		}
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	      comboEntidades.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Oyentes de los botones
	 */
	
	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);

	}

	private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {
		// Obtenemos la entidad seleccionada
		TransferEntidad te = this.listaEntidades.get(this.comboEntidades.getSelectedIndex());
		// Generamos los datos que enviaremos al controlador
		Vector<Transfer> datos = new Vector<Transfer>();
		datos.add(this.getRelacion());
		datos.add(te);
		// Mandamos el mensaje al controlador con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIEstablecerEntidadPadre_ClickBotonAceptar, datos);
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonAceptarActionPerformed(null);
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
			if(e.getKeyCode()==10){botonAceptarActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Interfaz
	 */
	
	private JButton getBotonAceptar() {
		if(botonAceptar == null) {
			botonAceptar = new JButton();
			botonAceptar.setText(Lenguaje.getMensaje(Lenguaje.SELECT));
			botonAceptar.setBounds(186, 95, 80, 25);
			botonAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonAceptarActionPerformed(evt);
				}
			});
			botonAceptar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonAceptarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonAceptar.setMnemonic(Lenguaje.getMensaje(Lenguaje.SELECT).charAt(0));
		}
		return botonAceptar;
	}
	
	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(277, 95, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonAceptar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}
	
	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			ComboBoxModel comboEntidadesModel = 
				new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
			comboEntidades = new JComboBox();
			comboEntidades.setModel(comboEntidadesModel);
			comboEntidades.setBounds(132, 47, 228, 21);
			comboEntidades.addKeyListener(general);
		}
		return comboEntidades;
	}
	
	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setBounds(18, 22, 102, 98);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
		}
		return labelIcono;
	}
	
	private JTextPane getTextoExplicacion() {
		if(textoExplicacion == null) {
			textoExplicacion = new JTextPane();
			textoExplicacion.setText(Lenguaje.getMensaje(Lenguaje.SELECT_PARENT_ENTITY));
			textoExplicacion.setBounds(132, 10, 233, 25);
			textoExplicacion.setEditable(false);
			textoExplicacion.setOpaque(false);
			textoExplicacion.setFocusable(false);
		}
		return textoExplicacion;
	}
	
	/*
	 * Metodos de la clase
	 */
	
	private String[] generaItems(){
		int cont = 0;
		String[] items = new String[this.listaEntidades.size()];
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			items[cont] = te.getNombre();			
			cont++;
		}
		return items;
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
	
	public Vector<TransferEntidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(Vector<TransferEntidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public TransferRelacion getRelacion() {
		return relacion;
	}

	public void setRelacion(TransferRelacion relacion) {
		this.relacion = relacion;
	}
	
	

}

