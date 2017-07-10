package orihd.orihd;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Matthew on 7/10/2017.
 */

public class SpinnerActivity extends customize implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        switch(pos){
            case 0:
                FragmentTab1.tagval = 1;

                break;
            case 1:
                FragmentTab1.tagval = 2;
                Toast.makeText(getApplicationContext(), "Tagval is 2", Toast.LENGTH_LONG).show();
                break;
            case 2:
                FragmentTab1.tagval = 3;
                break;
            case 3:
                FragmentTab1.tagval = 4;
                break;
            case 4:
                FragmentTab1.tagval = 5;
                break;
            case 5:
                FragmentTab1.tagval = 6;
                break;

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}