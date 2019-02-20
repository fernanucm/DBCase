package Presentacion.GUIPanels;

import javax.swing.JTextPane;

import Presentacion.Theme.Theme;
/*
 * 
 * Es un JTextPanel con estilo personalizado
 * 
 * */
@SuppressWarnings("serial")
public class reportPanel extends JTextPane{

	public reportPanel(Theme theme) {
		this.setContentType("text/html");
		//css
		this.setText("<style>.blue{color:#004e5a;}strong{color:#f6323e;}body{background-color:#f4f4f4;margin:0}"
				+ "h1{padding:10;padding-left:30px;"
				+ "background-color:#004e5a;color:white;"
				+ "font-size:20px}div{padding:15px}"
				+ "p{font-family:monaco;padding-left:30px}</style>");
	}
	
}
