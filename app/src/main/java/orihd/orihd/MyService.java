package orihd.orihd;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Matthew on 7/23/2017.
 */

public class MyService extends Service {
public Context context;
    private double[] arrayvalue;
    public static String distance;
    public static String state;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //NOTIFICATION SETUP
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.alert);
        notification.setTicker("This is the Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("AQI Alert!");
        notification.setContentText("The AQI in your surrounding has reached an unhealthy level");

        TrackGPS NewGPS = new TrackGPS(this);
        final double longitudev = NewGPS.getLongitude();
        final double latitudev = NewGPS.getLatitude();

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
                        if(count != 0){
                            if(count%3==0){
                                double testlong = arrayvalue[count-1];
                                double testlat = arrayvalue[count-2];
                                double aqivalue = arrayvalue[count-3];
                                double indexlong = Math.abs(longitudev-testlong);
                                double indexlat = Math.abs(latitudev-testlat);
                                double distlong = indexlong*indexlong;
                                double distlat = indexlat*indexlat;
                                double truedist = distlat+distlong;
                                double distance = Math.sqrt(truedist);
                                if(aqivalue > 100 && distance > 1){
                                    //SEND PUSH NOTIFICATION

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




        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }


}