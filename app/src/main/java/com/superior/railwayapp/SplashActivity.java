package com.superior.railwayapp;

// Activity import - har screen ke liye zaroori hai
import androidx.appcompat.app.AppCompatActivity;

// Android OS imports
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    // AppCompatActivity ko extend kiya - matlab ye ek screen hai
    // extends ka matlab: SplashActivity ko AppCompatActivity ki
    // sari properties mil gayi hain (back button, title bar etc)

    @Override
    // @Override matlab: parent class (AppCompatActivity) ka
    // ye method hum apne hisaab se likh rahe hain
    protected void onCreate(Bundle savedInstanceState) {
        // onCreate - jab activity pehli baar banti hai tab ye chalta hai
        // Activity Lifecycle ka pehla method hai ye

        super.onCreate(savedInstanceState);
        // super keyword - parent class ka onCreate call kiya
        // ye zaroori hai warna app crash ho jayegi

        setContentView(R.layout.activity_splash);
        // ye line batati hai ke is activity ka design
        // activity_splash.xml file mein hai

        // Handler - kuch time baad koi kaam karne ke liye
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 3 second baad LoginActivity pe jao
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                // Intent - ek activity se doosri activity pe jaane ka tarika
                startActivity(intent);
                // LoginActivity shuru karo
                finish();
                // finish() - SplashActivity band karo taake
                // back press karne pe wapas na aaye
            }
        }, 3000);
        // 3000 = 3 seconds
    }

    @Override
    protected void onStart() {
        super.onStart();
        // onStart - activity visible hoti hai user ko
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume - activity foreground mein aa jaati hai
        // user interact kar sakta hai
    }

    @Override
    protected void onPause() {
        super.onPause();
        // onPause - activity background mein jaati hai
    }

    @Override
    protected void onStop() {
        super.onStop();
        // onStop - activity bilkul invisible ho jaati hai
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // onDestroy - activity memory se hata di jaati hai
        // Activity Lifecycle ka aakhri method
    }
}