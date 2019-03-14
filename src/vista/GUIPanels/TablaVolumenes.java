package vista.GUIPanels;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class TablaVolumenes extends JTable{
	
	public TablaVolumenes(){
		super(new MyTableModel());
		this.setRowHeight(30);
		this.putClientProperty("terminateEditOnFocusLost", false);
	}
	
	public void refresh(String[][] s){
		((MyTableModel) this.getModel()).refresh(s);
		((MyTableModel) this.getModel()).fireTableDataChanged();
	}
}
