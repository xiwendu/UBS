package com.example.swangya.ubs;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.graphics.Color.RED;

public class Register extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText fname, lname, id, password, repassword, type, q1, q2, q3;
    Button register, view;
    String pass, repass, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        fname = (EditText) findViewById(R.id.etUserFirstName);
        lname = (EditText) findViewById(R.id.etUserLastName);
        id = (EditText) findViewById(R.id.etUserID);
        password = (EditText) findViewById(R.id.etPassword);
        repassword = (EditText) findViewById(R.id.etretypePassword);
        type = (EditText) findViewById(R.id.etidentity);
        q1 = (EditText) findViewById(R.id.etSecurityQuestion1);
        q2 = (EditText) findViewById(R.id.etSecurityQuestion2);
        q3 = (EditText) findViewById(R.id.etSecurityQuestion3);
        register = (Button) findViewById(R.id.ibregister);
        //view=(Button)findViewById(R.id.ibviewdata);
        //viewAll();
        register.setOnClickListener(
                new View.OnClickListener() {
                    int valid = 1;

                    public void onClick(View v) {
                        email = id.getText().toString();
                        pass = password.getText().toString();
                        repass = repassword.getText().toString();
                        if (fname.length() == 0 || lname.length() == 0 || id.length() == 0 || password.length() == 0 || repassword.length() == 0 || type.length() == 0 || q1.length() == 0 || q2.length() == 0 || q3.length() == 0) {
                            valid = 0;
                        }
                        if (password.length() < 8) {
                            password.setError("Password should be at least 8 Characters long");
                            valid = 0;
                        }
                        if (!pass.equals(repass) && !repass.equals(pass)) {
                            repassword.setError("Passwords do not match");
                            repassword.getFreezesText();
                            valid = 0;
                        }
                        if (pass.equals(repass) && repass.equals(pass)) {
                            valid = 1;
                        }
                        Cursor res1 = myDb.logindata();
                        if (res1.getCount() == 0) {
                            showMessage("Error", "Nothing Found");
                            return;
                        }
                        res1.moveToFirst();
                        do {
                            if ((email.equals(res1.getString(0)))) {
                                valid = 0;
                                id.setError("Email address already exists, try login in or click forgot password");
                            }
                        } while (res1.moveToNext());

                        if (valid == 1) {
                            boolean isInserted = myDb.insertData(fname.getText().toString(), lname.getText().toString(), id.getText().toString(), password.getText().toString(), repassword.getText().toString(), type.getText().toString(), q1.getText().toString(), q2.getText().toString(), q3.getText().toString());
                            if (isInserted == true)
                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            finish();
                        } else
                            Toast.makeText(Register.this, "Error! Please Try Again", Toast.LENGTH_LONG).show();

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

/*public void viewAll()
{
    view.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Cursor res= myDb.getData();
                    if(res.getCount()==0)
                    {
                        showMessage("Error","Nothing Found");
                        return;
                    }
                    StringBuffer buffer=new StringBuffer();
                    while(res.moveToNext())
                        {
                        buffer.append("User Number: "+res.getString(0)+"\n");
                        buffer.append("First Name: "+res.getString(1)+"\n");
                        buffer.append("Last Name: "+res.getString(2)+"\n");
                        buffer.append("ID: "+res.getString(3)+"\n");
                        buffer.append("Password: "+res.getString(4)+"\n");
                        buffer.append("Confirmed Password: "+res.getString(5)+"\n");
                        buffer.append("User Type: "+res.getString(6)+"\n");
                        buffer.append("Answer 1: "+res.getString(7)+"\n");
                        buffer.append("Answer 2: "+res.getString(8)+"\n");
                        buffer.append("Answer 3: "+res.getString(9)+"\n\n");

                    }
                    showMessage("Data",buffer.toString());
                }


            }
    );

}




}*/
