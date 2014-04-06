package com.example.yacht;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Game extends Activity {

	Button btnShuffle;
	Button btnReshuffle;
	Button btnDone;
	
	ImageView imgDie1;
	ImageView imgDie2;
	ImageView imgDie3;
	ImageView imgDie4;
	ImageView imgDie5;
	
	ListView listPlayer1;
	ListView listPlayer2;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		btnShuffle = (Button) findViewById(R.id.btnShuffle);
		btnReshuffle = (Button) findViewById(R.id.btnReshuffle);
		btnDone = (Button) findViewById(R.id.btnDone);
		
		imgDie1 = (ImageView) findViewById(R.id.imgDice1);
		imgDie2 = (ImageView) findViewById(R.id.imgDice2);
		imgDie3 = (ImageView) findViewById(R.id.imgDice3);
		imgDie4 = (ImageView) findViewById(R.id.imgDice4);
		imgDie5 = (ImageView) findViewById(R.id.imgDice5);
		
		listPlayer1 = (ListView) findViewById(R.id.listPlayer1);
		listPlayer2 = (ListView) findViewById(R.id.listPlayer2);
		
		String[] combinations = this.getResources().getStringArray(getResources().getIdentifier("combinations","array", getPackageName()));
		
		ArrayList<String> combs = new ArrayList<String>(Arrays.asList(combinations));
		
		listPlayer1.setAdapter(new ScoreAdapter(this, combs, 0));
		listPlayer2.setAdapter(new ScoreAdapter(this, combs, 1));
				
	}
	
}
