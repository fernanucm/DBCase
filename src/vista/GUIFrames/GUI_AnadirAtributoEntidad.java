package vista.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import controlador.Controlador;
import controlador.TC;
import modelo.tools.ImagePath;
import modelo.tools.TipoDominio;
import modelo.transfers.TransferAtributo;
import modelo.transfers.TransferDominio;
import modelo.transfers.TransferEntidad;
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
public class GUI_AnadirAtributoEntidad extends javax.swing.JDialog implements KeyListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private TransferEntidad entidad;
	private Controlador controlador;
	// Variables declaration - do not modify
	private JTextField cajaNombre;
	private JCheckBox opcionClavePrimaria;
	private JCheckBox opcionMultivalorado;
	private JCheckBox opcionCompuesto;
	private JCheckBox opcionNotnull;
	private JCheckBox opcionUnique;
	private JComboBox comboDominios;
	private JButton botonCancelar;
	private JButton botonAnadir;
	private JLabel labelTamano;
	private JTextField cajaTamano;
	private JTextPane jTextPane2;
	private JLabel labelIcono;
	private JTextPane explicacion;
	private Vector<TransferDominio> listaDominios;
	// End of variables declaration


	public GUI_AnadirAtributoEntidad() {
		this.initComponents();
	}

	private void initComponents() {
		setTitle(Lenguaje.getMensaje(Lenguaje.INSERT_ATTRIBUTE));
		this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		getContentPane().add(getExplicacion());
		getContentPane().add(getCajaNombre());
		getContentPane().add(getLabelIcono());
		getContentPane().add(getOpcionClavePrimaria());
		getContentPane().add(getOpcionCompuesto());
		getContentPane().add(getOpcionNotnull());
		getContentPane().add(getOpcionUnique());
		getContentPane().add(getOpcionMultivalorado());
		getContentPane().add(getJTextPane2());
		getContentPane().add(getComboDominios());
		getContentPane().add(getCajaTamano());
		getContentPane().add(getLabelTamano());
		getContentPane().add(getBotonAnadir());
		getContentPane().add(getBotonCancelar());
		this.setSize(402, 385);
		this.addMouseListener(this);
		this.addKeyListener(this);
	}


	/*
	 * Oyentes de los checkBox y del combo
	 */
	private void opcionCompuestoItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.opcionCompuesto.isSelected()){
			this.comboDominios.setEnabled(false);
			this.opcionNotnull.setSelected(false);
			this.opcionNotnull.setEnabled(false);
			this.opcionUnique.setSelected(false);
			this.opcionUnique.setEnabled(false);
			this.cajaTamano.setEditable(false);
			this.cajaTamano.setEnabled(false);
			//this.comboDominios.setSelectedItem("");
			//this.cajaTamano.setText("");
		}
		else{
			if(!this.opcionClavePrimaria.isSelected()){
				this.comboDominios.setEnabled(true);
				//this.comboDominios.setSelectedItem("");
				this.opcionNotnull.setEnabled(true);
				this.opcionUnique.setEnabled(true);
			}
			this.cajaTamano.setEditable(this.activarTamano());
			this.cajaTamano.setEnabled(true);
		}
	}
	
	private void opcionClavePrimariaItemStateChanged(java.awt.event.ItemEvent evt) {
		if(this.opcionClavePrimaria.isSelected()){
			this.opcionNotnull.setSelected(false);
			this.opcionNotnull.setEnabled(false);
			this.opcionUnique.setSelected(false);
			this.opcionUnique.setEnabled(false);
			this.opcionMultivalorado.setSelected(false);
			this.opcionMultivalorado.setEnabled(false);
		}
		else{
			if(!this.opcionCompuesto.isSelected()){
				this.opcionNotnull.setEnabled(true);
				this.opcionUnique.setEnabled(true);
			}
			this.opcionMultivalorado.setEnabled(true);
		}
	}

	private void comboDominiosItemStateChanged(java.awt.event.ItemEvent evt) {
		if (this.activarTamano()){
			this.cajaTamano.setText("10");
			this.cajaTamano.setEditable(true);
			this.cajaTamano.setEnabled(true);
		}
		else{
			this.cajaTamano.setText("");
			this.cajaTamano.setEditable(false);
			this.cajaTamano.setEnabled(false);
		}
	}

	/*
	 * Oyentes de los botones
	 */
	private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {
		TransferAtributo ta = new TransferAtributo();
		ta.setNombre(this.cajaNombre.getText());
		String tamano = "";
		
		double x = this.getEntidad().getPosicion().getX() + 100;
		double y = this.getEntidad().getPosicion().getY();
		ta.setPosicion(new Point2D.Double(x,y));
		ta.setListaComponentes(new Vector());
		if(this.opcionClavePrimaria.isSelected()){
			ta.setClavePrimaria(true);
		}
		else{
			ta.setClavePrimaria(false);
		}
		
		// Si esta seleccionada la opcion compuesto el dominio del atributo lo
		// ponemos a null
		if(this.opcionCompuesto.isSelected()){
			ta.setCompuesto(true);
			ta.setNotnull(false);
			ta.setUnique(false);
			ta.setDominio("null");
			ta.setMultivalorado(opcionMultivalorado.isSelected());
		}
		else{
			ta.setCompuesto(false);
			// Si esta seleccionado la opcion multivalorado
			if (this.opcionMultivalorado.isSelected()){
				ta.setMultivalorado(true);
			}
			else{
				ta.setMultivalorado(false);				
			}
			
			//Unique y Notnull
			ta.setNotnull(this.opcionNotnull.isSelected());
			//ponemos unique a false, ya que en caso de ser Unique se hace la llamada abajo.
			ta.setUnique(false);
			
			TipoDominio dominio;
			String dom;
			try{
				// Obtenemos el dominio seleccionado
				dominio = TipoDominio.valueOf(this.comboDominios.getSelectedItem().toString());
				// Si es un dominio para el que no hay que especificar tamano
				if (!(((TipoDominio)dominio).equals(TipoDominio.CHAR) ||
					((TipoDominio)dominio).equals(TipoDominio.VARCHAR) ||
					((TipoDominio)dominio).equals(TipoDominio.TEXT) ||
					((TipoDominio)dominio).equals(TipoDominio.INTEGER) ||
					((TipoDominio)dominio).equals(TipoDominio.DECIMAL) ||
					((TipoDominio)dominio).equals(TipoDominio.FLOAT))){
						dom=dominio.toString();
						ta.setDominio(dom);
				}
				// Si hay que especificar el tamano
				else{
					tamano = this.cajaTamano.getText();
					// Si no se ha especificado tamano ponemos un 1 por defecto
					if (tamano.isEmpty()){
						tamano = "10";
					}
					// Formamos la cadena del dominio
					ta.setDominio(dominio.toString()+"("+tamano+")");
				}
			}catch(Exception e){//Dominio definido por el usuario
				dom = this.comboDominios.getSelectedItem().toString();
				ta.setDominio(dom);
				ta.setNotnull(this.opcionNotnull.isSelected());
				ta.setCompuesto(this.opcionCompuesto.isSelected());
				ta.setMultivalorado(this.opcionMultivalorado.isSelected());
			}
		}
		ta.setListaRestricciones(new Vector());
		
		// Mandamos la entidad, el nuevo atributo y si hay tamano tambien
		Vector<Object> v = new Vector<Object>();
		v.add(this.getEntidad());
		v.add(ta);
		if (!tamano.isEmpty())
			v.add(tamano);
		controlador.mensajeDesde_GUI(TC.GUIAnadirAtributoEntidad_Click_BotonAnadir, v);
		//if(ta.isClavePrimaria()){
		if (this.opcionClavePrimaria.isSelected()){
			Vector<Object> v1= new Vector<Object>();
			//
			TransferAtributo clon_atributo2 = ta.clonar();
			clon_atributo2.setClavePrimaria(false);
			v1.add(clon_atributo2);
		    v1.add(entidad);
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarClavePrimariaAtributo,v1);
		}
		//actualizamos la tabla de Uniques de la entidad
		if (this.opcionUnique.isSelected()){
			TransferAtributo clon_atributo = ta.clonar();
			controlador.mensajeDesde_PanelDiseno(TC.PanelDiseno_Click_EditarUniqueAtributo,clon_atributo);
		}
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
			else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
			
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};
	
	/*
	 * Activar y desactivar el dialogo
	 */

	public void setActiva(){
		this.centraEnPantalla();
		this.opcionClavePrimaria.setSelected(false);
		this.opcionCompuesto.setSelected(false);
		this.opcionNotnull.setSelected(false);
		this.opcionUnique.setSelected(false);
		this.opcionMultivalorado.setSelected(false);
		this.comboDominios.setEnabled(true);
		this.cajaTamano.setText("");
		this.cajaNombre.setText("");
		controlador.mensajeDesde_GUI(TC.GUIAnadirAtributoEntidad_ActualizameLaListaDeDominios, null);
		
		TipoDominio[] basicos = modelo.tools.TipoDominio.values();
		Object[] nuevos = new Object[this.listaDominios.size()];
		this.generaItems(nuevos);
		String[] items = new String[this.listaDominios.size()+basicos.length];
		int i;
		for(i=0; i<basicos.length;i++){
			items[i]=basicos[i].toString();
		}
		int j=0;
		while(i<this.listaDominios.size()+basicos.length){
			items[i]=nuevos[j].toString();
			i++;
			j++;
		}
		quicksort(items);
		
		this.comboDominios.setModel(new javax.swing.DefaultComboBoxModel(items));
		this.comboDominios.setSelectedItem("VARCHAR");
		if (this.activarTamano()){
			this.cajaTamano.setEditable(true);
			this.cajaTamano.setText("10");
		}
		else{
			this.cajaTamano.setEditable(false);
		}
		this.centraEnPantalla();
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
	 * Getters y Setters
	 */

	public TransferEntidad getEntidad() {
		return entidad;
	}

	public void setEntidad(TransferEntidad entidad) {
		this.entidad = entidad;
	}

	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	/*
	 * Interfaz
	 */

	private JTextPane getExplicacion() {
		if(explicacion == null) {
			explicacion = new JTextPane();
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.NAME));
			explicacion.setBounds(130, 25, 232, 20);
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setFocusable(false);
		}
		return explicacion;
	}
	
	private JTextField getCajaNombre() {
		if(cajaNombre == null) {
			cajaNombre = new JTextField();
			cajaNombre.setBounds(130, 48, 232, 21);
		}
		cajaNombre.addKeyListener(general);
		return cajaNombre;
	}
	
	private JLabel getLabelIcono() {
		if(labelIcono == null) {
			labelIcono = new JLabel();
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 12, 100, 100);
		}
		return labelIcono;
	}
	
	private JCheckBox getOpcionCompuesto() {
		if(opcionCompuesto == null) {
			opcionCompuesto = new JCheckBox();
			opcionCompuesto.setText(Lenguaje.getMensaje(Lenguaje.COMPOSITE_ATTRIBUTE));
			opcionCompuesto.setBounds(142, 104, 220, 18);
			opcionCompuesto.setOpaque(false);
			opcionCompuesto.setBorderPaintedFlat(true);
			opcionCompuesto.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					opcionCompuestoItemStateChanged(evt);
				}
			});
			opcionCompuesto.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){opcionCompuesto.setSelected(
												!opcionCompuesto.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return opcionCompuesto;
	}
	
	private JCheckBox getOpcionClavePrimaria() {
		if(opcionClavePrimaria == null) {
			opcionClavePrimaria = new JCheckBox();
			opcionClavePrimaria.setText(Lenguaje.getMensaje(Lenguaje.PRIMARY_KEY_ATTRIBUTE));
			opcionClavePrimaria.setBounds(142, 85, 220, 18);
			opcionClavePrimaria.setOpaque(false);
			opcionClavePrimaria.setBorderPaintedFlat(true);
			opcionClavePrimaria.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					opcionClavePrimariaItemStateChanged(evt);
				}
			});
			opcionClavePrimaria.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){opcionClavePrimaria.setSelected(
												!opcionClavePrimaria.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return opcionClavePrimaria;
	}
	
	private JCheckBox getOpcionNotnull() {
		if(opcionNotnull == null) {
			opcionNotnull = new JCheckBox();
			opcionNotnull.setText(Lenguaje.getMensaje(Lenguaje.NOT_NULL_ATTRIBUTE));
			opcionNotnull.setBounds(142, 123, 220, 18);
			opcionNotnull.setOpaque(false);
			opcionNotnull.setBorderPaintedFlat(true);
			opcionNotnull.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){opcionNotnull.setSelected(
												!opcionNotnull.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return opcionNotnull;
	}
	
	private JCheckBox getOpcionUnique() {
		if(opcionUnique == null) {
			opcionUnique = new JCheckBox();
			opcionUnique.setText(Lenguaje.getMensaje(Lenguaje.UNIQUE_ATTRIBUTE));
			opcionUnique.setBounds(142, 142, 220, 18);
			opcionUnique.setOpaque(false);
			opcionUnique.setBorderPaintedFlat(true);
			opcionUnique.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){opcionUnique.setSelected(
												!opcionUnique.isSelected());}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
		}
		return opcionUnique;
	}
	
	private JCheckBox getOpcionMultivalorado() {
		if(opcionMultivalorado == null) {
			opcionMultivalorado = new JCheckBox();
			opcionMultivalorado.setText(Lenguaje.getMensaje(Lenguaje.VALUE_ATTRIBUTE));
			opcionMultivalorado.setBounds(142, 161, 221, 18);
			opcionMultivalorado.setOpaque(false);
			opcionMultivalorado.setBorderPaintedFlat(true);
		}
		opcionMultivalorado.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==10){opcionMultivalorado.setSelected(
											!opcionMultivalorado.isSelected());}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		return opcionMultivalorado;
	}
	
	private JTextPane getJTextPane2() {
		if(jTextPane2 == null) {
			jTextPane2 = new JTextPane();
			jTextPane2.setText(Lenguaje.getMensaje(Lenguaje.DOMAIN_ATTRIBUTE));
			jTextPane2.setEditable(false);
			jTextPane2.setOpaque(false);
			jTextPane2.setBounds(129, 188, 231, 20);
			jTextPane2.setFocusable(false);
		}
		return jTextPane2;
	}
	
	private JComboBox getComboDominios() {
		if(comboDominios == null) {
			comboDominios = new JComboBox();
			comboDominios.setBounds(129, 220, 231, 21);
			comboDominios.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent evt) {
					comboDominiosItemStateChanged(evt);
				}
			});
			comboDominios.addKeyListener(general);
		}
		return comboDominios;
	}
	
	private JTextField getCajaTamano() {
		if(cajaTamano == null) {
			cajaTamano = new JTextField();
			cajaTamano.setBounds(194, 253, 166, 21);
		}
		cajaTamano.addKeyListener(general);
		return cajaTamano;
	}
	
	private JLabel getLabelTamano() {
		if(labelTamano == null) {
			labelTamano = new JLabel();
			labelTamano.setText(Lenguaje.getMensaje(Lenguaje.SIZE_ATTRIBUTE));
			labelTamano.setBounds(142, 253, 67, 14);
		}
		return labelTamano;
	}
	
	private JButton getBotonAnadir() {
		if(botonAnadir == null) {
			botonAnadir = new JButton();
			botonAnadir.setText(Lenguaje.getMensaje(Lenguaje.INSERT));
			botonAnadir.setBounds(196, 302, 80, 25);
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
			char s= Lenguaje.getMensaje(Lenguaje.INSERT).charAt(0);
			botonAnadir.setMnemonic(s);


		}
		return botonAnadir;
	}
	
	private JButton getBotonCancelar() {
		if(botonCancelar == null) {
			botonCancelar = new JButton();
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(281, 302, 80, 25);
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
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		return botonCancelar;
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
	
	/**
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
	
	//Utilidades
	private static void quicksort(String[] a) {
        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(String[] a, int left, int right) {
        if (right <= left) return;
        int i = partition(a, left, right);
        quicksort(a, left, i-1);
        quicksort(a, i+1, right);
    }

    private static int partition(String[] a, int left, int right) {
        int i = left - 1;
        int j = right;
        while (true) {
            while ((a[++i].compareToIgnoreCase(a[right])<0))
                ;                               
            while ((a[right].compareToIgnoreCase(a[--j])<0))
                if (j == left) break;       
            if (i >= j) break;              
            exch(a, i, j);                  
        }
        exch(a, i, right);                  
        return i;
    }
    private static void exch(String[] a, int i, int j) {
        String swap = a[i];
        a[i]=a[j];
        a[j]=swap;
    }
}
