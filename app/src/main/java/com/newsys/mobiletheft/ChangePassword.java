package com.newsys.mobiletheft;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePassword extends ActionBarActivity {

    EditText et1,et2,et3;
    String a,b,c,d;
    SharedPreferences share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepswd);
        et1= (EditText) findViewById(R.id.editText1);
        et2= (EditText) findViewById(R.id.editText2);
        et3= (EditText) findViewById(R.id.editText3);
        share=getSharedPreferences("Mobiletheft",0);
    }

 public void ok(View v){
     a=et1.getText().toString();
    c=et2.getText().toString();
     d=et3.getText().toString();
     if(!(a.matches(""))){
         b=share.getString("password","hai");
         if(a.matches(b)){
             if(c.matches(d)){
                 SharedPreferences.Editor e=share.edit();
                 e.putString("password",c);
                 e.commit();
                 Toast.makeText(this,"password changed successfully",3000).show();
                 Intent i= new Intent(this,Active.class);
                 startActivity(i);
             }
             else {
                 Toast.makeText(this,"new password not matches",3000).show();
                 et1.setText("");
                 et2.setText("");
                 et3.setText("");
             }
         }
         else {
             Toast.makeText(this,"you enter wrong password",3000).show();
             et1.setText("");
             et2.setText("");
             et3.setText("");
         }
     }else {
         Toast.makeText(this,"fill all field",3000).show();
         et1.setText("");
         et2.setText("");
         et3.setText("");
     }

 }



}
