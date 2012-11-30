package org.deiv.contactos.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.deiv.contactos.R;

public class AdaptadorBD {
	
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
	
	public AdaptadorBD(Context context)
	{
		String bbddPath = String.format("/data/data/%s/databases/%s", 
				context.getPackageName(), 
				BBDD_NOMBRE);
		File dbFile = new File(bbddPath);
		
		if (!dbFile.exists()) {
			dbFile.mkdirs();
			importaDesdeRecursos(context, R.raw.dbcontactos, bbddPath);
		}

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
	
	public Cursor getContacto(long idContacto)
	{
		String[] cols = {"_id", "nombre", "email"};
		String[] args = {String.valueOf(idContacto)};
		
		Cursor cursor = db.query(TABLA_CONTACTOS, 
				cols, 
				"_id=?" /*selection*/, 
				args /*selectionArgs*/, 
				null /*groupBy*/, 
				null /*having*/, 
				"nombre DESC" /*orderBy*/, 
				null/*limit*/
		);
		
		return cursor;
 	}
	
	public void editaContacto(long idContacto, String nombre, String email)
	{
		ContentValues valores = new ContentValues();
		String[] whereArgs = {String.valueOf(idContacto)};

		valores.put("nombre", nombre);
		valores.put("email", email);
		
		db.update(TABLA_CONTACTOS, valores, "_id=?", whereArgs);
 	}
	
	public void eliminaContacto(long idContacto)
	{
		String[] whereArgs = {String.valueOf(idContacto)};
		
		db.delete(TABLA_CONTACTOS, "_id=?", whereArgs);
 	}
	
	public void insertaContacto(String nombre, String email)
	{
		ContentValues nuevoRegistro = new ContentValues();
		
		nuevoRegistro.put("nombre", nombre);
		nuevoRegistro.put("email", email);
		
		db.insert(TABLA_CONTACTOS, null, nuevoRegistro);
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
			db.execSQL(BBDD_DDL_CREATEBBDD);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			//db.execSQL(BBDD_DDL_DROPBBDD);
			//onCreate(db);
		}
	}

}
