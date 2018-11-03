package com.example.cheesepuff.plantinventory.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Plants app.
 */
public class PlantContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public PlantContract() {
    }

    // inner class that defines constant values for the plants database table.
    // each entry in the table represents one plant.
    public final static class PlantEntry implements BaseColumns {

        //Name of table DB
        public final static String TABLE_NAME = "plant";

        //Column of table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PLANT_NAME = "plant_name";
        public final static String COLUMN_PLANT_PRICE = "price";
        public final static String COLUMN_PLANT_QUANTITY = "quantity";
        public final static String COLUMN_PLANT_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_PLANT_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";

        // SUPPLIER_NAME LIST VALUES
        public final static int SUPPLIER_UNKNOWN = 0;
        public final static int SUPPLIER1 = 1;
        public final static int SUPPLIER2 = 2;
        public final static int SUPPLIER3 = 3;


    }
}
