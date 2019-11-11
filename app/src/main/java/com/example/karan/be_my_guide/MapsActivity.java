package com.example.karan.be_my_guide;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.karan.be_my_guide.AppConfig.GEOMETRY;
import static com.example.karan.be_my_guide.AppConfig.GOOGLE_BROWSER_API_KEY;
import static com.example.karan.be_my_guide.AppConfig.ICON;
import static com.example.karan.be_my_guide.AppConfig.LATITUDE;
import static com.example.karan.be_my_guide.AppConfig.LOCATION;
import static com.example.karan.be_my_guide.AppConfig.LONGITUDE;
import static com.example.karan.be_my_guide.AppConfig.NAME;
import static com.example.karan.be_my_guide.AppConfig.OK;
import static com.example.karan.be_my_guide.AppConfig.PLACE_ID;
import static com.example.karan.be_my_guide.AppConfig.REFERENCE;
import static com.example.karan.be_my_guide.AppConfig.STATUS;
import static com.example.karan.be_my_guide.AppConfig.SUPERMARKET_ID;
import static com.example.karan.be_my_guide.AppConfig.VICINITY;
import static com.example.karan.be_my_guide.AppConfig.ZERO_RESULTS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker curretnLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode)
       {
           case REQUEST_LOCATION_CODE:
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
               {
                   //permission granted
                   if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                   {
                       if (client == null )
                       {
                           buildGoogleApiClient();
                       }
                       mMap.setMyLocationEnabled(true);
                   }

               }
               else // permission is denied
               {
                   Toast.makeText(this, "permission Denied!",Toast.LENGTH_LONG).show();
               }
               return;

       }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            CameraPosition cameraPosition;                   // Creates a CameraPosition from the builder
            cameraPosition = new CameraPosition.Builder().target(new LatLng(35.655566 , 78.3428638734)).bearing(180).tilt(30).zoom(20).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    protected  synchronized void buildGoogleApiClient()
    {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;
        if (curretnLocationMarker !=null)
        {
            curretnLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("current_location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        curretnLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));//to zoom the map

        latitude = latLng.latitude;
        longitude = latLng.longitude;

        if (client !=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);

        }
    }

    public void search(View V)

    {


        switch (V.getId()) {
            case R.id.B_search:
                mMap.clear();

            {


                EditText TF_location = (EditText) findViewById(R.id.TF_location);
                String location = TF_location.getText().toString();

                List<Address> addressList = null;
                MarkerOptions mo = new MarkerOptions();


                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < addressList.size(); i++) {
                        Address myAddress = addressList.get(i);
                        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        mo.position(latLng);
                        mo.title("your search Result ");
                        mMap.addMarker(mo);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }


                }

            }
            break;
            case R.id.hospital:
                mMap.clear();
                String hospital = "hospital";
                loadNearByPlaces( hospital ,latitude, longitude );


                Toast.makeText(MapsActivity.this, "showing near by hospitals", Toast.LENGTH_LONG).show();
                break;

            case R.id.hotel:

                mMap.clear();
                String hotel = "hotel";

                loadNearByPlaces( hotel ,latitude, longitude );




                Toast.makeText(MapsActivity.this, "showing near by hotels", Toast.LENGTH_LONG).show();
                break;

            case R.id.restaurant:
                mMap.clear();
                String restaurant = "restaurant";


                loadNearByPlaces( restaurant ,latitude, longitude );



                Toast.makeText(MapsActivity.this, "showing near by restaurant", Toast.LENGTH_LONG).show();
                break;

            case R.id.Popular:
                mMap.clear();
                String popular = "popular";

                loadNearByPlaces( popular ,latitude, longitude );



                Toast.makeText(MapsActivity.this, "showing near by popular places ", Toast.LENGTH_LONG).show();
                break;

            case R.id.atm:
                mMap.clear();
                String atm = "atm";

                loadNearByPlaces( atm ,latitude, longitude );


                Toast.makeText(MapsActivity.this, "showing near by ATM ", Toast.LENGTH_LONG).show();
                break;
        }
    }



    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

        LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);
        }
    }
    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return false;

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void loadNearByPlaces(final String type , double latitude, double longitude) {
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works

        type.toLowerCase();

        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=").append(type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(), new JSONObject( ),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        Log.i("", "onResponse: Result= " + result.toString());
                        parseLocationResult(result , type);
                    }
                },
                new Response.ErrorListener() {
                    @Override                    public void onErrorResponse(VolleyError error) {
                        Log.e("", "onErrorResponse: Error= " + error);
                        Log.e("", "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(MapsActivity.this).add(request);
    }

    private void parseLocationResult(JSONObject result , String type) {

        String id, place_id, placeName = null, reference, icon, vicinity = null;
        double latitude, longitude;

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    id = place.getString(SUPERMARKET_ID);
                    place_id = place.getString(PLACE_ID);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                    reference = place.getString(REFERENCE);
                    icon = place.getString(ICON);

                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : " + vicinity);

                    mMap.addMarker(markerOptions);
                }

                Toast.makeText(getBaseContext(), jsonArray.length() + " "+type+" found!",
                        Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No "+type+" found in 5KM radius!!!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e("", "parseLocationResult: Error=" + e.getMessage());
        }
    }


}
