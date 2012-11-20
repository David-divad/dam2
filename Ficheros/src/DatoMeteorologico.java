import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Encapsula el comportamiento comun (fechas) para todos los datos metereologicos
 * que estamos manejando. 
 */
public abstract class DatoMeteorologico implements LectorVSC.DatoVSC {

	protected Date fecha;
	
	public boolean equals(Object o) 
	{
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof DatoMeteorologico)) return false;

		DatoMeteorologico d = (DatoMeteorologico) o;

		return fecha.equals(d.fecha);
	}
	
	public void mapeaCampos(ArrayList<String> campos)
	{
		mapeaFechaHora(campos.get(Encabezados.FECHAHORA));
	}
	
	/*
	 * Convierte el formato de fecha-hora del fichero de entrada. 
	 */
	protected void mapeaFechaHora(String fechahora)
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		/* formato de entrada: '07/03/2012 00:00' */
		dt.applyPattern("dd/MM/yyyy HH:mm");
		
		try {
			fecha = dt.parse(fechahora);
			
		} catch (ParseException e) {
			throw new NumberFormatException("formato fecha incorrecto");
		}
	}
	
	public void copia(DatoMeteorologico t)
	{	
		fecha = (Date) t.fecha.clone();
	}
	
	public String toString()
	{
		return fecha.toString();
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public boolean esMismoDia(DatoMeteorologico d)
	{
		GregorianCalendar calendario1 = new GregorianCalendar();
		GregorianCalendar calendario2 = new GregorianCalendar();
		
		calendario1.setTime(fecha);
		calendario2.setTime(d.fecha);

		if (calendario1.get(Calendar.YEAR) == calendario2.get(Calendar.YEAR)) {
			if (calendario1.get(Calendar.MONTH) == calendario2.get(Calendar.MONTH)) {
				if (calendario1.get(Calendar.DAY_OF_MONTH) == calendario2.get(Calendar.DAY_OF_MONTH)) {
					return true;
				}	
			}
		}
		
		return false;
	}
	
	public String geFecha()
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		dt.applyPattern("dd/MM/yyyy");
		
		return dt.format(fecha);
	}
	
	public String getHora()
	{
		SimpleDateFormat dt = new SimpleDateFormat();
		
		dt.applyPattern("HH:mm");
		
		return dt.format(fecha);
	}
}
