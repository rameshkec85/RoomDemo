package com.andhradroid.roomdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andhradroid.roomdemo.db.AppDatabase;
import com.andhradroid.roomdemo.db.Employee;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase mDb;
    private TextView txt_list;
    private Button button;
    private EditText etName;
    private EditText etDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_list = (TextView) findViewById(R.id.txt_list);
        etName = (EditText) findViewById(R.id.etName);
        etDesignation = (EditText) findViewById(R.id.etDesignation);
        button = (Button) findViewById(R.id.button);
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        populateEmployList();
        button.setOnClickListener(this);
    }

    private void populateEmployList() {
        txt_list.setText("");
        List<Employee> employeeList = mDb.employDao().findAllEmploySync();
        for (Employee employee : employeeList) {
            txt_list.append(employee.name + " : " + employee.designation);
            txt_list.append("\n");
        }
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        String designation = etDesignation.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(designation)) {
            Toast.makeText(this, "Name/Designation should not be empty", Toast.LENGTH_SHORT).show();
        } else {
            Employee employee = new Employee();
            employee.name = name;
            employee.designation = designation;
            mDb.employDao().insertEmploy(employee);
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etDesignation.setText("");
            etName.requestFocus();
            populateEmployList();
        }
    }
}
