package vista.GUIFrames;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controlador.Controlador;
import controlador.TC;
import modelo.tools.ImagePath;
import modelo.transfers.TransferAtributo;
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
public class GUI_TablaUniqueRelacion extends javax.swing.JDialog  implements KeyListener, MouseListener {


	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private TransferRelacion relacion;
	// Variables declaration - do not modify
	private JButton botonCancelar;
	private JLabel labelIcono;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JPanel panelBotones;
	private JLabel jLabel1;
	private JButton botonNueva;
	private JButton botonEliminar;
	private JButton botonAceptar;
	private JTable tablaConjuntos;
	private Vector<JButton> botones;
	// End of variables declaration

	public GUI_TablaUniqueRelacion() {
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
			labelIcono.setIcon(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.RATON)));
			labelIcono.setBounds(12, 21, 100, 100);
		}
		{
			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1);
			jScrollPane1.setBounds(149, 34, 310, 92);
			{
				TableModel tablaModel = 
					new DefaultTableModel(
							new String[][] { { "" } },
							new String[] { Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE)+":" });
				tablaConjuntos = new JTable();
				jScrollPane1.setViewportView(tablaConjuntos);
				tablaConjuntos.setModel(tablaModel);
				tablaConjuntos.setBounds(58, 16, 307, 81);
				tablaConjuntos.setPreferredSize(new java.awt.Dimension(292, -6));
			}
		}
		{
			jScrollPane2 = new JScrollPane();
			this.panelBotones = new JPanel();
			//jScrollPane2.add(this.panelBotones);
			getContentPane().add(jScrollPane2);
			jScrollPane2.setBounds(20, 196, 250, 49);
			
		}
		{
			botonAceptar = new JButton();
			getContentPane().add(botonAceptar);
			botonAceptar.setText(Lenguaje.getMensaje(Lenguaje.ACCEPT));
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
			botonAceptar.setMnemonic(Lenguaje.getMensaje(Lenguaje.ACCEPT).charAt(0));
		}
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.getMensaje(Lenguaje.CANCEL));
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
			botonCancelar.setMnemonic(Lenguaje.getMensaje(Lenguaje.CANCEL).charAt(0));
		}
		{
			botonNueva = new JButton();
			getContentPane().add(botonNueva);
			botonNueva.setText(Lenguaje.getMensaje(Lenguaje.NEW));
			botonNueva.setBounds(288, 150, 80, 25);
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
			botonNueva.setMnemonic(Lenguaje.getMensaje(Lenguaje.NEW).charAt(0));
		}
		{
			botonEliminar = new JButton();
			getContentPane().add(botonEliminar);
			botonEliminar.setText(Lenguaje.getMensaje(Lenguaje.DELETE));
			botonEliminar.setBounds(379, 150, 80, 25);
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
			botonEliminar.setMnemonic(Lenguaje.getMensaje(Lenguaje.DELETE).charAt(0));
		}
		{
			jLabel1 = new JLabel();
			getContentPane().add(jLabel1);
			jLabel1.setText(Lenguaje.getMensaje(Lenguaje.ATTRIBUTES)+":");
			jLabel1.setBounds(24, 158, 88, 17);
		}

		this.addKeyListener(this);
		TableModel tablaModel = 
			new DefaultTableModel(
					new String[][] { { "" } },
					new String[] { Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE)+":" });
		tablaConjuntos = new JTable();
		jScrollPane1.setViewportView(tablaConjuntos);
		tablaConjuntos.setModel(tablaModel);
		this.addKeyListener(this);
	}

	/*
	 * Oyentes de los botones 
	 */
	private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		Vector<String> predicados = new Vector<String>();
		int i=0;
		boolean correcto = true;
		while (i<tablaConjuntos.getRowCount()&&correcto){
			String s= tablaConjuntos.getValueAt(i, 0).toString();
			if (!s.startsWith("UNIQUE(")||!s.endsWith(")"))
				correcto=false;
			i++;
		}
		if(correcto){ 
			i=0;
			while (i<tablaConjuntos.getRowCount()&&correcto){
				String s= tablaConjuntos.getValueAt(i, 0).toString();
				//comprbamos que los atributos elegidos pertenezcan a la relacion
				try{
					s=s.substring(7,s.length()-1);
					correcto=comprobarAtributos(s);
				}catch(Exception e){
					correcto=false;
				}
				
				s=s.replaceAll("  ","");
				s=s.replaceAll(",",", ");
				predicados.add(s);
				i++;
			}
			if(!correcto){
				JOptionPane.showMessageDialog(null, Lenguaje.getMensaje(Lenguaje.UNIQUE_ERROR), Lenguaje.getMensaje(Lenguaje.ERROR), 0);
			}else{
				Vector<Object> v = new Vector<Object>();
				v.add(predicados);
				v.add(this.relacion);
				controlador.mensajeDesde_GUI(TC.GUIPonerUniquesARelacion_Click_BotonAceptar, v);
				actualizarUniquesUnitarios();
				this.setInactiva();
			}
		}
	}                                       
		
	private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                              
		this.setInactiva();
	}
	
	private void botonNuevaActionPerformed(ActionEvent evt) {
		String[][] items = new String[tablaConjuntos.getRowCount()+1][1];
		int i=0;
		while (i<tablaConjuntos.getRowCount()){
			items[i][0]=tablaConjuntos.getValueAt(i,0).toString();
			i++;
		}
		items[tablaConjuntos.getRowCount()][0]="UNIQUE(...)";
		TableModel tablaModel = 
			new DefaultTableModel(
				items,
				new String[] { Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE)+":" });
		tablaConjuntos.setModel(tablaModel);
	}
	
	private void botonEliminarActionPerformed(ActionEvent evt) {
		try{
			int selec =tablaConjuntos.getSelectedRow();
			String[][] items = new String[tablaConjuntos.getRowCount()-1][1];
			int i=0;
			int j=0;
			while (i<tablaConjuntos.getRowCount()){
				if (selec!=i){
					items[j][0]=tablaConjuntos.getValueAt(i,0).toString();
					j++;
				}
				i++;
			}
			TableModel tablaModel = 
				new DefaultTableModel(
						items,
						new String[] { Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE)+":" });
			tablaConjuntos.setModel(tablaModel);
			
		}catch(Exception e){
			System.out.println("no hay ninguna seleccionada");
		}
		
	}
	
			
	/*
	 * Activar y desactivar el dialogo
	 */
	public void setActiva(){
		this.centraEnPantalla();
		this.setTitle(Lenguaje.getMensaje(Lenguaje.ADD_UNIQUE) +" of "+ relacion.getNombre());
		muestraEntradas();
		muestraAtributos();
		SwingUtilities.invokeLater(doFocus);
		this.setVisible(true);
	}
	
	private Runnable doFocus = new Runnable() {
	     public void run() {
	         for(int i=0; i<botones.size();i++)
	        	 botones.get(i).grabFocus();
	    	 botonNueva.grabFocus();   
	     }
	 };
	
	public void setInactiva(){
		while(!botones.isEmpty())
			botones.remove(0);
		this.setVisible(false);
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
	
	private boolean comprobarAtributos(String s) {
		int pos0 = 0;
		int pos1 = s.indexOf(",");
		while(pos0 != -1 ){
			String subS;
			if (pos1 !=-1){ 
				subS = s.substring(pos0, pos1);
			}
			else{
				subS = s.substring(pos0, s.length());
			}
			subS = subS.replaceAll(" ","");
			subS = subS.replaceAll(",","");
			pos0 = pos1;
			pos1 = s.indexOf (",",pos1+1);
			//comprobar que subS sea un atributo
			boolean esAtributo=false;
			for(int i=0;i<botones.size();i++){
				if(subS.equals(botones.get(i).getText())){  
					esAtributo=true;
				}
			}
			if(!esAtributo)
				return false;			
		}
		return true;
	}
	
	private void muestraEntradas(){
		int i=0;
		Vector<String> unitarios = new Vector<String>();
		
		String[][] items = new String[relacion.getListaUniques().size()+unitarios.size()][1];
		
		i=0;
		while(i<+unitarios.size()){
			items[i][0]="UNIQUE("+unitarios.get(i).toString()+")";
			i++;
		}
		
		i=0;
		while (i<relacion.getListaUniques().size()){
			items[i+unitarios.size()][0]="UNIQUE("+relacion.getListaUniques().get(i).toString()+")";
			i++;
		}
		TableModel tablaModel = 
			new DefaultTableModel(
					items,
					new String[] { Lenguaje.getMensaje(Lenguaje.TABLE_UNIQUE)+":" });
		tablaConjuntos.setModel(tablaModel);
	}
	
	private void muestraAtributos(){
		botones = new Vector<JButton>();
		int i=0;
		
		while (i<relacion.getListaAtributos().size()){
			final JButton boton1 = new JButton();
				
			boton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					ponAtributo(boton1.getText());
				}
			});
			boton1.setText(controlador.getTheServiciosAtributos().getNombreAtributo(Integer.parseInt(relacion.getListaAtributos().get(i).toString())));
			boton1.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			this.botones.add(boton1);
			boton1.setBounds(0,25*i, 80, 25);
			this.panelBotones.add(boton1);
			boton1.setBounds(0,25*i, 80, 25);
			i++;
		}
		jScrollPane2.setBounds(20, 197, 236, 43);
		//panelBotones.setBounds(0, 0, 25*i,80); 
		jScrollPane2.getViewport().setView(panelBotones);
	}

	private void ponAtributo(String text) {
		try{
			int row =tablaConjuntos.getSelectedRow();
			int col =tablaConjuntos.getSelectedColumn();
			String s = tablaConjuntos.getModel().getValueAt(row, col).toString();
			s=s.substring(0, s.length()-1);
			if(s.contains(text)){
				int pos = s.indexOf(text);
				String s1 =s.substring(0,pos);
				String s2;
				if(s.indexOf(",",pos)>0){
					s2 = s.substring(s.indexOf(",",pos)+1,s.length());
					s= s1+s2+")";
				}else{
					s2 = "";
					if(s1.lastIndexOf(",")>0)
						s1= s1.substring(0,s.lastIndexOf(","));
					s= s1+s2+")";
				}
				s= s.replaceAll(" ", "");
				s= s.replaceAll(",",", ");
			}else{
				if (s.endsWith("...")){
					s=s.substring(0, s.length()-3);
				}
				else{
					if(!s.endsWith("("))
						s=s+", ";
				}
				s= s +text +")"; 
				s= s.replaceAll(" ", "");
				s= s.replaceAll(",",", ");
			}
			tablaConjuntos.getModel().setValueAt(s, row, col);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,Lenguaje.getMensaje(Lenguaje.ERROR_TABLE),
					Lenguaje.getMensaje(Lenguaje.DBCASE),JOptionPane.ERROR_MESSAGE);
		}
	}

	@SuppressWarnings("unchecked")
	private void actualizarUniquesUnitarios(){
		Vector<String> vUniques = this.relacion.getListaUniques();
		Vector<String> vAtributos = this.relacion.getListaAtributos();
		controlador.getTheServiciosAtributos().ListaDeAtributos();
		for (int i=0; i<vUniques.size();i++){
			for (int j=0; j<vAtributos.size(); j++)
				if(vUniques.get(i).equals(controlador.getTheServiciosAtributos().getNombreAtributo(Integer.parseInt(vAtributos.get(j))))){
					if(!controlador.getTheServiciosAtributos().idUnique(Integer.parseInt(vAtributos.get(j)))){
						int numAtributo=-1;
						for (int k=0; k<controlador.getTheGUIPrincipal().getListaAtributos().size();k++){
							String nombre= controlador.getTheServiciosAtributos().getNombreAtributo(Integer.parseInt(vAtributos.get(j)));
							if(((TransferAtributo)controlador.getTheGUIPrincipal().getListaAtributos().get(k)).getNombre().equals(nombre)){
								numAtributo=k;	
							}
						}
						final TransferAtributo atributo = (TransferAtributo)controlador.getTheGUIPrincipal().getListaAtributos().get(numAtributo);
						TransferAtributo clon_atributo = atributo.clonar();
						controlador.getTheServiciosAtributos().editarUniqueAtributo(clon_atributo);
					}
				}
		}
		for (int i=0; i<vAtributos.size(); i++){
			if (controlador.getTheServiciosAtributos().idUnique(Integer.parseInt(vAtributos.get(i)))){
				boolean encontrado=false;
				for (int j=0; j<vUniques.size(); j++){
					if(vUniques.get(j).equals(controlador.getTheServiciosAtributos().getNombreAtributo(Integer.parseInt(vAtributos.get(i))))){
						encontrado=true;
					}
				}
				if(!encontrado){
					//hay que arreglar uno unique unitario que acabas de quitar
					int numAtributo=-1;
					for (int k=0; k<controlador.getTheGUIPrincipal().getListaAtributos().size();k++){
						String nombre= controlador.getTheServiciosAtributos().getNombreAtributo(Integer.parseInt(vAtributos.get(i)));
						if(((TransferAtributo)controlador.getTheGUIPrincipal().getListaAtributos().get(k)).getNombre().equals(nombre)){
							numAtributo=k;	
						}
					}
					final TransferAtributo atributo = (TransferAtributo)controlador.getTheGUIPrincipal().getListaAtributos().get(numAtributo);
					TransferAtributo clon_atributo = atributo.clonar();
					controlador.getTheServiciosAtributos().editarUniqueAtributo(clon_atributo);
				}
			}
		}
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
	
	public void setRelacion(TransferRelacion relacion){
		this.relacion = relacion;
	}
	
	
}
