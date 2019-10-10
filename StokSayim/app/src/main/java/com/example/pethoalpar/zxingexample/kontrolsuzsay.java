package com.example.pethoalpar.zxingexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Amonsis on 12.7.2016.
 */
public class kontrolsuzsay extends Activity {

    EditText barkod,dosyadi,adetxt;
    CheckBox tek,surekli;
    Button liste,kapat,kaydet,kamera;
    TextView barkodtkr;
    int durum=0;


    ArrayList<String> barkodlar=new ArrayList<String>();
    ArrayList<String> adet=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontrolsuzsay_layout);

        final Activity activity = this;
        barkod=(EditText) findViewById(R.id.txt_barkod);
        barkodtkr=(TextView) findViewById(R.id.txt_barkodtkr);
        adetxt=(EditText) findViewById(R.id.txt_adet);
        liste=(Button) findViewById(R.id.btn_liste);
        kapat=(Button) findViewById(R.id.btn_kapat);
        kaydet=(Button) findViewById(R.id.btn_kaydet);
        kamera=(Button) findViewById(R.id.btn_kamera);
        dosyadi=(EditText) findViewById(R.id.txt_dosyaadi);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        barkod.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(barkod.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Barkod Boş Geçilemez!!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        listeyaz();
                    }
                    return true;
                }
                return false;
            }
        });

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Lütfen barkodu okutmadan önce adetini ayarlayınız.");
        dlgAlert.setTitle("Önemli!!");
        dlgAlert.setPositiveButton("Tamam", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();


        kamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Taranıyor");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t=new Intent(kontrolsuzsay.this,liste_goster.class);
                t.putStringArrayListExtra("barkod", barkodlar);
                t.putStringArrayListExtra("adet", adet);
                startActivity(t);
            }
        });
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dosyadi.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Lütfen dosya adı alanını boş bırakmayınız.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    try {
                        File myFile = new File("/sdcard/" + dosyadi.getText().toString() + ".txt");

                        if (!myFile.exists()) {
                            myFile.createNewFile();
                        }
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        int c = 0;
                        for (int i = 0; i < barkodlar.size(); i++) {
                            myOutWriter.append(barkodlar.get(i).toString() + ";" + adet.get(i).toString() + ";");
                        }
                        myOutWriter.close();
                        fOut.close();
                        Toast.makeText(getApplicationContext(),"Başarıyla Kaydedildi.",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("kontrolsuzsay", "Tarama İptal");
                Toast.makeText(this, "İptal Edildi", Toast.LENGTH_LONG).show();
            } else {
                Log.d("kontrolsuzsay", "Tarandı");
                barkod.setText(result.getContents());
                barkodtkr.setText(result.getContents());
if(barkod.getText().toString().equals(""))
{
    Toast.makeText(getApplicationContext(),"Barkod Boş Geçilemez!!",Toast.LENGTH_LONG).show();
}
                else
{
    listeyaz();
}


                barkod.setText("");

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void listeyaz()
    {
        if(barkodlar.size()==0)
        {
            barkodlar.add(barkod.getText().toString());
            adet.add(adetxt.getText().toString());
            Toast.makeText(getApplicationContext(),"Başarıyla eklendi",Toast.LENGTH_LONG).show();
        }
        else
        {
            int f=0,g=0;
            for(int i=0;i<barkodlar.size();i++)
            {
                if (barkodlar.get(i).toString().equals(barkod.getText().toString()))
                {
                    int a=Integer.parseInt(adet.get(i));
                    int b=Integer.parseInt(adetxt.getText().toString());
                    a=a+b;
                    adet.set(i,String.valueOf(a));
                    Toast.makeText(getApplicationContext(),"Adet olarak eklendi.",Toast.LENGTH_LONG).show();
                    g=1;
                    break;
                }
            }
            if(g==0)
            {
                barkodlar.add(barkod.getText().toString());
                adet.add(adetxt.getText().toString());
                Toast.makeText(getApplicationContext(),"Başarıyla eklendi",Toast.LENGTH_LONG).show();
            }
        }
    }

}
