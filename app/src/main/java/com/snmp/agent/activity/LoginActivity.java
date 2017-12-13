package com.snmp.agent.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.snmp.agent.R;
import com.snmp.agent.service.AgentService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zirco on 12/12/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private int nbclick = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView myImage = (ImageView) findViewById(R.id.imageView);
        myImage.setClickable(true);


        myImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nbclick = nbclick + 1;
                //Toast.makeText(LoginActivity.this,"IMAGE CLIC"+nbclick,Toast.LENGTH_SHORT).show();

                if(nbclick == 5) {
                    // Check if we're running on Android 6.0 (M) or higher
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Fingerprint API only available on from Android 6.0 (M)
                        FingerprintManager fingerprintManager = (FingerprintManager) LoginActivity.this.getSystemService(Context.FINGERPRINT_SERVICE);
                        if (!fingerprintManager.isHardwareDetected()) {
                            // Device doesn't support fingerprint authentication
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startService(intent);
                        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                            // User hasn't enrolled any fingerprints to authenticate with
                        } else {
                            // Everything is ready for fingerprint authentication

                            Toast.makeText(LoginActivity.this,"EASTER EGG",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FingerprintActivity.class);
                            startActivity(intent);
                            nbclick = 0;

                        }
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startService(intent);



                }
            }
        });
        //Toast.makeText(LoginActivity.this,"YO",Toast.LENGTH_LONG).show();

    }


}
