package com.example.tiobob.parkscanada;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ListParkActivity extends ActionBarActivity {

    // class members
    private TextView tv_title;
    private int parkId = 0;
    private String parkEng = "";
    private String parkFre = "";

    private List<FeesMaster> values;
    private DataBaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_park);

        // return the intent that started this activity
        Intent intentExtras = getIntent();

        // get extras out of intent
        parkId = intentExtras.getIntExtra("ids", 0);
        parkEng = intentExtras.getStringExtra("parkEn");
        parkFre = intentExtras.getStringExtra("parkFr");

        // get a reference to text views
        tv_title = (TextView) findViewById(R.id.tv_park_title);

        // set appropriate language (default is english)
        setLanguage();

        // get all information for specific park
        ParkLookup parkLookup = new ParkLookup();
        parkLookup.execute();
    }

    private class ParkLookup extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            dbHelper = new DataBaseOpenHelper(ListParkActivity.this);
            dbHelper.openDb();

            values = dbHelper.getParkFeeTypes(parkId);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            setListAdapter();
        }
    }

    private void setLanguage(){
        // set language to english
        if(MyConstants.LANGUAGE == 1){
            tv_title.setText(parkEng);
        }
        // set language to french
        else{
            tv_title.setText(parkFre);
        }

        // update list language if list initialized
        if(values != null){
            setListAdapter();
        }
    }

    private void setListAdapter(){

        //get list view reference and clear adapter
        ListView lv = (ListView) findViewById(R.id.lv_all_feetypes_list);
        lv.setAdapter(null);

        //create adapter array & attach it to ListView
        final ArrayAdapter<FeesMaster> adapter = new ArrayAdapter<FeesMaster>(ListParkActivity.this,
                android.R.layout.simple_expandable_list_item_1, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // lookup the park selected
                FeesMaster feeLookup = values.get(position);

                // create intent to start activity and put park id
                Intent parkInfo = new Intent(view.getContext(), ListFeeActivity.class);
                parkInfo.putExtra("ids", feeLookup.get_id());
                parkInfo.putExtra("parkEn", parkEng);
                parkInfo.putExtra("parkFr", parkFre);

                // start the activity
                startActivity(parkInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_park, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // english chosen, change & display all text in english
        if (id == R.id.action_english) {

            MyConstants.LANGUAGE = 1;
            setLanguage();

            return true;
        }
        // french chosen, change & display all text in french
        else if(id == R.id.action_french){

            MyConstants.LANGUAGE = 2;
            setLanguage();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
