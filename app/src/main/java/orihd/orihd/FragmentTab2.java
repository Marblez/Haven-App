package orihd.orihd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import orihd.orihd.Manifest.permission;

public class FragmentTab2 extends Fragment implements OnMapReadyCallback{
    public double longitudev;
    public double latitudev;
    public static double testing;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    static double arrayvalue[] = new double[100];
    public static FragmentTab2 newInstance() {
        FragmentTab2 fragment = new FragmentTab2();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_tab2, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");

        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {

                        arrayvalue = new double[100];
                        Double doubleval = snapshot2.getValue(Double.class);
                        arrayvalue[count] = doubleval;
                        count++;

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        */

        return rootView;
    }


    public double[] returnarray(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");



        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        double doubleval = snapshot2.getValue(double.class);
                        arrayvalue[count] = doubleval;
                        count++;
                    }
                }



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return arrayvalue;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        TrackGPS NewGPS = new TrackGPS(getContext());
        longitudev = NewGPS.getLongitude();
        latitudev = NewGPS.getLatitude();
        LatLng current = new LatLng(latitudev, longitudev);

        googleMap.addMarker(new MarkerOptions().position(current).icon(getMarkerIcon("#00f921"))
                .title("Current Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));


        for(int x = 0; x <6; x+=3){
            double aqitemp;
            double lattest;
            double longtest;
            arrayvalue = returnarray();
            aqitemp = arrayvalue[x];
            int aqitest = (int) aqitemp;
            lattest = arrayvalue[x+1];
            longtest = arrayvalue[x+2];
            LatLng newlocation = new LatLng(lattest,longtest);
            String color;
            switch(aqitest){
                case 1:
                    color = "#00f921";
                    break;
                case 2:
                    color = "#e5e514";
                    break;
                case 3:
                    color = "#ff9d00";
                    break;
                case 4:
                    color = "#ff1500";
                    break;
                case 5:
                    color = "#1b0289";
                    break;
                case 6:
                    color = "#000000";
                    break;
                default:
                    color = "#e5e514";
                    break;
            }
            googleMap.addMarker(new MarkerOptions().position(newlocation).icon(getMarkerIcon(color))
                    );
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation));
        }


    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
            try {
                getFragmentManager().beginTransaction().remove(mapFragment).commit();
            }
            catch(java.lang.RuntimeException e){
            gohome();
        }

    }

    public void gohome(){
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    public static class Location {
        String aqi;
        String longitude;
        String latitude;

    }


    }




