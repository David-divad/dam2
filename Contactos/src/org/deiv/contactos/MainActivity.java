package org.deiv.contactos;

import org.deiv.contactos.db.Adaptador;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView listViewContactos;
	Adaptador db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listViewContactos = (ListView) findViewById(R.id.listViewContactos);
		
		db = new Adaptador(this);
		
		rellenaListaContactos();
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
        
        OnItemLongClickListener onClick = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				Context context = getApplicationContext();
				CharSequence text = "click";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
				return true;
			}
        };
        
        listViewContactos.setOnItemLongClickListener(onClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
