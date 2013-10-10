package edu.uci.ics.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "mydb";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_NAME = "movies";
	private static final String MOVIE_ID = "_id";
	private static final String MOVIE_NAME = "title";
	private static final String MOVIE_YEAR = "year";
	private static final String MOVIE_DIRECTOR = "director";		
	private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("+MOVIE_ID+" integer primary key autoincrement, "+MOVIE_NAME+" text not null, "+MOVIE_YEAR+" text not null, "+MOVIE_DIRECTOR+" text not null);";	
	private static final String CREATE_TABLE2 = "CREATE TABLE stars (id integer primary key autoincrement, first_name text not null," +
			" last_name text not null);";
	private static final String CREATE_TABLE3 = "CREATE TABLE stars_in_movies (s_id integer not null, m_id integer not null);";
	private static final String CREATE_TABLE4 = "CREATE TABLE statistics(id integer primary key autoincrement, correct integer not null, error integer not null, time integer not null);";

	private SQLiteDatabase mDb;
	private Context mContext;
	
	public DbAdapter(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = ctx;
		this.mDb = getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_TABLE2);
		db.execSQL(CREATE_TABLE3);
		db.execSQL(CREATE_TABLE4);
		// populate database
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mContext.getAssets().open("movies.csv")));
			BufferedReader in2 = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars.csv")));
			BufferedReader in3 = new BufferedReader(new InputStreamReader(mContext.getAssets().open("stars_in_movies.csv")));
			String line;
			
			while((line=in.readLine())!=null) {
				ContentValues values = new ContentValues();				
				String temp[] = line.split(",");
				values.put(MOVIE_ID, temp[0].replaceAll("\"", ""));
				values.put(MOVIE_NAME, temp[1].replaceAll("\"", ""));
				values.put(MOVIE_YEAR, temp[2].replaceAll("\"", ""));
				values.put(MOVIE_DIRECTOR, temp[3].replaceAll("\"", ""));
				
				
				db.insert(TABLE_NAME, null, values);				
			}
			
			while((line=in2.readLine())!=null) {
				ContentValues values = new ContentValues();				
				String temp[] = line.split(",");
				values.put("id", temp[0].replaceAll("\"", ""));
				values.put("first_name", temp[1].replaceAll("\"", ""));
				values.put("last_name", temp[2].replaceAll("\"", ""));	
				
				
				db.insert("stars", null, values);				
			}
			
			while((line=in3.readLine())!=null) {
				ContentValues values = new ContentValues();				
				String temp[] = line.split(",");
				values.put("s_id", temp[0].replaceAll("\"", ""));
				values.put("m_id", temp[1].replaceAll("\"", ""));			
				
				db.insert("stars_in_movies", null, values);				
			}
			
			in.close();
			in2.close();
			in3.close();
			//db.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS stars");
		db.execSQL("DROP TABLE IF EXISTS stars_in_movies");
		onCreate(db);	
	}
	
	public Cursor fetchAll() {
		return mDb.query(TABLE_NAME, new String[] {MOVIE_NAME, MOVIE_DIRECTOR}, null, null, null, null, null);
	}	

}
