package com.niit.sqlitedemo;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    Class Members -> Referencing the widgets---
    Button btnAdd, btnView;
    EditText firstName;
    EditText surName;
    EditText age;
    Switch activeMember;
    ListView listView;
    MemberModel memberModel;
    ArrayAdapter memberArrayList;
    DatabaseAccessModeller databaseAccessModeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        firstName = findViewById(R.id.firstName);
        surName = findViewById(R.id.surName);
        age = findViewById(R.id.age);
        activeMember = findViewById(R.id.activeMember);
        listView = findViewById(R.id.listView);

        databaseAccessModeller = new DatabaseAccessModeller(MainActivity.this);
//        dssds
        memberArrayList = new ArrayAdapter<MemberModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseAccessModeller.getResultList());
        listView.setAdapter(memberArrayList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    memberModel = new MemberModel(-1, firstName.getText().toString(), surName.getText().toString(), Integer.parseInt(age.getText().toString()), activeMember.isChecked());
//                    Toast.makeText(MainActivity.this, memberModel.toString(), Toast.LENGTH_SHORT).show();
                    DatabaseAccessModeller databaseAccessModeller = new DatabaseAccessModeller(MainActivity.this);

                    boolean success = databaseAccessModeller.addOne(memberModel);

                    Toast.makeText(MainActivity.this, "Status = "+success, Toast.LENGTH_SHORT).show();

                    memberArrayList = new ArrayAdapter<MemberModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseAccessModeller.getResultList());
                    listView.setAdapter(memberArrayList);

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Inputs Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseAccessModeller = new DatabaseAccessModeller(MainActivity.this);
//                List<MemberModel> allMembers = databaseAccessModeller.getResultList();

                memberArrayList = new ArrayAdapter<MemberModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseAccessModeller.getResultList());
                listView.setAdapter(memberArrayList);

//                Toast.makeText(MainActivity.this, allMembers.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemberModel clickedMember = (MemberModel) adapterView.getItemAtPosition(i);
                databaseAccessModeller.deleteOne(clickedMember);
                memberArrayList = new ArrayAdapter<MemberModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseAccessModeller.getResultList());
                listView.setAdapter(memberArrayList);
                Toast.makeText(MainActivity.this, "Deleted " + clickedMember.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}