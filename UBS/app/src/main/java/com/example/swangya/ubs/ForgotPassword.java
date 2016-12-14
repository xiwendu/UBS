package com.example.swangya.ubs;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.password;

public class ForgotPassword extends AppCompatActivity {
    DatabaseHelper FPDBH;
    EditText firstName, lastName, sq1,sq2,sq3;
    String StrfirstName, StrlastName, Strsq1, Strsq2, Strsq3;
    Button Submit,Email;
    String fname, lname, eml, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        FPDBH= new DatabaseHelper(this);
        firstName = (EditText) findViewById(R.id.etUserFirstNamefp);
        lastName = (EditText) findViewById(R.id.etUserLastNamefp);
        sq1 = (EditText) findViewById(R.id.etSecurityQuestion1);
        sq2 = (EditText) findViewById(R.id.etSecurityQuestion2);
        sq3 = (EditText) findViewById(R.id.etSecurityQuestion3);
        Submit = (Button) findViewById(R.id.submitfp);
        Email = (Button) findViewById(R.id.Emailfp);

        Submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          boolean status = false;
                                          StrfirstName=firstName.getText().toString();
                                          StrlastName=lastName.getText().toString();
                                          Strsq1=sq1.getText().toString();
                                          Strsq2=sq2.getText().toString();
                                          Strsq3=sq3.getText().toString();
                                          Cursor res = FPDBH.forgotPassword();
                                          StringBuffer buffer=new StringBuffer();
                                          res.moveToFirst();
                                          do {
                                              if ((StrfirstName.equals(res.getString(0))) && (StrlastName.equals(res.getString(1)))
                                                      && (Strsq1.equals(res.getString(2)))
                                                      && (Strsq2.equals(res.getString(3))) && (Strsq3.equals(res.getString(4))))
                                              {
                                                  buffer.append("Name: "+res.getString(0)+ " " +res.getString(1)+ "\n");
                                                  buffer.append("ID: "+res.getString(6)+"\n");
                                                  buffer.append("Password: "+res.getString(5)+"\n");
                                                  status = true;
                                              }
                                          } while (res.moveToNext());

                                          if (status == true) {
                                              showMessage("Login Details:",buffer.toString());
                                          }
                                          else {
                                              Toast.makeText(ForgotPassword.this, "Details do not match, try again", Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  }
        );

        Email.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         StrfirstName=firstName.getText().toString();
                                         StrlastName=lastName.getText().toString();
                                         Strsq1=sq1.getText().toString();
                                         Strsq2=sq2.getText().toString();
                                         Strsq3=sq3.getText().toString();

                                         boolean status = false;
                                         Cursor res1 = FPDBH.forgotPassword();
                                         res1.moveToFirst();
                                         do {
                                             if ((StrfirstName.equals(res1.getString(0))) && (StrlastName.equals(res1.getString(1)))
                                                     && (Strsq1.equals(res1.getString(2)))
                                                     && (Strsq2.equals(res1.getString(3))) && (Strsq3.equals(res1.getString(4))))
                                             {
                                                 status = true;

                                                 fname=res1.getString(0);
                                                 lname=res1.getString(1);
                                                 eml=res1.getString(6);

                                                 pwd=res1.getString(5);
                                             }
                                         } while (res1.moveToNext());


                                         if (status == true) {
                                             Intent email = new Intent(Intent.ACTION_SEND);
                                             email.putExtra(Intent.EXTRA_EMAIL, new String[]{" "});
                                             email.putExtra(Intent.EXTRA_SUBJECT, "UBS Login Details for "+fname+" "+lname+"\n\n");
                                             email.putExtra(Intent.EXTRA_TEXT,"Email: "+eml+"\n"+"Password: "+pwd+"\n");
                                             email.setType("message/rfc822");
                                             startActivity(Intent.createChooser(email, "Choose Client"));
                                         }
                                         else {
                                             Toast.makeText(ForgotPassword.this, "Details do not match, try again", Toast.LENGTH_LONG).show();
                                         }
                                     }
                                 }
        );
    }
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}