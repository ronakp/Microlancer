package com.diginals.microlancer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class PostJob extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner4;
    private Button button3;
    private Spinner spinner6;

    private EditText editText8;
    private EditText editText9;
    private EditText editText;
    private EditText editText12;

    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        ib = (ImageButton) findViewById(R.id.imageButton1);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.editText);
        ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };


    public void addListenerOnSpinnerItemSelection() {
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner4.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner6.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        button3 = (Button) findViewById(R.id.button3);

        editText = (EditText) findViewById(R.id.editText);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText12 = (EditText) findViewById(R.id.editText12);

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitPost();
            }

        });
    }

    private void submitPost() {
        final String jobPostingTitle = editText8.getText().toString();
        final String description = editText9.getText().toString();
        final String location = editText12.getText().toString();
        final String workDate = editText.getText().toString();
        final String wage4 = spinner4.getSelectedItem().toString();
        final String payment6 = spinner6.getSelectedItem().toString();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [START single_value_read]
        final String userId = user.getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(PostJob.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(jobPostingTitle, description, payment6, wage4, workDate, location, user);
                            Toast.makeText(PostJob.this, "Success: Job posted!", Toast.LENGTH_SHORT).show();
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        spinner4.setEnabled(enabled);
        spinner6.setEnabled(enabled);
        editText.setEnabled(enabled);
        editText8.setEnabled(enabled);
        editText9.setEnabled(enabled);
        editText12.setEnabled(enabled);
        if (enabled) {
            button3.setVisibility(View.VISIBLE);
        } else {
            button3.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String jobTitle, String description, String payment6, String wage4, String workDate, String location, User owner) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("jobs").push().getKey();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String currentFormattedDate = df.format(c.getTime());
        Job job = new Job(currentFormattedDate, currentFormattedDate, jobTitle, description, payment6, wage4, workDate, location, owner);
        java.util.Map<String, Object> postValues = job.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/jobs/" + key, postValues);

        mDatabase.updateChildren(childUpdates);



    }

}
