package vista.GUIFrames;

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
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import controlador.TC;
import modelo.persistencia.EntidadYAridad;
import modelo.tools.ImagePath;
import modelo.transfers.TransferEntidad;
import modelo.transfers.TransferRelacion;
import vista.lenguaje.Lenguaje;

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
public class GUI_QuitarEntidadARelacion extends javax.swing.JDialog  implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private TransferRelacion relacion;
	// Variables declaration - do not modify
	private JComboBox comboEntidades;
	private JComboBox comboRoles;
	private JTextPane explicacionRol;
	private JButton botonQuitar;
	private JButton botonCancelar;
	private JLabel labelIcono;
	private JTextPane explicacion;
	private Vector<String> items;
	private Vector<String> itemsRoles;
    // End of variables declaration



	public GUI_QuitarEntidadARelacion() {
		initComponents();
	}

	private void initComponents() {

		setTitle(Lenguaje.getMensaje(Lenguaje.QUIT_ENTITY));
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		getContentPane().add(getExplicacion());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getComboEntidades());
		getContentPane().add(getBotonCancelar());
		getContentPane().add(getBotonQuitar());
		getContentPane().add(getExplicacionRol());
		getContentPane().add(getComboRoles());
		this.setSize(399, 227);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}


	/*
	 * Activar y desactivar el dialogo
	 */

	@SuppressWarnings("unchecked")
	public void setActiva(){
		// Si no tiene entidades
		if(this.getRelacion().getListaEntidadesYAridades().isEmpty())
			JOptionPane.showMessageDialog(
				null,
				(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_QUIT_ENTITY))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.NO_ENTITIES_IN_RELATION))+"\n",
				Lenguaje.getMensaje(Lenguaje.QUIT_ENTITY),
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.controlador.mensajeDesde_GUI(TC.GUIQuitarEntidadARelacion_ActualizameListaEntidades, null);
			// Generamos los items (ya filtrados)
			this.items = this.generaItems();
			//Los ordenamos alfabeticamente
			quicksort((Vector<String>)this.items);
			comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(this.items));
			comboEntidades.setSelectedIndex(0);
			comboEntidades.grabFocus();
			this.itemsRoles = this.generaItemsRoles();
			//Los ordenamos alfabeticamente
			//quicksort((Vector<String>)this.itemsRoles);
			comboRoles.setModel(new javax.swing.DefaultComboBoxModel(this.itemsRoles));
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

	//Genera los items de las entidades que están asociadas a la relación	
	@SuppressWarnings("unchecked")
	private Vector<String> generaItems(){
		// Filtramos la lista de entidades quitando las entidades que no intervienen
		Vector<EntidadYAridad> vectorTupla = this.getRelacion().getListaEntidadesYAridades();
		Vector vectorIdsEntidades = new Vector();
		int cont = 0; // Para saltar la entidad padre
		while(cont<vectorTupla.size()){
			vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
			cont++;
		}
		cont = 0;
		Vector<TransferEntidad> listaEntidadesFiltrada = new Vector<TransferEntidad>();
		while(cont<this.getListaEntidades().size()){
			TransferEntidad te = this.getListaEntidades().get(cont);
			if(vectorIdsEntidades.contains(te.getIdEntidad())){
				listaEntidadesFiltrada.add(te);
			}
			cont++;
		}		
		this.setListaEntidades(listaEntidadesFiltrada);
		// Generamos los items
		cont = 0;
		Vector<String> items = new Vector<String>();;
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			items.add(cont,te.getNombre());
			cont++;
		}
		return items;
	}
	
	//Genera los roles asociados a la entidad que esté seleccionada
	private Vector<String> generaItemsRoles(){
		Vector<String> v = new Vector<String>();
		int itemSeleccionado = this.comboEntidades.getSelectedIndex();
		TransferEntidad te = this.listaEntidades.get(indiceAsociado(itemSeleccionado));
		int idEntidad = te.getIdEntidad();
		Vector veya = this.relacion.getListaEntidadesYAridades();
		int cont = 0;
		while(cont<veya.size()){
			EntidadYAridad eya = (EntidadYAridad) veya.get(cont);
			if(eya.getEntidad()==idEntidad){
				v.add(eya.getRol());
			}
			cont++;
		}
		return v;
	}
	
	

	/*
	 * Oyentes de los botones
	 */

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		this.setVisible(false);
	}                                          

	private void botonQuitarActionPerformed(java.awt.event.ActionEvent evt) { 
		int numRol = comboRoles.getSelectedIndex();
		this.itemsRoles = this.generaItemsRoles();
		String rol = this.itemsRoles.get(numRol);
		Vector<Object> v = new Vector<Object>();
		//Añadimos la relación
		v.add(this.getRelacion());
		//Añadimos la entidad-> Como están ordenadas alfabéticamente hay que calcular el indice asociado
		v.add(this.listaEntidades.get(indiceAsociado(this.comboEntidades.getSelectedIndex())));
		v.add(rol);
		//Mandamos el mensaje al controlador con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIQuitarEntidadARelacion_ClickBotonQuitar, v);
		
		
	}
	
	@SuppressWarnings("unchecked")
	private void comboEntidadesItemStateChanged(java.awt.event.ItemEvent evt) {
		Vector<String> itemsRoles = generaItemsRoles();
		comboRoles.setModel(new javax.swing.DefaultComboBoxModel(itemsRoles));
		comboRoles.grabFocus();		
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
	
	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText((Lenguaje.getMensaje(Lenguaje.SELECT_ENTITY_TO_QUIT)));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(124, 12, 238, 22);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}
	
	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(12, 52, 100, 87);
		}
		return labelIcono;
	}
	
	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			comboEntidades = new JComboBox();
			comboEntidades.setBounds(124, 40, 238, 21);
			comboEntidades.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					comboEntidadesItemStateChanged(evt);
				}
			});
			comboEntidades.addKeyListener(general);
		}
		return comboEntidades;
	}

	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(276, 149, 80, 25);
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
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}
	
	private JButton getBotonQuitar() {
		if(botonQuitar == null) {
			botonQuitar = new JButton();
			botonQuitar.setText(Lenguaje.getMensaje(Lenguaje.REMOVE));
			botonQuitar.setBounds(185, 149, 80, 25);
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
			botonQuitar.setMnemonic(Lenguaje.getMensaje(Lenguaje.REMOVE).charAt(0));
		}
		return botonQuitar;
	}

	private JTextPane getExplicacionRol() {
		if(explicacionRol == null) {
			explicacionRol = new JTextPane();
			explicacionRol.setText(Lenguaje.getMensaje(Lenguaje.IF_ENTITY_HAS_ROLLE));
			explicacionRol.setBounds(124, 67, 238, 22);
			explicacionRol.setEditable(false);
			explicacionRol.setOpaque(false);
			explicacionRol.setFocusable(false);
		}
		return explicacionRol;
	}
	
	private JComboBox getComboRoles() {
		if(comboRoles == null) {
			comboRoles = new JComboBox();
			comboRoles.setBounds(124, 95, 238, 21);
		}
		comboRoles.addKeyListener(general);
		return comboRoles;
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
	
	/*Dada la posición seleccionada en el comboBox devuelve el índice correspondiente a dicho 
	 * elementeo en la lista de Entidades. Es necesario porque al ordenar alfabeticamente se perdió 
	 * la correspondencia.*/
	private int indiceAsociado (int selec){
		boolean encontrado= false;
		int i=0;
		while ((i<this.listaEntidades.size())&& (!encontrado)){
			if((this.items.get(selec)).equals(this.listaEntidades.get(i).getNombre())){
				encontrado =true;
				return i;
			}
			else
				i++;
		}
		return i;
	}

	//Métodos para ordenar alfabéticamente-> Quicksort
	private static void quicksort(Vector<String> a) {
        quicksort(a, 0, a.size() - 1);
    }

    // quicksort a[left] to a[right]
    private static void quicksort(Vector<String> a, int left, int right) {
        if (right <= left) return;
        int i = partition(a, left, right);
        quicksort(a, left, i-1);
        quicksort(a, i+1, right);
    }

    // partition a[left] to a[right], assumes left < right
    private static int partition(Vector<String> a, int left, int right) {
        int i = left - 1;
        int j = right;
        while (true) {
            while ((a.get(++i).compareToIgnoreCase(a.get(right))<0))      // find item on left to swap
                ;                               // a[right] acts as sentinel
            while ((a.get(right).compareToIgnoreCase(a.get(--j))<0))      // find item on right to swap
                if (j == left) break;           // don't go out-of-bounds
            if (i >= j) break;                  // check if pointers cross
            exch(a, i, j);                      // swap two elements into place
        }
        exch(a, i, right);                      // swap with partition element
        return i;
    }
    private static void exch(Vector<String> a, int i, int j) {
        //exchanges++;
        String swap = a.get(i);
        a.set(i,a.get(j));
        a.set(j,swap);
    }

	
}

