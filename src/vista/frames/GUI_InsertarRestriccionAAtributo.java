package vista.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controlador.Controlador;
import controlador.TC;
import modelo.lenguaje.Lenguaje;
import modelo.tools.ImagePath;
import modelo.transfers.TransferAtributo;

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
public class GUI_InsertarRestriccionAAtributo extends javax.swing.JDialog  implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private TransferAtributo atributo;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JLabel labelIcono;
	private JScrollPane jScrollPane1;
	private JButton botonNueva;
	private JButton botonEliminar;
	private JButton botonAceptar;
	private JTable tabla;
	// End of variables declaration

	public GUI_InsertarRestriccionAAtributo() {
		initComponents();
	}

	private void initComponents() {

	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		this.setSize(497, 367);
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.TECLADO)));
			labelIcono.setBounds(12, 60, 100, 100);
		}
		{
			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1);
			jScrollPane1.setBounds(130, 34, 310, 182);
			{
				TableModel tablaModel = 
					new DefaultTableModel(
							new String[][] { { "" } },
							new String[] { Lenguaje.text(Lenguaje.RESTRICTIONS)+":" });
				tabla = new JTable();
				jScrollPane1.setViewportView(tabla);
				tabla.setModel(tablaModel);
				tabla.setBounds(58, 16, 307, 81);
				tabla.setPreferredSize(new java.awt.Dimension(307, 139));
			}
		}
		{
			botonAceptar = new JButton();
			getContentPane().add(botonAceptar);
			botonAceptar.setText(Lenguaje.text(Lenguaje.ACCEPT));
			botonAceptar.setBounds(288, 291, 80, 25);
			botonAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonAceptarActionPerformed(evt);
				}
			});
			botonAceptar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonAceptar.setMnemonic(Lenguaje.text(Lenguaje.ACCEPT).charAt(0));
		}
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.text(Lenguaje.CANCEL));
			botonCancelar.setBounds(379, 291, 80, 25);
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
			botonCancelar.setMnemonic(Lenguaje.text(Lenguaje.CANCEL).charAt(0));
		}
		{
			botonNueva = new JButton();
			getContentPane().add(botonNueva);
			botonNueva.setText(Lenguaje.text(Lenguaje.NEW));
			botonNueva.setBounds(288, 237, 80, 25);
			botonNueva.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==39){botonEliminar.grabFocus();}
				}
			});
			botonNueva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonNuevaActionPerformed(evt);
				}
			});
			botonNueva.setMnemonic(Lenguaje.text(Lenguaje.NEW).charAt(0));
		}
		{
			botonEliminar = new JButton();
			getContentPane().add(botonEliminar);
			botonEliminar.setText(Lenguaje.text(Lenguaje.DELETE));
			botonEliminar.setBounds(379, 237, 80, 25);
			botonEliminar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonNueva.grabFocus();}
				}
			});
			botonEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonEliminarActionPerformed(evt);
				}
			});
			botonEliminar.setMnemonic(Lenguaje.text(Lenguaje.DELETE).charAt(0));
		}

		this.addMouseListener(this);TableModel tablaModel = 
			new DefaultTableModel(
					new String[][] { { "" } },
					new String[] { Lenguaje.text(Lenguaje.RESTRICTIONS)+":" });
		tabla = new JTable();
		jScrollPane1.setViewportView(tabla);
		tabla.setModel(tablaModel);
		this.addKeyListener(this);
	}

	/*
	 * Oyentes de los botones 
	 */
	private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {    
		tabla.editCellAt(0,0);
		Vector<String> predicados = new Vector<String>();
		int i=0;
		while (i<tabla.getRowCount()){
			String s= tabla.getValueAt(i, 0).toString();
			if (!s.equals(""))
				predicados.add(s);
			i++;
		}
		Vector<Object> v = new Vector<Object>();
		v.add(predicados);
		v.add(this.atributo);
		controlador.mensajeDesde_GUI(TC.GUIPonerRestriccionesAAtributo_Click_BotonAceptar, v);
		this.setInactiva();
	}                                       
		
	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setInactiva();
	}
	
	private void botonNuevaActionPerformed(ActionEvent evt) {
		tabla.editCellAt(0,0);
		String[][] items = new String[tabla.getRowCount()+1][1];
		int i=0;
		while (i<tabla.getRowCount()){
			items[i][0]=tabla.getValueAt(i,0).toString();
			i++;
		}
		items[tabla.getRowCount()][0]="";
		TableModel tablaModel = 
			new DefaultTableModel(
				items,
				new String[] { Lenguaje.text(Lenguaje.RESTRICTIONS)+":" });
		tabla.setModel(tablaModel);
	}
	
	private void botonEliminarActionPerformed(ActionEvent evt) {
		try{
			int selec =tabla.getSelectedRow();
			String[][] items = new String[tabla.getRowCount()-1][1];
			int i=0;
			int j=0;
			while (i<tabla.getRowCount()){
				if (selec!=i){
					items[j][0]=tabla.getValueAt(i,0).toString();
					j++;
				}
				i++;
			}
			TableModel tablaModel = 
				new DefaultTableModel(
						items,
						new String[] { Lenguaje.text(Lenguaje.RESTRICTIONS)+":" });
			tabla.setModel(tablaModel);
			
		}catch(Exception e){
			System.out.println("no hay ninguna seleccionada");
		}
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
	
	
		
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.centraEnPantalla();
		this.setTitle(Lenguaje.text(Lenguaje.ADD_RESTRICTIONS) + atributo.getNombre());
		muestraRestricciones();
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         botonNueva.grabFocus();
	     }
	 };
	
	public void setInactiva(){
		this.setVisible(false);
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
	
	private void muestraRestricciones(){
		String[][] items = new String[atributo.getListaRestricciones().size()][1];
		int i=0;
		while (i<atributo.getListaRestricciones().size()){
			items[i][0]=atributo.getListaRestricciones().get(i).toString();
			i++;
		}
		TableModel tablaModel = 
			new DefaultTableModel(
					items,
					new String[] { Lenguaje.text(Lenguaje.RESTRICTIONS)+":" });
		tabla.setModel(tablaModel);
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
	
	public void setAtributo(TransferAtributo atributo){
		this.atributo = atributo;
	}
	
}
