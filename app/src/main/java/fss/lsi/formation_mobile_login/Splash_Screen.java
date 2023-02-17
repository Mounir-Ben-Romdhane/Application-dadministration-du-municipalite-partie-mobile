package fss.lsi.formation_mobile_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {
    private static int SPLASH_TIMER=3000;
    ImageView backgroundImage;
    TextView poweredByLine;

    Animation sideAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //hooks
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.powered_by_line);

        /*
        SharedPreferences preferences = getSharedPreferences("firstLogin",MODE_PRIVATE);
        String first = preferences.getString("first","");
        if (first.equals("1")){
            startActivity(new Intent(Splash_Screen.this, HomeActivity.class));
        }

         */

        //Animations
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set Animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime = preferences.getBoolean("firstTime",true);

                if (isFirstTime){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("firstTime",false);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    editor.apply();

                }else if (!isFirstTime){
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }




                /*
                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                 */

            }
        },SPLASH_TIMER);
    }
}