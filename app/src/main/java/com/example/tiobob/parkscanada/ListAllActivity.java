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


public class ListAllActivity extends ActionBarActivity {

    // class members
    private TextView tv_title;
    private TextView tv_desc;

    private List<ParksId> values;
    private DataBaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);

        // get references to activity TextViews
        tv_title = (TextView) findViewById(R.id.tv_view_all_title);
        tv_desc = (TextView) findViewById(R.id.tv_view_all_desc);

        // set appropriate language (default is english)
        setLanguage();

        // look up db in a background thread
        listAllParks newThread = new listAllParks();
        newThread.execute();

    }

    // class used to get all parks in a background process
    private class listAllParks extends AsyncTask<String, String, String> {

        //private List<ParksId> values;

        @Override
        protected String doInBackground(String... params) {

            dbHelper = new DataBaseOpenHelper(ListAllActivity.this);
            dbHelper.openDb();

            // get list of parks
            values = dbHelper.getAllParks();

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
            tv_title.setText(R.string.view_all_title_en);
            tv_desc.setText(R.string.view_all_desc_en);
        }
        // set language to french
        else{
            tv_title.setText(R.string.view_all_title_fr);
            tv_desc.setText(R.string.view_all_desc_fr);
        }

        // update list language if list initialized
        if(values != null){
            setListAdapter();
        }
    }

    private void setListAdapter(){
        //get list view reference and clear adapter
        ListView lv = (ListView) findViewById(R.id.lv_all_parks_list);
        lv.setAdapter(null);

        //create adapter array & attach it to ListView
        final ArrayAdapter<ParksId> adapter = new ArrayAdapter<ParksId>(ListAllActivity.this,
                android.R.layout.simple_expandable_list_item_1, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // lookup the park selected
                ParksId parkLookup = values.get(position);

                // create intent to start activity and put park id
                Intent parkInfo = new Intent(view.getContext(), ListParkActivity.class);
                parkInfo.putExtra("ids", parkLookup.get_id());
                parkInfo.putExtra("parkEn", parkLookup.get_lowEngFull());
                parkInfo.putExtra("parkFr", parkLookup.get_lowFreFull());

                // start the activity
                startActivity(parkInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_all, menu);
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
