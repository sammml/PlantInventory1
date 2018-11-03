package com.example.cheesepuff.plantinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.example.cheesepuff.plantinventory.data.PlantContract.PlantEntry;
import com.example.cheesepuff.plantinventory.data.PlantDbHelper;

/**
 * Displays list of plants that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private PlantDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PlantDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the plants database.
     */
    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                PlantEntry._ID,
                PlantEntry.COLUMN_PLANT_NAME,
                PlantEntry.COLUMN_PLANT_PRICE,
                PlantEntry.COLUMN_PLANT_QUANTITY,
                PlantEntry.COLUMN_PLANT_SUPPLIER_NAME,
                PlantEntry.COLUMN_PLANT_SUPPLIER_PHONE_NUMBER
        };
        Cursor cursor = db.query(
                PlantEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_plant);

        try {
            displayView.setText("Plant inventory contains : " + cursor.getCount() + " plants.\n\n");

            displayView.append(
                    PlantEntry._ID + " | " +
                            PlantEntry.COLUMN_PLANT_NAME + " | " +
                            PlantEntry.COLUMN_PLANT_PRICE + " | " +
                            PlantEntry.COLUMN_PLANT_QUANTITY + " | " +
                            PlantEntry.COLUMN_PLANT_SUPPLIER_NAME + " | " +
                            PlantEntry.COLUMN_PLANT_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PlantEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PlantEntry.COLUMN_PLANT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(PlantEntry.COLUMN_PLANT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(PlantEntry.COLUMN_PLANT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(PlantEntry.COLUMN_PLANT_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(PlantEntry.COLUMN_PLANT_SUPPLIER_PHONE_NUMBER);
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                int currentSupplierName = cursor.getInt(supplierNameColumnIndex);
                int currentSupplierPhone = cursor.getInt(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhone));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}


