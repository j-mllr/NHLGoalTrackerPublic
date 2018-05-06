package nhlgoalnotification.nhlgoaltracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GameInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        TextView home = (TextView) findViewById(R.id.textView6);
        TextView away = (TextView) findViewById(R.id.textView10);
        TextView goalsHome = (TextView) findViewById(R.id.textView15);
        TextView goalsAway = (TextView) findViewById(R.id.textView16);
        TextView periodInfo = (TextView) findViewById(R.id.textView21);

        home.setText(getIntent().getExtras().getString("homeTeam"));
        away.setText(getIntent().getExtras().getString("awayTeam"));
        goalsHome.setText(getIntent().getExtras().getString("scoreHome"));
        goalsAway.setText(getIntent().getExtras().getString("scoreAway"));
        String periodString = getIntent().getExtras().getString("period") + ":" + getIntent().getExtras().getString("timeRemaining");
        periodInfo.setText(periodString);
    }
}
