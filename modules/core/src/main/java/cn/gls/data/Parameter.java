package cn.gls.data;
/**
 * @ClassName: Parameter.java
 * @Description  键值对类
 * @Date  2012-5-25
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-25
 */
public class Parameter implements Comparable<Parameter> {
	/** The first object. */
	private volatile String first;

	/** The second object. */
	private volatile String second;

	public String getName() {
		return getFirst();
	}

	public void setName(String name) {
		setFirst(name);
	}

	public String getValue() {
		return getSecond();
	}

	public void setValue(String value) {
		setSecond(value);
	}

	public Parameter() {
		this(null, null);
	}

	public Parameter(String first, String second) {
		super();
		this.first = first;
		this.second = second;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public int compareTo(Parameter o) {
		return getName().compareTo(o.getName());
	}
}
