package Presentacion.GUIFrames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Vector;
import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import Controlador.Controlador;
import Controlador.TC;
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
public class GUI_InsertarEntidad extends javax.swing.JDialog  implements KeyListener, MouseListener{
	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private Point2D posicionEntidad;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JTextField cajaNombre;
	private JCheckBox CasillaEsDebil;
	private JTextPane explicacion;
	private JLabel labelIcono;
	private JButton botonInsertar;
	private JTextField jTextRelacion;
	private JTextPane nombreRelacion;
	private JComboBox comboEntidadesFuertes;
	private JTextPane selecFuerte;
	private Vector<TransferEntidad> listaEntidades;
	private Vector<String> items;
	private TransferRelacion relacion;
	private boolean factibleEntidad;//Sirve para la comprobación de si se puede añadir una entidad debil
	// End of variables declaration

	public GUI_InsertarEntidad() {
		initComponents();
	}

	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(Lenguaje.getMensaje(Lenguaje.INSERT_ENTITY));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		this.setSize(409, 208);
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
			botonCancelar.setBounds(262, 140, 80, 25);
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
			botonInsertar.setBounds(171, 140, 80, 25);
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
			/*labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(14, 39, 94, 86);*/
		}
		{
			explicacion = new JTextPane();
			getContentPane().add(explicacion);
			explicacion.setText(Lenguaje.getMensaje(Lenguaje.WRITE_ENTITY_NAME));
			explicacion.setEditable(false);
			explicacion.setOpaque(false);
			explicacion.setBounds(26, 12, 67, 21);
			explicacion.setFocusable(false);
			explicacion.setAlignmentX(0.0f);
		}
		{
			cajaNombre = new JTextField();
			getContentPane().add(cajaNombre);
			cajaNombre.setBounds(100, 13, 200, 29);
			cajaNombre.addKeyListener(general);
		}
		{
			CasillaEsDebil = new JCheckBox();
			getContentPane().add(CasillaEsDebil);
			CasillaEsDebil.setText(Lenguaje.getMensaje(Lenguaje.WEAK_ENTITY));
			CasillaEsDebil.setBounds(126, 48, 125, 22);
			CasillaEsDebil.setOpaque(false);
			CasillaEsDebil.setBorderPaintedFlat(true);
			CasillaEsDebil.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){
						CasillaEsDebil.setSelected(!CasillaEsDebil.isSelected());
					}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			/*Si la entidad es debil, entonces hay que activar la parte de la ventana que permite seleccionar la entidad
			 * fuerte, y dar un nombre a la relación que unira la entidad fuerte y la débil.*/
			CasillaEsDebil.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent arg0) {
					//Entidad débil
					if(CasillaEsDebil.isSelected()){
						//Le pedimos al controlador que nos actualice la lista de entidades
						controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadARelacion_ActualizameListaEntidades, null);
						//Generamos los items del comboEntidades
						items = generaItems();
						//Los ordenamos alfabeticamente
						quicksort((Vector<String>)items);
						if(items.size() == 0){
							JOptionPane.showMessageDialog(null, Lenguaje.getMensaje(Lenguaje.CREATE_STRONG_ENTITY), Lenguaje.getMensaje(Lenguaje.ERROR), 0);
						}
						else{
							ampliarVentana();
							jTextRelacion.setEnabled(true);
							selecFuerte.setVisible(true);
							selecFuerte.setEnabled(true);
							selecFuerte.setEditable(true);
							comboEntidadesFuertes.setEnabled(true);
							comboEntidadesFuertes.setVisible(true);
							jTextRelacion.setEnabled(true);
							jTextRelacion.setVisible(true);
							nombreRelacion.setEnabled(true);
							nombreRelacion.setVisible(true);
							if(items.size() != 0){
								comboEntidadesFuertes.setModel(new javax.swing.DefaultComboBoxModel(items));
							}
						}
					}
					//Entidad normal
					else{
						reducirVentana();
						jTextRelacion.setEnabled(false);
						selecFuerte.setEnabled(false);
						selecFuerte.setEditable(false);
						selecFuerte.setVisible(false);
						comboEntidadesFuertes.setEnabled(false);
						comboEntidadesFuertes.setVisible(false);
						jTextRelacion.setEnabled(false);
						jTextRelacion.setVisible(false);
						nombreRelacion.setEnabled(false);
						nombreRelacion.setVisible(false);
					}
				}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseReleased(MouseEvent arg0) {}
			});
		}
		{
			selecFuerte = new JTextPane();
			getContentPane().add(selecFuerte);
			selecFuerte.setText(Lenguaje.getMensaje(Lenguaje.SELECT_STRONG_ENTITY));
			selecFuerte.setBounds(124, 78, 250, 36);
			selecFuerte.setOpaque(false);
			selecFuerte.setEditable(false);
			selecFuerte.setEnabled(false);
			selecFuerte.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			selecFuerte.setVisible(false);
		}
		{
			
			if(comboEntidadesFuertes == null) 
				comboEntidadesFuertes = new JComboBox();
			getContentPane().add(comboEntidadesFuertes);
			getContentPane().add(comboEntidadesFuertes);
			comboEntidadesFuertes.setEnabled(false);
			comboEntidadesFuertes.setBounds(124, 103, 180, 21);
			comboEntidadesFuertes.setVisible(false);
		}
		{
			nombreRelacion = new JTextPane();
			getContentPane().add(nombreRelacion);
			nombreRelacion.setText(Lenguaje.getMensaje(Lenguaje.WRITE_RELATION_WEAK));
			nombreRelacion.setBounds(124, 130, 180, 31);
			nombreRelacion.setEditable(false);
			nombreRelacion.setEnabled(false);
			nombreRelacion.setOpaque(false);
			nombreRelacion.setVisible(false);
		}
		{
			jTextRelacion = new JTextField();
			getContentPane().add(jTextRelacion);
			jTextRelacion.setEnabled(false);
			jTextRelacion.setBounds(124, 155, 180, 21);
			jTextRelacion.setVisible(false);
		}
		this.addMouseListener(this);
		this.addKeyListener(this);

	}
	
	/*Redimensiona la ventana para que se puedan mostrar y solicitar los datos necesarios 
	 * para hacer las entidades débiles*/
	public void ampliarVentana(){
		this.setSize(350, 280); 
		botonInsertar.setBounds(130, 200, 80, 25);
		botonCancelar.setBounds(221, 200, 80, 25);
	}
	
	/*Redimensiona la ventana para que se oculten la parte de las entidades débiles*/
	public void reducirVentana(){
		this.setSize(339, 178); 
		botonInsertar.setBounds(148, 100, 80, 25);
		botonCancelar.setBounds(233, 100, 80, 25);
	}
	
	/*
	 * Oyentes de los botones
	 */
	@SuppressWarnings("unchecked")
	private void botonInsertarActionPerformed(java.awt.event.ActionEvent evt) {  
		//Si es una entidad normal, sólo hay que crear dicha entidad
		if (!this.CasillaEsDebil.isSelected()){
			// Generamos el transfer que mandaremos al controlador
			TransferEntidad te = new TransferEntidad();
			te.setPosicion(this.getPosicionEntidad());
			te.setNombre(this.cajaNombre.getText());
			te.setDebil(this.CasillaEsDebil.isSelected());
			te.setListaAtributos(new Vector());
			te.setListaClavesPrimarias(new Vector());
			te.setListaRestricciones(new Vector());
			te.setListaUniques(new Vector());
			// Mandamos mensaje + datos al controlador
			this.getControlador().mensajeDesde_GUI(TC.GUIInsertarEntidad_Click_BotonInsertar, te);
		}
		/*Si es una entidad débil hay que crear dicha entidad, y además crear la relación débil que la unirá con
		 * una entidad fuerte (que existía antes)*/
		else if (!this.cajaNombre.getText().isEmpty()){
			TransferEntidad te = new TransferEntidad();
			//Si la entidad débil y la relación se llaman de diferente manera
			if (!this.cajaNombre.getText().equals(this.jTextRelacion.getText())){
				//Generamos el transfer que mandaremos al controlador para generar la entidad débil				
				te.setPosicion(this.getPosicionEntidad());
				te.setNombre(this.cajaNombre.getText());
				te.setDebil(this.CasillaEsDebil.isSelected());
				te.setListaAtributos(new Vector());
				te.setListaClavesPrimarias(new Vector());
				te.setListaRestricciones(new Vector());
				te.setListaUniques(new Vector());
				// Mandamos mensaje + datos al controlador
				this.getControlador().mensajeDesde_GUI(TC.GUIInsertarEntidadDebil_Click_BotonInsertar, te);
			}
			else{
				te.setNombre(this.cajaNombre.getText());
				this.getControlador().mensajeDesde_GUI(TC.GUIInsertarEntidadDebil_Entidad_Relacion_Repetidos,te);
				this.factibleEntidad=false;
				return;
			}
		}
	} 
	
	@SuppressWarnings("unchecked")
	public void comprobadaEntidad(boolean factibleEntidad){
		this.factibleEntidad=factibleEntidad;
		//Si la entidad va a poder añadirse compruebo si la relación también va a poder añadirse
		if(factibleEntidad){
			//Generamos el transfer que mandaremos al controlador para generar la relación
			TransferRelacion tr = new TransferRelacion();
			tr.setPosicion((this.getPosicionEntidad()));
			tr.setNombre(this.jTextRelacion.getText());
			tr.setListaAtributos(new Vector());
			tr.setListaEntidadesYAridades(new Vector());			
			tr.setListaRestricciones(new Vector());
			tr.setListaUniques(new Vector());
			tr.setTipo("Debil");
			// Mandamos mensaje + datos al controlador
			this.getControlador().mensajeDesde_GUI(TC.GUIInsertarRelacionDebil_Click_BotonInsertar, tr);
		}					
	}
	
	@SuppressWarnings("unchecked")
	public void comprobadaRelacion(boolean factibleRelacion){
		/*Si la entidad va a poder añadirse compruebo si la relación también va a poder añadirse
		 *  y la relacion también entonces es cuando creamos la entidad, y la relación*/
		if((items.size() != 0)&&(factibleRelacion)&&(this.factibleEntidad)){
			//Generamos el transfer que mandaremos al controlador para crear la entidad
			TransferEntidad te = new TransferEntidad();
			te.setPosicion(this.getPosicionEntidad());
			te.setNombre(this.cajaNombre.getText());
			te.setDebil(this.CasillaEsDebil.isSelected());
			te.setListaAtributos(new Vector());
			te.setListaClavesPrimarias(new Vector());
			te.setListaRestricciones(new Vector());
			te.setListaUniques(new Vector());
			// Mandamos mensaje + datos al controlador
			this.getControlador().mensajeDesde_GUI(TC.GUIInsertarEntidad_Click_BotonInsertar, te);
			
			//Generamos el transfer que mandaremos al controlador para crear la relación
			TransferRelacion tr = new TransferRelacion();
			tr.setPosicion((this.getPosicionEntidad()));
			tr.setNombre(this.jTextRelacion.getText());
			tr.setListaAtributos(new Vector());
			tr.setListaEntidadesYAridades(new Vector());			
			tr.setListaRestricciones(new Vector());
			tr.setListaUniques(new Vector());
			tr.setTipo("Debil");
			// Mandamos mensaje + datos al controlador
			this.getControlador().mensajeDesde_GUI(TC.GUIInsertarRelacion_Click_BotonInsertar, tr);
			//Unir la entidad fuerte con la relación
			//Mandaremos el siguiente vector al controlador
			Vector<Object> v = new Vector<Object>();
			v.add(tr);			
			v.add(this.getListaEntidades().get(indiceAsociado(this.comboEntidadesFuertes.getSelectedIndex())));
			v.add(Integer.toString(1));//Inicio
			v.add("n");//Fin
			v.add("");//Rol
			// Mandamos el mensaje y el vector con los datos
			this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadARelacion_ClickBotonAnadir,v);
			
			//Unir la entidad debil con la relación
			//Mandaremos el siguiente vector al controlador
			Vector<Object> w = new Vector<Object>();
			w.add(tr);
			w.add(te);
			w.add(Integer.toString(1));//Inicio
			w.add("1");//Fin
			w.add("");//Rol
			// Mandamos el mensaje y el vector con los datos
			this.controlador.mensajeDesde_GUI(TC.GUIAnadirEntidadARelacion_ClickBotonAnadir,w);
		}
		if(items.size() == 0){
			JOptionPane.showMessageDialog(null, Lenguaje.getMensaje(Lenguaje.CREATE_STRONG_ENTITY), Lenguaje.getMensaje(Lenguaje.ERROR), 0);
		}
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
			reducirVentana();
			this.jTextRelacion.setEnabled(false);
			this.selecFuerte.setEnabled(false);
			this.selecFuerte.setEditable(false);
			this.comboEntidadesFuertes.setEnabled(false);
			this.jTextRelacion.setEnabled(false);
			this.jTextRelacion.setText("");
			this.nombreRelacion.setEnabled(false);
			this.CasillaEsDebil.setSelected(false);
			this.centraEnPantalla();
			this.cajaNombre.setText("");
			this.cajaNombre.grabFocus();
			SwingUtilities.invokeLater(doFocus);
			this.setVisible(true);	
			this.factibleEntidad = false;
			selecFuerte.setVisible(false);
			comboEntidadesFuertes.setVisible(false);
			jTextRelacion.setVisible(false);
			nombreRelacion.setVisible(false);
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         cajaNombre.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
	}	
	
	//	@SuppressWarnings("unchecked")
	private Vector<String> generaItems(){
		// Generamos los items
		int cont = 0;
		int indice =0;
		Vector<String> items = new Vector<String>(this.listaEntidades.size());
		while (cont<this.listaEntidades.size()){
			TransferEntidad te = this.listaEntidades.get(cont);
			if (!te.isDebil()){
				items.add(indice,te.getNombre());
				indice++;
			}
			cont++;
		}
		return items;
	}
	
	/*Dada la posición seleccionada en el comboBox devuelve el índice correspondiente a dicho 
	 * elementeo en la lista de Entidades. Es necesario porque al ordenar alfabeticamente se perdió 
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
	
	
	//Utilidades
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
	 * Getters y Setters
	 */
	public Point2D getPosicionEntidad() {
		return posicionEntidad;
	}

	public void setPosicionEntidad(Point2D posicionEntidad) {
		this.posicionEntidad = posicionEntidad;
	}

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
