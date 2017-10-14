package orihd.orihd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.GoogleMap;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.Manifest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

import org.json.JSONException;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import orihd.orihd.Manifest.permission;

public class FragmentTab2 extends Fragment implements OnMapReadyCallback{
    public double longitudev;
    public double latitudev;
    public static int colorset;
    public String mycolor;
    public static String color;
    public GoogleMap googleMap;
    public List<MyItem> items;

    private IconGenerator mIconGenerator;
    private ShapeDrawable mColoredCircleBackground;
    private SparseArray<BitmapDescriptor> mIcons = new SparseArray();
    private float mDensity;
    private Context mContext;


    private ClusterManager<MyItem> mClusterManager;
    public static double testing;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    static double arrayvalue[] = new double[100000];
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
        //Intent i= new Intent(getContext(), MyService.class);
        //getContext().startService(i);
/*
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

            }

            @Override
            public void onError(Status status) {

            }
        });
*/

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


/*
    private void setUpClusterer() {
        mClusterManager.setAnimation(false);
        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, getMap());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }
    */

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        TrackGPS NewGPS = new TrackGPS(getContext());

            longitudev = NewGPS.getLongitude();
            latitudev = NewGPS.getLatitude();
            LatLng current = new LatLng(latitudev, longitudev);

            //START SERVICE
            double doubleArray[] = {latitudev,longitudev};


            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
            googleMap.addMarker(new MarkerOptions().position(current).icon(getMarkerIcon("#00f921"))
                    .title("Current Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));


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

                //String testval = Integer.toString(count);
                //Toast.makeText(getContext(), testval, Toast.LENGTH_SHORT).show();
                int limit = count -1;


                // Initialize the manager with the context and the map.
                // (Activity extends context, so we can pass 'this' in the constructor.)
                mClusterManager = new ClusterManager<MyItem>(getContext(), googleMap);
                mClusterManager.setRenderer(new MyClusterRenderer(getContext(), googleMap,
                        mClusterManager));
                // Point the map's listeners at the listeners implemented by the cluster
                // manager.
                googleMap.setOnCameraIdleListener(mClusterManager);
                googleMap.setOnMarkerClickListener(mClusterManager);

                for(int x = 0; x <limit; x+=3){
                    double aqitemp;
                    double lattest;
                    double longtest;

                    aqitemp = arrayvalue[x];
                    int aqitest = (int) aqitemp;
                    color = aqiprocess(aqitest);
                    lattest = arrayvalue[x+1];
                    longtest = arrayvalue[x+2];
                    String AQITAG = new String();
                    String AQIMSG = new String();
                    //CHANGE AQI VALUE HERE



                    // DONE CHANGING AQI VALUE

                    MyItem offsetItem = new MyItem(lattest, longtest,aqitest,color);

                    mClusterManager.addItem(offsetItem);


                    //googleMap.addMarker(new MarkerOptions().position(newlocation).icon(getMarkerIcon(color))
                    //);
                    //googleMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        for(int x = 0; x <6; x+=3){
            double aqitemp;
            double lattest;
            double longtest;

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
            */



    }

    private void setUpClusterer(GoogleMap googleMap, double lat, double lng) {
        // Position the map.
        if(Settings.LocationStatus()== 1 ) {
            TrackGPS NewGPS = new TrackGPS(getContext());
            longitudev = NewGPS.getLongitude();
            latitudev = NewGPS.getLatitude();
            LatLng current = new LatLng(latitudev, longitudev);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        }
        else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 15));
        }
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getContext(), googleMap);
        mClusterManager.clearItems();
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        addItems(lat,lng);

    }

    private void addItems(double lat, double lng){

            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem2 = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem2);
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
    public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {

        private final IconGenerator mClusterIconGenerator = new IconGenerator(getContext());

        public MyClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
            // Set minimum cluster size
            setMinClusterSize(3);

        }


        @Override
        protected void onBeforeClusterItemRendered(MyItem item,
                                                   MarkerOptions markerOptions) {




            String colorprivate = item.getColor();
            switch(colorprivate){
                case "#00f921":
                    BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#e5e514":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#ff9d00":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#ff1500":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#5500b7":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#966600":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    markerOptions.icon(markerDescriptor);
                    break;
                default:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    markerOptions.icon(markerDescriptor);
                    break;
            }

            /*switch(color){
                case "#00f921":

                    //  BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    BitmapDescriptor markerDescriptor = getMarkerIcon("color");
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#e5e514":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#ff9d00":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#ff1500":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#5500b7":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    markerOptions.icon(markerDescriptor);
                    break;
                case "#966600":
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    markerOptions.icon(markerDescriptor);
                    break;
                default:
                    markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    markerOptions.icon(markerDescriptor);
                    break;
            }
            */



        }

        @Override
        protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }



        @Override
        protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
            int colordef = 0;
            double colordef2 = 0;
            int tempvar = 0;
            double sumtotal = 0;
            double size = cluster.getSize();

            Collection<MyItem> tempitems = cluster.getItems();
            for(MyItem item : tempitems) {
                tempvar = item.getAQIVALUE();
                sumtotal = sumtotal + tempvar;
            }

            colordef2 = sumtotal/size;

            if(colordef2 < 50){
                colordef = 1;
            }
            else if(colordef2 <100){
                colordef=2;
            }
            else if (colordef2 < 150){
                colordef =3;
            }
            else if (colordef2 < 200){
                colordef =4;
            }
            else if (colordef2 < 300){
                colordef =5;
            }
            else{
                colordef = 6;
            }
            switch(colordef){
                case 1:
                    BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    markerOptions.icon(markerDescriptor);
                    break;
                case 2:
                    BitmapDescriptor markerDescriptor2 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    markerOptions.icon(markerDescriptor2);
                    break;
                case 3:
                    BitmapDescriptor markerDescriptor3 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    markerOptions.icon(markerDescriptor3);
                    break;
                case 4:
                    BitmapDescriptor markerDescriptor4 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    markerOptions.icon(markerDescriptor4);
                    break;
                case 5:
                    BitmapDescriptor markerDescriptor5 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    markerOptions.icon(markerDescriptor5);
                    break;
                case 6:
                    BitmapDescriptor markerDescriptor6 = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    markerOptions.icon(markerDescriptor6);
                    break;
                default:
                    BitmapDescriptor markerDescriptord = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    markerOptions.icon(markerDescriptord);
                    break;

            }

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

    public String aqiprocess(int aqivalue){
        if(aqivalue<=50){
            mycolor = "#00f921";
        }
        else if (aqivalue <=100){
            mycolor = "#e5e514";
        }
        else if (aqivalue <=150){
            mycolor = "#ff9d00";
        }
        else if (aqivalue <=200){
            mycolor = "#ff1500";
        }
        else if (aqivalue <=300){
            mycolor = "#5500b7";
        }
        else if (aqivalue <=300){
            mycolor = "#966600";
        }
        else{
            mycolor = "#966600";
        }

        //NOTIFICATION CHECK//////////////////////////////////





        /////////////////////////////////////////////////////

        return mycolor;
    }


    }




