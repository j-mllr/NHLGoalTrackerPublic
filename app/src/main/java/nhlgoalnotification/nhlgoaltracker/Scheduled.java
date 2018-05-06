package nhlgoalnotification.nhlgoaltracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Scheduled extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled);

        TextView gameTime = (TextView) findViewById(R.id.textView3);
        String s = "Game starts at:" + getIntent().getExtras().get("gameTime");
        gameTime.setText(s);
    }
}
