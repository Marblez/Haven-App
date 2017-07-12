package orihd.orihd;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.Manifest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import orihd.orihd.Manifest.permission;

public class FragmentTab2 extends Fragment implements OnMapReadyCallback{
    public double longitudev;
    public double latitudev;
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
 = new TrackGPS();
        return rootView;
    }



    @Override
    public void onMapReady(GoogleTTrack Map googleMap) {
        TrackGPS NewGPS = new TrackGPS();
        longitudev = NewGPS.getLongitude();
        latitudev = NewGPS.getLatitude();
        LatLng current = new LatLng(latitudev,longitudev);
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(current)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }


}
