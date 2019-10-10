package com.example.pethoalpar.zxingexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Amonsis on 12.7.2016.
 */
public class kontrollusay extends Activity {

    EditText barkod,dosyadi,adettxt;
    TextView barkodtkr;
    Button liste,kapat,kaydet,stokacik,kamera,dosyaekle,yukle;

    ArrayList<String> barkodlar=new ArrayList<String>();
    ArrayList<String> barkod2=new ArrayList<String>();
    ArrayList<String> adet=new ArrayList<String>();
    ArrayList<String> adet2=new ArrayList<String>();
int tt=0;
    String a="0";
    String[] yemk;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontrollusay_layout);

        final Activity activity = this;
        database=new Database(this);

        barkod=(EditText) findViewById(R.id.txt_barkod);
        barkodtkr=(TextView) findViewById(R.id.txt_barkodtkr);
        liste=(Button) findViewById(R.id.btn_liste);
        kapat=(Button) findViewById(R.id.btn_kapat);
        kaydet=(Button) findViewById(R.id.btn_kaydet);
        adettxt=(EditText) findViewById(R.id.txt_adet);
        dosyadi=(EditText) findViewById(R.id.txt_dosyaadi);
        kamera=(Button) findViewById(R.id.btn_kamera);
        dosyaekle=(Button) findViewById(R.id.btn_dosyaekle);
        yukle=(Button) findViewById(R.id.btn_ykle);

    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
    dlgAlert.setMessage("Lütfen ilk önce Dosya Ekle'yip sonra Yükle butonuna basarak ekletiniz.");
    dlgAlert.setTitle("Uyarıı!!");
    dlgAlert.setPositiveButton("Tamam", null);
    dlgAlert.setCancelable(true);
    dlgAlert.create().show();

        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        dosyaekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(kontrollusay.this,fileexplorer.class);
                startActivity(a);

            }
        });
        yukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    gel();
                tt=1;
                    Toast.makeText(getApplicationContext(),"Başarıyla Yüklenmiştir.",Toast.LENGTH_LONG).show();
            }
        });

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        barkod.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        int a=0;
                        if(tt==1)
                        {
                            if(barkod.getText().toString().equals(""))
                            {
                                Toast.makeText(getApplicationContext(),"Barkod Boş Geçilemez!!",Toast.LENGTH_LONG).show();
                            }
                            else
                            {

                                for(int i=0;i<yemk.length;i++)
                                {
                                    if(yemk[i].equals(barkod.getText().toString()))
                                    {
                                        a=1;
                                        break;
                                    }
                                }
                                if(a==0)
                                {
                                    as();
                                }
                                else
                                {
                                    listeyaz();
                                }
                            }
                            barkod.setText("");
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Lütfen önce dosya ekleyip , yükleyiniz.",Toast.LENGTH_LONG).show();
                        }

                        return true;
                    }
                return false;
            }
        });
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(kontrollusay.this,liste_goster.class);
                i.putStringArrayListExtra("barkod", barkodlar);
                i.putStringArrayListExtra("adet", adet);
                startActivity(i);
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
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
kamera.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(tt==1)
        {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Taranıyor");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Lütfen önce dosya ekleyip , yükleyiniz.",Toast.LENGTH_LONG).show();
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
                int a=0;
                if(barkod.getText().toString().equals(""))
                {
                        Toast.makeText(getApplicationContext(),"Barkod Boş Geçilemez!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    for(int i=0;i<yemk.length;i++)
                    {
                        if(yemk[i].equals(barkod.getText().toString()))
                        {
                            a=1;
                            break;
                        }
                    }
                    if(a==0)
                    {
                        as();
                    }
                    else
                    {
                        listeyaz();
                    }
                }


            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void gel()
    {
        Intent i=getIntent();
        String txtismi=i.getStringExtra("txtismi");
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        File file = new File(sdcard,txtismi);

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        String ad=text.toString();

        yemk=ad.split(";");

    }
    public void as()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Bu barkod listede tanımlı değildir. Yinede eklemek istiyor musunuz?");
        dlgAlert.setTitle("Önemli!!");
        dlgAlert.setPositiveButton("Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                        listeyaz();
                    }
                });
        dlgAlert.setNegativeButton("Hayır",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void listeyaz()
    {
        if(barkodlar.size()==0)
        {
            barkodlar.add(barkod.getText().toString());
            adet.add(adettxt.getText().toString());
            Toast.makeText(getApplicationContext(),"Başarıyla eklendi",Toast.LENGTH_LONG).show();
            barkod.setText("");
        }
        else
        {
            int f=0,g=0;
            for(int i=0;i<barkodlar.size();i++)
            {
                if (barkodlar.get(i).toString().equals(barkod.getText().toString()))
                {
                    int a=Integer.parseInt(adet.get(i));
                    int b=Integer.parseInt(adettxt.getText().toString());
                    a=a+b;
                    adet.set(i,String.valueOf(a));
                    Toast.makeText(getApplicationContext(),"Adet olarak eklendi",Toast.LENGTH_LONG).show();
                    g=1;
                    break;
                }
            }
            if(g==0)
            {
                barkodlar.add(barkod.getText().toString());
                adet.add(adettxt.getText().toString());
                Toast.makeText(getApplicationContext(),"Başarıyla eklendi",Toast.LENGTH_LONG).show();
            }
            barkod.setText("");
        }
    }


    /*protected void barkodEkle(String barkod) {
        SQLiteDatabase db=database.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("ad", barkod);
//Kaydet ya da herhangi bir sorun oluştuğunda fırlat
        db.insertOrThrow("barkodlar_listesi",null,cv);
    }*/
}