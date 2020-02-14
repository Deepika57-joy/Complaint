package com.example.complaintbox;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class SQLiteHelperOfc extends SQLiteOpenHelper {
    static String DATABASE_NAME="OfficerDataBase";
    public static final String TABLE_NAME="OfficerTable";
    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_Name="name";
    public static final String Table_Column_2_Phonenumber="phonenumber";
    public static final String Table_Column_3_Address="address";
    public static final String Table_Column_4_District="district";
    public static final String Table_Column_5_Wardno="wardno";
    public static final String Table_Column_6_Taluk="taluk";
    public static final String Table_Column_7_Designation="designation";
    public static final String Table_Column_8_Username="username";
    public static final String Table_Column_9_Password="password";
    public static final String Table_Column_10_ConfirmPassword="confirmpassword";

    public SQLiteHelperOfc(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    public SQLiteHelperOfc(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Phonenumber+" INTEGER, "+Table_Column_3_Address+" VARCHAR, "+Table_Column_4_District+" VARCHAR, "+Table_Column_5_Wardno+" INTEGER, "+Table_Column_6_Taluk+" VARCHAR, "+Table_Column_7_Designation+" VARCHAR, "+Table_Column_8_Username+" VARCHAR, "+Table_Column_9_Password+" VARCHAR, "+Table_Column_10_ConfirmPassword+" VARCHAR)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}