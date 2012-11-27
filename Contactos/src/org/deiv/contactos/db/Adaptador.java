package org.deiv.contactos.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.deiv.contactos.R;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Adaptador {
	
	static protected final String BBDD_NOMBRE = "contactos.db";
	static protected final int    BBDD_VERSION = 1;
	static protected final String TABLA_CONTACTOS = "CONTACTOS";
	
	/* DDL */
	static protected final String BBDD_DDL_DROPBBDD = "DROP TABLE IF EXISTS " + TABLA_CONTACTOS;
	static protected final String BBDD_DDL_CREATEBBDD = "CREATE TABLE " + TABLA_CONTACTOS +
			"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"nombre TEXT," +
			"email TEXT)";
	
	ConectorBBDD conector;
	private SQLiteDatabase db;
	
	public Adaptador(Context context)
	{
		String bbddPath = String.format("/data/data/%s/databases/%s", 
				context.getPackageName(), 
				BBDD_NOMBRE);
		File dbFile = new File(bbddPath);
		//dbFile.delete();
		importaDesdeRecursos(context, R.raw.dbcontactos, bbddPath);
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
	
	public Cursor listaContactos()
	{
		String[] cols = {"_id", "nombre", "email"};
		
		Cursor cursor = db.query(TABLA_CONTACTOS, 
				cols, 
				null /*selection*/, 
				null /*selectionArgs*/, 
				null /*groupBy*/, 
				null /*having*/, 
				"nombre DESC" /*orderBy*/, 
				null/*limit*/
		);
		
		return cursor;
 	}
	
	public void importaDesdeRecursos(Context context, int resourceId, String destino)
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
	
	protected static class ConectorBBDD extends SQLiteOpenHelper {
		
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
			
			//if (dbFile.exists()) {
				
				//importaDesdeRecursos(R.raw.dbcontactos, bbddPath);
			//}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			//db.execSQL(BBDD_DDL_DROPBBDD);
			//onCreate(db);
		}
	}

}
