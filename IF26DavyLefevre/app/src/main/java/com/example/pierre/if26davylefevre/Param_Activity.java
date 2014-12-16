package com.example.pierre.if26davylefevre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Param_Activity extends Activity {

    private String login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);

        Intent intent =getIntent();
        login = intent.getStringExtra("Login");

        Button btnContacts = (Button) findViewById(R.id.btnContacts);
        btnContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent contacts_Activity = new Intent(getApplicationContext(),Contacts_Activity.class);
                contacts_Activity.putExtra("Login",login);
                startActivity(contacts_Activity);
            }
        });

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent map_Activity = new Intent(getApplicationContext(),Map_Activity.class);
                map_Activity.putExtra("Login",login);
                startActivity(map_Activity);
            }
        });
    }
}
