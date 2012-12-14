package org.deiv.meteo;


public class FileFormatException extends java.io.IOException {

	private static final long serialVersionUID = 4550277946412885951L;	
	
	protected Exception motivo;
	
	public FileFormatException(Exception motivo)
	{
		this.motivo = motivo;
	}

	public FileFormatException() 
	{
	}
}