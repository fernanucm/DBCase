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
public class GUI_EditarCardinalidadEntidad extends javax.swing.JDialog  implements KeyListener, MouseListener{


	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	private Controlador controlador;
	private Vector<TransferEntidad> listaEntidades;
	private JComboBox comboEntidades;
	private JTextField cajaInicio;
	private JLabel jLabel1;
	private JTextField cajaFinal;
	private JRadioButton buttonMinMax;
	private JComboBox comboRoles;
	private JTextPane jTextPane1;
	private JTextField cajaRol;
	private JTextPane jTextRol;
	private JTextPane explicacion2;
	private JTextPane explicacion;
	private JButton botonEditar;
	private JButton botonCancelar;
	private TransferRelacion relacion;
	private String rolViejo;
	private JRadioButton buttonNaN;
	private JRadioButton button1aN;
	private JRadioButton button1a1;
	
	public GUI_EditarCardinalidadEntidad() {
		initComponents();
	}

	private void initComponents() {

        setTitle(Lenguaje.getMensaje(Lenguaje.EDIT_ARITY_AND_ROLLE));
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        getContentPane().setLayout(null);
        getContentPane().add(getBotonCancelar());
        getContentPane().add(getBotonEditar());
        //getContentPane().add(getLabelIcono());
        getContentPane().add(getComboEntidades());
        getContentPane().add(getExplicacion());
        getContentPane().add(getExplicacion2());
        getContentPane().add(getCajaFinal());
        getContentPane().add(getJLabel1());
        getContentPane().add(getCajaInicio());
        getContentPane().add(getJTextRol());
        getContentPane().add(getcajaRol());
        getContentPane().add(getJTextPane1());
        getContentPane().add(getComboRoles());
        getContentPane().add(getButton1aN());
        getContentPane().add(getButton1a1());
        getContentPane().add(getButtonMinMax());
        getContentPane().add(getButtonNaN());
        this.setSize(394, 418);
        this.addMouseListener(this);
		this.addKeyListener(this);
    }
	

	/*
	 * Activar y desactivar el dialogo
	 */

	public void setInactiva(){
		this.setVisible(false);
	}

	public void setActiva(){
		if(this.getRelacion().getListaEntidadesYAridades().isEmpty())
			JOptionPane.showMessageDialog(
				null,
				(Lenguaje.getMensaje(Lenguaje.ERROR))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.IMPOSIBLE_EDIT_ROLLE))+"\n" +
				(Lenguaje.getMensaje(Lenguaje.NO_ENTITIES_IN_RELATION))+"\n",
				(Lenguaje.getMensaje(Lenguaje.EDIT_ARITY)),
				JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(getClass().getClassLoader().getResource(ImagePath.ERROR)));
		else{
			this.controlador.mensajeDesde_GUI(TC.GUIEditarCardinalidadEntidad_ActualizameListaEntidades, null);
			this.cajaInicio.setText("");
			this.cajaFinal.setText("");
			this.cajaFinal.setEnabled(false);
			this.cajaInicio.setEnabled(false);
			this.cajaFinal.setEditable(false);
			this.cajaFinal.setEditable(false);
			this.button1a1.setEnabled(true);
			this.button1a1.setSelected(false);
			this.button1aN.setEnabled(true);
			this.button1aN.setSelected(false);
			this.buttonNaN.setEnabled(true);
			this.buttonNaN.setSelected(true);
			this.buttonMinMax.setEnabled(true);
			this.buttonMinMax.setSelected(false);			
			// Generamos los items (ya filtrados)
			String[] itemsEntidades = this.generaItemsEntidades();
			comboEntidades.setModel(new javax.swing.DefaultComboBoxModel(itemsEntidades));
			comboEntidades.setSelectedIndex(0);
			
			comboEntidades.grabFocus();
			// Inicio y fin de la entidad seleccionada
			int vez = 1;//Flag para distingir la inicialización de la ventana
			Vector<String> v = this.generaInicioFin(vez);
			vez ++;
			this.cajaRol.setText(v.get(2));
			rolViejo=v.get(2);
			Vector<String> itemsRoles = this.generaItemsRoles();
			comboRoles.setModel(new javax.swing.DefaultComboBoxModel(itemsRoles));
			if(this.buttonMinMax.isSelected()){
				cajaInicio.setText(v.get(0));
				cajaFinal.setText(v.get(1));
			}
			else{
				cajaInicio.setText("");
				cajaFinal.setText("");
				this.cajaInicio.setEditable(false);
				this.cajaFinal.setEditable(false);
				this.cajaFinal.setEnabled(false);
				this.cajaInicio.setEnabled(false);
			}			
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
	
	private Vector<String> generaInicioFin(int vez){
		int inicio = 0;
		int fin = 0;
		String rol="";
		int itemSeleccionado = this.comboEntidades.getSelectedIndex();
		int rolSeleccionado = this.comboRoles.getSelectedIndex();
		TransferEntidad te = this.listaEntidades.get(itemSeleccionado);
		int idEntidad = te.getIdEntidad();
		Vector veya = this.relacion.getListaEntidadesYAridades();
		int cont = 0;
		int numRolVisitados = 0;//Si en una relación participa varias veces una entidad con que aparición me quedo
		boolean salir = false;
		while(cont<veya.size() && !salir){
			EntidadYAridad eya = (EntidadYAridad) veya.get(cont);
			if(vez ==1){//Inicialización de la ventana
				if(eya.getEntidad() == idEntidad){
						inicio = eya.getPrincipioRango();
						fin = eya.getFinalRango();
						rol=eya.getRol();
						salir = true;	
				}
			}
			else{
				if(eya.getEntidad() == idEntidad){
					if(numRolVisitados == rolSeleccionado){
						inicio = eya.getPrincipioRango();
						fin = eya.getFinalRango();
						rol=eya.getRol();
						salir = true;	
					}
					else
						numRolVisitados++;
					
				}
			}
			cont++;
		}
		Vector<String> v = new Vector<String>();
		if(inicio == Integer.MAX_VALUE) v.add("n"); else v.add(String.valueOf(inicio));
		if(fin == Integer.MAX_VALUE) v.add("n"); else v.add(String.valueOf(fin));
		v.add(String.valueOf(rol));
		return v;
	}

	private String[] generaItemsEntidades(){
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
	
	private Vector<String> generaItemsRoles(){
		Vector<String> v = new Vector<String>();
		int itemSeleccionado = this.comboEntidades.getSelectedIndex();
		TransferEntidad te = this.listaEntidades.get(itemSeleccionado);
		int idEntidad = te.getIdEntidad();
		te.isDebil();
		Vector veya = this.relacion.getListaEntidadesYAridades();
		int cont = 0;
		int numApariciones=0;
		while(cont<veya.size()){
			EntidadYAridad eya = (EntidadYAridad) veya.get(cont);
			if(eya.getEntidad()==idEntidad){
				v.add(eya.getRol());
				numApariciones++;
			}
			cont++;
		}
		if(numApariciones==1) {
		}
		return v;
	}



	/*
	 * Oyentes
	 */

	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                           
		this.setVisible(false);
	}                                          

	private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {
		Vector<Object> v = new Vector<Object>();
		v.add(this.getRelacion());
		v.add(this.listaEntidades.get(this.comboEntidades.getSelectedIndex()));
//		En función de que boton de la cardinalidad haya seleccionado se guardará una u otra:
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
		v.add(this.cajaRol.getText());
		v.add(rolViejo);
		this.controlador.mensajeDesde_GUI(TC.GUIEditarCardinalidadEntidad_ClickBotonEditar, v);
	}                                           

	private void comboEntidadesItemStateChanged(java.awt.event.ItemEvent evt) {
		//Se llama a generaInicioFin con un valor cualquiera >1 
		Vector<String> v = this.generaInicioFin(13);
		if(this.buttonMinMax.isSelected()){
			cajaInicio.setText(v.get(0));
			cajaFinal.setText(v.get(1));
		}
		else{
			cajaInicio.setText("");
			cajaFinal.setText("");
			this.cajaInicio.setEditable(false);
			this.cajaFinal.setEditable(false);
			this.cajaFinal.setEnabled(false);
			this.cajaInicio.setEnabled(false);
		}
		cajaRol.setText(v.get(2));
		rolViejo=v.get(2);
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
	
	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setFont(new java.awt.Font("SansSerif",0,11));
			botonCancelar.setBounds(279, 347, 80, 25);
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
			botonEditar.setFont(new java.awt.Font("SansSerif",0,11));
			botonEditar.setBounds(188, 347, 80, 25);
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
	
	private JComboBox getComboEntidades() {
		if(comboEntidades == null) {
			comboEntidades = new JComboBox();
			comboEntidades.setBounds(25, 52, 238, 21);
			comboEntidades.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					comboEntidadesItemStateChanged(evt);
				}
			});
			comboEntidades.addKeyListener(general);
		}
		return comboEntidades;
	}
	
	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.SELECT_ENTITY_TO_CHANGE));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(25, 21, 238, 25);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}
	
	private JTextPane getExplicacion2() {
		if(explicacion2 == null) {
			explicacion2 = new JTextPane();
			explicacion2.setText(Lenguaje.getMensaje(Lenguaje.WRITE_NEW_ARITY));
			explicacion2.setEditable(false);
			explicacion2.setOpaque(false);
			explicacion2.setBounds(25, 133, 238, 25);
			explicacion2.setFocusable(false);
		}
		return explicacion2;
	}
	
	private JTextField getCajaFinal() {
		if(cajaFinal == null) {
			cajaFinal = new JTextField();
			cajaFinal.setBounds(174, 235, 56, 25);
			cajaFinal.addKeyListener(general);
		}
		return cajaFinal;
	}
	
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText(Lenguaje.getMensaje(Lenguaje.TO));
			jLabel1.setBounds(150, 235, 56, 25);
		}
		return jLabel1;
	}
	
	private JTextField getCajaInicio() {
		if(cajaInicio == null) {
			cajaInicio = new JTextField();
			cajaInicio.setBounds(86, 235, 56, 25);
			cajaInicio.addKeyListener(general);
		}
		return cajaInicio;
	}
	
	
	private JTextPane getJTextRol() {
		if(jTextRol == null) {
			jTextRol = new JTextPane();
			jTextRol.setText(Lenguaje.getMensaje(Lenguaje.WRITE_NEW_ROLLE));
			jTextRol.setBounds(25, 261, 239, 25);
			jTextRol.setOpaque(false);
			jTextRol.setEditable(false);
			jTextRol.setFocusable(false);
		}
		return jTextRol;
	}
	
	private JTextField getcajaRol() {
		if(cajaRol == null) {
			cajaRol = new JTextField();
			cajaRol.setBounds(25, 299, 239, 21);
		}
		cajaRol.addKeyListener(general);
		return cajaRol;
	}
	
	private JTextPane getJTextPane1() {
		if(jTextPane1 == null) {
			jTextPane1 = new JTextPane();
			jTextPane1.setText(Lenguaje.getMensaje(Lenguaje.IF_ENTITY_HAS_ROLLE));
			jTextPane1.setEditable(false);
			jTextPane1.setOpaque(false);
			jTextPane1.setBounds(25, 77, 238, 25);
			jTextPane1.setFocusable(false);
		}
		return jTextPane1;
	}
		
	private JComboBox getComboRoles() {
		if(comboRoles == null) {
			comboRoles = new JComboBox();
			comboRoles.setBounds(25, 110, 238, 21);
			comboRoles.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					comboRolesItemStateChanged(evt);
				}
			});
			comboRoles.addKeyListener(general);
		}
		return comboRoles;
	}
	
	private void comboRolesItemStateChanged(java.awt.event.ItemEvent evt) {
		//Se llama a generaInicioFin con un valor cualquiera >1 
		Vector<String> v = this.generaInicioFin(13);
		/*cajaInicio.setText(v.get(0));
		cajaFinal.setText(v.get(1));*/
		if(this.buttonMinMax.isSelected()){
			cajaInicio.setText(v.get(0));
			cajaFinal.setText(v.get(1));
		}
		else{
			cajaInicio.setText("");
			cajaFinal.setText("");
			this.cajaInicio.setEditable(false);
			this.cajaFinal.setEditable(false);
			this.cajaFinal.setEnabled(false);
			this.cajaInicio.setEnabled(false);
		}
		cajaRol.setText(v.get(2));
		rolViejo=v.get(2);
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
	
	/*Al seleccionar la cardinalidad 1 a 1 deshabilito el resto de botones  y los habilito 
	 * al desseleccionar*/
	private void button1a1ItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.button1a1.isSelected()){
			this.button1aN.setSelected(false);
			this.buttonNaN.setSelected(false);
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
			this.button1a1.setSelected(false);
			this.buttonNaN.setSelected(false);
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
			this.button1a1.setSelected(false);
			this.button1aN.setSelected(false);
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
			//Inicio y fin de la entidad seleccionada
			Vector<String> v = this.generaInicioFin(13);
			this.cajaInicio.setText(v.get(0));
			this.cajaFinal.setText(v.get(1));
			this.button1a1.setSelected(false);
			this.button1aN.setSelected(false);
			this.buttonNaN.setSelected(false);
			this.cajaFinal.setEnabled(true);
			this.cajaInicio.setEnabled(true);
			this.cajaFinal.setEditable(true);
			this.cajaInicio.setEditable(true);
		}
		else{
			if(!this.buttonMinMax.isSelected()){
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
			button1a1.setText(Lenguaje.getMensaje(Lenguaje.LABEL1A1));
			button1a1.setBounds(25, 159, 127, 30);
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
			button1aN.setBounds(25, 183, 127, 30);
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
			buttonNaN.setBounds(25, 208, 127, 30);
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
			buttonMinMax.setBounds(25, 235, 70, 25);
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
