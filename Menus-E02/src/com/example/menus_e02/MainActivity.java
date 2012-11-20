package com.example.menus_e02;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId()) {
    		case R.id.item5:
    		case R.id.item6:
    		case R.id.item7:
    			if (item.isChecked()) item.setChecked(false);
    			else item.setChecked(true);
    			
    			return true;
    	}
    	
    	return false;
    }
}
