package orihd.orihd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class FragmentTab5 extends Fragment {
    private BluetoothAdapter mBluetoothAdapter;
    Context mcontext;
    private final static String TAG = FragmentTab3.class.getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_fragment_tab5, container, false);

        String fbid = FragmentTab1.facebookidfuck;
        final String[] stringarray2 = FragmentTab1.stringarray;

//m jkml,m mn  m, k km km nmnmmmmnhgcsrftgbnhbvdfghjklmnbgfdsdfghjkljhgfdfghjkl;lkjhgfdfghjkl;l,mknuyftdszwf7g8hpjk
        // Error check for later use


        int afdsize = getthearraysize(stringarray2);
        Log.d(TAG, "adfsize is " +Integer.toString(afdsize));
        Log.d(TAG, "afd[0] is " +stringarray2[0]);
        while(afdsize>0) {
            String afd = stringarray2[afdsize - 1];
            FirebaseDatabase database2 = FirebaseDatabase.getInstance();

            if (afdsize > 0) {
                DatabaseReference databaseRef2 = database2.getReference("Users").child(afd);

                databaseRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count2 = 0;
                        String total = "";
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String temp = snapshot.getValue().toString();
                            total = total + " " + temp;
                            count2++;
                        }


                        // DISPLAYING STRINGS
                        String[] parts = total.split(Pattern.quote("}"));
                        String part2 = parts[1];
                        String[] splitted = part2.split("\\s+");
                        String hellafinal = "Name: " + splitted[1] + "\n" + "AQI: " + splitted[2] + "\n" + "Particulates: " + splitted[3] + "\n" + "Harmful Gas: " + splitted[4] + "\n" + "Battery Level: " + splitted[5] + "% " + "\n" + "TimeStamp: " + splitted[6];
                        // Setting String in the TextView
                        String tempstring = FragmentTab1.citynamuuu;

                        MyItem2 tempitem = new MyItem2(splitted[2], splitted[1], splitted[3],
                                splitted[6], splitted[4], splitted[5], splitted[6]);

                        final List<MyItem2> itemlist = new ArrayList<MyItem2>(100);
                        itemlist.add(new MyItem2(splitted[2], splitted[1], splitted[3],
                                splitted[6], splitted[4], splitted[5], splitted[7]));
                        Log.d(TAG, "split4 is" + splitted[4]);
                        Log.d(TAG, "split5 is" + splitted[5]);
                        Log.d(TAG, "split6 is" + splitted[6]);
                        Log.d(TAG, "TimeStamp2" + splitted[7]);
                        int ethereum = getthearraysize(stringarray2);
                        Log.d(TAG, "ethereum size is " + Integer.toString(ethereum));
                        String[] fuckschool = new String[ethereum];
                        for (int yee = 0; yee < ethereum; yee++) {
                            MyItem2 temporaryitemlmao = itemlist.get(yee);
                            String yeeschool = temporaryitemlmao.getname();
                            Log.d(TAG, "Yeeschool is " + yeeschool);
                            String[] namesplit = yeeschool.split(Pattern.quote("_"));
                            Log.d(TAG, "Namesplit is " + namesplit[0]);
                            String newname = namesplit[0] + " " + namesplit[1];
                            fuckschool[yee] = newname;
                            try {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.da_item, fuckschool);
                                ListView list = (ListView) rootView.findViewById(R.id.ultimatelistview);
                                list.setAdapter(adapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MyItem2 displayitem = itemlist.get(position);
                                        String thename = displayitem.getname();
                                        String thelocation = displayitem.getloc();
                                        String theaqi = displayitem.getaqi();
                                        String theparticulates = displayitem.getparticulates();
                                        String theharmfulgas = displayitem.getharmfulgas();
                                        String thebatterylevel = displayitem.getbatterylevel();
                                        String thetimestamp = displayitem.getTimestamp();

                                        Intent intent = new Intent(getActivity(), InfoActivity.class);
                                        intent.putExtra("The Name", thename);
                                        intent.putExtra("The Particulates", theharmfulgas);
                                        intent.putExtra("The AQI", theaqi);
                                        intent.putExtra("The HarmfulGas", thebatterylevel);
                                        intent.putExtra("The BatteryLevel", theparticulates);
                                        intent.putExtra("The Timestamp", thetimestamp);
                                        intent.putExtra("The Location", thelocation);

                                        startActivity(intent);
                                    }

                                });
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }

            afdsize = afdsize-1;
        }
            return rootView;

    }


    private int getthearraysize(String[] smartassarray){
        int counterint = 0;
        for(int y = 0; y < 100; y++){
            if(smartassarray[counterint]!=null){
                counterint++;
            }
        }
        return counterint;
    }

}







