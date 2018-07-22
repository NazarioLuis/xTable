package py.com.cs.xtable.table_model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import py.com.cs.xtable.xutil.WraperUtil;

public class GenericTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 5623346388561641297L;

	private String columnsNames[] = {};
	private String columns[] = {};
	private List<?> list = new ArrayList<>();
	
	public void setList(List<?> list) {
		this.list = list;
	}
	
	public Object getColumnValue(int r,Object c) {
		return getValue(r, c);
	}
	
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	
	public void setColumnsNames(String[] columnsNames) {
		this.columnsNames = columnsNames;
	}

	@Override
	public String getColumnName(int i) {
		return columnsNames[i];
	}
	
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	@Override
	public Object getValueAt(int r, int c) {
		return getValue(r,c);
	}

	private Object getValue(int r, Object c) {
		try {
			String attrs[] = null;
			if(c.getClass() == String.class) attrs = ((String) c).replace("+", " ").split(" ");
			else attrs = columns[(int) c].replace("+", " ").split(" ");
			
			Object val = null;
			for (int i = 0; i < attrs.length; i++) {
				Field field = list.get(r).getClass().getDeclaredField(attrs[i]);
				field.setAccessible(true);
				if(val == null) val = field.get(list.get(r));
				else val += " " + field.get(list.get(r));
			}
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			return new Object();
		}
	}

	@Override
	public Class<?> getColumnClass(int c) {
		if(list !=null && list.size()>0){
			try {
				String attrs[] = columns[c].replace("+", " ").split(" ");
				Class<?> columnType = String.class;
				if(attrs.length == 1) {
					Field field = list.get(0).getClass().getDeclaredField(attrs[0]);
					field.setAccessible(true);
					columnType = field.getType();
				}
				columnType = columnType.isPrimitive() ? WraperUtil.wrap(columnType) : columnType;
				return columnType;
			} catch (Exception e) {
				return Object.class;
			}
		}else{
			return Object.class;
		}
	}
	
}
