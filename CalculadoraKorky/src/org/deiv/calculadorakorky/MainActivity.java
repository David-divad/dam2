package org.deiv.calculadorakorky;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tvResultado;
	private String ultimo_introducido = "";

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvResultado = (TextView) findViewById(R.id.textViewResultado);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onNumberButtonClick(View arg0) 
	{	
		switch (arg0.getId()) {
			case R.id.button0:
				if (tvResultado.getText().length() < 1 ) break;
				introduceCaracter("0");
				break;
	
			case R.id.button1:
				introduceCaracter("1");
				break;
	
			case R.id.button2:
				introduceCaracter("2");
				break;
	
			case R.id.button3:
				introduceCaracter("3");
				break;
	
			case R.id.button4:
				introduceCaracter("4");
				break;
	
			case R.id.button5:
				introduceCaracter("5");
				break;
	
			case R.id.button6:
				introduceCaracter("6");
				break;
	
			case R.id.button7:
				introduceCaracter("7");
				break;
	
			case R.id.button8:
				introduceCaracter("8");
				break;
	
			case R.id.button9:
				introduceCaracter("9");
				break;
	
			case R.id.buttonPunto:
				/* FIXME: manejar la entrada de 2 puntos decimales */
				//tvResultado.append(".");
				if (ultimo_introducido.contentEquals(".")) break;
				introduceCaracter(".");
				break;
	
			case R.id.buttonBorrar:
				tvResultado.setText("");
				break;
	
			default:
				throw new IllegalArgumentException("Id de vista inexistente");
		}	
	}

	protected void introduceCaracter(String c)
	{
		tvResultado.append(c);
		ultimo_introducido = c;
	}
}
