package Presentacion.GUIPanels;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TablaVolumenes extends JTable{

	public void refresh(String[][] s){
		for (int row = 0; row < s.length; row ++)
		    for (int col = 0; col < s[0].length; col++)
		    	this.setValueAt(s[row][col], row, col);
	}
	
	public TablaVolumenes(){
		super(new DefaultTableModel() { 
            
            @Override 
            public int getColumnCount() { 
                return 3; 
            } 

            @Override 
            public String getColumnName(int index) { 
            	String[] campos = {"Tabla", "Volumen", "Frecuencia"};
                return campos[index]; 
            } 
            @Override
            public boolean isCellEditable(int row, int col) {
                 if(col==0)return false;
                 return true;
            }

            @Override
            public int getRowCount() {
                 return 30;
            }
        });
		this.setRowHeight(30);
	}
	
	
}

