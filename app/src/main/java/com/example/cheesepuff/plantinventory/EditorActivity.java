package com.example.cheesepuff.plantinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheesepuff.plantinventory.data.PlantContract.PlantEntry;
import com.example.cheesepuff.plantinventory.data.PlantDbHelper;

/**
 * Allows user to create a new plant or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    private EditText mPlantNameEditText;
    private EditText mPlantPriceEditText;
    private EditText mPlantQuantityEditText;
    private Spinner mPlantSupplierNameSpinner;
    private EditText mPlantSupplierPhoneNumberEditText;


    /**
     * Type of the plant. The possible values are:
     * 0 for unknown, annuals 1, bulbs 2, ground covers 3, house plants 4, seeds 5, shrubs 6,
     * succulents 7, trees 8, tropical plants 9, vines 10.
     */
    private int mSupplierName = PlantEntry.SUPPLIER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mPlantNameEditText = findViewById(R.id.edit_plant_name);
        mPlantPriceEditText = findViewById(R.id.edit_plant_price);
        mPlantQuantityEditText = findViewById(R.id.edit_plant_quantity);
        mPlantSupplierNameSpinner = findViewById(R.id.spinner_supplier);
        mPlantSupplierPhoneNumberEditText = findViewById(R.id.edit_plant_supplier_phone);
        setupSpinner();

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter plantSupplierNameSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_plant_suppliers, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        plantSupplierNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mPlantSupplierNameSpinner.setAdapter(plantSupplierNameSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mPlantSupplierNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier1))) {
                        mSupplierName = PlantEntry.SUPPLIER1;
                    } else if (selection.equals(getString(R.string.supplier2))) {
                        mSupplierName = PlantEntry.SUPPLIER2;
                    } else if (selection.equals(getString(R.string.supplier3))) {
                        mSupplierName = PlantEntry.SUPPLIER3;
                    } else {
                        mSupplierName = PlantEntry.SUPPLIER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplierName = PlantEntry.SUPPLIER_UNKNOWN;
            }
        });
    }

    // get user input from editor and save new plant into database
    private void insertPlant() {

        // read the input fields
        // use trim to eliminate leading or trailing while space
        String plantNameString = mPlantNameEditText.getText().toString().trim();

        String plantPriceString = mPlantPriceEditText.getText().toString().trim();
        int plantPriceInteger = Integer.parseInt(plantPriceString);

        String plantQuantityString = mPlantQuantityEditText.getText().toString().trim();
        int plantQuantityInteger = Integer.parseInt(plantQuantityString);

        String productSupplierPhoneNumberString = mPlantSupplierPhoneNumberEditText.getText().toString().trim();
        int plantSupplierPhoneNumberInteger = Integer.parseInt(productSupplierPhoneNumberString);

        PlantDbHelper mDbHelper = new PlantDbHelper(this);
        // gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Create a ContentValues object where column names are the keys,
        // and plants each attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PlantEntry.COLUMN_PLANT_NAME, plantNameString);
        values.put(PlantEntry.COLUMN_PLANT_PRICE, plantPriceInteger);
        values.put(PlantEntry.COLUMN_PLANT_QUANTITY, plantQuantityInteger);
        values.put(PlantEntry.COLUMN_PLANT_SUPPLIER_NAME, mSupplierName);
        values.put(PlantEntry.COLUMN_PLANT_SUPPLIER_PHONE_NUMBER, plantSupplierPhoneNumberInteger);
        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(PlantEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving a plant", Toast.LENGTH_SHORT).show();
            Log.d("Error message", "Doesn't insert row on table");

        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Plant saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
            Log.d("successful message", "insert row on table");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        Log.d("message", "open Editor Activity");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertPlant();
                // Exit activity
                finish();
                // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


