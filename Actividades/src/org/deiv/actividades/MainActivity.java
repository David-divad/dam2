package org.deiv.actividades;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected final int ID_PANTALLA_PUNTUAR = 1; 
	
	protected TextView textView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView = (TextView) findViewById(R.id.textView1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void lanzarAcercaDe(View view)
    {
    	Intent i = new Intent(this, AcercaDe.class);
    	startActivity(i);
    }
    
    public void lanzarPuntuar(View view)
    {
    	Intent i = new Intent(this, Puntuar.class);
    	
    	startActivityForResult(i, ID_PANTALLA_PUNTUAR);
    }
    
    @Override
    protected void onActivityResult(int id, int result, Intent data)
    {
    	switch (id) {
    		case ID_PANTALLA_PUNTUAR:
    			if (result == RESULT_OK) {
    				String puntuacion = (String) data.getExtras().get("puntuacion");
    			
    				textView.setText(puntuacion);
    			}
    			break;
    	}
    }
}
