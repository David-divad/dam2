package org.deiv.dialogos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	final protected int ID_DIALOGO1 = 1;
	final protected int ID_DIALOGO2 = 2;
	
	Button boton1;
	Button boton2;
	ProgressDialog dialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        boton1 = (Button) findViewById(R.id.button1);
        boton1.setOnClickListener(this);
        
        boton2 = (Button) findViewById(R.id.button2);
        boton2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) 
	{
		int id;
		
		switch (v.getId()) { 
			case R.id.button1:
				id = ID_DIALOGO1;
				break;
				
			case R.id.button2:
				id = ID_DIALOGO2;
				break;
				
			default:
				throw new IllegalArgumentException("id de dialogo erronea");
		}
		
		showDialog(id);
		new ProgressAsyncTask().execute(id);
	}
	
	@Override
	public Dialog onCreateDialog(int id)
	{
		dialog = new ProgressDialog(this);
		
		switch (id) {
			case ID_DIALOGO1:
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				break;
				
			case ID_DIALOGO2:
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				break;
				
			default:
				throw new IllegalArgumentException("id de dialogo erronea");
		}
		
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("Progreso...");
		
		return dialog;
	}
	
    class ProgressAsyncTask extends AsyncTask<Integer, Void, Void> {

    	int progreso = 0;
    	int dialogId = 0;
    	
		@Override
		protected Void doInBackground(Integer... params) 
		{
			dialogId = params[0];
				
			for (int c=0; c < 100; c++) {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					/* */
				}
				
				progreso = c + 1;
				publishProgress();
			}
			
			return null;
		}
    	
		@Override
		protected void onProgressUpdate(Void... progress)
		{
			dialog.setProgress(progreso);
			if (progreso == 100) {
				removeDialog(dialogId);
			}
		}

    }
	
}
