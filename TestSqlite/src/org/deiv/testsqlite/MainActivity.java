package org.deiv.testsqlite;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (EditText) findViewById(R.id.editText1);
		
		AlmacenPuntuaciones ap = new AlmacenPuntuaciones(this);
		
		ap.abrir();
		
		/* borramos y recreamos de nuevo los datos */
		ap.borrarPuntuaciones();
		ap.guardarPuntuacion(10, "David", 21872);
		ap.guardarPuntuacion(7, "Armando Casitas", 21870);
		ap.guardarPuntuacion(8, "Aitor Tilla", 21860);
		
		/* mostramos en pantalla */
		for (String p : ap.listaPuntuaciones(3)) {
			text.append("- " + p + "\n");
		}
		
		ap.cerrar();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
