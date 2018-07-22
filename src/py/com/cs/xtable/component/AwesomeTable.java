package py.com.cs.xtable.component;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import py.com.cs.xtable.table_model.GenericTableModel;
import py.com.cs.xtable.xutil.Comparison;

public class AwesomeTable extends JTable {

	private static final long serialVersionUID = -1835257686646299108L;
	private GenericTableModel model;
	private List<Map<String, Object>> conditions = new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>> combinedConditions = new ArrayList<Map<String,Object>>();
	private Color ccColor = Color.blue;
	private int widthArray[];
	
	public AwesomeTable() {
		this.model = new GenericTableModel();
		this.setModel(this.model);
	}
	
	public void config(String[] columns) {
		model.setColumns(getAttributes(columns));
		model.setColumnsNames(getColumns(columns));
	}
	
	public void setData(List<?> list) {
		model.setList(list);
		model.fireTableStructureChanged();
	}
	
	public void addCondition(int columnIndex, Color color, Comparison comparison, Object value1, Object value2) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", columnIndex);
		map.put("color", color);
		map.put("com", comparison);
		map.put("v1", value1);
		map.put("v2", value2);
		conditions.add(map);
	}
	
	public void addCondition(int columnIndex, Color color, Comparison comparison, Object value1) {
		addCondition(columnIndex, color, comparison, value1, null);
	}
	
	public void addCombinedCondition(int columnIndex , Comparison comparison, Object value1, Object value2) {
		Map<String, Object> map = new HashMap<>();
		map.put("index", columnIndex);
		map.put("com", comparison);
		map.put("v1", value1);
		map.put("v2", value2);
		combinedConditions.add(map);
	}
	
	public void addCombinedCondition(int columnIndex, Comparison comparison, Object value1) {
		addCombinedCondition(columnIndex, comparison, value1, null);
	}

	private String[] getColumns(String[] columns) {
		String newColumns[] = new String[columns.length];
		for (int i = 0; i < newColumns.length; i++) {
			String[] parts = columns[i].split("=>");
			if(parts.length > 1) newColumns[i] = parts[1].toUpperCase();
			else {
				parts = parts[0].split("(?=\\p{Upper})");
				for (int j = 0; j < parts.length; j++) {
					if(j==0) newColumns[i] = parts[j].toUpperCase();
					else newColumns[i] = newColumns[i]+" "+parts[j].toUpperCase();
				}
			}
		}
		return newColumns;
	}

	private String[] getAttributes(String[] columns) {
		String attributes[] = new String[columns.length];
		for (int i = 0; i < attributes.length; i++) {
			String[] parts = columns[i].split("=>");
			attributes[i] = parts[0];
		}
		return attributes;
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		Component c = super.prepareRenderer(renderer, row, column);
		if (row == 0) {
			if(column == 0) widthArray = new int[this.getColumnCount()];
			widthArray[column] = 15;
		}
		widthArray[column] = Math.max(c.getPreferredSize().width +1 , widthArray[column]);
		
		if (row == this.getRowCount()-1) {
			columnModel.getColumn(column).setPreferredWidth(widthArray[column]);
		}
		if(conditions.size()>0) checkConditios(c, row, false,conditions);
		if(combinedConditions.size()>0) checkConditios(c, row, true, combinedConditions);
		return c;
	}	

	private void checkConditios(Component comp, int row, boolean combined, List<Map<String, Object>> conditions) {
		Color color = Color.black;
		int aux = 0;
		for (int i = 0; i < conditions.size(); i++) {
			Comparison comparison = (Comparison) conditions.get(i).get("com");
			Object colValue = model.getColumnValue(row, conditions.get(i).get("index"));
			Object v1 = null;
			Object v2 = null;
			if(conditions.get(i).get("v1").getClass()==Comparison.Self.class){
				Object c = ((Comparison.Self) conditions.get(i).get("v1")).getColumn();
				v1 = model.getColumnValue(row, c);
			}else v1 = conditions.get(i).get("v1");
			boolean result;
			if (comparison == Comparison.BETWEEN) {
				if(conditions.get(i).get("v2").getClass()==Comparison.Self.class){
					Object c = ((Comparison.Self) conditions.get(i).get("v2")).getColumn();
					v2 = model.getColumnValue(row, c);
				}else v2 = conditions.get(i).get("v2");
				result = comparison.compare(colValue,v1,v2);
			}else{
				result = comparison.compare(colValue,v1);
			}
			
			if(result && !combined) color = (Color) conditions.get(i).get("color");
			if(result && combined) aux ++;
		}
		
		comp.setForeground((aux > 0 && aux == combinedConditions.size())?ccColor:color);
	}
	public void setCombinedConditionColor(Color color) {
		this.ccColor = color;
	}
}
