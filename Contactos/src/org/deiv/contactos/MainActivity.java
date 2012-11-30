package org.deiv.contactos;

import org.deiv.contactos.db.AdaptadorBD;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/*
 * TODO: refactorizar dialogos
 */

public class MainActivity extends Activity {

	ListView listViewContactos;
	AdaptadorBD db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listViewContactos = (ListView) findViewById(R.id.listViewContactos);
		
		db = new AdaptadorBD(this);
		
		rellenaListaContactos();
		
		registerForContextMenu(listViewContactos);
	}
	
	protected void rellenaListaContactos()
	{
        db.abrir();

        Cursor c = db.listaContactos();
        c.moveToFirst(); 

        ListAdapter adapter = new SimpleCursorAdapter(
        		this, 
        		R.layout.listitem_contacto, 
        		c, 
        		new String[] {"nombre", "email"}, 
        		new int[] {R.id.textNombre, R.id.textEmail}
        );
        
        listViewContactos.setAdapter(adapter);

        db.cerrar(); 
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_listitem_contacto, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    
	    switch (item.getItemId()) {
	        case R.id.menuEditar:
	        	lanzarDialogoEditar(info.id);
	            return true;
	            
	        case R.id.menuBorrar:
	        	lanzarDialogoEliminar(info.id);
	            return true;
	            
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	protected final int ID_PANTALLA_EDITAR = 1;
	protected final int ID_PANTALLA_ELIMINAR = 2;
	protected final int ID_PANTALLA_CREAR = 3;
	
    public void lanzarDialogoEditar(long id)
    {
    	Intent i = new Intent(this, DialogoContacto.class);
    	
    	i.setAction("editar");
    	i.putExtra("idContacto", id);
    	
    	startActivityForResult(i, ID_PANTALLA_EDITAR);
    }
    
    public void lanzarDialogoEliminar(long id)
    {
    	Intent i = new Intent(this, DialogoContacto.class);
    	
    	i.setAction("eliminar");
    	i.putExtra("idContacto", id);
    	
    	startActivityForResult(i, ID_PANTALLA_ELIMINAR);
    }
    
    public void lanzarDialogoCrear()
    {
    	Intent i = new Intent(this, DialogoContacto.class);
    	
    	i.setAction("crear");
    	
    	startActivityForResult(i, ID_PANTALLA_CREAR);
    }
    
    @Override
    protected void onActivityResult(int id, int result, Intent data)
    {
    	switch (id) {
    		case ID_PANTALLA_EDITAR:
    			if (result == RESULT_OK) {
    				Toast.makeText(getApplicationContext(),  "modificado", Toast.LENGTH_SHORT).show();
    				rellenaListaContactos();
    			}
    			break;
    			
    		case ID_PANTALLA_ELIMINAR:
    			if (result == RESULT_OK) {
    				Toast.makeText(getApplicationContext(),  "eliminado", Toast.LENGTH_SHORT).show();
    				rellenaListaContactos();
    			}
    			break;
    			
    		case ID_PANTALLA_CREAR:	
    			if (result == RESULT_OK) {
    				Toast.makeText(getApplicationContext(),  "creado", Toast.LENGTH_SHORT).show();
    				rellenaListaContactos();
    			}
    			break;
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_nuevocontacto:
	        	lanzarDialogoCrear();
	            return true;

	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
