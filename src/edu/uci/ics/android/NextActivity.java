package edu.uci.ics.android;



import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NextActivity  extends Activity {
	
	Button backButton;
	Button nextButton;
	RadioGroup rg1;
	RadioButton rd1;
	RadioButton rd2;
	RadioButton rd3;
	RadioButton rd4;
	String q;
	String a1;
	String a2;
	String a3;
	String a4;
	
	
	int pick;
	int random;
	
	long total_time;
	int correct;
	int error;
	
	private TextView displayLabel;
	private TextView mTimeLabel;
	
	
	private Handler mHandler = new Handler();
	private long mStart;
	private long elapsed;
	private long startQ;
	
	
	//private static final long duration = 30000;
	long durations;
	//private static final long duration = 180000;
	
	

	
	private Runnable updateTask = new Runnable() {
		
		
		public void run() {
			long now = SystemClock.uptimeMillis();
			elapsed = durations - (now - mStart);
			
			
			 if (elapsed > 0) {
				int seconds = (int) (elapsed / 1000);
				int minutes = seconds / 60;
				seconds     = seconds % 60;

				if (seconds < 10) {
					mTimeLabel.setText("" + minutes + ":0" + seconds);
				} else {
					mTimeLabel.setText("" + minutes + ":" + seconds);            
				}

				mHandler.postAtTime(this, now + 1000);
			}
			else{
				mHandler.removeCallbacks(this);
				int seconds;
				addStat((int) total_time/1000);
				if((correct+error) != 0 )
				{
					seconds = (int) (total_time/1000/(correct+error));
				}
				else{
					seconds = 0;}
				
				int minutes = seconds / 60;				
				displayLabel.setText("correct = " + correct + "\nerror = " + error + "\naverage time spent = " + minutes + " min " + (seconds % 60) + "seconds ");
				nextButton.setEnabled(false);		       
				
				//finish();
			}
			
		}
			
		};
	
	private void addStat(int seconds)
	{
		DbAdapter db = new DbAdapter(this);
	       
        SQLiteDatabase sqldb = db.getReadableDatabase();
        sqldb.execSQL("insert into statistics (correct, error, time) values (" + correct + ", " + error + ", " + seconds + ")");
        sqldb.close();
        db.close();
	}
	
	
	private String[] questions = new String[] 
	{
			// question 1
			"Who directed the movie X?",
			// question 2
			"When was the movie X released?",
			// question 3
			"Which star was in the movie X?",
			// question 4
			"Which star was not in the movie X?",
			// question 5
			"In which movie the stars X and Y appear together?",
			// question 6
			"Who directed the star X?",
			// question 7
			"Who did not direct the star X?",
			// question 8
			"Which star appears in both movies X and Y?",
			// question 9
			"Which star did not appear in the same movie with the star X?",
			// question 10
			"Who directed the star X in year Y?"
	};
	
	
	public void display()
	{
		
        DbAdapter db = new DbAdapter(this);
       
        SQLiteDatabase sqldb = db.getReadableDatabase();
        
        
        Random generator = new Random();
        pick = generator.nextInt(4);   // randomly chooses which button to assign the correct answer
        
        random = generator.nextInt(10); // generates random index for question along with respective query
        
        //Cursor curs;
        
        
    	//curs = sqldb.rawQuery(queries[1], null);
    	TextView tv = (TextView)this.findViewById(R.id.textView1);                
        rg1 = (RadioGroup) this.findViewById(R.id.radioGroup1);        
        this.rd1 = (RadioButton)this.findViewById(R.id.radio1);
        rd1.setChecked(false);
        this.rd2 = (RadioButton)this.findViewById(R.id.radio2);
        rd2.setChecked(false);
        this.rd3 = (RadioButton)this.findViewById(R.id.radio3);
        rd3.setChecked(false);
        this.rd4 = (RadioButton)this.findViewById(R.id.radio4);
        rd4.setChecked(false);
        
        
    	//random=3;
        //9th has error ; fixed it. 
        if(random==0)
        {        
        	String query="select title, director " +
        			"from movies order by random() limit 4;";        	
        	Cursor curs=sqldb.rawQuery(query, null);
        	q = questions[0];
        	curs.moveToFirst();
            for(int i = 0;i<4;i++)
            {
            	if(i == pick)
            	{
            		q = q.replace("X", curs.getString(0));        	
            	}
            	if(i==0)
            	{        
            		a1 = curs.getString(1);
            		rd1.setText(a1);
            	}
            	if(i==1)
            	{
            		a2 = curs.getString(1);
            		rd2.setText(a2);
            	}
            	if(i==2)
            	{
            		a3 = curs.getString(1);
            		rd3.setText(a3);
            	}
            	
            	if(i==3)
            	{
            		a4 = curs.getString(1);
            		rd4.setText(a4);
            	}
            
            	curs.moveToNext();
            }
            
            tv.setText(q);       	
        	curs.close();
        }else if(random==1) //has a problem.
        {
        	String query = "select title, year " +
			"from movies group by year order by random() limit 4;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	q = questions[1];
        	curs.moveToFirst();
            for(int i = 0;i<4;i++)
            {
            	if(i == pick)
            	{
            		q = q.replace("X", curs.getString(0));        	
            	}
            	
            	
            	if(i==0)
            	{        
            		a1 = curs.getString(1);
            		rd1.setText(a1);
            	}
            	if(i==1)
            	{
            		a2 = curs.getString(1);
            		rd2.setText(a2);
            	}
            	if(i==2)
            	{
            		a3 = curs.getString(1);
            		rd3.setText(a3);
            	}
            	
            	if(i==3)
            	{
            		a4 = curs.getString(1);
            		rd4.setText(a4);
            	}
            
            	curs.moveToNext();
            }
            
            tv.setText(q);
            curs.close();
        }else if(random==2)
        {
        	q = questions[2];
        	String query = "select s.first_name, s.last_name, m.title " +
			"from stars_in_movies sm, stars s, movies m " +	
			"where sm.s_id = s.id and sm.m_id = m._id order by random() limit 1;";
        	
        	Cursor curs=sqldb.rawQuery(query, null);
        	curs.moveToFirst();
        	
        	String query2 = "select s.first_name, s.last_name, m.title " + 
			"from stars_in_movies sm, stars s, movies m " +	
			"where sm.s_id = s.id and sm.m_id = m._id and first_name != '" + curs.getString(0) + "' and title != \"" + curs.getString(2) +"\" order by random() limit 3;";
        	
        	Cursor curs2=sqldb.rawQuery(query2, null);
        	
        	curs2.moveToFirst();
        	
        	
        	for(int i = 0;i<4;i++)
            {
            	if(i == pick)
            	{
            		q = q.replace("X", curs.getString(2));        	
            	}
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 = curs.getString(0) + " " +curs.getString(1);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 = curs2.getString(0) + " " +curs2.getString(1);
            		rd1.setText(a1);
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs.getString(0) + " " +curs.getString(1);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2=curs2.getString(0) + " " +curs2.getString(1);
            		rd2.setText(a2);
            		curs2.moveToNext();
            		}
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			a3 = curs.getString(0) + " " +curs.getString(1);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3 = curs2.getString(0) + " " +curs2.getString(1);
            		rd3.setText(a3);
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4 = curs.getString(0) + " " +curs.getString(1);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4=curs2.getString(0) + " " +curs2.getString(1);
            		rd4.setText(a4);
            		curs2.moveToNext();
            		}
            	}
            
            	
            }
            
            tv.setText(q);
            curs.close();
            curs2.close();
        	
        }else if(random==3) 
        {
        	String query = "select distinct first_name, last_name, title from movies, stars, stars_in_movies, (select mi, count from (select m_id as mi, count(distinct s_id) as count from stars_in_movies group by m_id order by count(distinct s_id) desc) where count > 2 order by random() limit 1) where _id=mi and m_id=mi and s_id=id order by random() limit 3;";
                  	Cursor curs2=sqldb.rawQuery(query, null);
                	curs2.moveToFirst();
                	while(curs2.getCount() != 3)
                	{
                		curs2=sqldb.rawQuery(query, null);
                	}
                	curs2.moveToFirst();
                	
                	String query2 = "select first_name, last_name " +
        			"from stars " +	
        			"where first_name != '" +curs2.getString(0) + "' and last_name != '" + curs2.getString(1) + "'";
                	curs2.moveToNext();
                	query2= query2 + " and first_name != '" + curs2.getString(0) + "' and last_name != '" + curs2.getString(1) + "'";
                	curs2.moveToNext();
                	query2= query2 + " and first_name != '" + curs2.getString(0) + "' and last_name != '" + curs2.getString(1) + "' order by random() limit 1;";              	               	
                	
                	Cursor curs=sqldb.rawQuery(query2, null);
                	curs.moveToFirst();
                	q = questions[3];
                	tv.setText(q.replace("X", curs2.getString(2)));

                	curs2.moveToFirst();
                	for(int i = 0;i<4;i++)
                    {
                    	
                    	if(i==0)
                    	{   
                    		if(i==pick)
                    		{
                    			a1=curs.getString(0)+ " " +curs.getString(1);
                    			rd1.setText(a1);
                    		}
                    		else
                    		{
                    			a1=curs2.getString(0) + " " +curs2.getString(1);
                    		rd1.setText(a1);
                    		curs2.moveToNext();
                    		}
                    	}
                    	if(i==1)
                    	{
                    		if(i==pick)
                    		{
                    			a2 = curs.getString(0)+" " +curs.getString(1);
                    			rd2.setText(a2);
                    		}
                    		else{
                    			a2 = curs2.getString(0) +" " + curs2.getString(1);
                    			rd2.setText(a2);
                        		curs2.moveToNext();
                    			
                    		}
                    		
                    	}
                    	if(i==2)
                    	{
                    		if(i==pick)
                    		{
                    			a3 = curs.getString(0)+" " +curs.getString(1);
                    			rd3.setText(a3);
                    		}
                    		else
                    		{
                    			a3 = curs2.getString(0) +" " + curs2.getString(1);
                    		rd3.setText(a3);
                    		curs2.moveToNext();
                    		}
                    	}
                    	
                    	if(i==3)
                    	{
                    		if(i==pick)
                    		{
                    			a4 = curs.getString(0)+" " +curs.getString(1);
                    			rd4.setText(a4);
                    		}
                    		else
                    		{
                    			a4 = curs2.getString(0) +" " + curs2.getString(1);
        	            		rd4.setText(a4);
        	            		curs2.moveToNext();
                    		}
                    	}
                    
                    	
                    }
                    curs.close();
                    curs2.close();
                    
        }else if(random==4)
        {
        	String query="select first_name, last_name, title from movies, stars, stars_in_movies, (select mi, count from (select m_id as mi, count(s_id) as count from stars_in_movies group by m_id order by count(s_id) desc) where count > 2 order by random() limit 1) where _id=mi and m_id=mi and s_id=id order by random() limit 2;"; 	
          	Cursor curs2=sqldb.rawQuery(query, null);
        	curs2.moveToFirst();
        	q=questions[4];
        	q=q.replace("X", curs2.getString(0) + " " + curs2.getString(1));
        	curs2.moveToNext();
        	q=q.replace("Y", curs2.getString(0) + " " + curs2.getString(1));
        	
        	String query2="select distinct title from movies where title !=\"" + curs2.getString(2) + "\" order by random() limit 3";
        	Cursor curs=sqldb.rawQuery(query2, null);
        	
        	curs.moveToFirst();
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 = curs2.getString(2);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 = curs.getString(0);
            		rd1.setText(a1);
            		curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs2.getString(2);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2=curs.getString(0);
            		rd2.setText(a2);
            		curs.moveToNext();
            		}            		
            	}
            	if(i==2) 
            	{
            		if(i==pick)
            		{
            			a3 = curs2.getString(2);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3 = curs.getString(0);
            		rd3.setText(a3);
            		curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4=curs2.getString(2);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4=curs.getString(0);
            		rd4.setText(a4);
            		curs.moveToNext();
            		}
            	}
            
            	
            }
        	
        	tv.setText(q);       
        	
            curs.close();
            curs2.close();
            
        }else if(random==5)
        {
        	//answer
        	String query="select first_name, last_name, director from stars, stars_in_movies, movies where m_id=_id and s_id=id and director = (select director from movies order by random() limit 1);";
        	Cursor curs= sqldb.rawQuery(query, null);
        	curs.moveToFirst();
        	
        	//no answers
        	String query2="select distinct first_name, last_name, director from (select first_name, last_name, director from stars, stars_in_movies, movies where m_id=_id and s_id=id except select first_name, last_name, director from stars, stars_in_movies, movies where m_id=_id and s_id=id and director = '" + curs.getString(2) +"') order by random() limit 3;";
        	Cursor curs2=sqldb.rawQuery(query2, null);
        	while(curs2.getCount() != 3)
        	{
        		curs2 = sqldb.rawQuery(query2, null);
        	}
        	
        	
        	curs2.moveToFirst();

        	
        	q=questions[5].replace("X", curs.getString(0) + " " + curs.getString(1));
        	tv.setText(q);
        	
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1=curs.getString(2);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1=curs2.getString(2);
            		rd1.setText(a1);
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs.getString(2);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2= curs2.getString(2);
            		rd2.setText(a2);
            		curs2.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			a3 = curs.getString(2);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3 = curs2.getString(2);
            		rd3.setText(a3);
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4=curs.getString(2);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4= curs2.getString(2);
            		rd4.setText(a4);
            		curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();	
        	
        }else if(random==6) // has a problem
        {
        	String query = "select distinct first_name, last_name, director, id from stars, movies, stars_in_movies, (select s, count from (select s_id as s, count(distinct director) as count from movies, stars, stars_in_movies where _id=m_id and s_id=id group by s_id order by count(distinct director) desc) where count > 2 order by random() limit 1) where s=s_id and s_id=id and _id=m_id order by random() limit 3;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	while(curs.getCount() != 3)
        	{
        		curs = sqldb.rawQuery(query, null);
        	}
        	
        	curs.moveToFirst();
        	
        	String query2 = "select director from movies, stars_in_movies where m_id=_id and s_id != " + curs.getInt(3) + " order by random() limit 1;";
        	Cursor curs2 = sqldb.rawQuery(query2, null);
        	
        	curs2.moveToFirst();
        	
        	q=questions[6].replace("X", curs.getString(0) + " " + curs.getString(1));
        	tv.setText(q);
        	
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 =curs2.getString(0);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 = curs.getString(2);
            		rd1.setText(a1);
            		curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs2.getString(0);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2= curs.getString(2);
            		rd2.setText(a2);
            		curs.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{	a3 = curs2.getString(0);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3=curs.getString(2);
            		rd3.setText(a3);
            		curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4 = curs2.getString(0);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4 = curs.getString(2);
            		rd4.setText(a4);
            		curs.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();	
        	
        	
        }else if(random==7)
        {
        	String query = "select distinct first_name, last_name, title, _id, title from stars, stars_in_movies, movies, (select s_id as s from(select s_id, count(distinct m_id) as count from stars_in_movies group by s_id order by count(distinct m_id)) where count > 1 order by random() limit 1) where _id=m_id and s_id=id and s=id order by random() limit 2;";
        	q = questions[7];
        	Cursor curs = sqldb.rawQuery(query, null);
        	while(curs.getCount() != 2 )
        	{
        		curs = sqldb.rawQuery(query, null);
        	}
        	
        	curs.moveToFirst();
        	q = q.replace("X", curs.getString(4));
        	String query2 = "select first_name, last_name from stars, stars_in_movies where s_id=id and m_id != " + curs.getInt(3);
        	curs.moveToNext();
        	query2= query2 + " and m_id != " + curs.getInt(3) + " order by random() limit 3;";
        	
        	
        	Cursor curs2 = sqldb.rawQuery(query2, null);
        	while(curs2.getCount() != 3)
        	{
        		curs2 = sqldb.rawQuery(query2, null);
        	}
        	q = q.replace("Y", curs.getString(4));
        	
        	tv.setText(q);
        	
        	curs2.moveToFirst();
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 = curs.getString(0) + " " +  curs.getString(1);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 = curs2.getString(0) + " " + curs2.getString(1);
            			rd1.setText(a1);
            			curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs.getString(0) + " " +  curs.getString(1);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2 = curs2.getString(0) + " " + curs2.getString(1);
            			rd2.setText(a2);
            			curs2.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			a3 = curs.getString(0) + " " +  curs.getString(1);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3  =curs2.getString(0) + " " + curs2.getString(1);
            			rd3.setText(a3);
            			curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4 = curs.getString(0) + " " +  curs.getString(1);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4 =curs2.getString(0) + " " + curs2.getString(1);
            			rd4.setText(a4);
            			curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();
        	
        	
        	
        	
        	
        	
        	
        }else if(random==8)
        {
        	String query = "select distinct first_name, last_name, _id from stars, movies, stars_in_movies, (select m from (select m_id as m, count(distinct s_id) as c from stars_in_movies group by m_id order by count(distinct s_id)) where c > 3 order by random() limit 1) where m=m_id and m_id=_id and s_id=id order by random() limit 4;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	while(curs.getCount() != 4)
        	{
        		curs = sqldb.rawQuery(query, null);
        	}
        	
        	curs.moveToFirst();
        	String query2 = "select first_name, last_name from stars, stars_in_movies where s_id=id and m_id != " + curs.getInt(2)+ " order by random() limit 1;";
        	
        	Cursor curs2 =sqldb.rawQuery(query2, null);
        	curs2.moveToFirst();
        	
        	q=questions[8].replace("X", curs.getString(0) + " " + curs.getString(1));
        	tv.setText(q);
        	curs.moveToNext();
        	
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 = curs2.getString(0) + " " +  curs2.getString(1);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 = curs.getString(0) + " " + curs.getString(1);
            			rd1.setText(a1);
            			curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs2.getString(0) + " " +  curs2.getString(1);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2 = curs.getString(0) + " " + curs.getString(1);
            			rd2.setText(a2);
            			curs.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			a3 = curs2.getString(0) + " " +  curs2.getString(1);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3 = curs.getString(0) + " " + curs.getString(1);
            			rd3.setText(a3);
            			curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4 = curs2.getString(0) + " " +  curs2.getString(1);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4 = curs.getString(0) + " " + curs.getString(1);
            			rd4.setText(a4);
            			curs.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();
        	
        }else
        {
        	
        	String query = "select director, year, first_name, last_name, _id from movies, stars, stars_in_movies where m_id=_id and id=s_id order by random() limit 1;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	curs.moveToFirst();
        	
        	String query2= "select distinct director from movies where director != '" + curs.getString(0) + "' and year !='" + curs.getString(1) + "' order by random() limit 3;"; 
        	Cursor curs2 = sqldb.rawQuery(query2, null);
        	while(curs2.getCount() != 3)
        	{
        		curs2 = sqldb.rawQuery(query2, null);
        	}
        	
        	q = questions[9];
        	q = q.replace("X", curs.getString(2) + " " + curs.getString(3));
        	q = q.replace("Y", curs.getString(1));
        	tv.setText(q);
        	curs2.moveToFirst();
        	
        	for(int i = 0;i<4;i++)
            {
               	if(i==0)
            	{   
            		if(i==pick)
            		{
            			a1 = curs.getString(0);
            			rd1.setText(a1);
            		}
            		else
            		{
            			a1 =curs2.getString(0);
            		rd1.setText(a1);
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			a2 = curs.getString(0);
            			rd2.setText(a2);
            		}
            		else
            		{
            			a2 = curs2.getString(0);
            		rd2.setText(a2);
            		curs2.moveToNext();
            		}
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			a3 = curs.getString(0);
            			rd3.setText(a3);
            		}
            		else
            		{
            			a3 = curs2.getString(0);
            		rd3.setText(a3);
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			a4 = curs.getString(0);
            			rd4.setText(a4);
            		}
            		else
            		{
            			a4 = curs2.getString(0);
            		rd4.setText(a4);
            		curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();
        }

        sqldb.close();
        db.close();
        
        // Retrieve the button, change its value and add an event listener
        
        
        this.nextButton = (Button)this.findViewById(R.id.button1);
        this.nextButton.setOnClickListener(new OnClickListener() {
        
    		
    		public void onClick(View v) {
    			
    					
    			
    			String feedback;
    			if(pick == 0 && rd1.isChecked() == true)
    			{
    				feedback = "Correct!";
    				correct++;
    			}
    			else if(pick == 1 && rd2.isChecked() == true)
    			{
    				feedback = "Correct!";
    				correct++;
    			}
    			else if(pick == 2 && rd3.isChecked() == true)
    			{
    				feedback = "Correct!";
    				correct++;
    			}
    			else if(pick == 3 && rd4.isChecked() == true)
    			{
    				feedback = "Correct!";
    				correct++;
    			}
    			else
    			{
    				feedback = "Error!";
    				error++;
    			}
    			
    			
    			Toast.makeText(getApplicationContext(), feedback, 1500).show();
    			
    			
    			
    			
    			total_time = total_time + (SystemClock.uptimeMillis() - startQ);
    			startQ = SystemClock.uptimeMillis();
    			
    			display();
    			
    		}
            });
        
        this.backButton = (Button)this.findViewById(R.id.backButton);
        this.backButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
        });
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);
        displayLabel = (TextView)this.findViewById(R.id.textView2);
        displayLabel.setText("");
        
        
        
        
        if(savedInstanceState == null)
        {
        	total_time=0;
            correct=0;
            error=0;
            durations = 180000;
        	display();        
            mTimeLabel = (TextView)this.findViewById(R.id.timeLabel);
            
            
            //feedback.setVisibility(feedback.INVISIBLE);
            
            
            mStart = SystemClock.uptimeMillis();
            startQ = SystemClock.uptimeMillis();
            mHandler.post(updateTask);
        }
        else
        {
        	
        	this.q = savedInstanceState.getString("q");
          	this.pick = savedInstanceState.getInt("pick");
          	this.a1 = savedInstanceState.getString("a1");
          	this.a2 = savedInstanceState.getString("a2");
          	this.a3 = savedInstanceState.getString("a3");
          	this.a4 = savedInstanceState.getString("a4");  	
          	this.correct = savedInstanceState.getInt("correct");
          	this.error = savedInstanceState.getInt("error");
          	this.total_time = savedInstanceState.getLong("total_time");
          	this.durations = savedInstanceState.getLong("elapsed");
          	this.startQ = SystemClock.uptimeMillis() - savedInstanceState.getLong("elapsed");
          	savedInstanceState.clear();
        	
        	TextView tv = (TextView)this.findViewById(R.id.textView1);                
            rg1 = (RadioGroup) this.findViewById(R.id.radioGroup1);        
            this.rd1 = (RadioButton)this.findViewById(R.id.radio1);
            rd1.setChecked(false);
            this.rd2 = (RadioButton)this.findViewById(R.id.radio2);
            rd2.setChecked(false);
            this.rd3 = (RadioButton)this.findViewById(R.id.radio3);
            rd3.setChecked(false);
            this.rd4 = (RadioButton)this.findViewById(R.id.radio4);
            rd4.setChecked(false);
            
            
            rd1.setText(a1);
            rd2.setText(a2);
            rd3.setText(a3);
            rd4.setText(a4);
            tv.setText(q);
            
            
            
            this.nextButton = (Button)this.findViewById(R.id.button1);
            this.nextButton.setOnClickListener(new OnClickListener() {
            
        		
        		public void onClick(View v) {
        			
        			
        
        			
        			String feedback;
        			if(pick == 0 && rd1.isChecked() == true)
        			{
        				feedback = "Correct!";
        				correct++;
        			}
        			else if(pick == 1 && rd2.isChecked() == true)
        			{
        				feedback = "Correct!";
        				correct++;
        			}
        			else if(pick == 2 && rd3.isChecked() == true)
        			{
        				feedback = "Correct!";
        				correct++;
        			}
        			else if(pick == 3 && rd4.isChecked() == true)
        			{
        				feedback = "Correct!";
        				correct++;
        			}
        			else
        			{
        				feedback = "Error!";
        				error++;
        			}
        			
        			
        			Toast.makeText(getApplicationContext(), feedback, 1500).show();
        			total_time = total_time + (SystemClock.uptimeMillis() - startQ);
        			
        			display();
        			
        		}
                });
            
            this.backButton = (Button)this.findViewById(R.id.backButton);
            this.backButton.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			finish();
    		}
            });
            
            mTimeLabel = (TextView)this.findViewById(R.id.timeLabel);
            mStart = SystemClock.uptimeMillis();
            mHandler.post(updateTask);
            
            
        }
        
        
        
    }
    
    public void onPause()
    {
    	
       	super.onPause();
       	mHandler.removeCallbacks(updateTask);
       	startQ = SystemClock.uptimeMillis() - startQ;
       
       	
       	
       	
    }   
    
    public void onResume()
    {
    	
       	super.onResume();
       	if(elapsed != 0)
       	{
       		durations = elapsed;
       		mStart = SystemClock.uptimeMillis();
       		startQ = SystemClock.uptimeMillis() - startQ;
       		
       	}
       			
       	mHandler.post(updateTask);
       	
       	
       	
       	
    }    
    
    
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	  super.onSaveInstanceState(savedInstanceState);
    	  // Save UI state changes to the savedInstanceState.
    	  // This bundle will be passed to onCreate if the process is
    	  // killed and restarted.
    	  
    	  savedInstanceState.putString("q", q);
    	  savedInstanceState.putString("a1", a1);
    	  savedInstanceState.putString("a2",a2);
    	  savedInstanceState.putString("a3",a3);
    	  savedInstanceState.putString("a4",a4);
    	  savedInstanceState.putInt("pick", pick);
    	  savedInstanceState.putInt("correct", correct);
    	  savedInstanceState.putInt("error", error);
    	  savedInstanceState.putLong("total_time", total_time);
    	  savedInstanceState.putLong("elapsed", elapsed);
    	  savedInstanceState.putLong("startQ", SystemClock.uptimeMillis() - startQ);
    	  
    	  // etc.
    	}    
    

}












