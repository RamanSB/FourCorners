package ramansb.fourcorners;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Game extends Activity{


    static int WIDTH;
    static int HEIGHT;
    private GestureDetectorCompat gestureDetector;
    public static boolean canvasColor;
    public static boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sound = getIntent().getBooleanExtra("Sound", StartActivity.sound);
        canvasColor = getIntent().getBooleanExtra("CanvasType", StartActivity.canvasWhite);
        Display display = getWindowManager().getDefaultDisplay();
        WIDTH = display.getWidth();
        HEIGHT = display.getHeight();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);


        setContentView(new GamePanel(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void onBackPressed() {}
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

