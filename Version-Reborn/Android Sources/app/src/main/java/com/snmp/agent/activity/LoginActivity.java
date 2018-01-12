package com.snmp.agent.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snmp.agent.R;
import com.snmp.agent.service.AgentService;

/**
 * Created by zirco on 12/12/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private int nbclick = 0;
    private String username;
    private String password;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView myImage = (ImageView) findViewById(R.id.imageView);
        Button myConnexion = (Button) findViewById(R.id.loginButton);
        final EditText myUsername = (EditText) findViewById(R.id.username);
        final EditText myPassword = (EditText) findViewById(R.id.password);

        myImage.setClickable(true);

        myConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = myUsername.getText().toString();
                password = myPassword.getText().toString();

                if(username.equals("admin") && password.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou Mot de passe incorrect", Toast.LENGTH_LONG).show();
                }



            }
        });


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
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            //Toast.makeText(LoginActivity.this,"PAS DE LECTEUR D'EMPREINTE",Toast.LENGTH_SHORT).show();
                            startService(intent);
                        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                            // User hasn't enrolled any fingerprints to authenticate with
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            // Everything is ready for fingerprint authentication

                            //Toast.makeText(LoginActivity.this,"EASTER EGG",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FingerprintActivity.class);
                            startActivity(intent);
                            nbclick = 0;

                        }
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }





                }
            }
        });
        //Toast.makeText(LoginActivity.this,"YO",Toast.LENGTH_LONG).show();

    }


}
