package orihd.orihd;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 30;
    private TextView textView;
    private Handler handler = new Handler();
    private Switch filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        textView = (TextView) findViewById(R.id.textView3);
        filter = (Switch) findViewById(R.id.switch1);
        //Long operation by thread


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter.isChecked()) {

                    new Thread(new Runnable() {
                        public void run() {
                            while (progressStatus < 100 && filter.isChecked()) {
                                progressStatus += 1;
                                //Update progress bar with completion of operation
                                handler.post(new Runnable() {
                                    public void run() {
                                        progressBar.setProgress(progressStatus);
                                        textView.setText(progressStatus + "%");
                                    }
                                });
                                try {
                                    // Sleep for 200 milliseconds.
                                    //Just to display the progress slowly
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });


    }




    }




