package com.example.yusepmaulana07.myalarm3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    TextView textView;
    int mHour,mMinute;
//    Ringtone r;
    Context con = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        textView = (TextView) findViewById(R.id.timeTextView);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMinute = i1;
                textView.setText("Time : " + mHour + ":" + mMinute);
            }
        });

        Button next = (Button) findViewById(R.id.button4);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SecondActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    public void setTimer(View v){

        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        final PendingIntent pendingIntent;

        Date date = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY,mHour);
        cal_alarm.set(Calendar.MINUTE,mMinute);
        cal_alarm.set(Calendar.SECOND,0);

        if (cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
        }

        Intent i = new Intent(MainActivity.this,MyBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this,24444,i,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),pendingIntent);

//        Uri notif_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        r = RingtoneManager.getRingtone(this,notif_uri);
//        r.play();

//        Intent startIntent = new Intent(this, RingtoneService.class);
//        startIntent.putExtra("ringtone-uri", notif_uri.getPath());
//       startService(startIntent);



    }

    public void stopTimer (View v){
//        r.stop();
//        Context context = this;
        Intent stopIntent = new Intent(this, RingtoneService.class);
        stopService(stopIntent);
//        System.out.print("context main activity:"+context.toString());
    }
}
