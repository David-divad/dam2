package org.deiv.testsqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	/* DDL */
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
		
		Context context;
		
		ConectorBBDD(Context context)
		{
			super(context, BBDD_NOMBRE, null, BBDD_VERSION);
			
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			//db.execSQL(BBDD_DDL_CREATEBBDD);
			
			String bbddPath = String.format("/data/data/%s/databases/%s", 
					context.getPackageName(), 
					BBDD_NOMBRE);
			
			File dbFile = new File(bbddPath);
			
			if (!dbFile.exists()) {
				
				importaDesdeRecursos(R.raw.dbpuntuaciones, bbddPath);
			}
		}
		
		public void importaDesdeRecursos(int resourceId, String destino)
		{
			FileOutputStream fos = null;
			InputStream is = null;
			
			try {	
				byte[] buffer = new byte[1024];
				int length;
				
				fos = new FileOutputStream(destino);
				is = context.getResources().openRawResource(resourceId);
				
				while ((length = is.read(buffer)) > 0) {
					fos.write(buffer, 0, length);
				}
				
			} catch (IOException e) {
				throw new RuntimeException(e);
				
			} finally {
				
				try {
					if (is != null) is.close();
					if (fos != null) fos.close();
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL(BBDD_DDL_DROPBBDD);
			onCreate(db);
		}
	}
	
}
