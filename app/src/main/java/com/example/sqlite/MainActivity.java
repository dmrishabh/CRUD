package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mydb;
    EditText edId, edName, edEmail, edcc;
    Button btnAdd, btnView, btnUpdate, btnDelete, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
///// finding every thing of activity main
        mydb = new DataBaseHelper(this);
        edId = findViewById(R.id.editText_id);
        edName = findViewById(R.id.editText_name);
        edEmail = findViewById(R.id.editText_email);
        edcc = findViewById(R.id.editText_CC);
        btnAdd = findViewById(R.id.button_add);
        btnView = findViewById(R.id.button_view);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);
        btnViewAll = findViewById(R.id.button_viewAll);

        addData();
        getData();
        viewAll();
        update();
        delete();

    }

    public void addData() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edId.getText().toString();
                if (id.equals(String.valueOf(""))){
                    edId.setError("Enter ID ");
                    return;
                }
                boolean isInserted = mydb.insertData(edName.getText().toString(), edEmail.getText().toString(), edcc.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getData() {
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edId.getText().toString();
                if (id.equals(String.valueOf(""))) {
                    edId.setError("Enter ID ");
                    return;
                }
                Cursor cursor = mydb.getData(id);
                String data = null;
                if (cursor.moveToNext()) {
                    data = "ID: " + cursor.getString(0) + "\n" +
                            "Name:" + cursor.getString(1) + "\n" +
                            "Email:" + cursor.getString(2) + "\n" +
                            "Course Count:" + cursor.getString(3) + "\n";
                }
                showMsg("DATA", data);
            }
        });

    }

    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mydb.getAllData();
                // small test
                if (cursor.getCount() == 0) {
                    showMsg("Error", "No Data found in database");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()) {

                    buffer.append("ID:" + cursor.getString(0) + "\n");
                    buffer.append("Name:" + cursor.getString(1) + "\n");
                    buffer.append("Email:" + cursor.getString(2) + "\n");
                    buffer.append("Course Count:" + cursor.getString(3) + "\n");
                }
                showMsg("All Data", buffer.toString());


            }
        });


    }

    public void update() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edId.getText().toString();
                if (id.equals(String.valueOf(""))){
                    edId.setError("Enter ID to be Updated");
                    return;
                }
                boolean isUpdate = mydb.updateData(edId.getText().toString(),
                        edName.getText().toString(),
                        edEmail.getText().toString(),
                        edcc.getText().toString());
                if (isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Oops", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void delete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edId.getText().toString();
                if (id.equals(String.valueOf(""))){
                    edId.setError("Enter ID to be deleted");
                    return;
                }
                Integer deletedRow = mydb.deleteData(edId.getText().toString());
                if (deletedRow > 0) {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "This Id not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showMsg(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}



















