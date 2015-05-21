package com.newsys.mobiletheft;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SimchangeService extends Service {
//   public SimchangeService() {
//    }

    TelephonyManager tm;
    SharedPreferences share;
    SmsManager sms;

    LocationManager locationManager;
    String result_address, location_lati_long;
    Location currentLocation;double currentLongitude; double currentLatitude;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        share = getSharedPreferences("MobileThept", 0);
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_LONG).show();
    }
    @SuppressWarnings("rawtypes")
    @Override
    public void onStart(Intent intent,int startId){
        super.onStart(intent,startId);
        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        if(locationListener!=null){

        }
             try{
                 Geocoder gcd=new Geocoder(this, Locale.getDefault());
                 List addresses= gcd.getFromLocation(currentLatitude,currentLongitude,100);
                 if (addresses.size()>0){
                     StringBuilder result=new StringBuilder();
                     Address address= (Address) addresses.get(0);
                     int maxIndex=address.getMaxAddressLineIndex();
                     int x;
                     for( x=0;x<maxIndex;x++);
                     {
                        result.append(address.getAddressLine(x));
                         result.append(",");
                     }
                     result.append(address.getLocality());
                     result.append(",");
                     //result.append(address.getPostalCode());
                     result.append("\n");
                    // getting total address
                     result_address=result.toString();
                     Toast.makeText(this,location_lati_long,Toast.LENGTH_LONG).show();
                     Toast.makeText(this,result_address,Toast.LENGTH_LONG).show();


                 }

             } catch (IOException ex){
                 Toast.makeText(this,location_lati_long,Toast.LENGTH_LONG).show();
                 Toast.makeText(this,ex.getMessage().toString()+"no service",Toast.LENGTH_LONG).show();
                 result_address=ex.getMessage().toString()      ;
             }
        String ss=tm.getSimSerialNumber();
        String oldss=share.getString("oldss","nonum");
        sendingsms();
       // sendingMail();
        if(!(ss.matches(oldss))){

            Toast.makeText(this,"sim changed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,oldss +"="+ ss,Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this,"Services Started",Toast.LENGTH_LONG).show();
    }

    void sendingsms(){
        String s2,s3;
        s2=share.getString("phno","");
        s3=share.getString("msg","")+ " \n"+location_lati_long + "\n" +result_address;
        Toast.makeText(this,""+ s2 + " /n"+ "location:"+s3,Toast.LENGTH_LONG).show();
        sms= SmsManager.getDefault();
        sms.sendTextMessage(s2,null,s3,null,null);
    }
    void sendingMail(){
        String s1,s3;
        s1=share.getString("email","");
        s3=share.getString("msg","")+ "\n"+result_address + "\n"+location_lati_long;
        Intent mail=new Intent(Intent.ACTION_SEND, Uri.parse("mailto :" +s1));
        mail.putExtra(Intent.EXTRA_SUBJECT,s3);
        mail.setType("message/rfc822");
        startActivity(mail);

    }
    void updateLocation(Location location){
      // currentLocation location;
        currentLatitude =currentLocation.getLatitude();
        currentLongitude=currentLocation.getLongitude();


    }
}
