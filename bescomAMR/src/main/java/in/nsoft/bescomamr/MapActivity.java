package in.nsoft.bescomamr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends android.support.v4.app.FragmentActivity  implements LocationListener {
	ReadSlabNTarifSbmBillCollection SlabColl;
	private static final String TAG = null;
	private ArrayList<MyMarker> mMark=new ArrayList<MyMarker>();
	
	private HashMap<Marker,MyMarker> mMarkerHashMap;
	//private HashMap<Marker,MyMarker> mMarkerHashMap;
	//private ArrayList<LatLong> alLatLng;

	SupportMapFragment mMap;
	private GoogleMap googleMap;
	DatabaseHelper db = new DatabaseHelper(this);	
	TextView tvlocation;
	MarkerOptions markOpt;
	LatLng latLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		SlabColl = BillingObject.GetBillingObject();		
		googleMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();//for getting map from Google

		try
		{

			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			googleMap.setTrafficEnabled(true);

			googleMap.setMyLocationEnabled(true);

			LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria=new Criteria();
			String provider=locationManager.getBestProvider(criteria, true);
			Location location=locationManager.getLastKnownLocation(provider);
			if(location!=null){
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(provider, 20000, 0, this);
			mMarkerHashMap=new HashMap<Marker,MyMarker>();
			googleMap.getUiSettings().setZoomControlsEnabled(false);
			googleMap.setIndoorEnabled(true);

			//alLatLng=db.GetLatLng();
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "Error in Obtaining Map", Toast.LENGTH_SHORT).show();
		}


		/*Button btn_find=(Button)findViewById(R.id.btn_find);
		OnClickListener findClickListener=new OnClickListener(){
			public void onClick(View v){

				try{
					EditText etLocation=(EditText)findViewById(R.id.et_location);
					String location=etLocation.getText().toString();
					if(location!=null && !location.equals("")){
						new GeocoderTask().execute(location);
					}
				}
				catch(Exception e){
					e.printStackTrace();
					Toast.makeText(getBaseContext(), "Error in Map", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(MapActivity.this,Billing.class);
					startActivity(i);
				}	

			}

		};

		btn_find.setOnClickListener(findClickListener);*/










		try{

			/*for(int i=0;i<alLatLng.size();i++)	
		{
			mMark.add(new MyMarker(String.valueOf(i), Double.parseDouble(alLatLng.get(i).getmLatitude()),Double.parseDouble(alLatLng.get(i).getmLongitude())));
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(alLatLng.get(i).getmLatitude()),Double.parseDouble(alLatLng.get(i).getmLongitude())),12f));

		}*/

			mMark.add(new MyMarker(SlabColl.getmRRNo()+ " " + SlabColl.getmCustomerName(), Double.parseDouble(SlabColl.getmGps_Latitude_image()),Double.parseDouble(SlabColl.getmGps_Longitude_image()),String.valueOf(SlabColl.getmBlCnt())));
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SlabColl.getmGps_Latitude_image()),Double.parseDouble(SlabColl.getmGps_Latitude_image())),12f));

			LatLng CENTER=new LatLng(Double.parseDouble(SlabColl.getmGps_Latitude_image()),Double.parseDouble(SlabColl.getmGps_Longitude_image()));
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(CENTER));
			final int zoom=8;
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom),1500,null);

			setUpMarker();
			plotMarkers(mMark);	

			//25-06-2015
			/*try
			{
				LatLngBounds.Builder builder=new LatLngBounds.Builder();			
				for(MyMarker MM: mMark )
				{
					LatLng latlng = new LatLng(MM.getmLatitude(),MM.getmLongitude());			
					builder.include(latlng).build();
				}			
				LatLngBounds bounds=builder.build();	
				CameraPosition.Builder builder1=CameraPosition.builder();
				builder1.zoom(2);
				builder1.bearing(100);			
				CameraUpdate cu=CameraUpdateFactory.newLatLngZoom(latLng, 3);

				googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,100,100,0));


				bounds.getCenter();
			}
			catch(Exception e){
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "Error in Lat Long 0", Toast.LENGTH_SHORT).show();
			}*/	
			//End 25-06-2015


		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "Error in Lat Long 1", Toast.LENGTH_SHORT).show();
		}	


		//try{

		/*LatLng CENTER=new LatLng(Double.parseDouble(SlabColl.getmGps_Latitude_image()),Double.parseDouble(SlabColl.getmGps_Longitude_image()));
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(CENTER));
			final int zoom=8;
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom),1500,null);

			setUpMarker();
			plotMarkers(mMark);	*/	 
		/*}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "Error in Lat Long 2", Toast.LENGTH_SHORT).show();
		}*/
	}


	private void plotMarkers(ArrayList<MyMarker> markers) {
		try{
			if(markers.size()>0)
			{
				for(MyMarker mMark:markers)
				{
					MarkerOptions markerOption=new MarkerOptions().position(new LatLng(mMark.getmLatitude(),mMark.getmLongitude()));
					BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

					Marker currentMarker=googleMap.addMarker(markerOption);
					mMarkerHashMap.put(currentMarker, mMark);
					googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "PlotMarkers", Toast.LENGTH_SHORT).show();
		}	
	}





	private void setUpMarker() {


		try
		{
			if(googleMap==null)
			{
				googleMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "unable to get google map", Toast.LENGTH_SHORT).show();
		}	

		if(googleMap!=null)
			googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() 
			{
				@Override
				public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
					marker.showInfoWindow();

					// TODO Auto-generated method stub
					return true;
				}
			});

		else
			Toast.makeText(getApplicationContext(), "Unable to create maps", Toast.LENGTH_SHORT).show();

	}
	public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

		public MarkerInfoWindowAdapter()
		{

		}

		@Override
		public View getInfoContents(Marker marker) {
			View v = null;
			try
			{
				v=getLayoutInflater().inflate(R.layout.infowindow_layout,null);
				MyMarker mMark=mMarkerHashMap.get(marker);

				TextView markerLabel=(TextView)v.findViewById(R.id.marker_label);
				markerLabel.setText(mMark.getmLabel());

			}

			catch(Exception e){
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "marker label infor", Toast.LENGTH_SHORT).show();
			}	
			return v;
		}

		@Override
		public View getInfoWindow(Marker arg0) {

			return null;
		}

	}



	//Modified Nitish 27-02-2014
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.		
		getMenuInflater().inflate(R.menu.spotbillingmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return OptionsMenu.navigate(item,MapActivity.this);		
	}	



	private class GeocoderTask extends AsyncTask<String,Void,List<Address>>{

		protected List<Address> doInBackground(String...locationName){
			Geocoder geocoder=new Geocoder(getBaseContext());
			List<Address> addresses=null;
			try{
				addresses=geocoder.getFromLocationName(locationName[0], 1);

			}
			catch(IOException e){
				e.printStackTrace();
			}
			return addresses;

		}
		protected void onPostExecute(List<Address> addresses)
		{
			if(addresses==null||addresses.size()==0)
			{
				Toast.makeText(getBaseContext(), "no location found", Toast.LENGTH_SHORT).show();
			}
			/*googleMap.clear();*/

			try{

				for(int i=0;i<addresses.size();i++)
				{
					Address address=(Address)addresses.get(i);
					latLng =new LatLng(address.getLatitude(),address.getLongitude());
					String addressText=String.format("%s, %s",address.getMaxAddressLineIndex()>0 ? address.getAddressLine(0):"",
									address.getCountryName());

					markOpt=new MarkerOptions();
					markOpt.position(latLng);
					markOpt.title(addressText);
					markOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					googleMap.addMarker(markOpt);




					if(i==0)//locate the first location

						googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

				}
			}
			catch(Exception e){
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "error on clicking marker", Toast.LENGTH_SHORT).show();
			}	

			/*googleMap.setOnMarkerClickListener(new OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub

					marker.remove();
					return true;

				}
			 */


			/*});*/


		}
	}


	@Override
	public void onLocationChanged(Location location){
		try
		{
			double latitude=location.getLatitude();
			double longitude=location.getLongitude();

			LatLng latlng= new LatLng(latitude, longitude);
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));   
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "onLocationChanged", Toast.LENGTH_SHORT).show();
		}	


	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}


}	






