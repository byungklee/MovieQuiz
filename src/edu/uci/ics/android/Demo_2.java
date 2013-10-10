package edu.uci.ics.android;

import edu.uci.ics.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Demo_2 extends Activity {
	
	private Button button2;
	private Button button1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Change the content of the text view
        this.button2 = (Button)this.findViewById(R.id.button2);
        this.button2.setText("Take a quiz!");
        this.button2.setOnClickListener(new OnClickListener() {
    		
    		public void onClick(View v) {
    			// Change the button image
    			//button2.setBackgroundResource(R.drawable.btn_default_normal_green);	
    			Intent intent = new Intent(Demo_2.this, NextActivity.class);
    			startActivity(intent);
    		}
            });
        
        // Retrieve the button, change its value and add an event listener
        this.button1 = (Button)this.findViewById(R.id.button1);
        this.button1.setText("Statistic");
        this.button1.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// Change the button image
			//button1.setBackgroundResource(R.drawable.btn_default_normal_green);	
			Intent intent = new Intent(Demo_2.this, Statistic.class);
			startActivity(intent);
		}
        });

    }
    
    public void onPause()
    {
    	super.onPause();
    }
    public void onResume()
    {
    	super.onPause();
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
    	super.onSaveInstanceState(savedInstanceState);
    	
    	
    }
    
    
}