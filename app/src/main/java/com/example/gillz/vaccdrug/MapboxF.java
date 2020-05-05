package com.example.gillz.vaccdrug;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
// classes needed to initialize map
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
// classes needed to add the location component
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import android.location.Location;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.mapbox.mapboxsdk.geometry.LatLng;
import android.support.annotation.NonNull;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
// classes needed to add a marker
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

public class MapboxF extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {

    private MapView mapView;
    // variables for adding location layer
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location originLocation;
    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private Point destinationCoord;
    // variables for calculating and drawing a route
    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private Button button;
    ArrayAdapter<CharSequence> arrayAdapter;
    Spinner spinner1;
    String facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_mapbox_f);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        button = findViewById(R.id.startButton);
        spinner1=findViewById(R.id.Sp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(MapboxF.this, options);
                button.setEnabled(true);
                button.setBackgroundResource(R.color.go4vac);
            }

        });

    }



    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        enableLocationComponent();
        originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());
        mapboxMap.addOnMapClickListener(this);
        button = findViewById(R.id.startButton);
        spinner1=findViewById(R.id.Sp);
        arrayAdapter= ArrayAdapter.createFromResource(this,R.array.Facility, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(arrayAdapter);

        Snackbar.make(this.findViewById(android.R.id.content),
                "Choose a Facility You want to Navigate to in the spinner!!", Snackbar.LENGTH_LONG).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(MapboxF.this, options);
            }
        });
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.414016, 36.919520))
                .title("Nyeri Refferal ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.4284662, 36.933448))
                .title("Mathari ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.4734511, 37.1051102))
                .title("PCEA TumuTumu ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.327445, 36.906135))
                .title("Mweiga ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.413824, 36.953926))
                .title("Mary-Immaculate ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.426137, 36.963272))
                .title("PGH ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.422063, 36.952575))
                .title("Aga Khan ")
                .snippet("Hospital"));
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(-0.4283863, 36.935835))
                .title("OutSpan ")
                .snippet("Hospital"));

    }

    @Override
    public void onMapClick(@NonNull LatLng point){
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

        //todo: Facility selected
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facility = (String) adapterView.getItemAtPosition(i);
                if (i==1)
                {
                    destinationCoord = Point.fromLngLat( 36.919520, -0.414016);
                    destinationPosition = Point.fromLngLat( -36.919520,-0.414016);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==2){
                    destinationCoord = Point.fromLngLat(  36.933448,-0.4284662);
                    destinationPosition = Point.fromLngLat(  36.933448,-0.4284662);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==3){
                    destinationCoord = Point.fromLngLat( 37.1051102,-0.4734511);
                    destinationPosition = Point.fromLngLat( 37.1051102,-0.4734511);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==4){
                    destinationCoord = Point.fromLngLat( 36.906135,-0.327445);
                    destinationPosition = Point.fromLngLat( 36.906135,-0.327445);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==5){
                    destinationCoord = Point.fromLngLat( 36.953926,-0.413824);
                    destinationPosition = Point.fromLngLat( 36.953926,-0.413824);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==6){
                    destinationCoord = Point.fromLngLat( 36.963272,-0.426137);
                    destinationPosition = Point.fromLngLat( 36.963272,-0.426137);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==7){

                    destinationCoord = Point.fromLngLat( 36.952575,-0.422063);
                    destinationPosition = Point.fromLngLat( 36.952575,-0.422063);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else if (i==8){
                    destinationCoord = Point.fromLngLat( 36.935835,-0.4283863);
                    destinationPosition = Point.fromLngLat( 36.935835,-0.4283863);
                    originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                    getRoute(originPosition, destinationPosition);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.color.go4vac);

                }
                else {
                    Snackbar.make(view, "Choose a Facility You want to Navigate to in the spinner", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this);
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            originLocation = locationComponent.getLastKnownLocation();

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "user_location_permission_explanation", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(this, "user_location_permission_not_granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}