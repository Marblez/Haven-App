package orihd.orihd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView name = (TextView) findViewById(R.id.textView24);
        TextView location = (TextView) findViewById(R.id.textView25);
        TextView aqi = (TextView) findViewById(R.id.textView28);
        TextView particulates = (TextView) findViewById(R.id.textView22);
        TextView harmfulgas = (TextView) findViewById(R.id.textView26);
        TextView batterylevel = (TextView) findViewById(R.id.textView29);
        TextView timestamp = (TextView) findViewById(R.id.textView27);

        String newname;
        String newaqi;
        String newparticulates;
        String newharmfulgas;
        String newlocation;
        String newbatterylevel;
        String newtimestamp;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newname= null;
            } else {
                newname= extras.getString("The Name");
                newaqi= extras.getString("The AQI");
                newparticulates= extras.getString("The Particulates");
                newharmfulgas= extras.getString("The HarmfulGas");
                newlocation= extras.getString("The Location");
                newbatterylevel= extras.getString("The BatteryLevel");
                newtimestamp= extras.getString("The Timestamp");
                String[] newname2 = newname.split("_");
                String newname3 = newname2[0]+ " " + newname2[1];
                name.setText("Name: "+newname3);
                aqi.setText("AQI: " + newaqi);
                particulates.setText("Particulates: "+ newparticulates);
                harmfulgas.setText("Harmful Gas: "+ newharmfulgas);
                location.setText("Location: "+ newlocation);
                batterylevel.setText("Battery Level: " + newbatterylevel +"%");
                timestamp.setText("TimeStamp: "+newtimestamp);

            }
        } else {
            // BACKUP FOR NO SAVED INSTANCE STATE
            newname= (String) savedInstanceState.getSerializable("The name");
            newaqi= (String) savedInstanceState.getSerializable("The AQI");
            newparticulates= (String) savedInstanceState.getSerializable("The Particulates");
            newharmfulgas= (String) savedInstanceState.getSerializable("The HarmfulGas");
            newlocation= (String) savedInstanceState.getSerializable("The Location");
            newbatterylevel= (String) savedInstanceState.getSerializable("The BatteryLevel");
            newtimestamp= (String) savedInstanceState.getSerializable("The Timestamp");

            String[] newname2 = newname.split("_");
            String newname3 = newname2[0]+ " " + newname2[1];
            name.setText("Name: "+newname3);
            aqi.setText("AQI: " + newaqi);
            particulates.setText("Particulates: "+ newparticulates);
            harmfulgas.setText("Harmful Gas: "+ newharmfulgas);
            location.setText("Location: "+ newlocation);
            batterylevel.setText("Battery Level: " + newbatterylevel);
            timestamp.setText("TimeStamp: "+newtimestamp);
        }


        /*
        location.setText(newlocation);
        aqi.setText(newaqi);
        particulates.setText(newparticulates);
        harmfulgas.setText(newharmfulgas);
        batterylevel.setText(newbatterylevel);
        timestamp.setText(newtimestamp);
        */



    }
}
