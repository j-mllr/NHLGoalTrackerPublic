package nhlgoalnotification.nhlgoaltracker;

import Model.Game;
import Model.LiveEventProvider;
import Model.Schedule;
import Model.ScheduleProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private String selectedTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Teams));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        initializeData();

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0){
                    selectedTeam = myAdapter.getItem(i);
                    if (Schedule.getInstance().isPlaying(selectedTeam)){
                        checkFeed();
                    } else {
                        startActivity(new Intent(MainActivity.this, NoGames.class));
                    }
                }

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeData() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    try {
                        ScheduleProvider sp = new ScheduleProvider();
                        sp.getSchedule();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private void checkFeed(){
        Game game = Schedule.getInstance().findGame(selectedTeam);
        LiveEventProvider feed = new LiveEventProvider();

        try {
            feed.getEvents(game);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
