package com.example.quiqu.easyweather.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiqu.easyweather.R;

import static com.example.quiqu.easyweather.R.id.add;


// This class will hold all the information for the user to log in as well as a way to activate the main interface
// and set the database.
public class HelloUserWindow extends AppCompatActivity {

    // secure all the UI information in here
    private EditText editTextName;
    private EditText editTextCity;
    private Button   letsGoButton;

    // database instance
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_user_window);

        // pass all the information from the ui in here
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextCity = (EditText) findViewById(R.id.editTextCity);

        letsGoButton = (Button) findViewById(R.id.buttonLetsGo);

        // this creates the database
        createDatabase();

        // now we instert by the click of a button and we are of to the actual main activity
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first we make a call to inserting into the database
                insertIntoDB();

                // now we open up the intent that will take us to the main screen
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



    // in here we create the database that will hold the user' information
    protected void createDatabase() {
        db = openOrCreateDatabase("UserDb", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, city VARCHAR);");

    }

    // method that inserts into the database
    protected void insertIntoDB() {
        String name = editTextName.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        if(name.equals("") || city.equals("")) {
            Toast.makeText(getApplicationContext(), "Please go back and  fill all the fields fields", Toast.LENGTH_SHORT).show();
        } else {

            // now the query that will be inserting the shit we have in here
            String query = "INSERT INTO users (name, city) VALUES('" + name + "', '" + city + "');";
            // execute the above string
            db.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved succesfully", Toast.LENGTH_SHORT).show();
        }

    }


}
