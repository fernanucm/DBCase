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
@SuppressWarnings({"rawtypes" ,"unchecked"})
public class GUI_AnadirEntidadHija extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	// End of variables declaration
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private JComboBox comboEntidades;
	private JButton botonAnadir;
	private JButton botonSalir;
	private JLabel labelIcono;
	private JTextPane explicacion;
	private TransferRelacion relacion;



	public GUI_AnadirEntidadHija() {
		initComponents();
	}

	private void initComponents() {
		getContentPane().setLayout(null);
		setTitle(Lenguaje.text(Lenguaje.INSERT_NEW_DAUGTHER));
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		getContentPane().add(getExplicacion());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getComboEntidades());
		getContentPane().add(getBotonSalir());
		getContentPane().add(getBotonAnadir());
		this.setSize(382, 183);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	/*
	 * Oyentes de los botones
	 */

	private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {                                           
		this.setVisible(false);
	}                                          

	private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {
		// Obtenemos la entidad seleccionada
		TransferEntidad te = this.listaEntidades.get(this.comboEntidades.getSelectedIndex());
		// Generamos los datos que enviaremos al controlador
		Vector<Transfer> datos = new Vector<Transfer>();
		datos.add(this.getRelacion());
		datos.add(te);
		// Mandamos el mensaje al controlador con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadHija_ClickBotonAnadir, datos);
	}
	public void keyPressed( KeyEvent e ) {
		switch (e.getKeyCode()){
			case 27: {
				this.setInactiva();
				break;
			}
			case 10:{
				this.botonAnadirActionPerformed(null);
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
			if(e.getKeyCode()==10){botonAnadirActionPerformed(null);}
			if(e.getKeyCode()==27){botonSalirActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		// Si no tiene establecida la entidad padre, lanzamos un error
		if(this.getRelacion().getListaEntidadesYAridades().isEmpty())
			JOptionPane.showMessageDialog(
					null,
					(Lenguaje.text(Lenguaje.ERROR))+"\n" +
					(Lenguaje.text(Lenguaje.IMPOSIBLE_TO_INSERT_DAUGHTER))+"\n" +
					(Lenguaje.text(Lenguaje.NO_FATHER))+"\n",
					Lenguaje.text(Lenguaje.INSERT_NEW_DAUGTHER),
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadHija_ActualizameListaEntidades, null);
			// Generamos los items (ya filtrados)
			String[] items = this.generaItems();
			if(items.length == 0)
				JOptionPane.showMessageDialog(
						null,
						(Lenguaje.text(Lenguaje.ERROR))+"\n" +
						(Lenguaje.text(Lenguaje.IMPOSIBLE_TO_INSERT_DAUGHTER))+"\n" +
						(Lenguaje.text(Lenguaje.NO_ENTITIES_AVAILABLES))+"\n",
						Lenguaje.text(Lenguaje.INSERT_NEW_DAUGTHER),
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));

			else{
				comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(items));
				comboEntidades.setSelectedIndex(0);
				SwingUtilities.invokeLater(doFocus);
				this.centraEnPantalla();
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

	private String[] generaItems(){
		// Filtramos la lista de entidades quitando la entidad padre y las entidades hermanas
		Vector<EntidadYAridad> vectorTupla = this.getRelacion().getListaEntidadesYAridades();
		Vector vectorIdsEntidades = new Vector();
		int cont = 0;
		while(cont<vectorTupla.size()){
			vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
			cont++;
		}
		cont = 0;
		Vector<TransferEntidad> listaEntidadesFiltrada = new Vector<TransferEntidad>();
		while(cont<this.getListaEntidades().size()){
			TransferEntidad te = this.getListaEntidades().get(cont);
			if(!vectorIdsEntidades.contains(te.getIdEntidad()))
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

	/*
	 * Interfaz
	 */
	
	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.text(Lenguaje.SELECT_ENTITY_DAUTHTER));
			explicacion.setBounds(136, 12, 224, 22);
			explicacion.setOpaque(false);
			explicacion.setEditable(false);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}

	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setBounds(18, 19, 100, 100);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
		}
		return labelIcono;
	}

	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			ComboBoxModel comboEntidadesModel = 
				new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
			comboEntidades = new JComboBox();
			comboEntidades.setModel(comboEntidadesModel);
			comboEntidades.setBounds(138, 46, 224, 21);
		}
		comboEntidades.addKeyListener(general);
		return comboEntidades;
	}

	private JButton getBotonSalir() {
		if(botonSalir == null) {
			botonSalir = new JButton();
			botonSalir.setText(Lenguaje.text(Lenguaje.EXIT));
			botonSalir.setBounds(275, 99, 80, 25);
			botonSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonSalirActionPerformed(evt);
				}
			});
			botonSalir.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonSalirActionPerformed(null);}
					else if(e.getKeyCode()==27){botonSalirActionPerformed(null);}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonSalir.setMnemonic(Lenguaje.text(Lenguaje.EXIT).charAt(0));
		}
		return botonSalir;
	}

	private JButton getBotonAnadir() {
		if(botonAnadir == null) {
			botonAnadir = new JButton();
			botonAnadir.setText(Lenguaje.text(Lenguaje.INSERT));
			botonAnadir.setBounds(190, 99, 80, 25);
			botonAnadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonAnadirActionPerformed(evt);
				}
			});
			botonAnadir.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonAnadirActionPerformed(null);}
					else if(e.getKeyCode()==27){botonSalirActionPerformed(null);}
					else if(e.getKeyCode()==37){botonAnadir.grabFocus();}
					else if(e.getKeyCode()==39){botonSalir.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonAnadir.setMnemonic(Lenguaje.text(Lenguaje.INSERT).charAt(0));
		}
		return botonAnadir;
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
