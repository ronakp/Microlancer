package com.diginals.microlancer;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends FragmentActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
        GoogleMap.OnMyLocationButtonClickListener, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String TAG = "DebugPoint";
    TextView name;
    TextView email;
    String uidrec = "";
    private static final LatLng RBC = new LatLng(43.641143, -79.378187);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.textVieww2);
        email = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        uidrec = intent.getStringExtra(Login.EXTRA_MSG);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("users").child(uidrec).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // String a = dataSnapshot.getValue("/users/"+uidrec+"/name");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Park Confirmed", Toast.LENGTH_SHORT).show();
        // marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.carpark));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.postjob) {
            /*Intent postjobint = new Intent(this, PostJob.class);
            startActivity(postjobint);*/
        } else if (id == R.id.history) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.logout) {
            Intent logoutint = new Intent(this, Login.class);
            startActivity(logoutint);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.641143, -79.378187)).title("88 Queens Quay"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.306729, -83.063314)).title("567 Aurther St."));
        /*mMap.addMarker(new MarkerOptions().position(new LatLng(42.306491, -83.071522)).title("332 Parkington Ave.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carnotpark)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.309997, -83.066720)).title("Bean Ladle Maxican")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carnotpark)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.309822, -83.058966)).title("Sio Teory")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carnotpark)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.294081, -83.063202)).title("234 Bapa St.")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carnotpark)));*/
        /*Uri gmmIntentUri = Uri.parse("geo:43.641143, -79.378187");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RBC, 10));
        // Add a camera idle listener.
       /* mMap.setOnCameraIdleListener(new OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mMessageView.setText("CameraChangeListener: " + mMap.getCameraPosition());
            }
        });*/
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            PermissionUtils.requestPermission(this, 1,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if (mMap != null)
        {
            mMap.setMyLocationEnabled(true);
        }
    }
    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}
