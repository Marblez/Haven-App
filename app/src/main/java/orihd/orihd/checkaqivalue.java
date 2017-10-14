package orihd.orihd;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by Matthew on 8/9/2017.
 */

public class checkaqivalue extends Service {
    public int onStartCommand(Intent intent, int flags, int startId) {
        final double arrayvalue[] = new double[100000];
        TrackGPS NewGPS = new TrackGPS(getContext());
        final double longitudev = NewGPS.getLongitude();
        final double latitudev = NewGPS.getLatitude();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Location");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;

                double tempdistance = 99999;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                        double doubleval = snapshot2.getValue(double.class);
                        arrayvalue[count] = doubleval;
                        if (count != 0) {
                            if (count % 3 == 0) {
                                double testlong = arrayvalue[count - 1];
                                double testlat = arrayvalue[count - 2];
                                double aqivalue = arrayvalue[count - 3];
                                double indexlong = Math.abs(longitudev - testlong);
                                double indexlat = Math.abs(latitudev - testlat);
                                double distlong = indexlong * indexlong;
                                double distlat = indexlat * indexlat;
                                double truedist = distlat + distlong;
                                double distance = Math.sqrt(truedist);
                                if (tempdistance > distance) {
                                    tempdistance = distance;
                                    int AQI = (int) aqivalue;

                                }

                            }
                        }
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return Service.START_STICKY;}

    public IBinder onBind(Intent intent) {
        return null;

}
}

