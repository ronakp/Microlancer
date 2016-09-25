package com.diginals.microlancer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostJob extends AppCompatActivity implements View.OnClickListener{
    private Spinner spinner4;
    private Button button3;
    private Spinner spinner6;


    private ImageButton ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
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

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(PostJob.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner4.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner6.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
}
