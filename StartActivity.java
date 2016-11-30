package ramansb.fourcorners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    static boolean sound = true;
    static boolean canvasWhite = true;
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        ImageView bg = (ImageView) findViewById(R.id.imageViewBg);
        final ImageButton soundButton = (ImageButton) findViewById(R.id.sound_button);
        TextView title = (TextView) findViewById(R.id.title);

        TextView playText = (TextView) findViewById(R.id.play_text);

        TextView canvasText = (TextView) findViewById(R.id.canvas_text);
        final CheckBox whiteCanvas = (CheckBox) findViewById(R.id.checkBoxWhite);
        final CheckBox blackCanvas = (CheckBox) findViewById(R.id.checkBoxBlack);

        TextView infoText = (TextView) findViewById(R.id.info_text);

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound = !sound;
                if (sound == false) {
                    soundButton.setImageResource(R.drawable.sound_disabled);
                    //Set music to off
                    Log.e("AUDIO", "Music off");
                } else {
                    soundButton.setImageResource(R.drawable.sound_option);
                    //MUSIC On
                    Log.e("AUDIO", "Music on");
                }
            }
        });

        Animation animation = new TranslateAnimation(0.0f, 0.0f,
                        -50.0f, 50.0f);
        animation.setDuration(5000);
        animation.setRepeatCount(TranslateAnimation.INFINITE);
        animation.setRepeatMode(TranslateAnimation.REVERSE);
        animation.setFillAfter(true);
        bg.startAnimation(animation);

        Animation animationAlphaTitle = new AlphaAnimation(1.0f, 0.75f);
        animationAlphaTitle.setDuration(1000);
        animationAlphaTitle.setRepeatCount(Animation.INFINITE);
        animationAlphaTitle.setRepeatMode(Animation.REVERSE);
        title.startAnimation(animationAlphaTitle);

        Animation blinkTextAnim = new AlphaAnimation(1.0f, 0.5f);
        blinkTextAnim.setDuration(500);
        blinkTextAnim.setRepeatCount(TranslateAnimation.INFINITE);
        blinkTextAnim.setRepeatMode(TranslateAnimation.REVERSE);

        playText.startAnimation((blinkTextAnim));

        whiteCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whiteCanvas.isChecked()) {
                    blackCanvas.setChecked(false);
                    canvasWhite = true;
                } else {
                    blackCanvas.setChecked(true);
                    canvasWhite = false;
                }
            }
        });

        blackCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blackCanvas.isChecked()) {
                    whiteCanvas.setChecked(false);
                    canvasWhite = false;
                } else {
                    whiteCanvas.setChecked(true);
                    canvasWhite = true;
                }
            }
        });

        playText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGame = new Intent(StartActivity.this, Game.class);
                startGame.putExtra("CanvasType", canvasWhite);
                startGame.putExtra("Sound", sound);
                startActivity(startGame);
            }
        });

        infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(StartActivity.this, InfoActivity.class);
                startActivity(infoIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {}
}




