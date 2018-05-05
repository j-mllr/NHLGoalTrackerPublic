package nhlgoalnotification.nhlgoaltracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Scheduled extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled);

        EditText gameTime = (EditText) findViewById(R.id.editText);
        String s = "Game starts at:" + getIntent().getExtras().get("gameTime");
        gameTime.setText(s);
    }
}
