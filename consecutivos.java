package com.example.invial;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class consecutivos extends informaciongeneral {
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consecutivos);
    
        Spinner Spinner1 = (Spinner)findViewById(R.id.spinner1);
        String []opciones = {"TIPO DE TERRENO","CUNETA","PUENTE"};
        ArrayAdapter<String> adapter = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        Spinner1.setAdapter(adapter);   
        
       Button BtnGPS = (Button)findViewById(R.id.BtnPasarGPS);
       BtnGPS.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			
			Intent pGPS = new Intent(consecutivos.this,GPS.class);
			startActivity(pGPS);
	
			
		}
	});
     
        
        
	}
  
        
        
        
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

