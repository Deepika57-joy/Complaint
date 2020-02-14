package com.example.complaintbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {
    Button Login;
    EditText Username,Password;
    TextView Register;
    String UsernameHolder,PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        Login = (Button) findViewById(R.id.login);

        Register = (TextView) findViewById(R.id.register);

        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);

        sqLiteHelper = new SQLiteHelper(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, User_Registration.class);
                startActivity(intent);

            }
        });
    }
        public void LoginFunction(){

            if(EditTextEmptyHolder) {
                sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
                cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Address + "=?", new String[]{UsernameHolder}, null, null, null);
                while (cursor.moveToNext()) {
                    if (cursor.isFirst()) {
                        cursor.moveToFirst();
                        TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));
                        cursor.close();
                    }
                }
                CheckFinalResult();
            }
            else {
                Toast.makeText(Login_Activity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();

            }

        }
        public void CheckEditTextStatus(){
            UsernameHolder = Username.getText().toString();
            PasswordHolder = Password.getText().toString();
            if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)){

                EditTextEmptyHolder = false ;
            }
            else {

                EditTextEmptyHolder = true ;
            }
        }
        public void CheckFinalResult(){

            if(TempPassword.equalsIgnoreCase(PasswordHolder))
            {

                Toast.makeText(Login_Activity.this,"Login Successfully",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Login_Activity.this,Dashboard.class);
                intent.putExtra(UserEmail, UsernameHolder);
                startActivity(intent);
            }
            else {
                Toast.makeText(Login_Activity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
            }
            TempPassword = "NOT_FOUND" ;
        }

    }
