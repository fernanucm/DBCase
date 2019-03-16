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
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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
@SuppressWarnings({"rawtypes" ,"unchecked"})
public class GUI_AnadirEntidadARelacion extends javax.swing.JDialog implements KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	// Variables declaration - do not modify
	private JComboBox comboEntidades;
	private JButton botonCancelar;
	private JLabel jLabel1;
	private JTextField cajaFinal;
	private JRadioButton buttonNaN;
	private JRadioButton button1aN;
	private JRadioButton button1a1;
	private JRadioButton buttonMinMax;
	private JButton botonAnadir;
	private JTextField cajaInicio;
	private JTextPane explicacion2;
	private JLabel labelIcono;
	private JTextPane explicacion;
	private TransferRelacion relacion;
	private JTextPane explicacion3;
	private JTextField cajaRol;
	private Vector<String> items;
	
	
	public GUI_AnadirEntidadARelacion() {
		initComponents();
	}

	private void initComponents() {
		setTitle(Lenguaje.getMensaje(Lenguaje.INSERT_NEW_ENTITY_TO_RELATION));
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        getContentPane().setLayout(null);
        getContentPane().add(getExplicacion());
        getContentPane().add(getLabelIcono());
        getContentPane().add(getComboEntidades());
        getContentPane().add(getExplicacion2());
        getContentPane().add(getCajaInicio());
        getContentPane().add(getCajaFinal());
        getContentPane().add(getJLabel1());
        getContentPane().add(getExplicacion3());
        getContentPane().add(getCajaRol());
        getContentPane().add(getBotonCancelar());
        getContentPane().add(getBotonAnadir());
        getContentPane().add(getButton1a1());
        getContentPane().add(getButton1aN());
        getContentPane().add(getButtonNaN());
        getContentPane().add(getButtonMinMax());
        this.setSize(369, 342);
        this.addMouseListener(this);
		this.addKeyListener(this);
    }

	/*
	 * Activar y desactivar el dialogo
	 */
	
	public void setActiva(){
		// Le pedimos al controlador que nos actualice la lista de entidades
		this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadARelacion_ActualizameListaEntidades, null);
		// Generamos los items del comboEntidades
		items = this.generaItems();
		//Los ordenamos alfabéticamente
		quicksort((Vector<String>)items);
		if(items.size() == 0)
			JOptionPane.showMessageDialog(
				null,
				(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.INSERT_NEW_ENTITY_TO_RELATION))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_TO_INSERT_ENTITY))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.NO_ENTITY))+"\n",
				(Lenguaje.getMensaje(Lenguaje.INSERT_ATTRIBUTE)),
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));	
		else{
			this.cajaInicio.setText("");
			this.cajaFinal.setText("");
			this.cajaFinal.setEditable(false);
			this.button1a1.setEnabled(true);
			this.button1a1.setSelected(false);
			this.button1aN.setEnabled(true);
			this.button1aN.setSelected(false);
			this.buttonNaN.setEnabled(true);
			this.buttonNaN.setSelected(true);
			this.buttonMinMax.setEnabled(true);
			this.buttonMinMax.setSelected(false);
			this.comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(items));
			this.comboEntidades.setSelectedItem(primerItem());
			this.cajaRol.setText("");
			this.centraEnPantalla();
			SwingUtilities.invokeLater(doFocus);
			this.setVisible(true);
		}	
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         comboEntidades.grabFocus();
	     }
	 };

	//@SuppressWarnings("unchecked")
	private Vector<String> generaItems(){
		// Generamos los items
		int cont = 0;
		Vector<String> items = new Vector<String>(this.listaEntidades.size());
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			items.add(cont,te.getNombre());
			cont++;
		}
		return items;
	}
	
	
	/*Devuelve el nombre de la primera entidad que haya en el sistema y no esté participando
	 * en la relación.*/
	private String primerItem(){
		// Filtramos la lista de entidades quitando las entidades que no intervienen
		Vector<EntidadYAridad> vectorTupla = this.getRelacion().getListaEntidadesYAridades();
		Vector vectorIdsEntidades = new Vector();
		int cont = 0; // Para saltar la entidad padre
		//Guardo en vectorIdsEntidades los ids de las entidades que ya participan en esa relacion
		while(cont<vectorTupla.size()){
			vectorIdsEntidades.add((vectorTupla.get(cont)).getEntidad());
			cont++;
		}
		cont = 0;
		boolean encontrado=false;
		Vector<TransferEntidad> listaEntidadesFiltrada = new Vector<TransferEntidad>();
		while((cont<this.getListaEntidades().size())&&(!encontrado)){
			TransferEntidad te = this.getListaEntidades().get(cont);
			if(vectorIdsEntidades.contains(te.getIdEntidad())){
				listaEntidadesFiltrada.add(te);
				cont++;
			}
			else
				encontrado= true;
			
		}
		TransferEntidad te;
		if((this.listaEntidades.size()==1)||(!encontrado))
			te = this.listaEntidades.get(0);
		else
			te = this.listaEntidades.get(cont);
		return te.getNombre();
	}
	
	/*Dada la posición seleccionada en el comboBox devuelve el índice correspondiente a dicho 
	 * elementeo en la lista de Entidades.  Es necesario porque al ordenar alfabeticamente se perdió 
	 * la correspondencia.*/
	private int indiceAsociado (int selec){
		boolean encontrado= false;
		int i=0;
		while ((i<this.getListaEntidades().size())&& (!encontrado)){
			if((this.items.get(selec)).equals(this.getListaEntidades().get(i).getNombre())){
				encontrado =true;
				return i;
			}
			else
				i++;
		}
		return i;
	}


	public void setInactiva(){
		this.setVisible(false);
	}

	/*
	 * Oyentes de los botones
	 */
	private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {
		// Mandaremos el siguiente vector al controlador
		Vector v = new Vector();
		v.add(this.getRelacion());
		v.add(this.getListaEntidades().get(indiceAsociado(this.comboEntidades.getSelectedIndex())));
		//En función de que boton de la cardinalidad haya seleccionado se guardará una u otra:
		if (this.button1a1.isSelected()){
			v.add(String.valueOf(1));
			v.add(String.valueOf(1));
		}
		else if (this.button1aN.isSelected()){
			v.add(String.valueOf(1));
			v.add("n");
		}
		else if (this.buttonNaN.isSelected()){
			v.add("n");
			v.add("n");
		}		
		else{
			v.add(String.valueOf(this.cajaInicio.getText().toLowerCase()));
			v.add(String.valueOf(this.cajaFinal.getText().toLowerCase()));
		}
		v.add(String.valueOf(this.cajaRol.getText()));
		// Mandamos el mensaje y el vector con los datos
		this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadARelacion_ClickBotonAnadir,v);

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
			if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Interfaz
	 */
	
	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.SELECT_ENTITY));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(124, 8, 115, 19);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}
	
	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 12, 100, 87);
		}
		return labelIcono;
	}
	
	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			comboEntidades = new JComboBox();
			comboEntidades.setBounds(124, 30, 208, 21);
		}
		comboEntidades.addKeyListener(general);
		return comboEntidades;
	}
	
	private JTextPane getExplicacion2() {
		if(explicacion2 == null) {
			explicacion2 = new JTextPane();
			explicacion2.setText(Lenguaje.getMensaje(Lenguaje.WRITE_NUMBERS_RELATION));
			explicacion2.setEditable(false);
			explicacion2.setOpaque(false);
			explicacion2.setBounds(124, 63, 107, 17);
			explicacion2.setFocusable(false);
		}
		return explicacion2;
	}
	
	
	private JTextField getCajaInicio() {
		if(cajaInicio == null) {
			cajaInicio = new JTextField();
			cajaInicio.setEditable(false);
			cajaInicio.setBounds(170, 160, 56, 21);
			cajaInicio.addKeyListener(general);
		}
		return cajaInicio;
	}
	
	private JTextField getCajaFinal() {
		if(cajaFinal == null) {
			cajaFinal = new JTextField();
			cajaFinal.setBounds(262, 160, 51, 21);
			cajaFinal.setEnabled(false);
			cajaFinal.addKeyListener(general);
		}
		return cajaFinal;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setEnabled(false);
			jLabel1.setText(Lenguaje.getMensaje(Lenguaje.TO));
			jLabel1.setBounds(234, 160, 35, 21);
		}
		return jLabel1;
	}
	
	private JTextPane getExplicacion3() {
		if(explicacion3 == null) {
			explicacion3 = new JTextPane();
			explicacion3.setText(Lenguaje.getMensaje(Lenguaje.WRITE_ROLL));
			explicacion3.setEditable(false);
			explicacion3.setOpaque(false);
			explicacion3.setBounds(124, 194, 147, 21);
			explicacion3.setFocusable(false);
		}
		return explicacion3;
	}
	
	private JTextField getCajaRol() {
		if(cajaRol == null) {
			cajaRol = new JTextField();
			cajaRol.setBounds(124, 227, 208, 21);
		}
		cajaRol.addKeyListener(general);
		return cajaRol;
	}
	
	
	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(237, 269, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonAnadir.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.setMnemonic(	Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
	}
	
	private JButton getBotonAnadir() {
		if(botonAnadir == null) {
			botonAnadir = new JButton();
			botonAnadir.setText(Lenguaje.getMensaje(Lenguaje.INSERT));
			botonAnadir.setBounds(146, 269, 80, 25);
			botonAnadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonAnadirActionPerformed(evt);
				}
			});
			botonAnadir.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonAnadirActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonAnadir.setMnemonic(Lenguaje.getMensaje(Lenguaje.INSERT).charAt(0));
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
	
//	Utilidades
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
	
	/*Al seleccionar la cardinalidad 1 a 1 deshabilito el resto de botones  y los habilito 
	 * al desseleccionar*/
	private void button1a1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.button1a1.isSelected()){
			//this.button1aN.setEnabled(false);
			this.button1aN.setSelected(false);
			//this.buttonNaN.setEnabled(false);
			this.buttonNaN.setSelected(false);
			//this.buttonMinMax.setEnabled(false);
			this.buttonMinMax.setSelected(false);
			this.cajaFinal.setEditable(false);
			this.cajaFinal.setText("");
			this.cajaInicio.setText("");
			this.cajaInicio.setEditable(false);
			this.jLabel1.setEnabled(false);
		}
		else{
			if(!this.button1a1.isSelected()){
				this.button1aN.setEnabled(true);
				this.buttonNaN.setEnabled(true);
				this.buttonMinMax.setEnabled(true);
				this.jLabel1.setEnabled(true);
			}
		}
	}
	
	/*Al seleccionar la cardinalidad 1 a N deshabilito el resto de botones  y los habilito 
	 * al desseleccionar*/
	private void button1aNItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.button1aN.isSelected()){
			//this.button1a1.setEnabled(false);
			this.button1a1.setSelected(false);
			//this.buttonNaN.setEnabled(false);
			this.buttonNaN.setSelected(false);
			//this.buttonMinMax.setEnabled(false);
			this.buttonMinMax.setSelected(false);
			this.cajaFinal.setText("");
			this.cajaInicio.setText("");
			this.cajaFinal.setEditable(false);
			this.cajaInicio.setEditable(false);
			this.jLabel1.setEnabled(false);
		}
		else{
			if(!this.button1aN.isSelected()){
				this.button1a1.setEnabled(true);
				this.buttonNaN.setEnabled(true);
				this.buttonMinMax.setEnabled(true);
				this.jLabel1.setEnabled(true);
			}
		}
	}
	
	/*Al seleccionar la cardinalidad N a N deshabilito el resto de botones  y los habilito 
	 * al desseleccionar*/
	private void buttonNaNItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.buttonNaN.isSelected()){
			//this.button1a1.setEnabled(false);
			this.button1a1.setSelected(false);
			//this.button1aN.setEnabled(false);
			this.button1aN.setSelected(false);
			//this.buttonMinMax.setEnabled(false);
			this.buttonMinMax.setSelected(false);
			this.cajaFinal.setEditable(false);
			this.cajaFinal.setText("");
			this.cajaInicio.setText("");
			this.cajaInicio.setEditable(false);
			this.jLabel1.setEnabled(false);
		}
		else{
			if(!this.buttonNaN.isSelected()){
				this.button1a1.setEnabled(true);
				this.button1aN.setEnabled(true);
				this.buttonMinMax.setEnabled(true);
				this.jLabel1.setEnabled(true);
			}
		}
	}
	
	/*Al seleccionar la cardinalidad Min Max deshabilito el resto de botones  y los habilito 
	 * al desseleccionar*/
	private void buttonMinMaxItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.buttonMinMax.isSelected()){
			//this.button1a1.setEnabled(false);
			this.button1a1.setSelected(false);
			//this.button1aN.setEnabled(false);
			this.button1aN.setSelected(false);
			//this.buttonNaN.setEnabled(false);
			this.buttonNaN.setSelected(false);
			this.cajaFinal.setEnabled(true);
			this.cajaInicio.setEnabled(true);
			this.cajaFinal.setEditable(true);
			this.cajaInicio.setEditable(true);
		}
		else{
			if(!this.buttonMinMax.isSelected()){
				//this.button1a1.setEnabled(true);
				//this.button1aN.setEnabled(true);
				//this.buttonNaN.setEnabled(true);
				this.cajaFinal.setEditable(false);
				this.cajaFinal.setText("");
				this.cajaInicio.setText("");
				this.cajaInicio.setEditable(false);
			}
		}
	}
	
	private JRadioButton getButton1a1() {
		if(button1a1 == null) {
			button1a1 = new JRadioButton();
			button1a1.setOpaque(false);
			button1a1.setEnabled(false);
			//TODO -internacionalizar mensaje
			button1a1.setText(Lenguaje.getMensaje(Lenguaje.LABEL1A1));
			button1a1.setBounds(124, 86, 127, 17);
			button1a1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					button1a1ItemStateChanged(evt);
				}
			});
			button1a1.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){button1a1.setSelected(
												!button1a1.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return button1a1;
	}
	
	private JRadioButton getButton1aN() {
		if(button1aN == null) {
			button1aN = new JRadioButton();
			button1aN.setOpaque(false);
			button1aN.setText(Lenguaje.getMensaje(Lenguaje.LABEL1AN));
			button1aN.setEnabled(false);
//			TODO -internacionalizar mensaje
			button1aN.setBounds(124, 112, 86, 18);
			button1aN.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					button1aNItemStateChanged(evt);
				}
			});
			button1aN.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){button1aN.setSelected(
												!button1aN.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return button1aN;
	}
	
	private JRadioButton getButtonNaN() {
		if(buttonNaN == null) {
			buttonNaN = new JRadioButton();
			buttonNaN.setOpaque(false);
			buttonNaN.setSelected(true);
			buttonNaN.setText(Lenguaje.getMensaje(Lenguaje.LABELNAN));
			buttonNaN.setBounds(124, 137, 86, 18);
			buttonNaN.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					buttonNaNItemStateChanged(evt);
				}
			});
			buttonNaN.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){buttonNaN.setSelected(
												!buttonNaN.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return buttonNaN;
	}
	
	private JRadioButton getButtonMinMax() {
		if(buttonMinMax == null) {
			buttonMinMax = new JRadioButton();
			buttonMinMax.setOpaque(false);
			buttonMinMax.setEnabled(false);
			buttonMinMax.setText(Lenguaje.getMensaje(Lenguaje.THE));
			buttonMinMax.setBounds(124, 162, 46, 17);
			buttonMinMax.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					buttonMinMaxItemStateChanged(evt);
				}
			});
			buttonMinMax.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){buttonMinMax.setSelected(
												!buttonMinMax.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return buttonMinMax;
	}

}
