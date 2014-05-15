package com.example.yacht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
	
	//the values of the dices
	Vector<Integer> dices;
	
	//which dices i can reshuffle
	Vector<Boolean> active_dices;
	
	//i can reshuffle twice
	int reshuffle_count = 2;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		btnShuffle = (Button) findViewById(R.id.btnShuffle);
		btnReshuffle = (Button) findViewById(R.id.btnReshuffle);
		btnReshuffle.setEnabled(false);
		btnDone = (Button) findViewById(R.id.btnDone);
		btnDone.setEnabled(false);
		
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
		
		dices = new Vector<Integer>(5);
		for(int i =  0; i < 5; i++)
			dices.add(0);
		
		active_dices = new Vector<Boolean>(5);
		for(int i = 0; i < 5; i++)
			active_dices.add(true);
		
		btnShuffle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//we have to generate 5 random numbers
				
				for(int i = 0; i < 5; i++){
					Random rand = new Random();
					Integer val = rand.nextInt(6) + 1;
					dices.set(i, val);
				}
				
				imgDie1.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(0)), null, null));
				imgDie2.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(1)), null, null));
				imgDie3.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(2)), null, null));
				imgDie4.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(3)), null, null));
				imgDie5.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(4)), null, null));
				
				btnShuffle.setEnabled(false);
				btnReshuffle.setEnabled(true);
			}
		});
		
		btnReshuffle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(reshuffle_count > 0){
					reshuffle_count --;
					btnReshuffle.setText("Reshuffle" + "(" + String.valueOf(reshuffle_count) + ")");
					reshuffle();
				}
			}
		});
		
		imgDie1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(0, false);
				imgDie1.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_black_" + String.valueOf(dices.get(0)), null, null));
				imgDie1.setEnabled(false);
			}
		});
		
		imgDie2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(1, false);
				imgDie2.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_black_" + String.valueOf(dices.get(1)), null, null));
				imgDie2.setEnabled(false);
			}
		});
		
		imgDie3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(2, false);
				imgDie3.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_black_" + String.valueOf(dices.get(2)), null, null));
				imgDie3.setEnabled(false);
			}
		});
		
		imgDie4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(3, false);
				imgDie4.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_black_" + String.valueOf(dices.get(3)), null, null));
				imgDie4.setEnabled(false);
			}
		});
		
		imgDie5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(4, false);
				imgDie5.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_black_" + String.valueOf(dices.get(4)), null, null));
				imgDie5.setEnabled(false);
			}
		});
	}
	
	public void reshuffle(){
		for(int i = 0; i < 5; i++)
			if(active_dices.get(i) == true){
				Random rand = new Random();
				Integer val = rand.nextInt(6) + 1;
				dices.set(i, val);
				
				switch(i){
				case 0:
					imgDie1.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(0)), null, null));
					break;
					
				case 1:
					imgDie2.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(1)), null, null));
					break;
					
				case 2:
					imgDie3.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(2)), null, null));
					break;
				
				case 3:
					imgDie4.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(3)), null, null));
					break;
				
				case 4:
					imgDie5.setImageResource(getResources().getIdentifier(getPackageName() + ":drawable/" + "die_face_" + String.valueOf(dices.get(4)), null, null));
					break;
						
				}
			}
	}
	
}
