package edu.uci.ics.android;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Statistic extends Activity {
	
	private TextView txLabel;
	Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistic);
		
		DbAdapter db = new DbAdapter(this);
	       
        SQLiteDatabase sqldb = db.getReadableDatabase();
        
        
        
        
        String query = "select count(id), sum(correct), sum(error), sum(time) from statistics;";
        Cursor curs = sqldb.rawQuery(query, null);
        curs.moveToFirst();
        int quiz = curs.getInt(0);
        int correct = curs.getInt(1);
        int error = curs.getInt(2);
        int time = curs.getInt(3);
        if(correct != 0 || error != 0){
        time = time/(correct+error);}
        else
        	time = 0;
        int minute = time / 60;
        
        query = "Statistics\nNumber of Quizzes have taken: " + quiz + "\nNumber of Correct Answers: " + correct + "\nNumber of Errors: " + error + "\nAverage time spend of each question: " + minute + "min " + (time%60) + "second";
        
        
        
		txLabel = (TextView)this.findViewById(R.id.statTextView);
		txLabel.setText(query);
		
		
	
		
		
    	this.backButton =(Button)this.findViewById(R.id.statBackButton);
        this.backButton.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			//mHandler.removeCallbacks(updateTask);
    			finish();
    		}
            });
        curs.close();
        sqldb.close();
        db.close();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistic, menu);
		return true;
	}

}
