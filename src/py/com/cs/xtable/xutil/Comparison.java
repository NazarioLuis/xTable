package py.com.cs.xtable.xutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum Comparison {
    EQ("=") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) == 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	},
    NE("!=") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) != 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	},
    GTE(">=") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) >= 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	}, 
    GT(">") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) > 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	}, 
    LT("<") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) < 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	}, 
    LTE("<=") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return a.compareTo(b) <= 0;
		}

		@Override
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return false;
		}
	}, 
    BETWEEN("[]") {
		@Override
		protected boolean apply(Comparable<Object> a, Object b) {
			return false;
		}
		
		protected boolean apply(Comparable<Object> a, Object b, Object c) {
			return a.compareTo(b) >= 0 && a.compareTo(c) <= 0;
		}
		
	};

    private final String value;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
    
    public String getValue() {
		return value;
	}

    private Comparison(String text) {
        this.value = text;
    }

    protected abstract boolean apply(Comparable<Object> a, Object b);
    
    protected abstract boolean apply(Comparable<Object> a, Object b, Object c);
    
    @SuppressWarnings("unchecked")
	public Comparable<Object> getComparable(Object o){
		return (Comparable<Object>) o;
    }

	public boolean compare(Object a, Object b) {
		if(a.getClass() != Date.class && a.getClass() == WraperUtil.wrap(b.getClass())){
			return apply(getComparable(a), b);
		}else if(a.getClass() == Double.class || a.getClass() == Float.class){
			return apply(getComparable(a),getDoubleValue(b));
		}else if(a.getClass() == Integer.class){
			return apply(getComparable(a),getIntegerValue(b));
		}else if(a.getClass() == Long.class){
			return apply(getComparable(a),getLongValue(b));
		}else if(a.getClass() == Boolean.class){
			return apply(getComparable(a),getBooleanValue(b));
		}else if(a.getClass() == Date.class){
			return apply(getComparable(getDateOnly(b)),getDateOnly(b));
		}else{
			return apply(getComparable(a.toString()),b.toString());
		}
	}
	
	public boolean compare(Object a, Object b, Object c) {
		if(a.getClass() != Date.class && a.getClass() == WraperUtil.wrap(b.getClass()) && a.getClass() == WraperUtil.wrap(c.getClass())){
			return apply(getComparable(a), b , c);
		}else if(a.getClass() == Double.class || a.getClass() == Float.class){
			return apply(getComparable(a),getDoubleValue(b),getDoubleValue(c));
		}else if(a.getClass() == Integer.class){
			return apply(getComparable(a),getIntegerValue(b),getIntegerValue(c));
		}else if(a.getClass() == Long.class){
			return apply(getComparable(a),getLongValue(b),getLongValue(c));
		}else if(a.getClass() == Boolean.class){
			return apply(getComparable(a),getBooleanValue(b),getBooleanValue(c));
		}else if(a.getClass() == Date.class){
			return apply(getComparable(getDateOnly(b)),getDateOnly(b),getDateOnly(c));
		}else{
			return apply(getComparable(a.toString()),b.toString(),c.toString());
		}
	}
	
	protected Double getDoubleValue(Object o) {
    	try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	protected Long getLongValue(Object o) {
    	try {
			return Long.parseLong(o.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	protected Integer getIntegerValue(Object o) {
    	try {
			return Integer.parseInt(o.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	protected Boolean getBooleanValue(Object o) {
    	try {
			return Boolean.parseBoolean(o.toString());
		} catch (Exception e) {
			return null;
		}
	}
     
    protected Date getDateOnly(Object o) {
    	String str = null;
    	if(o.getClass() == Date.class){
    		str = dateFormat.format(o);
    	}else{
    		str = o.toString();
    	}
    	try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			return null;
		}		
	}
    
}
