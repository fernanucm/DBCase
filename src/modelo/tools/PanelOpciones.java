
package modelo.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import modelo.lenguaje.Lenguaje;


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
public class PanelOpciones extends javax.swing.JDialog implements KeyListener {
	{
		//Set Look & Feel
		try {
			//javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static final long serialVersionUID = 1L;
	
	private int respuesta;
	private JTextPane pregunta;
	private JButton botonNo;
	private JButton botonSi;
	private JButton botonCancelar;
	private JLabel labelIcono;
	private boolean cancelar= false;

	
	public PanelOpciones(){
		
		this.initComponents();
		
	}
		
	private void initComponents() {
		getContentPane().setLayout(null);
		//setTitle(titulo);
		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		{
			pregunta = new JTextPane();
			getContentPane().add(pregunta);
			//pregunta.setText(mensaje);
			pregunta.setBounds(99, 30, 274, 48);
			pregunta.setEditable(false);
			pregunta.setOpaque(false);
			pregunta.setFocusable(false);
		}
		{
			labelIcono = new JLabel();
			getContentPane().add(labelIcono);
			labelIcono.setBounds(20, 12, 61, 80);
		}
		{
			botonSi = new JButton();
			getContentPane().add(botonSi);
			botonSi.setText(Lenguaje.text(Lenguaje.YES));
			botonSi.setBounds(230, 105, 80, 25);
			botonSi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonSiActionPerformed(evt);
				}

			});
			botonSi.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonSiActionPerformed(null);}
					else if(e.getKeyCode()==27){if (cancelar)botonCancelarActionPerformed(null);
												else botonNoActionPerformed(null);}
					else if(e.getKeyCode()==39){botonNo.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonSi.addKeyListener(general);
		}
		{
			botonNo = new JButton();
			getContentPane().add(botonNo);
			botonNo.setText(Lenguaje.text(Lenguaje.NO));
			botonNo.setBounds(321, 105, 80, 25);
			botonNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonNoActionPerformed(evt);
				}
			});
			botonNo.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonNoActionPerformed(null);}
					else if(e.getKeyCode()==27){if (cancelar)botonCancelarActionPerformed(null);
												else botonNoActionPerformed(null);}
					else if(e.getKeyCode()==37){botonSi.grabFocus();}
					else if(e.getKeyCode()==39){if (cancelar)botonCancelar.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonNo.addKeyListener(general);
			
		}
		{
			botonCancelar = new JButton();
			getContentPane().add(botonCancelar);
			botonCancelar.setText(Lenguaje.text(Lenguaje.CANCEL));
			botonCancelar.setBounds(286, 90, 80, 25);
			botonCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					botonCancelarActionPerformed(evt);
				}
			});
			botonCancelar.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==10){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==27){botonCancelarActionPerformed(null);}
					else if(e.getKeyCode()==37){botonNo.grabFocus();}
				}
				public void keyReleased(KeyEvent e) {}
				public void keyTyped(KeyEvent e) {}
			});
			botonCancelar.addKeyListener(general);
			
		}
		this.setSize(391, 158);
		
		this.addKeyListener(this);
		this.addKeyListener(general);
	}

	public int setActiva(String mensaje, String titulo, boolean cancelar,Icon icon){
		pregunta.setText(mensaje);
		setTitle(titulo);
		this.labelIcono.setIcon(icon);
		this.cancelar=cancelar;
		if(cancelar){
			botonCancelar.setVisible(true);
			botonCancelar.setFocusable(true);
			botonSi.setBounds(136, 90, 80, 25);
			botonNo.setBounds(195, 90, 80, 25);
		}else{
			botonCancelar.setVisible(false);
			botonCancelar.setFocusable(false);
			botonSi.setBounds(195, 90, 80, 25);
			botonNo.setBounds(286, 90, 80, 25);
		}
		
		
		respuesta =-1;
		this.centraEnPantalla();
		this.setVisible(true);	
				
		return respuesta;
	}
	public void setInactiva(){
		this.setVisible(false);
	}
	
	
	private void botonSiActionPerformed(ActionEvent evt) {
		respuesta = 0;
		setInactiva();
	}
	
	private void botonNoActionPerformed(ActionEvent evt) {
		respuesta = 1;
		setInactiva();
	}
	private void botonCancelarActionPerformed(ActionEvent evt) {
		respuesta = 2;
		setInactiva();
	}
	
	private KeyListener general = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			if(Character.toLowerCase(e.getKeyChar()) == Character.toLowerCase(Lenguaje.text(Lenguaje.YES).charAt(0))){
				botonSiActionPerformed(null);}
			else if(Character.toLowerCase(e.getKeyChar()) == Character.toLowerCase(Lenguaje.text(Lenguaje.NO).charAt(0))){
				botonNoActionPerformed(null);}
			else if (cancelar)
				if(Character.toLowerCase(e.getKeyChar()) == Character.toLowerCase(Lenguaje.text(Lenguaje.CANCEL).charAt(0))){
					botonCancelarActionPerformed(null);}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	};

	
	@Override
	public void keyPressed(KeyEvent e) {		
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	
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