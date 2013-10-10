package edu.uci.ics.android;



import java.util.Random;
import java.util.StringTokenizer;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NextActivity  extends Activity {
	
	Button backButton;
	RadioGroup rg1;
	RadioButton rd1;
	RadioButton rd2;
	RadioButton rd3;
	RadioButton rd4;
	
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
	
	private String[] queries = new String[]
	{
			//query 1 title -- Who directed the movie X? --
			 	
			//query 2 - When was the movie X released? --
			
			//query 3 -- Which star was in the movie X? --		
			
			"select first_name, last_name, title " +
			"from (select first_name, last_name, title from stars_in_movies, stars, movies " +
				  "where sm.s_id != s.id and sm.m_id != m.id order by random() limit 3) UNION " +         //"from stars_in_movies sm, stars s, movies m " +  	
			"select first_name, last_name, title " +
			"from (select first_name, last_name, title from stars_in_movies",

			//query 4 -- Which star was not in the movie X? --
			"select s.first_name, s.last_name, m.title " +
			"from stars_in_movies, stars s, movies m " + 
			"where s_id != s.id and m_id = m.id order by random() limit 1;",
			//query 5 -- In which movie the stars X and Y appear together? --
			"select s1.first_name, s1.last_name, s2.first_name, s2.last_name, m.title " +
			"from movies m, stars_in_movies sm1, stars_in_movies sm2, stars s1, stars s2 " +
			"where s1.id != s2.id and s1.id = sm1.s_id and m.id = sm1.m_id and s2.id = sm2.s_id and sm1.m_id = sm2.m_id " +
			"order by random();",
			//query 6 -- Who directed the star X? -- 
			"select m.director, s.first_name, s.last_name " +
			"from movies m, stars s, stars_in_movies sm " +
			"where sm.s_id = s.id and m.id = sm.m_id " +
			"order by random();",
			//query 7 -- Who did not direct the star X? --
			"select m.director, s.first_name, s.last_name " +
			"from movies m, stars s, stars_in_movies sm " +
			"where m.id != sm.m_id and s.id = sm.s_id " +
			"order by random();",
			//query 8 -- Which star appears in both movies X and Y? --
			"select s.first_name, s.last_name, m1.title, m2.title " +
			"from movies m1, movies m2, stars s, stars_in_movies sm1, stars_in_movies sm2 " +
			"where s.id = sm1.s_id and s.id = sm2.s_id and sm1.m_id = m1.id and sm2.m_id = m2.id and m1.id != m2.id " +
			"order by random();",
			// query 9 -- Which star did not appear in the same movie with the star X? --
			"",
			//query 10 -- Who directed the star X in year Y? --
			""
	};
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);
        
        DbAdapter db = new DbAdapter(this);
        //Cursor cur = db.fetchAll();
        SQLiteDatabase sqldb = db.getReadableDatabase();
        
        
        Random generator = new Random();
        int pick = generator.nextInt(4);   // randomly chooses which button to assign the correct answer
        
        int random = generator.nextInt(10); // generates random index for question along with respective query
        
        //Cursor curs;
        String q;
        
    	//curs = sqldb.rawQuery(queries[1], null);
    	TextView tv = (TextView)this.findViewById(R.id.textView1);                
        rg1 = (RadioGroup) this.findViewById(R.id.radioGroup1);        
        this.rd1 = (RadioButton)this.findViewById(R.id.radio1);
        this.rd2 = (RadioButton)this.findViewById(R.id.radio2);
        this.rd3 = (RadioButton)this.findViewById(R.id.radio3);
        this.rd4 = (RadioButton)this.findViewById(R.id.radio4);   	
    	//random=9;
        
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
            		rd1.setText(curs.getString(1));
            	}
            	if(i==1)
            	{
            		rd2.setText(curs.getString(1));
            	}
            	if(i==2)
            	{
            		rd3.setText(curs.getString(1));
            	}
            	
            	if(i==3)
            	{
            		rd4.setText(curs.getString(1));
            	}
            
            	curs.moveToNext();
            }
            
            tv.setText(q);       	
        	curs.close();
        }else if(random==1)
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
            		rd1.setText(curs.getString(1));
            	}
            	if(i==1)
            	{
            		rd2.setText(curs.getString(1));
            	}
            	if(i==2)
            	{
            		rd3.setText(curs.getString(1));
            	}
            	
            	if(i==3)
            	{
            		rd4.setText(curs.getString(1));
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
			"where sm.s_id = s.id and sm.m_id = m._id and first_name != '" + curs.getString(0) + "' and title != '" + curs.getString(2) +"' order by random() limit 3;";
        	
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
            			rd1.setText(curs.getString(0) + " " +curs.getString(1));
            		}
            		else
            		{
            		rd1.setText(curs2.getString(0) + " " +curs2.getString(1));
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs.getString(0) + " " +curs.getString(1));
            		}
            		else
            		{
            		rd2.setText(curs2.getString(0) + " " +curs2.getString(1));
            		curs2.moveToNext();
            		}
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs.getString(0) + " " +curs.getString(1));
            		}
            		else
            		{
            		rd3.setText(curs2.getString(0) + " " +curs2.getString(1));
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs.getString(0) + " " +curs.getString(1));
            		}
            		else
            		{
            		rd4.setText(curs2.getString(0) + " " +curs2.getString(1));
            		curs2.moveToNext();
            		}
            	}
            
            	
            }
            
            tv.setText(q);
            curs.close();
            curs2.close();
        	
        }else if(random==3)
        {
        	String query = "select first_name, last_name, title from movies, stars, stars_in_movies, (select mi, count from (select m_id as mi, count(s_id) as count from stars_in_movies group by m_id order by count(s_id) desc) where count > 2 order by random() limit 1) where _id=mi and m_id=mi and s_id=id order by random() limit 3;";
                  	Cursor curs2=sqldb.rawQuery(query, null);
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
                    			rd1.setText(curs.getString(0)+ " " +curs.getString(1));
                    		}
                    		else
                    		{
                    		rd1.setText(curs2.getString(0) + " " +curs2.getString(1));
                    		curs2.moveToNext();
                    		}
                    	}
                    	if(i==1)
                    	{
                    		if(i==pick)
                    		{
                    			rd2.setText(curs.getString(0)+" " +curs.getString(1));
                    		}
                    		else{
                    			rd2.setText(curs2.getString(0) +" " + curs2.getString(1));
                        		curs2.moveToNext();
                    			
                    		}
                    		
                    	}
                    	if(i==2)
                    	{
                    		if(i==pick)
                    		{
                    			rd3.setText(curs.getString(0)+" " +curs.getString(1));
                    		}
                    		else
                    		{
                    		rd3.setText(curs2.getString(0) +" " + curs2.getString(1));
                    		curs2.moveToNext();
                    		}
                    	}
                    	
                    	if(i==3)
                    	{
                    		if(i==pick)
                    		{
                    			rd4.setText(curs.getString(0)+" " +curs.getString(1));
                    		}
                    		else
                    		{
        	            		rd4.setText(curs2.getString(0) +" " + curs2.getString(1));
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
        	
        	String query2="select title from movies where title !=\"" + curs2.getString(2) + "\" order by random() limit 3";
        	Cursor curs=sqldb.rawQuery(query2, null);
        	
        	curs.moveToFirst();
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			rd1.setText(curs2.getString(2));
            		}
            		else
            		{
            		rd1.setText(curs.getString(0));
            		curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs2.getString(2));
            		}
            		else
            		{
            		rd2.setText(curs.getString(0));
            		curs.moveToNext();
            		}            		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs2.getString(2));
            		}
            		else
            		{
            		rd3.setText(curs.getString(0));
            		curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs2.getString(2));
            		}
            		else
            		{
            		rd4.setText(curs.getString(0));
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
        	String query2="select first_name, last_name, director from (select first_name, last_name, director from stars, stars_in_movies, movies where m_id=_id and s_id=id except select first_name, last_name, director from stars, stars_in_movies, movies where m_id=_id and s_id=id and director = '" + curs.getString(2) +"') order by random() limit 3;";
        	Cursor curs2=sqldb.rawQuery(query2, null);
        	
        	
        	curs2.moveToFirst();

        	
        	q=questions[5].replace("X", curs.getString(0) + " " + curs.getString(1));
        	tv.setText(q);
        	
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			rd1.setText(curs.getString(2));
            		}
            		else
            		{
            		rd1.setText(curs2.getString(2));
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs.getString(2));
            		}
            		else
            		{
            		rd2.setText(curs2.getString(2));
            		curs2.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs.getString(2));
            		}
            		else
            		{
            		rd3.setText(curs2.getString(2));
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs.getString(2));
            		}
            		else
            		{
            		rd4.setText(curs2.getString(2));
            		curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();	
        	
        }else if(random==6)
        {
        	String query = "select first_name, last_name, director, id from stars, movies, stars_in_movies, (select s, count from (select s_id as s, count(distinct director) as count from movies, stars, stars_in_movies where _id=m_id and s_id=id group by s_id order by count(director) desc) where count > 2 order by random() limit 1) where s=s_id and s_id=id and _id=m_id order by random() limit 3;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	curs.moveToFirst();
        	
        	String query2 = "select director from movies, stars_in_movies where m_id=_id and s_id != " + curs.getInt(3) + ";";
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
            			rd1.setText(curs2.getString(0));
            		}
            		else
            		{
            		rd1.setText(curs.getString(2));
            		curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs2.getString(0));
            		}
            		else
            		{
            		rd2.setText(curs.getString(2));
            		curs.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs2.getString(0));
            		}
            		else
            		{
            		rd3.setText(curs.getString(2));
            		curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs2.getString(0));
            		}
            		else
            		{
            		rd4.setText(curs.getString(2));
            		curs.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();	
        	
        	
        }else if(random==7)
        {
        	String query = "select first_name, last_name, title, _id, title from stars, stars_in_movies, movies, (select s_id as s from(select s_id, count(distinct m_id) as count from stars_in_movies group by s_id order by count(m_id)) where count > 1 order by random() limit 1) where _id=m_id and s_id=id and s=id order by random() limit 2;";
        	q = questions[7];
        	Cursor curs = sqldb.rawQuery(query, null);
        	curs.moveToFirst();
        	q = q.replace("X", curs.getString(4));
        	String query2 = "select first_name, last_name from stars, stars_in_movies where m_id != " + curs.getInt(3);
        	curs.moveToNext();
        	query2= query2 + " and m_id != " + curs.getInt(3) + " order by random() limit 3;";
        	
        	Cursor curs2 = sqldb.rawQuery(query2, null);
        	q = q.replace("Y", curs.getString(4));
        	
        	tv.setText(q);
        	
        	curs2.moveToFirst();
        	for(int i = 0;i<4;i++)
            {
            	
            	if(i==0)
            	{   
            		if(i==pick)
            		{
            			rd1.setText(curs.getString(0) + " " +  curs.getString(1));
            		}
            		else
            		{
            			rd1.setText(curs2.getString(0) + " " + curs2.getString(1));
            			curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs.getString(0) + " " +  curs.getString(1));
            		}
            		else
            		{
            			rd2.setText(curs2.getString(0) + " " + curs2.getString(1));
            			curs2.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs.getString(0) + " " +  curs.getString(1));
            		}
            		else
            		{
            			rd3.setText(curs2.getString(0) + " " + curs2.getString(1));
            			curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs.getString(0) + " " +  curs.getString(1));
            		}
            		else
            		{
            			rd4.setText(curs2.getString(0) + " " + curs2.getString(1));
            			curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();
        	
        	
        	
        	
        	
        	
        	
        }else if(random==8)
        {
        	String query = "select first_name, last_name, _id from stars, movies, stars_in_movies, (select m from (select m_id as m, count(distinct s_id) as c from stars_in_movies group by m_id order by count(s_id)) where c > 3 order by random() limit 1) where m=m_id and m_id=_id and s_id=id order by random() limit 4;";
        	Cursor curs = sqldb.rawQuery(query, null);
        	
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
            			rd1.setText(curs2.getString(0) + " " +  curs2.getString(1));
            		}
            		else
            		{
            			rd1.setText(curs.getString(0) + " " + curs.getString(1));
            			curs.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs2.getString(0) + " " +  curs2.getString(1));
            		}
            		else
            		{
            			rd2.setText(curs.getString(0) + " " + curs.getString(1));
            			curs.moveToNext();
            		}    		
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs2.getString(0) + " " +  curs2.getString(1));
            		}
            		else
            		{
            			rd3.setText(curs.getString(0) + " " + curs.getString(1));
            			curs.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs2.getString(0) + " " +  curs2.getString(1));
            		}
            		else
            		{
            			rd4.setText(curs.getString(0) + " " + curs.getString(1));
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
        	
        	String query2= "select director from movies where director != '" + curs.getString(0) + "' and year ='" + curs.getString(1) + "' order by random() limit 3;"; 
        	Cursor curs2 = sqldb.rawQuery(query2, null);
        	
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
            			rd1.setText(curs.getString(0));
            		}
            		else
            		{
            		rd1.setText(curs2.getString(0));
            		curs2.moveToNext();
            		}
            	}
            	if(i==1)
            	{
            		if(i==pick)
            		{
            			rd2.setText(curs.getString(0));
            		}
            		else
            		{
            		rd2.setText(curs2.getString(0));
            		curs2.moveToNext();
            		}
            	}
            	if(i==2)
            	{
            		if(i==pick)
            		{
            			rd3.setText(curs.getString(0));
            		}
            		else
            		{
            		rd3.setText(curs2.getString(0));
            		curs2.moveToNext();
            		}
            	}
            	
            	if(i==3)
            	{
            		if(i==pick)
            		{
            			rd4.setText(curs.getString(0));
            		}
            		else
            		{
            		rd4.setText(curs2.getString(0));
            		curs2.moveToNext();
            		}
            	}	
            }
        	curs.close();
        	curs2.close();
        	
        	
        	
        }
        
        
        
        
        
        
        
        
        
        
        
        
        db.close();
        
        // Retrieve the button, change its value and add an event listener
        
        
        this.backButton = (Button)this.findViewById(R.id.backButton);
        this.backButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			finish();
		}
        });
    }

}












