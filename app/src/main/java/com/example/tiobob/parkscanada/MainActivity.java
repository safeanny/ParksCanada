package com.example.tiobob.parkscanada;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*  ******* REFERENCES ******** */

// www.sitepoint.ocm/learning-to-parse-data-in-your-android-app/
// stackoverflow.com/questions/180302602/where-to-place-assets-folder-in-android-studio
// all previous labs (mostly networking and database stuff)

/* **************************** */


public class MainActivity extends ActionBarActivity {

    // class members for all view references
    private TextView tv_w;
    private TextView tv_wd;

    private Button btn_search;
    private Button btn_view;

    private DataBaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String TAG = "ParksCanadaApp";
        String DB_FILE_NAME = "ParksCanada.db";
        Context context = getApplicationContext();
        String destPath = "/data/data/" + context.getPackageName() + "/databases/";

        // get references to activity TextViews
        tv_w = (TextView) findViewById(R.id.tv_welcome_string);
        tv_wd = (TextView) findViewById(R.id.tv_welcome_descript);

        // get references to activity buttons
        btn_search = (Button) findViewById(R.id.btn_wel_search);
        btn_view = (Button) findViewById(R.id.btn_wel_view);

        // set appropriate language (default is english)
        setLanguage();

        // create database folder if not found
        File destPathFile =  new File(destPath);
        if (!destPathFile.exists()) {
            destPathFile.mkdirs();
        }

        // create database if not found
        File destFile = new File(destPath + DB_FILE_NAME);
        if (!destFile.exists())
        {
            // disable buttons while background thread is doing work
            btn_search.setEnabled(false);
            btn_view.setEnabled(false);

            // run the database create in a background thread
            callDbHelper newThread = new callDbHelper();
            newThread.execute();
        }
    }

    // class used to call db helper to check database status
    private class callDbHelper extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            // get access to database (will call onCreate automatically if not exist)
            dbHelper = new DataBaseOpenHelper(MainActivity.this);
            // give DataBaseOpenHelper access to application context
            dbHelper.setContext(getApplicationContext());
            dbHelper.openDb();

            // have access to database - add table data from xml files
            dbHelper.parseFees();
            dbHelper.parseSubFees();
            dbHelper.parseParks();
            dbHelper.parseMasterList();

            // not using any data - close database
            //dbHelper.close();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // enable buttons
            btn_search.setEnabled(true);
            btn_view.setEnabled(true);
        }
    }

    private void setLanguage(){
        // set language to english
        if(MyConstants.LANGUAGE == 1){
            tv_w.setText(R.string.welcome_string_en);
            tv_wd.setText(R.string.welcome_app_description_en);

            btn_search.setText(R.string.welcome_btn_search_en);
            btn_view.setText(R.string.welcome_btn_view_en);
        }
        // set language to french
        else{
            tv_w.setText(R.string.welcome_string_fr);
            tv_wd.setText(R.string.welcome_app_description_fr);

            btn_search.setText(R.string.welcome_btn_search_fr);
            btn_view.setText(R.string.welcome_btn_view_fr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if(id == R.id.action_about){

            // display about app information
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    // view all parks - on click handler
    public void viewAllParks(View view) {
        //create intent used to start the new activity
        Intent intent = new Intent(this,ListAllActivity.class);
        startActivity(intent);
    }

    // search parks (lookup) - on click handler
    public void searchParks(View view) {
        Intent intent = new Intent(this,SearchAllActivity.class);
        startActivity(intent);
    }
}
