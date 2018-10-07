package com.example.gps;

import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
 
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements LocationListener {
	
	private MapView osm;
	private MapController mc;
	private LocationManager locationManager;
	
	 private Button btnActualizar;
	 private Button btnDesactivar;
	 private TextView lblLatitud; 
	 private TextView lblLongitud;
	 private TextView lblPrecision;
	     
	  private LocationManager locManager;
	  private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        
        //Se captura los 2 botones y los 3 TextView de la pantalla
           btnActualizar = (Button)findViewById(R.id.BtnActualizar);
           btnDesactivar = (Button)findViewById(R.id.BtnDesactivar);
           lblLatitud = (TextView)findViewById(R.id.LblPosLatitud);
           lblLongitud = (TextView)findViewById(R.id.LblPosLongitud);
           lblPrecision = (TextView)findViewById(R.id.LblPosPrecision);
            
           //Creamos un listener onClick para los botones actualizar y desactivar
           //estos se disparan cuando se clickea(touch) el respectivo botón
           btnActualizar.setOnClickListener(new OnClickListener() 
           {
            public void onClick(View v) 
            {
             Toast.makeText(getApplicationContext(), "Comienza localización...", Toast.LENGTH_SHORT).show();
             comenzarLocalizacion();
               }
           });
            
           btnDesactivar.setOnClickListener(new OnClickListener() 
           {
                public void onClick(View v) 
                {
                 locManager.removeUpdates(locListener);
                 Toast.makeText(getApplicationContext(), "Desactivadas las actualizaciones!", Toast.LENGTH_SHORT).show();
                }
           });
        
        
    }
    
    //Creamos un método que dispara eventos del GPS
    private void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
       locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
             
        //Instanciamos el locListener que nos permite escuchar al GPS
        locListener = new LocationListener() 
        {
         public void onLocationChanged(Location location) 
         {
          //Aqui mostramos los datos de la posicion que se encuentran en "location"
          mostrarPosicion(location);
         }
          
   @Override
   public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub
   }
 
   @Override
   public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub
   }
 
   @Override
   public void onStatusChanged(String provider, int status,
     Bundle extras) {
    // TODO Auto-generated method stub
   }
        };
         
        //El LocationManager administra el GPS, por ejemlo:
        //Realiza solicitudes de actualización cada 15000 milisegundos (15 segundos)
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    }
      
    private void mostrarPosicion(Location loc) 
    {
            if(loc != null)
            {
                    lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
                    lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
                    lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            }
            else
            {
                    lblLatitud.setText("Latitud: No hay datos");
                    lblLongitud.setText("Longitud: No hay datos");
                    lblPrecision.setText("Precision: No hay datos");
            }
            
     
            osm = (MapView) findViewById(R.id.mapView);
    		osm.setTileSource(TileSourceFactory.MAPNIK);
    		osm.setBuiltInZoomControls(true);
    		osm.setMultiTouchControls(true);
    		
    		
    		mc = (MapController) osm.getController();
    		mc.setZoom(12);
    		
    		GeoPoint center = new GeoPoint(-20.1698,-40.2487);
    		mc.animateTo(center);
    		addMarker(center);
    		
    		
    		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    	}
    	
    	
    	
    	public void addMarker(GeoPoint center){
    		Marker marker = new Marker(osm);
    		marker.setPosition(center);
    		marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
    		marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
    		
    		osm.getOverlays().clear();
    		osm.getOverlays().add(marker);
    		osm.invalidate();
    	}



    	@Override
    	public void onLocationChanged(Location location) {
    		GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
    		
    		mc.animateTo(center);
    		addMarker(center);
    	}
    	@Override
    	public void onProviderDisabled(String provider) {
    		// TODO Auto-generated method stub
    	}
    	@Override
    	public void onProviderEnabled(String provider) {
    		// TODO Auto-generated method stub
    	}
    	@Override
    	public void onStatusChanged(String provider, int status, Bundle extras) {
    		// TODO Auto-generated method stub
    	}
    	        
    
    @Override
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
