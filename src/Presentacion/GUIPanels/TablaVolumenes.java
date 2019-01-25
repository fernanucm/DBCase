package Presentacion.GUIPanels;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class TablaVolumenes extends JTable{

	public void refresh(String[][] s){
		((MyTableModel) this.getModel()).refresh(s);
		((MyTableModel) this.getModel()).fireTableDataChanged();
	}
	
	public TablaVolumenes(){
		super(new MyTableModel());
		this.setRowHeight(30);
		this.putClientProperty("terminateEditOnFocusLost", false);
	}
}
