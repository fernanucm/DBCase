package Presentacion.GUIPanels;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TablaVolumenes extends JTable{

	public void refresh(String[][] s){
		((MyTableModel) this.getModel()).refresh(s);
		((MyTableModel) this.getModel()).fireTableDataChanged();
	}
	
	public TablaVolumenes(){
		super(new MyTableModel());
		this.setRowHeight(30);
		this.getTableHeader().setReorderingAllowed(false);
	}
}

@SuppressWarnings("serial")
class MyTableModel extends AbstractTableModel{
	private String[] columnas = {"Tabla", "Volumen", "Frecuencia"};
	private ArrayList<ArrayList<String>> data;
	
	public MyTableModel(){
		super();
		data = new ArrayList<ArrayList<String>>();
	}
	public void refresh(String[][] s) {
		int size = s.length;
		data.clear();
		
		for (int row = 0; row < size; row ++) {
			ArrayList<String> Arr1 = new ArrayList<String>();
		    for (int col = 0; col < s[0].length; col++) Arr1.add(s[row][col]);
		    	data.add(Arr1);
		}
	}
	
	@Override 
    public String getColumnName(int index) { 
        return columnas[index]; 
    }
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
    public boolean isCellEditable(int row, int col) {
         if(col==0)return false;
         return true;
    }
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
}
