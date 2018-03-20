package com.viluvasa.radioonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASA on 20-Mar-18.
 * http://www.viluvasa.com
 * IG : https://www.instagram.com/ajianaz/
 */

public class MainActivity extends AppCompatActivity {
    private Spinner combo;
    private TextView txtDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi
        combo = (Spinner) findViewById(R.id.combo);
        txtDetails = (TextView) findViewById(R.id.txtDetails);

        //set value to list
        final List<Radio> listRadio = new ArrayList<>();
        Radio radio4 = new Radio("Radio KITA Cirebon", "http://live.radiosunnah.net/;");
        Radio radio6 = new Radio("A Taste of Jazz", "http://listen.57fm.com/toj");

        listRadio.add(radio4);
        listRadio.add(radio6);

        final String[] radioArr = new String[listRadio.size()];
        for (int i = 0; i < listRadio.size(); i++) {
            radioArr[i] = listRadio.get(i).getName();
        }

        //set value to autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, radioArr);
        combo.setAdapter(adapter);
        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                int pos = -1;

                for (int j = 0; j < radioArr.length; j++) {
                    if (radioArr[j].equals(selection)) {
                        pos = j;
                        break;
                    }
                }

                callRadio(listRadio.get(pos).getUrl(), listRadio.get(pos).getName());
                txtDetails.setText(listRadio.get(pos).getName() + " is Now Playing");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callRadio(String url, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", name);
        Intent serviceOn = new Intent(this, StreamingService.class);
        serviceOn.putExtras(bundle);
        startService(serviceOn);
    }

    class Radio {
        private String name, url;

        public Radio() {
        }

        public Radio(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
