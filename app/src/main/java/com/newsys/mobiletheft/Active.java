package com.newsys.mobiletheft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Active extends Activity {
   Button b1,b2;
    ToggleButton t;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tm= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        share=getSharedPreferences("mobiletheft",0);
      edit=share.edit();
        b1= (Button) findViewById(R.id.button1);
        b2= (Button) findViewById(R.id.button2);
        t= (ToggleButton) findViewById(R.id.toggleButton1);
        t.setChecked(share.getBoolean("State",false));
    }

public void activate(View v){
    if(share.getString("phno","no").matches("no")){
        Intent i=new Intent(this,Configure.class);
        startActivity(i);
    }
    else {
        if(t.isChecked()){
            String oldss=tm.getSimSerialNumber();
            edit.putBoolean("State",true);
            edit.putString("oldss",oldss);
            edit.commit();
            Toast.makeText(this,"oldss"+oldss,Toast.LENGTH_LONG).show();
            Toast.makeText(this,"activated",Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,SimchangeService.class);
            startService(i);
        }else {
            edit.putBoolean("State",false);
            edit.commit();
            Intent intent=new Intent(this,SimchangeService.class);
            stopService(intent);
            Toast.makeText(this,"Deactivated",Toast.LENGTH_LONG).show();
        }
    }

}
    public void conf(View v){
        Intent intent=new Intent(this,Configure.class);
        startActivity(intent);
    }
    public void changepswd(View v){
        Intent intent=new Intent(this,ChangePassword.class);
        startActivity(intent);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        t.setChecked(share.getBoolean("State",false));
        Toast.makeText(this,"App in active mode",Toast.LENGTH_LONG).show();
    }
}
