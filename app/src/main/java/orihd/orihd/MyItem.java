package orihd.orihd;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Matthew on 7/21/2017.
 */


public class MyItem implements ClusterItem{
    private final LatLng mPosition;
    public String mTitle;
    public String mSnippet;
    public String ClusterColor;
    public int AQIVALUE;

    public MyItem(double lat, double lng, int aqi, String ClusterColor2){
        ClusterColor = ClusterColor2;
        mPosition = new LatLng(lat,lng);
        AQIVALUE = aqi;
    }
    public MyItem(double lat, double lng, String title, String snippet, String ClusterColor2){
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        ClusterColor = ClusterColor2;
    }

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public String getColor(){ return ClusterColor;}

    public int getAQIVALUE(){
        return AQIVALUE;
    }

}