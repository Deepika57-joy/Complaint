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

public class Officer_Registration extends AppCompatActivity {

    EditText Name, Phonenumber, Address, Designation, username, password, confirmpassword ;
    Spinner District,Taluk,Wardno;
    Button Submit;
    String NameHolder, PhonenumberHolder, AddressHolder, DistrictHolder, WardnoHolder, TalukHolder, DesignationHolder, UsernameHolder, PasswordHolder, ConfirmpasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelperOfc sqLiteHelper;
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
        Designation=(EditText)findViewById(R.id.designation);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        confirmpassword = (EditText)findViewById(R.id.confirmpassword);

        sqLiteHelper = new SQLiteHelperOfc(this);

        // Adding click listener to register button.
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();

                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();


            }
        });

    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Phonenumber+ " VARCHAR, " + SQLiteHelper.Table_Column_3_Address + " VARCHAR, " + SQLiteHelper.Table_Column_4_District + " VARCHAR, " + SQLiteHelper.Table_Column_5_Wardno+ " VARCHAR, " + SQLiteHelper.Table_Column_6_Taluk+ " VARCHAR, " + SQLiteHelper.Table_Column_7_Username + " VARCHAR, " + SQLiteHelper.Table_Column_8_Password + " VARCHAR,"+SQLiteHelper.Table_Column_9_ConfirmPassword+"VARCHAR);");

    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,phonenumber,address,district,wardno,taluk,designation,username,password,confirmpassword) VALUES('"+NameHolder+"', '"+PhonenumberHolder+"', "+AddressHolder+", "+DistrictHolder+", "+WardnoHolder+", "+TalukHolder+", "+DesignationHolder+", "+UsernameHolder+", "+PasswordHolder+", "+ConfirmpasswordHolder+");";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            // Printing toast message after done inserting.
            Toast.makeText(Officer_Registration.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(Officer_Registration.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
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

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
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

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_7_Username + "=?", new String[]{UsernameHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(Officer_Registration.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}