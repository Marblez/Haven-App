package orihd.orihd;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Matthew on 7/21/2017.
 */

public class MyItem implements ClusterItem{
    private final LatLng mPosition;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
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

}