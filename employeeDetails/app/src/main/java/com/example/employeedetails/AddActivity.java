package com.example.employeedetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name,department,gender,email,address,eurl;
    Button btnAdd,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText)findViewById(R.id.txtName);
        department = (EditText)findViewById(R.id.txtDepartment);
        gender = (EditText)findViewById(R.id.txtGender);
        email = (EditText)findViewById(R.id.txtmail);
        address = (EditText)findViewById(R.id.txtAddress);
        eurl = (EditText)findViewById(R.id.txtImageUrl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        //make the add button

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();

            }
        });


        //make the back button

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /* saving to the variable & setting to the map & pass that
          value to the firebase that inserted */

    private void insertData()
    {

        Map<String,Object> map = new HashMap<>();

        //now we have to put all the values from the edit text

        map.put("name",name.getText().toString());
        map.put("department",department.getText().toString());
        map.put("gender",gender.getText().toString());
        map.put("email",email.getText().toString());
        map.put("eurl",eurl.getText().toString());

        //get the reference from the firebase database

        FirebaseDatabase.getInstance().getReference().child("students").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error while Insertion", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    //after saving data again to get a new input fields.
    private void clearAll()
    {
        name.setText("");
        department.setText("");
        gender.setText("");
        email.setText("");
        eurl.setText("");

    }
}