package com.example.tiobob.parkscanada;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ListFeeActivity extends ActionBarActivity {

    // class members
    private TextView tv_title;
    private TextView tv_master_desc;
    private TextView tv_fee_desc;
    private TextView tv_sub_fee_desc;
    private TextView tv_price_Gst;

    private String parkEng = "";
    private String parkFre = "";
    private int masterFeeId = 0;

    private FeesMaster fm;
    private DataBaseOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fee);

        // return the intent that started this activity
        Intent intentExtras = getIntent();

        // get extras out of intent
        masterFeeId = intentExtras.getIntExtra("ids", 0);
        parkEng = intentExtras.getStringExtra("parkEn");
        parkFre = intentExtras.getStringExtra("parkFr");

        // get a reference to text views
        tv_title = (TextView) findViewById(R.id.tv_fee_title);
        tv_master_desc = (TextView) findViewById(R.id.tv_master_title);
        tv_fee_desc = (TextView) findViewById(R.id.tv_fee_description);
        tv_sub_fee_desc = (TextView) findViewById(R.id.tv_subfee_description);
        tv_price_Gst = (TextView) findViewById(R.id.tv_fee_price);

        // set language
        setLanguage();

        FeeLookup feeLookup = new FeeLookup();
        feeLookup.execute();
    }

    private class FeeLookup extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            dbHelper = new DataBaseOpenHelper(ListFeeActivity.this);
            dbHelper.openDb();

            fm = dbHelper.getParkFee(masterFeeId);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //populate text views with info from database
            setTextViewInfo();
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

        if(fm != null){
            setTextViewInfo();
        }
    }

    private void setTextViewInfo(){

        if(MyConstants.LANGUAGE == 1){
            tv_master_desc.setText(fm.get_feeDescEng());
            tv_fee_desc.setText(fm.get_feeIdDesc());
            tv_sub_fee_desc.setText(fm.get_subFeeIdDesc());
            tv_price_Gst.setText(fm.get_priceGstEn());
        }
        else{
            tv_master_desc.setText(fm.get_feeDescFre());
            tv_fee_desc.setText(fm.get_feeIdDescFr());
            tv_sub_fee_desc.setText(fm.get_subFeeIdDescFr());
            tv_price_Gst.setText(fm.get_priceGstFr());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_fee, menu);
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
