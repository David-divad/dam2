package deiv.org.listas_e01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	protected ListView lista;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String[] nombres = getResources().getStringArray(R.array.distribuciones);
        String[] anios = getResources().getStringArray(R.array.anios);
        
        lista = (ListView) findViewById(R.id.listView1);
        DistroAdapter adaptador = new DistroAdapter(this, nombres, anios);
        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
