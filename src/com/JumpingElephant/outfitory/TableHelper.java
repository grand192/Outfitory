package com.JumpingElephant.outfitory;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class TableHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "OutfitoryDB";

	// Books table name
	private static final String TABLE_CLOTHES = "clothes";

	// Books Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_TYPE = "type";
	private static final String KEY_COLOR = "color";
	private static final String KEY_SEASON = "season";
	private static final String KEY_TAGS = "tags";
	private static final String KEY_PICTURE = "picture";

	private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_TYPE,
			KEY_COLOR, KEY_SEASON, KEY_TAGS, KEY_PICTURE };

	public TableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create clothing table
		String CREATE_OUTFIT_TABLE = "CREATE TABLE " + TABLE_CLOTHES + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
				+ " TEXT, " + KEY_TYPE + " TEXT, " + KEY_COLOR + " TEXT, "
				+ KEY_SEASON + " TEXT, " + KEY_TAGS + " TEXT, " + KEY_PICTURE
				+ " BLOB" + ")";

		// create clothing table
		db.execSQL(CREATE_OUTFIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older clothing table if existed
		db.execSQL("DROP TABLE IF EXISTS clothes");

		// create fresh clothing table
		this.onCreate(db);
	}

	public void addClothing(Clothing clothing) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, clothing.getName());
		values.put(KEY_TYPE, clothing.getType());
		values.put(KEY_COLOR, clothing.getColor());
		values.put(KEY_SEASON, clothing.getSeason());
		values.put(KEY_TAGS, clothing.getTags());
		values.put(KEY_PICTURE, clothing.getPicture());

		// 3. insert
		db.insert(TABLE_CLOTHES, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	public Clothing getClothing(String name) {

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = db.query(TABLE_CLOTHES, // a. table
				COLUMNS, // b. column names
				" name = ?", // c. selections
				new String[] { String.valueOf(name) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build clothing object
		Clothing clothing = new Clothing();
		clothing.setId(Integer.parseInt(cursor.getString(0)));
		clothing.setName(cursor.getString(1));
		clothing.setType(cursor.getString(2));
		clothing.setColor(cursor.getString(3));
		clothing.setSeason(cursor.getString(4));
		clothing.setTags(cursor.getString(5));
		clothing.setPicture(cursor.getBlob(6));

		// log
		// Log.d("getClothing(" + name + ")", clothing.toString());

		// 5. return clothing
		return clothing;
	}

	public List<Clothing> getAllClothes() {
		List<Clothing> clothes = new LinkedList<Clothing>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_CLOTHES;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Clothing clothing = null;
		if (cursor.moveToFirst()) {
			do {
				clothing = new Clothing();
				clothing.setId(Integer.parseInt(cursor.getString(0)));
				clothing.setName(cursor.getString(1));
				clothing.setType(cursor.getString(2));
				clothing.setColor(cursor.getString(3));
				clothing.setSeason(cursor.getString(4));
				clothing.setTags(cursor.getString(5));
				clothing.setPicture(cursor.getBlob(6));

				// Add book to books
				clothes.add(clothing);
			} while (cursor.moveToNext());
		}

		// Log.d("getAllBooks()", clothes.toString());

		// return books
		return clothes;
	}

	public List<Bitmap> getAllClothesPictures() {
		List<Bitmap> clothes = new LinkedList<Bitmap>();

		// 1. build the query
		String query = "SELECT picture FROM " + TABLE_CLOTHES;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Clothing clothing = null;
		if (cursor.moveToFirst()) {
			do {
				clothes.add(BitmapFactory.decodeByteArray(cursor.getBlob(0) , 0, cursor.getBlob(0).length));
				
			} while (cursor.moveToNext());
		}

		// Log.d("getAllBooks()", clothes.toString());

		// return books
		return clothes;
	}

}