package ramansb.fourcorners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class EndGameActivity extends Activity {

    private SharedPreferences preferences;
    public static final String highScorePref = "HighScorePref";
    public static final String highScore = "HighScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_game);
        preferences = getApplication().getSharedPreferences(highScorePref, Context.MODE_PRIVATE);
        
        int localHs = preferences.getInt(highScore, 0);

        if(GamePanel.score > preferences.getInt(highScore, 0)) {

            preferences.edit().putInt(highScore, GamePanel.score).commit();
        }

        RelativeLayout endBg = (RelativeLayout) findViewById(R.id.end_activity);

        if(Game.canvasColor){
            endBg.setBackgroundColor(Color.WHITE);
        }else{
            endBg.setBackgroundColor(Color.BLACK);
        }

        int user_score = getIntent().getExtras().getInt("Score");
        TextView highScore = (TextView) findViewById(R.id.highscore);
        TextView userScore = (TextView) findViewById(R.id.score);

        highScore.setText("High Score \n" + preferences.getInt(EndGameActivity.highScore, 0));
        userScore.setText("Score \n" + user_score);

        GamePanel.score = 0;

        ImageButton mainmenuButton = (ImageButton) findViewById(R.id.blue_circle);
        ImageButton retryButton = (ImageButton) findViewById(R.id.red_circle);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retryIntent = new Intent(EndGameActivity.this ,Game.class);
                StartActivity.counter = 0;
                startActivity(retryIntent);
            }
        });

        mainmenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainmenuIntent = new Intent(EndGameActivity.this, StartActivity.class);
                StartActivity.counter = 0;
                StartActivity.canvasWhite = true;
                Game.sound = true;
                startActivity(mainmenuIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

}
