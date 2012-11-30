package org.deiv.contactos;

import org.deiv.contactos.db.AdaptadorBD;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DialogoContacto extends Activity implements OnClickListener {
	
	TextView textNombre;
	TextView textEmail;
	Button botonSalvar;
	Button botonCancelar;
	long idContacto;
	String accion;
	
	AdaptadorBD db;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        db = new AdaptadorBD(this);
        
        accion = getIntent().getAction();
        
        if (accion.contentEquals("editar")) {
        	
        	idContacto = getIntent().getLongExtra("idContacto", -1);
        	
        	if (idContacto == -1) {
        		throw new RuntimeException("Error interno");
        	}
        	
            setContentView(R.layout.editar_contacto);
       	
            textNombre = (TextView) findViewById(R.id.editTextNombre);
            textEmail = (TextView) findViewById(R.id.editTextEmail);
            botonSalvar = (Button) findViewById(R.id.botonGuardar);
            
            botonSalvar.setOnClickListener(this);
        	
        	db.abrir();
        	Cursor c = db.getContacto(idContacto);		
        	c.moveToFirst();
        	
        	textNombre.setText(c.getString(1));
        	textEmail.setText(c.getString(2));
        	
        	db.cerrar();
        	
        } else if (accion.contentEquals("eliminar")) {
        	
        	idContacto = getIntent().getLongExtra("idContacto", -1);
        	
        	if (idContacto == -1) {
        		throw new RuntimeException("Error interno");
        	}
       
            setContentView(R.layout.eliminar_contacto);

            textNombre = (TextView) findViewById(R.id.textNombre);
            textEmail = (TextView) findViewById(R.id.textEmail);
            botonSalvar = (Button) findViewById(R.id.buttonEliminarOk);
            botonCancelar = (Button) findViewById(R.id.buttonEliminarCancelar);
            
            botonSalvar.setOnClickListener(this);
            botonCancelar.setOnClickListener(this);
        	
        	db.abrir();
        	Cursor c = db.getContacto(idContacto);		
        	c.moveToFirst();
        	
        	textNombre.setText(c.getString(1));
        	textEmail.setText(c.getString(2));
        	
        	db.cerrar();
        } else if (accion.contentEquals("crear")) {
        	
            setContentView(R.layout.crear_contacto);
       	
            textNombre = (TextView) findViewById(R.id.editTextNombre);
            textEmail = (TextView) findViewById(R.id.editTextEmail);
            botonSalvar = (Button) findViewById(R.id.botonGuardar);
            
            botonSalvar.setOnClickListener(this);
        }
    }
    
    public void salir()
    {
    	setResult(RESULT_OK);
    	
    	finish();
    }

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
			case R.id.botonGuardar:
				if (accion.contentEquals("editar")) {
					db.abrir();
					db.editaContacto(idContacto, textNombre.getText().toString(), textEmail.getText().toString());
					db.cerrar();
				} else if (accion.contentEquals("crear")) {
					db.abrir();
					db.insertaContacto(textNombre.getText().toString(), textEmail.getText().toString());
					db.cerrar();
				}
			
				salir();
				break;
				
			case R.id.buttonEliminarOk:
				db.abrir();
				db.eliminaContacto(idContacto);
				db.cerrar();
			
				salir();
				break;
				
			case R.id.buttonEliminarCancelar:
		    	setResult(RESULT_CANCELED);
		    	
		    	finish();
				break;
		}
	}
}
