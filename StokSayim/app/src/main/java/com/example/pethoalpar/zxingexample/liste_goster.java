package com.example.pethoalpar.zxingexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amonsis on 11.8.2016.
 */
public class liste_goster extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_goster);

        list=(ListView) findViewById(R.id.listView);
        Intent i=getIntent();

        ArrayList<String> barkod = getIntent().getStringArrayListExtra("barkod");
        ArrayList<String> adet = getIntent().getStringArrayListExtra("adet");
        list.setAdapter(new dataListAdapter(barkod,adet));

    }
    class dataListAdapter extends BaseAdapter {
        ArrayList<String> barkod=new ArrayList<String>();
        ArrayList<String> adet=new ArrayList<String>();


        dataListAdapter() {
            barkod = null;
            adet = null;
        }

        public dataListAdapter(ArrayList<String> text, ArrayList<String> text1) {
            barkod = text;
            adet = text1;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return barkod.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.satir_layout, parent, false);
            TextView title, detail;

            title = (TextView) row.findViewById(R.id.txt_barkoddd);
            detail = (TextView) row.findViewById(R.id.txt_adettt);

            title.setText(barkod.get(position));
            detail.setText(adet.get(position));


            return (row);
        }
    }

}
