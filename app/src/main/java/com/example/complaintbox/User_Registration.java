package com.example.complaintbox;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class User_Registration extends AppCompatActivity {

    EditText Name, Phonenumber, Address, username, password, confirmpassword ;
    Spinner District,Taluk,Wardno;
    Button Submit;
    String NameHolder, PhonenumberHolder, AddressHolder, DistrictHolder, WardnoHolder, TalukHolder, UsernameHolder, PasswordHolder, ConfirmpasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        Submit = (Button)findViewById(R.id.submit);
        Name = (EditText)findViewById(R.id.name);
        Phonenumber = (EditText)findViewById(R.id.phonenumber);
        Address = (EditText)findViewById(R.id.address);
        District = (Spinner) findViewById(R.id.district);
        Wardno = (Spinner) findViewById(R.id.wardno);
        Taluk = (Spinner) findViewById(R.id.taluk);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        sqLiteHelper = new SQLiteHelper(this);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                CheckingEmailAlreadyExistsOrNot();
                EmptyEditTextAfterDataInsert();
            }
        });

    }
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Phonenumber+ " VARCHAR, " + SQLiteHelper.Table_Column_3_Address + " VARCHAR, " + SQLiteHelper.Table_Column_4_District + " VARCHAR, " + SQLiteHelper.Table_Column_5_Wardno+ " VARCHAR, " + SQLiteHelper.Table_Column_6_Taluk+ " VARCHAR, " + SQLiteHelper.Table_Column_7_Username + " VARCHAR, " + SQLiteHelper.Table_Column_8_Password + " VARCHAR,"+SQLiteHelper.Table_Column_9_ConfirmPassword+"VARCHAR);");

    }
    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,phonenumber,address,district,wardno,taluk,username,password,confirmpassword) VALUES('"+NameHolder+"', '"+PhonenumberHolder+"', "+AddressHolder+", "+DistrictHolder+", "+WardnoHolder+", "+TalukHolder+", "+UsernameHolder+", "+PasswordHolder+", "+ConfirmpasswordHolder+");";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(User_Registration.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }

        else {
            Toast.makeText(User_Registration.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();
        Phonenumber.getText().clear();

        Address.getText().clear();

        //District.getSelectedItem().clear();
        //Wardno.getText().clear();

        //Taluk.getText().clear();
        username.getText().clear();

        password.getText().clear();
        confirmpassword.getText().clear();

    }
    public void CheckEditTextStatus(){

        NameHolder = Name.getText().toString() ;
        PhonenumberHolder = Phonenumber.getText().toString();
        AddressHolder = Address.getText().toString();
       // DistrictHolder = District.getText().toString() ;
       // WardnoHolder = Wardno.getText().toString();
       // TalukHolder = Taluk.getText().toString();
        UsernameHolder = username.getText().toString() ;
        PasswordHolder = password.getText().toString();
        ConfirmpasswordHolder = confirmpassword.getText().toString();
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(PhonenumberHolder) || TextUtils.isEmpty(AddressHolder) || TextUtils.isEmpty(DistrictHolder) || TextUtils.isEmpty(WardnoHolder) || TextUtils.isEmpty(TalukHolder) || TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(ConfirmpasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
    public void CheckingEmailAlreadyExistsOrNot(){
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_7_Username + "=?", new String[]{UsernameHolder}, null, null, null);
        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();

    }
    public void CheckFinalResult(){
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            Toast.makeText(User_Registration.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}