package com.example.mukesh.classmonitor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.Timestamp;
import java.util.Calendar;

import static android.app.PendingIntent.getActivity;


public class AddClass extends AppCompatActivity  {


    private EditText editText;
    private static int Hour, Day, Min, Month, Year;
    String date,time;
    private double location_x, location_y;
    private boolean locationFixed = false;
    private static boolean dateFixed = false;
    private static boolean timeFixed = false;
    private boolean course_title = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editText = (EditText) findViewById(R.id.editText2);

        final Button location = (Button) findViewById(R.id.button);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click




                  // moving to showmap activity to select class location .
                Intent activityChangeIntent = new Intent(getApplicationContext(), showmap.class);

                startActivityForResult(activityChangeIntent, 100);

            }
        });

        final Button submit = (Button) findViewById(R.id.submit);          //final submit.
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                // check error.
                if (Errorcheck() == true) {
                    Course course = new Course();
                    course.setCourseTitle(editText.getText().toString());
                    Log.d("edit:", editText.getText().toString());
                    //String timestamp = Integer.toString(Day) + Integer.toString(Month) + Integer.toString(Year) + Integer.toString(Hour) + Integer.toString(min);
                    course.setTimestamp(generate_timestamp());
                    course.setLatitude(location_x);
                    course.setLongitude(location_y);
                    course.setDate(date);
                    course.setTime(time);
                    TimetableDb tmp = new TimetableDb(getApplicationContext());
                    tmp.createCourse(course);
                    Log.d("createDb:", "" + course.getCourseTitle() + course.getLatitude() + course.getLongitude() + " " + course.getTimestamp());
                   /*
                    AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                    alert.setMessage("classs added");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {finish();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    */

                    Intent service = new Intent(getApplicationContext(), ETAService.class);   // starting service.
                    startService(service);
                    finish();
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public boolean Errorcheck() {

       /* alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
       */

        if (!editText.getText().toString().isEmpty())
            course_title = true;


        if (course_title == false) {
            Log.d("eror:", "course not filled");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please fill the course title");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            return false;
        }
        if (locationFixed == false) {
            Log.d("eror:", "location not filled");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please select the location of class");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            return false ;
        }
        if (timeFixed == false) {
            Log.d("eror:", "time not filled");

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please assign the time");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            return false;
        }
        if (dateFixed == false) {
            Log.d("eror:", "date not filled");

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please assign the date");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            return false;
        }

        return true;
    }

    public long generate_timestamp() { // Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
        String month,day,year,hour,min;
        Month+=1;
        if(Month<10)
            month = "0"+String.valueOf(Month);
        else
            month = String.valueOf(Month);
        if(Day <10)
            day = "0"+String.valueOf(Day);
        else
            day = String.valueOf(Day);
        year = String.valueOf(Year);
        date = day+"-"+month+"-"+year;
        if(Hour<10)
            hour = "0"+String.valueOf(Hour);
        else
            hour = String.valueOf(Hour);
        if(Min<10)
            min = "0"+String.valueOf(Min);
        else
            min = String.valueOf(Min);

        String tym = year + "-" + month + "-" + day + " " + hour + ":" + min + ":00.0";

        if(Hour>12)
            time = Hour%12 + ":" +min +"pm";
         else if(Hour==12)
            time = Hour + ":" + min +"pm";
           else
              time = Hour +":" + min + "am" ;

        Log.d("selected:",tym);

        Timestamp timestamp1 = Timestamp.valueOf(tym);

        // Log.d("tym:",tym);
        // Timestamp timestamp = Timestamp.valueOf(tym);
        return timestamp1.getTime();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddClass Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.mukesh.classmonitor/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AddClass Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.mukesh.classmonitor/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Hour = hourOfDay;
            Min = minute;
            timeFixed = true;
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            //view.getMonth();
            Day = day;
            Month = month;
            Year = year;

            //Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
            //long x = timestamp.getTime();
            Log.d("msg:",""+day+"," + month+","+year);
            dateFixed = true;


        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    /*@Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                location_x = place.getLatLng().latitude;
                location_y = place.getLatLng().longitude;
                locationFixed = true ;
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                Log.d("locn check:", String.valueOf(locationFixed));
            }
        }
    }
   */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                location_x = data.getDoubleExtra("lat_data", 0);
                location_y = data.getDoubleExtra("lng_data", 0);
                locationFixed = true;

                Log.d("locn check:",String.valueOf(locationFixed));
            }
        }
    }

}
