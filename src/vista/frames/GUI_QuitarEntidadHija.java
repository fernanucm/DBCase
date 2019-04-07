package vista.frames;

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

import controlador.Controlador;
import controlador.TC;
import modelo.lenguaje.Lenguaje;
import modelo.persistencia.EntidadYAridad;
import modelo.tools.ImagePath;
import modelo.transfers.Transfer;
import modelo.transfers.TransferEntidad;
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
@SuppressWarnings("rawtypes")
public class GUI_QuitarEntidadHija extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private TransferRelacion relacion;
	// Variables declaration - do not modify
	private JComboBox comboEntidades;
	private JLabel labelIcono;
	private JButton botonQuitar;
	private JButton botonCancelar;
	private JTextPane explicacion;
	// End of variables declaration


	public GUI_QuitarEntidadHija() {
		initComponents();
	}

	private void initComponents() {

		getContentPane().setLayout(null);
		setTitle(Lenguaje.text(Lenguaje.QUIT_DAUGHTER_ENTITY));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setModal(true);
		this.setResizable(false);
		getContentPane().add(getExplicacion());
		getContentPane().add(getBotonCancelar());
		getContentPane().add(getBotonQuitar());
		getContentPane().add(getComboEntidades());
		getContentPane().add(getLabelIcono());
		this.setSize(386, 173);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Oyentes de los botones
	 */

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                           
		this.setVisible(false);
	}                                          

	private void botonQuitarActionPerformed(java.awt.event.ActionEvent evt) {
		// Obtenemos la entidad seleccionada
		TransferEntidad te = this.listaEntidades.get(this.comboEntidades.getSelectedIndex());
		// Generamos los datos que enviaremos al controlador
		Vector<Transfer> datos = new Vector<Transfer>();
		datos.add(this.getRelacion());
		datos.add(te);
		// Mandamos el mensaje al controlador con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIQuitarEntidadHija_ClickBotonQuitar, datos);
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonQuitarActionPerformed(null);
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
			if(e.getKeyCode()==10){botonQuitarActionPerformed(null);}
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Activar y desactivar el dialogo
	 */

	@SuppressWarnings("unchecked")
	public void setActiva(){
		// Si no tiene entidades hijas lanzamos un error
		if(this.getRelacion().getListaEntidadesYAridades().size()<=1)
			JOptionPane.showMessageDialog(
					null,
					(Lenguaje.text(Lenguaje.ERROR))+"\n" +
					(Lenguaje.text(Lenguaje.IMPOSIBLE_QUIT_DAUGHTER_ENTITY))+"\n" +
					(Lenguaje.text(Lenguaje.NO_DAUGHTER_ENTITY))+"\n",
					Lenguaje.text(Lenguaje.QUIT_DAUGHTER_ENTITY),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.controlador.mensajeDesde_GUI(TC.GUIQuitarEntidadHija_ActualizameListaEntidades, null);
			// Generamos los items (ya filtrados)
			String[] items = this.generaItems();
			comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(items));
			comboEntidades.setSelectedIndex(0);
			SwingUtilities.invokeLater(doFocus);
			this.centraEnPantalla();
			this.setVisible(true);	
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
	 * Metodos privados del dialogo
	 */
	@SuppressWarnings("unchecked")
	private String[] generaItems(){
		// Filtramos la lista de entidades quitando la entidad padre y las entidades que no intervienen
		Vector<EntidadYAridad> vectorTupla = this.getRelacion().getListaEntidadesYAridades();
		Vector vectorIdsEntidades = new Vector();
		int cont = 1; // Para saltar la entidad padre
		while(cont<vectorTupla.size()){
			vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
			cont++;
		}
		cont = 0;
		Vector<TransferEntidad> listaEntidadesFiltrada = new Vector<TransferEntidad>();
		while(cont<this.getListaEntidades().size()){
			TransferEntidad te = this.getListaEntidades().get(cont);
			if(vectorIdsEntidades.contains(te.getIdEntidad()))
				listaEntidadesFiltrada.add(te);
			cont++;
		}
		this.setListaEntidades(listaEntidadesFiltrada);
		// Generamos los items
		cont = 0;
		String[] items = new String[this.listaEntidades.size()];
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			items[cont] = te.getNombre();
			cont++;
		}
		return items;
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

	/*
	 * Intefaz
	 */

	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.text(Lenguaje.SELECT_DAUGHTER_TO_QUIT));
			explicacion.setBounds(125, 12, 235, 20);
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}

	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.text(Lenguaje.CANCEL));
			botonCancelar.setBounds(280, 91, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonQuitar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(Lenguaje.text(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}

	private JButton getBotonQuitar() {
		if(botonQuitar == null) {
			botonQuitar = new JButton();
			botonQuitar.setText(Lenguaje.text(Lenguaje.REMOVE));
			botonQuitar.setBounds(189, 91, 80, 25);
			botonQuitar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonQuitarActionPerformed(evt);
				}
			});
			botonQuitar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonQuitarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonQuitar.setMnemonic(Lenguaje.text(Lenguaje.REMOVE).charAt(0));
		}
		return botonQuitar;
	}

	@SuppressWarnings("unchecked")
	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			ComboBoxModel comboEntidadesModel = 
				new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
			comboEntidades = new JComboBox();
			comboEntidades.setModel(comboEntidadesModel);
			comboEntidades.setBounds(130, 38, 230, 21);
		}
		comboEntidades.addKeyListener(general);
		return comboEntidades;
	}

	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(12, 25, 100, 87);
		}
		return labelIcono;
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
