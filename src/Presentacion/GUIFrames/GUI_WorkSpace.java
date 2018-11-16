package Presentacion.GUIFrames;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Controlador.Controlador;
import Controlador.TC;
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
public class GUI_WorkSpace extends javax.swing.JDialog{

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;
	private Controlador controlador;
	private JPanel panelPrincipal;
	private JFileChooser jfc;
	private JLabel jLabel1;
	//private File filetemp;
	//private File fileguardar;
	private int abrir;
	private boolean actuado; //vale true tras guardar o abrir, false si pulsa en cancelar o cierra la ventana
	
	public GUI_WorkSpace() {
		this.initComponents();
	}

	private void initComponents(){
		{
			this.setTitle(Lenguaje.getMensaje(Lenguaje.DBCASE));
			this.setModal(true);
			this.setResizable(false);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource(ImagePath.LOGODBDT)).getImage());

		}
		
		{
			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			getContentPane().add(panelPrincipal, BorderLayout.CENTER);
			panelPrincipal.setPreferredSize(new java.awt.Dimension(545, 318));
			{
				jLabel1 = new JLabel();
				panelPrincipal.add(jLabel1);
				jLabel1.setBounds(12, 12, 521, 14);
			}
			jfc = new JFileChooser();
			panelPrincipal.add(jfc);
			jfc.setBounds(0, 32, 547, 286);
			jfc.setDialogType(2);
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setAcceptAllFileFilterUsed(false);
			XMLFileFilter filter = new XMLFileFilter();
			jfc.addChoosableFileFilter(filter);
			jfc.setApproveButtonText(Lenguaje.getMensaje(Lenguaje.SELECT));
			jfc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jfcActionPerformed(evt);
				}
			});
		}
		this.setSize(553, 354);
		this.setContentPane(panelPrincipal);
	}
	
	/*
	 * Activar y desactivar el dialogo
	 */
	
	//devuelve siempre true, salvo si se ha pulsado en cancelar
	public boolean setActiva(int b){
		actuado=false;
		switch (b){
			case 0:{//iniciar la aplicacion
				try {
					controlador.setFiletemp(File.createTempFile("dbcase", "xml"));
					creaFicheroXML(controlador.getFiletemp());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
						Lenguaje.getMensaje(Lenguaje.ERROR_TEMP_FILE),
						Lenguaje.getMensaje(Lenguaje.DBCASE), JOptionPane.ERROR_MESSAGE);
				}
				String ruta = controlador.getFiletemp().getPath();
				this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_PrimeraSeleccion, ruta);
				break;
			}
			case 1:{//abrir
				jLabel1.setText(Lenguaje.getMensaje(Lenguaje.OPEN)+":");
				abrir=b;
				this.centraEnPantalla();
				this.setVisible(true);	
				break;
			}
			case 2:{//guardar
				jLabel1.setText(Lenguaje.getMensaje(Lenguaje.SAVE)+":");
				
				if(controlador.getFileguardar()==null || !controlador.getFileguardar().exists()){
					abrir=b;
					this.centraEnPantalla();
					this.setVisible(true);	
				}
				else{
					actuado=true;
					guardarProyecto();	
				}
				break;
			}
			case 3:{//guardarComo
				jLabel1.setText(Lenguaje.getMensaje(Lenguaje.SAVE_AS)+":");
				abrir=b;
				this.centraEnPantalla();
				this.setVisible(true);	
				break;
			}
		}
		return actuado;
	}
	
	public void setInactiva(){
		this.setVisible(false);
	}


	/*
	 * Oyente del jfilechooser
	 */
	private void jfcActionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		if (command.equals(JFileChooser.APPROVE_SELECTION)){
			actuado=true;
			switch (abrir){
				case 1:{
					abrirProyecto();
					break;
				}
				case 2:{
					guardarComo();
					break;
				}
				case 3:{
					guardarComo();
					break;
				}
			}
		}
		// Si se ha pulsado el boton cancelar
		else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
			actuado=false;
			this.dispose();
		}
	}
	
	private void abrirProyecto(){
		File f = this.jfc.getSelectedFile();
		//construimos la ruta
		String ruta = f.getPath();
		if (!ruta.endsWith(".xml")){
			ruta = ruta+".xml";
		}
		//si ya existe, 
		if (f.exists()){
			//compruebo que sea un xml de esta aplicación
			if (esValido(f)){
				controlador.setFileguardar(f);
				this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_Click_Abrir, ruta);
			}else{
				JOptionPane.showMessageDialog(null,
						Lenguaje.getMensaje(Lenguaje.WRONG_FILE),
						Lenguaje.getMensaje(Lenguaje.DBCASE), JOptionPane.ERROR_MESSAGE);
			}				
		}else{
			JOptionPane.showMessageDialog(null,
					Lenguaje.getMensaje(Lenguaje.NOT_EXIST_FILE),
					Lenguaje.getMensaje(Lenguaje.DBCASE), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void guardarProyecto(){
		String ruta=controlador.getFileguardar().getAbsolutePath();
		if (!ruta.endsWith(".xml"))
				ruta= ruta+".xml";
		if (controlador.getFileguardar().exists()){
			this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_Click_Guardar, ruta);
		}
		else{
			System.out.println("HAS INTENTADO GUARDAR SOBRE NULL");
		}
	}
	private void guardarComo(){
		File f = this.jfc.getSelectedFile();
		//construimos la ruta
		String ruta = f.getPath();
		if (!ruta.endsWith(".xml")){
			ruta = ruta+".xml";
		}
		//si ya existe, 
		if (f.exists()){
			this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_Click_Guardar, ruta);
		}
		else{
			//si no existe hay que crear el xml
			//boolean respPersistencia = this.creaFicheroXML(f);
			//if (respPersistencia){
				// Mensaje al controlador indicandole el path
				this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_Click_Guardar, ruta);
			//}
			//else{
				// Mensaje al controlador diciendo que ha habido un error en la creacion de los ficheros
			//	this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_ERROR_CreacionFicherosXML, ruta);
			//}
		}
		f = new File(ruta);
		controlador.setFileguardar(f);
	}
			
	/*GETTERS & SETTERS*/
	public Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	
	public void nuevoTemp(){
		try {
			controlador.setFiletemp(File.createTempFile("dbcase", "xml"));
			creaFicheroXML(controlador.getFiletemp());
			String ruta= controlador.getFiletemp().getAbsolutePath();
			this.controlador.mensajeDesde_GUIWorkSpace(TC.GUI_WorkSpace_Nuevo, ruta);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				Lenguaje.getMensaje(Lenguaje.ERROR_TEMP_FILE),
				Lenguaje.getMensaje(Lenguaje.DBCASE), JOptionPane.ERROR_MESSAGE);
		}
		controlador.setFileguardar(null);
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
	 * METODOS PARA MANEJAR LOS FICHEROS XML
	 */
	class XMLFileFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
		}

		@Override
		public String getDescription() {
			return "xml files";
		}
	}
	private boolean creaFicheroXML(File f){
		FileWriter fw;
		String ruta = f.getPath();
		try {
			fw = new FileWriter(ruta);
			fw.write("<?xml version=" + '\"' + "1.0" + '\"' + " encoding="
					+ '\"' + "utf-8" + '\"' + " ?>" + '\n');
			//"ISO-8859-1"
			fw.write("<Inf_dbcase>" + "\n");
			fw.write("<EntityList proximoID=\"1\">" + "\n" + "</EntityList>"+ "\n");
			fw.write("<RelationList proximoID=\"1\">" + "\n" + "</RelationList>"+ "\n");
			fw.write("<AttributeList proximoID=\"1\">" + "\n" + "</AttributeList>"+ "\n");
			fw.write("<DomainList proximoID=\"1\">" + "\n" + "</DomainList>");
			fw.write("</Inf_dbcase>" +"\n");
			fw.close();
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					Lenguaje.getMensaje(Lenguaje.ERROR_CREATING_FILE) + "\n" +ruta,
					Lenguaje.getMensaje(Lenguaje.DBCASE), JOptionPane.ERROR_MESSAGE);
			return false;
		}		
	}

	private boolean esValido(File f) {
		try{
			Document doc= dameDoc(f);
			//comprobamos que sea de la forma de nuestros ficheros
			NodeList LC = doc.getElementsByTagName("Inf_dbcase");
			if (LC.getLength()!=1)
				return false;
			else{
				if( LC.item(0).getChildNodes().item(1).getNodeName().equals("EntityList") &&
					LC.item(0).getChildNodes().item(3).getNodeName().equals("RelationList") &&
					LC.item(0).getChildNodes().item(5).getNodeName().equals("AttributeList") &&
					LC.item(0).getChildNodes().item(7).getNodeName().equals("DomainList"))
						return true;
				else return false;
			}	
		}catch(Exception e){
			return false;
		}
	}
	private Document dameDoc(File f) {
		Document doc = null;
		DocumentBuilder parser = null;
		try {
			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			parser = factoria.newDocumentBuilder();
			doc = parser.parse(f);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					Lenguaje.getMensaje(Lenguaje.ERROR)+":\n" +
					Lenguaje.getMensaje(Lenguaje.UNESPECTED_XML_ERROR)+" \""+f.getName() ,
					Lenguaje.getMensaje(Lenguaje.DBCASE),
					JOptionPane.ERROR_MESSAGE);
		}
		return doc;
	}
}
