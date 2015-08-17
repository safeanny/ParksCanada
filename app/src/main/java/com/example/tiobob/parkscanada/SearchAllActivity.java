package com.example.tiobob.parkscanada;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class SearchAllActivity extends ActionBarActivity {

    // class members

    private AutoCompleteTextView acTv;
    private Button btnSearch;

    private TextView tvSearchTitle;
    private TextView tvSearchDesc;

    private List<ParksId> values;
    private DataBaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        // get reference to text views
        acTv = (AutoCompleteTextView) findViewById(R.id.ac_tv_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        tvSearchTitle = (TextView) findViewById(R.id.tv_search_title);
        tvSearchDesc = (TextView) findViewById(R.id.tv_search_desc);

        // set appropriate language (default is english)
        setLanguage();

        // disable buttons until database look-up is complete
        acTv.setEnabled(false);
        btnSearch.setEnabled(false);

        searchForPark search = new searchForPark();
        search.execute();
    }

    // onClick handler for search button
    public void searchPark(View view) {

        // check if user has entered data into the text view
        if(acTv != null && !acTv.getText().toString().equals("")){

            // disable buttons until search is complete
            acTv.setEnabled(false);
            btnSearch.setEnabled(false);

            getParkIdFromName pId = new getParkIdFromName();
            pId.execute();
        }
        else{
            // implement message to user to enter a park name!
        }
    }

    // class used to get all park names from db for auto complete text view
    private class searchForPark extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            dbHelper = new DataBaseOpenHelper(SearchAllActivity.this);
            dbHelper.openDb();

            // get list of parks
            values = dbHelper.getAllParks();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //set adapter for auto complete text view
            setAutoCompleteAdapter();

            // enable buttons and text views
            acTv.setEnabled(true);
            btnSearch.setEnabled(true);
        }
    }

    // class used to get park id from database in in a background process
    private class getParkIdFromName extends AsyncTask<String, String, String>{

        ParksId p;

        @Override
        protected String doInBackground(String... params) {

            dbHelper = new DataBaseOpenHelper(SearchAllActivity.this);
            dbHelper.openDb();

            p = dbHelper.getParkId(acTv.getText().toString());

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(p != null){
                // create intent to start activity and put park id
                Intent parkInfo = new Intent(SearchAllActivity.this, ListParkActivity.class);
                parkInfo.putExtra("ids", p.get_id());
                parkInfo.putExtra("parkEn", p.get_lowEngFull());
                parkInfo.putExtra("parkFr", p.get_lowFreFull());

                // enable buttons and text views
                acTv.setEnabled(true);
                btnSearch.setEnabled(true);

                // start the activity
                startActivity(parkInfo);
            }
            else{
                // implement message that no park was found!

                // enable buttons and text views
                acTv.setEnabled(true);
                btnSearch.setEnabled(true);
            }
        }
    }

    private void setLanguage(){
        // set language to english
        if(MyConstants.LANGUAGE == 1){
            acTv.setHint(R.string.search_hint_en);
            btnSearch.setText(R.string.search_btn_en);
            tvSearchTitle.setText(R.string.search_title_en);
            tvSearchDesc.setText(R.string.search_description_en);
        }
        // set language to french
        else{
            acTv.setHint(R.string.search_hint_fr);
            btnSearch.setText(R.string.search_btn_fr);
            tvSearchTitle.setText(R.string.search_title_fr);
            tvSearchDesc.setText(R.string.search_description_fr);
        }

        // update list language if list initialized
        if(values != null){
            setAutoCompleteAdapter();
        }
    }

    private void setAutoCompleteAdapter(){

        // clear previous adapter
        acTv.setAdapter(null);
        // create adapter array & attach it to ListView
        final ArrayAdapter<ParksId> adapter = new ArrayAdapter<ParksId>(SearchAllActivity.this,
                android.R.layout.simple_expandable_list_item_1, values);
        acTv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_all, menu);
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
