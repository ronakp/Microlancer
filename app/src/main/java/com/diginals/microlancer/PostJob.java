package com.diginals.microlancer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PostJob extends AppCompatActivity {
    private Spinner spinner4;
    private Button button3;
    private Spinner spinner6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }


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
