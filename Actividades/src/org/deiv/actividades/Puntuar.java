package org.deiv.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

public class Puntuar extends Activity {
	
	RatingBar ratingBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuanos);
        
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar.setRating(2.5f);
    }
    
    public void returnPuntuacion(View view)
    {
    	Intent data = new Intent();
    	float puntuacion = ratingBar.getRating();
    			
    	data.putExtra("puntuacion", Float.toString(puntuacion));
    	setResult(RESULT_OK, data);
    	
    	finish();
    }
}
