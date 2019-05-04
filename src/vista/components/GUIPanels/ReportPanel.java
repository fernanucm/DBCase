package vista.components.GUIPanels;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import vista.tema.Theme;
/*
 * 
 * Es un JTextPanel con estilo personalizado
 * 
 * */
@SuppressWarnings("serial")
public class ReportPanel extends JTextPane{

	private JScrollPane scroll;
	private Theme theme = Theme.getInstancia();
	public ReportPanel() {
		setContentType("text/html");
		setBorder(null);
		//css
		setText("<style>"
				+ "body{background-color:"+theme.background().hexValue()+";margin:0}"
				+ "h2{padding-left:15px;font-size:18px}"
				+ "h3{padding-left:15px;font-size:14px}"
				+ "li{padding-top:10px}"
				+ ".card{border:1px solid "+theme.toolBar().hexValue()+";padding:5px;background:"+theme.control().hexValue()+";margin:15px;}"
				+ "p{padding-left:30px;color:"+theme.paragraph().hexValue()+"}"
				+ ".warning{background:"+theme.entity().hexValue()+";padding:5px;color:black;margin:15px;}"
				+ ".error{background:"+theme.relation().hexValue()+";padding:5px;color:white;margin:15px;}"
				+ "</style><p></p>");
		scroll = new JScrollPane(this);
	}
	
	public JScrollPane getPanel() {
		return scroll;
	}
	
	public void goToTop() {
		this.setCaretPosition(0);
	}
	
	@Override
	public String getText() {
		String text = super.getText();
		text = text.replaceAll("\n","");
		text = text.replaceAll("<h2>","\n#");
		text = text.replaceAll("</h2>", "\n");
		text = text.replaceAll("</p>", "\n");
		text = text.replaceAll("\\<.*?>","");
		text = text.replaceAll("&gt;",">");
		text = text.replaceAll("(?s)<!--.*?-->", "");
		text = text.replaceAll("[ ]{2,}","");
		text = text.trim();
		text = text.replaceAll("\\v\\v","");
		return text;
	}
	
	public String getInstrucciones() {
		String text = getText();
		text = text.replaceAll("#[^\n]*","");
		text = text.replaceAll("\n","");
		text = text.replaceAll(";",";\n");
		return text;
	}
}
