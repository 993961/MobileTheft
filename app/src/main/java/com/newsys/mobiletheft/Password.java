package com.newsys.mobiletheft;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Password extends Activity {
    SharedPreferences share;
    SharedPreferences.Editor edit;
    Dialog d;
    String password;
    EditText etnewpass,etnewrepass,etpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        share=getSharedPreferences("Mobiletheft",0);
        edit=share.edit();
        etpass= (EditText) findViewById(R.id.etpassword);
    password=share.getString("password",null);
        if(password==null)
        {
            d=new Dialog(Password.this);
            d.setTitle("New password");
            d.setContentView(R.layout.newpassword);
            d.show();
            etnewpass= (EditText) findViewById(R.id.newpass_etpass);
            etnewrepass= (EditText) findViewById(R.id.newpass_etrepass);
            Button newpass= (Button) findViewById(R.id.newpass_bok);
            newpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newpass=etnewpass.getText().toString();
                    String renewpass=etnewrepass.getText().toString();

                    if(!newpass.equals("") && (renewpass.equals(""))){
                        if(newpass.equals(etnewrepass.getText().toString())){
                            edit.putString("password",etnewpass.getText().toString());
                            edit.commit();
                            Toast.makeText(Password.this,"New Password saved ",3000).show();
                            d.dismiss();

                        }
                        else {
                            Toast.makeText(Password.this,"password missmatch",3000).show();
                        }
                    } else {
                        Toast.makeText(Password.this,"Enter new password",3000).show();
                    }

                }
            });
        }
    }

public void ok(View v){
    password=share.getString("password",null);
    if(password==null){
        Toast.makeText(Password.this,"must create new password",3000).show();
        d.show();
    }
    else {
        String etpasword = etpass.getText().toString();
        if (etpasword.matches(password)) {
            Toast.makeText(Password.this,
                    "HOME", 3000).show();
            etpass.setText("");
            Intent i = new Intent(this, Active.class);
            startActivity(i);

        }
        else{
            Toast.makeText(Password.this,
                    "Wrong Password", 3000).show();

        }
    }
}

}
