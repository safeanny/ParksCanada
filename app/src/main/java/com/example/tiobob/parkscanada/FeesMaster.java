package com.example.tiobob.parkscanada;

public class FeesMaster {

    // class members

    private int _id;
    private int _parkId;
    private int _feeId;
    private int _subFeeId;
    private String _feeDescEng;
    private String _feeDescFre;
    private String _priceGstEn;
    private String _priceGstFr;
    private String _natLocal;
    private String _travelTrade;
    private String _purpose;
    private String _class;

    // extra members used for display purposes (bad way to do this but time was running out!)
    private String _feeIdDesc;
    private String _feeIdDescFr;
    private String _subFeeIdDesc;
    private String _subFeeIdDescFr;

    // default constructor

    public FeesMaster(){

    }

    // setters

    public void set_id(int id) {
        this._id = id;
    }

    public void set_parkId(int parkId) {
        this._parkId = parkId;
    }

    public void set_feeId(int feeId) {
        this._feeId = feeId;
    }

    public void set_subFeeId(int subFeeId) {
        this._subFeeId = subFeeId;
    }

    public void set_feeDescEng(String feeDescEng) {
        this._feeDescEng = feeDescEng;
    }

    public void set_feeDescFre(String feeDescFre) {
        this._feeDescFre = feeDescFre;
    }

    public void set_priceGstEn(String priceGstEn) {
        this._priceGstEn = priceGstEn;
    }

    public void set_priceGstFr(String priceGstFr) {
        this._priceGstFr = priceGstFr;
    }

    public void set_natLocal(String natLocal) {
        this._natLocal = natLocal;
    }

    public void set_travelTrade(String travelTrade) {
        this._travelTrade = travelTrade;
    }

    public void set_purpose(String purpose) {
        this._purpose = purpose;
    }

    public void set_class(String c) {
        this._class = c;
    }

    public void set_subFeeIdDesc(String _subFeeIdDesc) {
        this._subFeeIdDesc = _subFeeIdDesc;
    }

    public void set_feeIdDesc(String _feeIdDesc) {
        this._feeIdDesc = _feeIdDesc;
    }

    public void set_subFeeIdDescFr(String _subFeeIdDescFr) {
        this._subFeeIdDescFr = _subFeeIdDescFr;
    }

    public void set_feeIdDescFr(String _feeIdDescFr) {
        this._feeIdDescFr = _feeIdDescFr;
    }


    // getters


    public int get_id() {
        return _id;
    }

    public int get_parkId() {
        return _parkId;
    }

    public int get_feeId() {
        return _feeId;
    }

    public int get_subFeeId() {
        return _subFeeId;
    }

    public String get_feeDescEng() {
        return _feeDescEng;
    }

    public String get_feeDescFre() {
        return _feeDescFre;
    }

    public String get_priceGstEn() {
        return _priceGstEn;
    }

    public String get_priceGstFr() {
        return _priceGstFr;
    }

    public String get_natLocal() {
        return _natLocal;
    }

    public String get_travelTrade() {
        return _travelTrade;
    }

    public String get_purpose() {
        return _purpose;
    }

    public String get_class() {
        return _class;
    }

    public String get_subFeeIdDesc() {
        return _subFeeIdDesc;
    }

    public String get_feeIdDesc() {
        return _feeIdDesc;
    }

    public String get_subFeeIdDescFr() {
        return _subFeeIdDescFr;
    }

    public String get_feeIdDescFr() {
        return _feeIdDescFr;
    }

    @Override
    public String toString() {

        // chk which language to return
        if(MyConstants.LANGUAGE == 1){
            return _feeDescEng + ": " + _feeIdDesc + " - " + _subFeeIdDesc;
        }
        else{
            return _feeDescFre + ": " + _feeIdDescFr + " - " + _subFeeIdDescFr;
        }
    }
}
