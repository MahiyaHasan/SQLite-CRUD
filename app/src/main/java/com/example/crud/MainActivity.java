package com.example.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    private EditText name_et, age_et, gender_et,id_et;
    private Button submit_bt;
    private Button fetch_bt;
    private Button update_bt;
    private Button delete_bt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customlayout);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();


        name_et = (EditText) findViewById(R.id.name_et);
        age_et = (EditText) findViewById(R.id.age_et);
        gender_et = (EditText) findViewById(R.id.gender_et);
        submit_bt = (Button) findViewById(R.id.submit_bt);
        fetch_bt = (Button) findViewById(R.id.fetch_bt);
        update_bt = (Button) findViewById(R.id.update_bt);
        id_et = (EditText) findViewById(R.id.id_et);
        delete_bt = (Button) findViewById(R.id.delete_bt);

        submit_bt.setOnClickListener(this);
        fetch_bt.setOnClickListener(this);
        update_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String name = name_et.getText().toString();
        String age = age_et.getText().toString();
        String gender = gender_et.getText().toString();
        String id = id_et.getText().toString();


        if (view.getId() == R.id.submit_bt) {
            long rowID = myDatabaseHelper.insertData(name, age, gender);
            if (rowID == -1) {
                Toast.makeText(getApplicationContext(), "Row is not successfully inserted", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(getApplicationContext(), "Row" + rowID + " is successfully inserted", Toast.LENGTH_LONG).show();

            }
        }


        if (view.getId() == R.id.fetch_bt) {
            Cursor cursor = myDatabaseHelper.displayAllData();
            if (cursor.getCount() == 0) {
                showData("Error", "No data found");
                return;
            }
            StringBuffer stringbuffer = new StringBuffer();
            while (cursor.moveToNext()) {
                stringbuffer.append("ID: " + cursor.getString(0) + "\n");
                stringbuffer.append("Name: " + cursor.getString(1) + "\n");
                stringbuffer.append("Age: " + cursor.getString(2) + "\n");
                stringbuffer.append("Gender: " + cursor.getString(3) + "\n");

            }
            showData("ResultSet", stringbuffer.toString());
        } else if (view.getId() == R.id.update_bt) {

            boolean isUpdated = myDatabaseHelper.updateData(id, name, age, gender);
            if (isUpdated == true) {
                Toast.makeText(getApplicationContext(), "Data is successfully updated", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Data is not successfully updated", Toast.LENGTH_LONG).show();

            }
        } else if (view.getId() == R.id.delete_bt) {
            int value = myDatabaseHelper.deleteData(id);
            if(value > 0)
            {
                Toast.makeText(getApplicationContext(), "Data is successfully deleted", Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Data is not successfully deleted", Toast.LENGTH_LONG).show();

            }

        }
    }
    public void showData (String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }

}
