package com.example.android_ab;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MainActivity", "onCreate");
        
        RelativeLayout layout = new RelativeLayout(this);
        TextView text = new TextView(this);
        Resources resources = this.getResources();
        
        String bucket = new ABUtil(this).getBucket("experiment_1");
        if("control".equals(bucket)){
        	text.setText(resources.getString(R.string.control_content));
        }else if("fancy".equals(bucket)){
        	text.setText(resources.getString(R.string.fancy_content));
        }else{
        	text.setText(resources.getString(R.string.default_content));
        }
        
        layout.addView(text);
        
        setContentView(layout);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = new Intent(this, ExperimentConfigService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
