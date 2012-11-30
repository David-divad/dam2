package org.deiv.ficheros;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	protected EditText edittext;
	protected final int TAMAÑO_BLOQUE = 128;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edittext = (EditText) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onSalvarClick(View view) throws IOException
	{
		String cadena = edittext.getText().toString();
		
		if (cadena.isEmpty()) {
			Toast.makeText(getBaseContext(), "error: cadena vacia", Toast.LENGTH_SHORT).show();
		}
		
		FileOutputStream os = openFileOutput("textfile", MODE_WORLD_READABLE );
		OutputStreamWriter ow = new OutputStreamWriter(os);
		
		ow.write(cadena);
		ow.flush();
		ow.close();
		
		edittext.setText("");
		
		Toast.makeText(getBaseContext(), "archivo guardado", Toast.LENGTH_SHORT).show();
	}
	
	public void onCargarClick(View view) throws IOException
	{
		FileInputStream is = openFileInput("textfile");
		InputStreamReader ir = new InputStreamReader(is);
		
		char buf[] = new char[TAMAÑO_BLOQUE];
		String s = "";
		int leido;
			
		while ((leido = ir.read(buf)) > 0 ) {
			s += String.copyValueOf(buf, 0, leido);
			buf = new char[TAMAÑO_BLOQUE];
		}
		
		ir.close();
		
		edittext.setText(s);
		
		Toast.makeText(getBaseContext(), "archivo cargado", Toast.LENGTH_SHORT).show();
	}

}
