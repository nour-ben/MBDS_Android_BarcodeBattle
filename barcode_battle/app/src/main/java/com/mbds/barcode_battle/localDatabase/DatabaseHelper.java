package com.mbds.barcode_battle.localDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nour_b on 24/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_EQUIPMENT = "table_EQUIPMENT";
    public static final String COL_ID_EQUIPMENT ="EQUIPMENT_id";
    public static final String COL_NAME_EQUIPMENT ="EQUIPMENT_name";
    public static final String COL_TYPE_EQUIPMENT ="EQUIPMENT_type";
    public static final String COL_VALUE_EQUIPMENT = "EQUIPMENT_value";
    public static final String COL_PHOTO_EQUIPMENT = "EQUIPMENT_photo";

    private final String CREATE_TABLE_EQUIPMENT = "CREATE TABLE " + TABLE_EQUIPMENT + "("
            + COL_ID_EQUIPMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME_EQUIPMENT + " TEXT, "
            + COL_TYPE_EQUIPMENT + " TEXT, "
            + COL_VALUE_EQUIPMENT + " INTEGER, "
            + COL_PHOTO_EQUIPMENT + "  TEXT)";

    public static final String TABLE_POTION = "table_POTION";
    public static final String COL_ID_POTION ="POTION_id";
    public static final String COL_VALUE_POTION ="POTION_value";
    public static final String COL_PHOTO_POTION = "POTION_photo";

    private final String CREATE_TABLE_POTION = "CREATE TABLE " + TABLE_POTION + "("
            + COL_ID_POTION  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_VALUE_POTION + " INTEGER)";

    public static final String TABLE_CREATURE = "table_CREATURE";
    public static final String COL_ID_CREATURE ="CREATURE_id";
    public static final String COL_NAME_CREATURE ="CREATURE_name";
    public static final String COL_PTATTACK_CREATURE ="CREATURE_ptAttack";
    public static final String COL_PTDEFENSE_CREATURE ="CREATURE_ptDefense";
    public static final String COL_PHOTO_CREATURE = "CREATURE_photo";

    private final String CREATE_TABLE_CREATURE = "CREATE TABLE " + TABLE_CREATURE + "("
            + COL_ID_CREATURE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME_CREATURE + " TEXT , "
            + COL_PTATTACK_CREATURE + " INTEGER NOT NULL, "
            + COL_PTDEFENSE_CREATURE + " INTEGER NOT NULL,"
            + COL_PHOTO_CREATURE + "  TEXT)";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EQUIPMENT);
        db.execSQL(CREATE_TABLE_POTION);
        db.execSQL(CREATE_TABLE_CREATURE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATURE+ ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POTION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT + ";");
        onCreate(db);
    }

}