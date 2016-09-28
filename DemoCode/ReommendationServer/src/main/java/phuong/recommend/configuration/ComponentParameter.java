package phuong.recommend.configuration;

import java.awt.event.ComponentEvent;

public class ComponentParameter 
{
	String _name;
	String _value;
	
	// Constructor
	public ComponentParameter(){}
	public ComponentParameter(String name, String value)
	{
		this._name=name;
		this._value=value;
	}
	public ComponentParameter(String name)
	{
		this._name=name;
	}
	
	
	
	//GETER SETER
	public String getName() {
		return _name;
	}
	public void setName(String _name) {
		this._name = _name;
	}
	public String getValue() {
		return _value;
	}
	public void setValue(String _value) {
		this._value = _value;
	}
	
	

}
