package ramansb.fourcorners;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView bg = (ImageView) findViewById(R.id.imageViewBgInfo);
        Animation bgAnim = new TranslateAnimation(0.0f, 0.0f, -50.0f, 50.0f);
        bgAnim.setDuration(4000);
        bgAnim.setRepeatCount(Animation.INFINITE);
        bgAnim.setRepeatMode(Animation.REVERSE);
        bgAnim.setFillAfter(true);
        bg.startAnimation(bgAnim);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(InfoActivity.this, StartActivity.class);
                startActivity(returnIntent);
            }
        });

        TextView infoTitle = (TextView) findViewById(R.id.info_textMain);

        Animation animationAlphaInfoTitle = new AlphaAnimation(1.0f, 0.75f);
        animationAlphaInfoTitle.setDuration(1000);
        animationAlphaInfoTitle.setRepeatCount(Animation.INFINITE);
        animationAlphaInfoTitle.setRepeatMode(Animation.REVERSE);
        infoTitle.startAnimation(animationAlphaInfoTitle);

        Animation blinkTextButtonAnim = new AlphaAnimation(1.0f, 0.5f);
        blinkTextButtonAnim.setDuration(1500);
        blinkTextButtonAnim.setRepeatMode(Animation.REVERSE);
        blinkTextButtonAnim.setRepeatCount(Animation.INFINITE);

        TextView mainmenuText = (TextView) findViewById(R.id.menuReturnText);
        mainmenuText.startAnimation(blinkTextButtonAnim);
        backButton.startAnimation(blinkTextButtonAnim);
    }
}
