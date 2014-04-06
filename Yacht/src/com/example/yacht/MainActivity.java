package com.example.yacht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button mBtnNewGame;
	Button mBtnHowToPlay;
	Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBtnNewGame = (Button) findViewById(R.id.btnNewGame);
		mBtnHowToPlay = (Button) findViewById(R.id.btnHowPlay);
		
		context = this;
		
		mBtnNewGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, Game.class);
				startActivity(i);
			}
		});
		
		mBtnHowToPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, Rules.class);
				startActivity(i);
				
			}
		});
		
	}
}
