package com.example.tiobob.parkscanada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    // private data members for db version and db name

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ParksCanada.db";
    private SQLiteDatabase database;
    public Context _context;

    /* *********** MASTER LIST TABLE DATA *************** */

    private static final String TABLE_MASTER_LIST = "MasterList";
    private static final String COL_MASTER_LIST_ID = "Ids";
    private static final String COL_MASTER_LIST_PARK_ID = "ParkId";
    private static final String COL_MASTER_LIST_FEE_ID = "FeeId";
    private static final String COL_MASTER_LIST_SUBFEE_ID = "SubFeeId";
    private static final String COL_MASTER_FEE_DESC_ENG = "FeeDescEng";
    private static final String COL_MASTER_FEE_DESC_FRE = "FeeDescFre";
    private static final String COL_MASTER_PRICE_GST_EN = "PriceGstEn";
    private static final String COL_MASTER_PRICE_GST_FR = "PriceGstFr";
    private static final String COL_MASTER_NAT_LOCAL = "NatLocal";
    private static final String COL_MASTER_TRAVEL_TRADE = "TravelTrade";
    private static final String COL_MASTER_PURPOSE = "Purpose";
    private static final String COL_MASTER_CLASS = "Class";

    private static final String TABLE_CREATE_MASTER = "create table "
            + TABLE_MASTER_LIST + "("
            + COL_MASTER_LIST_ID + " integer primary key, "
            + COL_MASTER_LIST_PARK_ID + " integer, "
            + COL_MASTER_LIST_FEE_ID + " integer, "
            + COL_MASTER_LIST_SUBFEE_ID + " integer, "
            + COL_MASTER_FEE_DESC_ENG + " text not null, "
            + COL_MASTER_FEE_DESC_FRE + " text not null, "
            + COL_MASTER_PRICE_GST_EN + " text not null, "
            + COL_MASTER_PRICE_GST_FR + " text not null, "
            + COL_MASTER_NAT_LOCAL + " text not null, "
            + COL_MASTER_TRAVEL_TRADE + " text not null, "
            + COL_MASTER_PURPOSE + " text not null, "
            + COL_MASTER_CLASS + " text not null);";

    private String[] allColMasterFees = { COL_MASTER_LIST_PARK_ID, COL_MASTER_LIST_FEE_ID, COL_MASTER_LIST_SUBFEE_ID,
            COL_MASTER_FEE_DESC_ENG, COL_MASTER_FEE_DESC_FRE, COL_MASTER_PRICE_GST_EN, COL_MASTER_PRICE_GST_FR,
            COL_MASTER_NAT_LOCAL, COL_MASTER_TRAVEL_TRADE, COL_MASTER_PURPOSE };

    private String[] listColMasterFees = { COL_MASTER_LIST_ID, COL_MASTER_LIST_PARK_ID, COL_MASTER_FEE_DESC_ENG, COL_MASTER_FEE_DESC_FRE };

    /* *********** PARKS TABLE DATA *************** */

    private static final String TABLE_PARKS = "Parks";
    private static final String COL_PARKS_ID = "Ids";
    private static final String COL_PARKS_UP_ENG_FULL = "UpEngFull";
    private static final String COL_PARKS_UP_FRE_FULL = "UpFreFull";
    private static final String COL_PARKS_LOW_ENG_FULL = "LowEngFull";
    private static final String COL_PARKS_LOW_FRE_FULL = "LowFreFull";
    private static final String COL_PARKS_LOW_ENG = "LowEng";
    private static final String COL_PARKS_LOW_FRE = "LowFre";
    private static final String COL_UP_ALPHA_FR_FULL = "UpAlphaFrFull";
    private static final String COL_LOW_FRNAME_FIRST = "LowFrNameFirst";
    private static final String COL_UP_NO_EN_FULL = "UpNoEnFull";
    private static final String COL_UP_NO_FR_FULL = "UpNoFrFull";

    private static final String TABLE_CREATE_PARKS= "create table "
            + TABLE_PARKS + "("
            + COL_PARKS_ID + " integer primary key, "
            + COL_PARKS_UP_ENG_FULL + " text not null, "
            + COL_PARKS_UP_FRE_FULL + " text not null, "
            + COL_PARKS_LOW_ENG_FULL + " text not null, "
            + COL_PARKS_LOW_FRE_FULL + " text not null, "
            + COL_PARKS_LOW_ENG + " text not null, "
            + COL_PARKS_LOW_FRE + " text not null, "
            + COL_UP_ALPHA_FR_FULL + " text not null, "
            + COL_LOW_FRNAME_FIRST + " text not null, "
            + COL_UP_NO_EN_FULL + " text not null, "
            + COL_UP_NO_FR_FULL + " text not null);";

    private String[] listColumnsParks = { COL_PARKS_ID, COL_PARKS_LOW_ENG_FULL, COL_PARKS_LOW_FRE_FULL };

    /* *********** FEES TABLE DATA *************** */

    private static final String TABLE_FEES = "Fees";
    private static final String COL_FEES_ID = "Ids";
    private static final String COL_FEES_FR_DESCRIPT = "FrDescription";
    private static final String COL_FEES_EN_DESCRIPT = "EnDescription";

    private static final String TABLE_CREATE_FEES = "create table "
            + TABLE_FEES + "("
            + COL_FEES_ID + " integer primary key, "
            + COL_FEES_EN_DESCRIPT + " text not null, "
            + COL_FEES_FR_DESCRIPT + " text not null);";

    /* *********** SUB-FEES TABLE DATA *************** */

    private static final String TABLE_SUB_FEES = "SubFees";
    private static final String COL_SUBFEE_ID = "Ids";
    private static final String COL_SUBFEES_FR_DESCRIPT = "FrDescription";
    private static final String COL_SUBFEES_EN_DESCRIPT = "EnDescription";

    private static final String TABLE_CREATE_SUB_FEES = "create table "
            + TABLE_SUB_FEES + "("
            + COL_SUBFEE_ID + " integer primary key, "
            + COL_SUBFEES_EN_DESCRIPT + " text not null, "
            + COL_SUBFEES_FR_DESCRIPT + " text not null);";


    // constructor
    public DataBaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // set application context from activity that called
    public void setContext(Context context) {
        this._context = context;
    }

    public void openDb(){
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create tables when db is first initialized
        db.execSQL(TABLE_CREATE_FEES);
        db.execSQL(TABLE_CREATE_SUB_FEES);
        db.execSQL(TABLE_CREATE_PARKS);
        db.execSQL(TABLE_CREATE_MASTER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // remove old db and re-create it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB_FEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_LIST);

        onCreate(db);
    }

    // parse data from fees.xml into database
    public void parseFees() {

        try{
            // get a reference to XMLPull parse from XmlPullParserFactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            // open fees.xml as an input stream
            InputStream in = _context.getAssets().open("fees.xml");
            // set input stream as input to the parser
            xpp.setInput(in, null);

            int eventType = xpp.getEventType();

            List<Fees> fees = new ArrayList<Fees>();
            Fees currentFee = null;

            while(eventType != XmlPullParser.END_DOCUMENT){

                String name = null;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // perform operations based on tag name
                        switch (name){
                            // new fee object
                            case "I_FEES":
                                currentFee = new Fees();
                                break;
                            case "ZFEE_ID":
                                currentFee.set_id(Integer.parseInt(xpp.nextText()));
                                break;
                            case "EN_DESCRIPTION":
                                currentFee.set_enDescription(xpp.nextText());
                                break;
                            case "FR_DESCRIPTION":
                                currentFee.set_frDescription(xpp.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // fee tag ended - add fee to list of fee objects
                        if(name.equals("I_FEES")){
                            fees.add(currentFee);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = xpp.next();
            }

            // look up size of list
            int size = fees.size();
            // iterate through list adding each object to database
            for(int i=0; i<size; i++){

                Fees f = fees.get(i);
                ContentValues values = new ContentValues();

                values.put(COL_FEES_ID, Integer.toString(f.get_id()));
                values.put(COL_FEES_EN_DESCRIPT, f.get_enDescription());
                values.put(COL_FEES_FR_DESCRIPT, f.get_frDescription());

                long insertId = database.insert(TABLE_FEES, null, values);
            }
        }
        catch (XmlPullParserException xe){

        }
        catch (IOException e){

        }
    }

    // parse data from subfees.xml into database
    public void parseSubFees(){

        try{
            // get a reference to XMLPull parse from XmlPullParserFactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            // open fees.xml as an input stream
            InputStream in = _context.getAssets().open("subfees.xml");
            // set input stream as input to the parser
            xpp.setInput(in, null);

            int eventType = xpp.getEventType();

            List<SubFees> fees = new ArrayList<SubFees>();
            SubFees currentFee = null;

            while(eventType != XmlPullParser.END_DOCUMENT){

                String name = null;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // perform operations based on tag name
                        switch (name){
                            // new sub-fee object
                            case "I_SUBFEES":
                                currentFee = new SubFees();
                                break;
                            case "ZSUBFEE_ID":
                                currentFee.set_id(Integer.parseInt(xpp.nextText()));
                                break;
                            case "EN_DESCRIPTION":
                                currentFee.setEnDescription(xpp.nextText());
                                break;
                            case "FR_DESCRIPTION":
                                currentFee.set_frDescription(xpp.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // fee tag ended - add fee to list of fee objects
                        if(name.equals("I_SUBFEES")){
                            fees.add(currentFee);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = xpp.next();
            }

            // look up size of list
            int size = fees.size();
            // iterate through list adding each object to database
            for(int i=0; i<size; i++){

                SubFees f = fees.get(i);
                ContentValues values = new ContentValues();

                values.put(COL_SUBFEE_ID, Integer.toString(f.get_id()));
                values.put(COL_SUBFEES_EN_DESCRIPT, f.get_enDescription());
                values.put(COL_SUBFEES_FR_DESCRIPT, f.get_frDescription());

                long insertId = database.insert(TABLE_SUB_FEES, null, values);
            }
        }
        catch (XmlPullParserException xe){

        }
        catch (IOException e){

        }
    }

    // parse data from parksid.xml into database
    public void parseParks(){

        try{
            // get a reference to XMLPull parse from XmlPullParserFactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            // open fees.xml as an input stream
            InputStream in = _context.getAssets().open("parksid.xml");
            // set input stream as input to the parser
            xpp.setInput(in, null);

            int eventType = xpp.getEventType();

            List<ParksId> parks = new ArrayList<ParksId>();
            ParksId currentPark = null;

            while(eventType != XmlPullParser.END_DOCUMENT){

                String name = null;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // perform operations based on tag name
                        switch (name){
                            // new sub-fee object
                            case "I_PRKS":
                                currentPark = new ParksId();
                                break;
                            case "ZPARK_ID":
                                currentPark.set_id(Integer.parseInt(xpp.nextText()));
                                break;
                            case "UP_ENG_FULL":
                                currentPark.set_upEngFull(xpp.nextText());
                                break;
                            case "UP_FRE_FULL":
                                currentPark.set_upFreFull(xpp.nextText());
                                break;
                            case "LOW_ENG_FULL":
                                currentPark.set_lowEngFull(xpp.nextText());
                                break;
                            case "LOW_FRE_FULL":
                                currentPark.set_lowFreFull(xpp.nextText());
                                break;
                            case "LOW_ENG":
                                currentPark.set_lowEng(xpp.nextText());
                                break;
                            case "LOW_FRE":
                                currentPark.set_lowFre(xpp.nextText());
                                break;
                            case "UP_ALPHA_FR_FULL":
                                currentPark.set_upAlphaFrFull(xpp.nextText());
                                break;
                            case "LOW_FR_NAME_1ST":
                                currentPark.set_lowFrNameFirst(xpp.nextText());
                                break;
                            case "UP_NO_EN_FULL":
                                currentPark.set_upNoEnFull(xpp.nextText());
                                break;
                            case "UP_NO_FR_FULL":
                                currentPark.set_upNoFrFull(xpp.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // fee tag ended - add fee to list of fee objects
                        if(name.equals("I_PRKS")){
                            parks.add(currentPark);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = xpp.next();
            }

            // look up size of list
            int size = parks.size();
            // iterate through list adding each object to database
            for(int i=0; i<size; i++){

                ParksId p = parks.get(i);
                ContentValues values = new ContentValues();

                values.put(COL_PARKS_ID, Integer.toString(p.get_id()));
                values.put(COL_PARKS_UP_ENG_FULL, p.get_upEngFull());
                values.put(COL_PARKS_UP_FRE_FULL, p.get_upFreFull());
                values.put(COL_PARKS_LOW_ENG_FULL, p.get_lowEngFull());
                values.put(COL_PARKS_LOW_FRE_FULL, p.get_lowFreFull());
                values.put(COL_PARKS_LOW_ENG, p.get_lowEng());
                values.put(COL_PARKS_LOW_FRE, p.get_lowFre());
                values.put(COL_UP_ALPHA_FR_FULL, p.get_upAlphaFrFull());
                values.put(COL_LOW_FRNAME_FIRST, p.get_lowFrNameFirst());
                values.put(COL_UP_NO_EN_FULL, p.get_upNoEnFull());
                values.put(COL_UP_NO_FR_FULL, p.get_upNoFrFull());

                long insertId = database.insert(TABLE_PARKS, null, values);
            }
        }
        catch (XmlPullParserException xe){

        }
        catch (IOException e){

        }
    }

    // parse data from fees_master.xml into database
    public void parseMasterList(){

        try{
            // get a reference to XMLPull parse from XmlPullParserFactory
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            // open fees.xml as an input stream
            InputStream in = _context.getAssets().open("feesmaster.xml");
            // set input stream as input to the parser
            xpp.setInput(in, null);

            int eventType = xpp.getEventType();

            List<FeesMaster> masterFees = new ArrayList<FeesMaster>();
            FeesMaster currentMaster = null;

            while(eventType != XmlPullParser.END_DOCUMENT){

                String name = null;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // perform operations based on tag name
                        switch (name){
                            // new sub-fee object
                            case "I_MAIN":
                                currentMaster = new FeesMaster();
                                break;
                            case "ZPARK_ID":
                                currentMaster.set_parkId(Integer.parseInt(xpp.nextText()));
                                break;
                            case "ZFEE_ID":
                                currentMaster.set_feeId(Integer.parseInt(xpp.nextText()));
                                break;
                            case "ZSUBFEE_ID":
                                currentMaster.set_subFeeId(Integer.parseInt(xpp.nextText()));
                                break;
                            case "FEEDESC_ENG":
                                currentMaster.set_feeDescEng(xpp.nextText());
                                break;
                            case "FEEDESC_FRE":
                                currentMaster.set_feeDescFre(xpp.nextText());
                                break;
                            case "PRICE_WITH_GST_EN":
                                currentMaster.set_priceGstEn(xpp.nextText());
                                break;
                            case "PRICE_WITH_GST_FR":
                                currentMaster.set_priceGstFr(xpp.nextText());
                                break;
                            case "NATIONAL_OR_LOCAL":
                                currentMaster.set_natLocal(xpp.nextText());
                                break;
                            case "TRAVEL_TRADE":
                                currentMaster.set_travelTrade(xpp.nextText());
                                break;
                            case "PURPOSE":
                                currentMaster.set_purpose(xpp.nextText());
                                break;
                            case "CLASS":
                                currentMaster.set_class(xpp.nextText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        // get the tag name
                        name = xpp.getName();
                        // fee tag ended - add fee to list of fee objects
                        if(name.equals("I_MAIN")){
                            masterFees.add(currentMaster);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = xpp.next();
            }

            // look up size of list
            int size = masterFees.size();
            // iterate through list adding each object to database
            for(int i=0; i<size; i++){

                FeesMaster fm = masterFees.get(i);
                ContentValues values = new ContentValues();

                values.put(COL_MASTER_LIST_PARK_ID, Integer.toString(fm.get_parkId()));
                values.put(COL_MASTER_LIST_FEE_ID, Integer.toString(fm.get_feeId()));
                values.put(COL_MASTER_LIST_SUBFEE_ID, Integer.toString(fm.get_subFeeId()));
                values.put(COL_MASTER_FEE_DESC_ENG, fm.get_feeDescEng());
                values.put(COL_MASTER_FEE_DESC_FRE, fm.get_feeDescFre());
                values.put(COL_MASTER_PRICE_GST_EN, fm.get_priceGstEn());
                values.put(COL_MASTER_PRICE_GST_FR, fm.get_priceGstFr());
                values.put(COL_MASTER_NAT_LOCAL, fm.get_natLocal());
                values.put(COL_MASTER_TRAVEL_TRADE, fm.get_travelTrade());
                values.put(COL_MASTER_PURPOSE, fm.get_purpose());
                values.put(COL_MASTER_CLASS, fm.get_class());

                long insertId = database.insert(TABLE_MASTER_LIST, null, values);
            }
        }
        catch (XmlPullParserException xe){

        }
        catch (IOException e){

        }
    }

    // get all parks from database
    public List<ParksId> getAllParks(){

        List<ParksId> parks = new ArrayList<ParksId>();

        Cursor cursor = database.query(TABLE_PARKS, listColumnsParks, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            ParksId p = new ParksId();

            // set park members
            p.set_id(cursor.getInt(0));
            p.set_lowEngFull(cursor.getString(1));
            p.set_lowFreFull(cursor.getString(2));

            // add park to list and move cursor
            parks.add(p);
            cursor.moveToNext();
        }

        cursor.close();
        return parks;
    }

    // look up a park id for fees search by park name and return id
    public ParksId getParkId(String parkName){

        Cursor cursor;
        ParksId retVal;

        // query based on language
        if(MyConstants.LANGUAGE == 1){
            cursor = database.query(TABLE_PARKS, listColumnsParks, "lowEngFull='" + parkName + "'", null, null, null, null);
        }
        else{
            cursor = database.query(TABLE_PARKS, listColumnsParks, "lowFreFull='" + parkName + "'", null, null, null, null);
        }

        if(cursor != null && cursor.getCount() > 0){

            retVal = new ParksId();

            cursor.moveToFirst();
            retVal.set_id(cursor.getInt(0));
            retVal.set_lowEngFull(cursor.getString(1));
            retVal.set_lowFreFull(cursor.getString(2));
            cursor.close();

            return retVal;
        }

        return null;
    }

    // get the park fee types based on id passed to method
    public List<FeesMaster> getParkFeeTypes(int id){

        List<FeesMaster> feeList = new ArrayList<FeesMaster>();

        String query = "Select MasterList.Ids,MasterList.FeeDescEng,MasterList.FeeDescFre,Fees.EnDescription,Fees.FrDescription,SubFees.EnDescription,SubFees.FrDescription " +
                "FROM MasterList,Fees,SubFees WHERE ParkId=" + id + " and MasterList.FeeId=Fees.Ids " +
                "and MasterList.SubFeeId=SubFees.Ids";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            FeesMaster f = new FeesMaster();

            // set master fee members
            f.set_id(cursor.getInt(0));
            f.set_feeDescEng(cursor.getString(1));
            f.set_feeDescFre(cursor.getString(2));
            f.set_feeIdDesc(cursor.getString(3));
            f.set_feeIdDescFr(cursor.getString(4));
            f.set_subFeeIdDesc(cursor.getString(5));
            f.set_subFeeIdDescFr(cursor.getString(6));

            // add master fee to list and move cursor
            feeList.add(f);
            cursor.moveToNext();
        }

        cursor.close();
        return feeList;
    }

    // get the particular park fee for more detailed information
    public FeesMaster getParkFee(int id){

        FeesMaster f = new FeesMaster();

        String query = "Select MasterList.Ids,MasterList.FeeDescEng,MasterList.FeeDescFre,Fees.EnDescription,Fees.FrDescription,SubFees.EnDescription,SubFees.FrDescription," +
                "MasterList.PriceGstEn,MasterList.PriceGstFr,MasterList.NatLocal,MasterList.TravelTrade,MasterList.Purpose " +
                "FROM MasterList,Fees,SubFees WHERE MasterList.Ids=" + id + " and MasterList.FeeId=Fees.Ids " +
                "and MasterList.SubFeeId=SubFees.Ids";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        // set master fee members
        f.set_id(cursor.getInt(0));
        f.set_feeDescEng(cursor.getString(1));
        f.set_feeDescFre(cursor.getString(2));
        f.set_feeIdDesc(cursor.getString(3));
        f.set_feeIdDescFr(cursor.getString(4));
        f.set_subFeeIdDesc(cursor.getString(5));
        f.set_subFeeIdDescFr(cursor.getString(6));
        f.set_priceGstEn(cursor.getString(7));
        f.set_priceGstFr(cursor.getString(8));
        f.set_natLocal(cursor.getString(9));
        f.set_travelTrade(cursor.getString(10));
        f.set_purpose(cursor.getString(11));

        cursor.close();
        return f;
    }
}