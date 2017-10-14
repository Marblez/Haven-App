package orihd.orihd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class mypet extends AppCompatActivity implements View.OnClickListener {

    ImageButton feed;
    ImageButton play;
    ImageButton clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);
        feed = (ImageButton) findViewById(R.id.feed);
        play = (ImageButton) findViewById(R.id.play);
        clean = (ImageButton) findViewById(R.id.clean);

        feed.setOnClickListener(this);
        play.setOnClickListener(this);
        clean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feed:
                Toast.makeText(getApplicationContext(), "Display feed animation", Toast.LENGTH_LONG).show();

                break;

            case R.id.play:
                Toast.makeText(getApplicationContext(), "Display play animation", Toast.LENGTH_LONG).show();

                break;


        }
    }
}


