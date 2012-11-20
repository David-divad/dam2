package org.deiv.testsqlite;

import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlmacenPuntuaciones {
	
	static protected final String BBDD_NOMBRE = "Puntuaciones";
	static protected final int    BBDD_VERSION = 1;
	static protected final String TABLA_PUNTUACIONES = "PUNTUACIONES";
	
	/* Consultas DDL */
	static protected final String BBDD_DDL_DROPBBDD = "DROP TABLE IF EXISTS " + TABLA_PUNTUACIONES;
	static protected final String BBDD_DDL_CREATEBBDD = "CREATE TABLE " + TABLA_PUNTUACIONES +
			"(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"puntos INTEGER, " +
			"nombre TEXT, " +
			"fecha LONG)";
	
	protected ConectorBBDD conector;
	private SQLiteDatabase db;

	
	public AlmacenPuntuaciones(Context context)
	{	
		conector = new ConectorBBDD(context);
	}
	
	public void abrir() throws SQLException
	{
		db = conector.getWritableDatabase();
	}

	public void cerrar()
	{
		conector.close();
	}
	
	public void borrarPuntuaciones()
	{	
		db.delete(TABLA_PUNTUACIONES, null, null);
	}
	
	public void guardarPuntuacion(int puntos, String nombre, long fecha)
	{
		ContentValues nuevoRegistro = new ContentValues();
		
		Date date = new Date();
		
		nuevoRegistro.put("puntos", puntos);
		nuevoRegistro.put("nombre", nombre);
		nuevoRegistro.put("fecha", date.getTime());
		
		db.insert(TABLA_PUNTUACIONES, null, nuevoRegistro);
	}
	
	public Vector<String> listaPuntuaciones(int cantidad)
	{
		Vector<String> puntuaciones = new Vector<String>();
		String[] cols = {"puntos", "nombre", "fecha"};
		
		Cursor cursor = db.query(TABLA_PUNTUACIONES, 
				cols, 
				null /*selection*/, 
				null /*selectionArgs*/, 
				null /*groupBy*/, 
				null /*having*/, 
				"puntos DESC" /*orderBy*/, 
				Integer.toString(cantidad) /*limit*/
		);
		
		while (cursor.moveToNext()) {
			Date date = new Date();
			date.setTime(cursor.getLong(2));
			
			puntuaciones.add(
					String.format("Puntuacion: %s por %s el %s", 
					cursor.getString(0), 
					cursor.getString(1),
					date.toGMTString())
			);
		}
		
		return puntuaciones;
		
 	}

	static class ConectorBBDD extends SQLiteOpenHelper {

		ConectorBBDD(Context context)
		{
			super(context, BBDD_NOMBRE, null, BBDD_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL(BBDD_DDL_CREATEBBDD);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL(BBDD_DDL_DROPBBDD);
			onCreate(db);
		}
	}
	
}
