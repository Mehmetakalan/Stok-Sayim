package com.example.pethoalpar.zxingexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import de.mxapplications.openfiledialog.OpenFileDialog;

public class MainActivity extends ActionBarActivity {

    private Button cks,kontsay,kontsuzsay,hakkimizda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();
        cks = (Button) this.findViewById(R.id.btn_cks);
        kontsay = (Button) this.findViewById(R.id.btn_kontsay);
        kontsuzsay = (Button) this.findViewById(R.id.btn_kontsuzsay);
        hakkimizda = (Button) this.findViewById(R.id.btn_hakkimizda);

        kontsay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), kontrollusay.class);
                startActivity(i);
            }
        });
        kontsuzsay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),kontrolsuzsay.class);
                startActivity(i);
            }
        });
        cks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        hakkimizda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Hakkimizda.class);
                startActivity(i);
            }
        });

    }
}
