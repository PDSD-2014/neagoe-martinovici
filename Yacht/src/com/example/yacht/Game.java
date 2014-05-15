package com.example.yacht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Game extends Activity {

	Button btnShuffle;
	Button btnReshuffle;
	Button btnDone;

	ImageView imgDie1;
	ImageView imgDie2;
	ImageView imgDie3;
	ImageView imgDie4;
	ImageView imgDie5;
	
	TextView myScore;

	ListView listPlayer1;
	ListView listPlayer2;

	// the values of the dices
	Vector<Integer> dices;

	// which dices i can reshuffle
	Vector<Boolean> active_dices;

	// I can reshuffle twice
	int reshuffle_count = 2;

	ArrayList<Choice> myScores;
	ArrayList<Choice> hisScores;
	
	// counts how many dice of each number I have
	ArrayList<Integer> diceCount;
	
	// last selection on list.
	Integer lastSelection = null;
	
	boolean isShuffled = false;
	
	Vector<Boolean> selections;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		btnShuffle = (Button) findViewById(R.id.btnShuffle);
		btnReshuffle = (Button) findViewById(R.id.btnReshuffle);
		btnReshuffle.setEnabled(false);
		btnDone = (Button) findViewById(R.id.btnDone);
		btnDone.setEnabled(false);
		
		myScore = (TextView) findViewById(R.id.txtPlayer1Score);

		imgDie1 = (ImageView) findViewById(R.id.imgDice1);
		imgDie2 = (ImageView) findViewById(R.id.imgDice2);
		imgDie3 = (ImageView) findViewById(R.id.imgDice3);
		imgDie4 = (ImageView) findViewById(R.id.imgDice4);
		imgDie5 = (ImageView) findViewById(R.id.imgDice5);
		
		imgDie1.setEnabled(false);
		imgDie2.setEnabled(false);
		imgDie3.setEnabled(false);
		imgDie4.setEnabled(false);
		imgDie5.setEnabled(false);

		listPlayer1 = (ListView) findViewById(R.id.listPlayer1);
		listPlayer2 = (ListView) findViewById(R.id.listPlayer2);

			
		String[] combinations = this.getResources().getStringArray(
				getResources().getIdentifier("combinations", "array",
						getPackageName()));

		ArrayList<String> combs = new ArrayList<String>(
				Arrays.asList(combinations));
		myScores = new ArrayList<Choice>();
		hisScores = new ArrayList<Choice>();
		for (String score : combs) {
			myScores.add(new Choice(score, ""));
			hisScores.add(new Choice(score, ""));
		}

		
		listPlayer1.setAdapter(new ScoreAdapter(this, myScores, 0));
		listPlayer2.setAdapter(new ScoreAdapter(this, hisScores, 1));
		
		dices = new Vector<Integer>(5);
		for (int i = 0; i < 5; i++)
			dices.add(0);

		diceCount = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++)
			diceCount.add(0);

		active_dices = new Vector<Boolean>(5);
		for (int i = 0; i < 5; i++)
			active_dices.add(true);
		
		//vector that says which item on the list can be chose
		// i can select everything on the list
		selections = new Vector<Boolean>();
		for (int i = 0; i < 14; i++)
			selections.add(true);

		listPlayer1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(isShuffled == true && selections.get(position) == true){
					if (lastSelection != null)
						myScores.get(lastSelection).setValue("");
					countDice();
					myScores.get(position).setValue(getScore(position).toString());

					((BaseAdapter) listPlayer1.getAdapter()).notifyDataSetChanged();
					lastSelection = position;
					
					btnDone.setEnabled(true);
				}
			}
		});

		//when i pressed done i finished my turn
		btnDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//re-enable shuffle
				btnShuffle.setEnabled(true);
				
				//reshuffle will be enabled after shuffle
				btnReshuffle.setEnabled(false);
				
				//this is set false so you can't make clicks on the list
				isShuffled = false;
				
				//you can't press again done
				btnDone.setEnabled(false);
				
				//you have again two reshuffles available
				reshuffle_count = 2;
				
				//this is reflected on the button text
				btnReshuffle.setText("Reshuffle" + "("
						+ String.valueOf(reshuffle_count) + ")");
				
				//i can't press anymore on this item of the list
				selections.set(lastSelection, false);
				
				//reset the lastSelection for the next turn
				lastSelection = null;
										
				//reset active_dices
				for (int i = 0; i < 5; i++)
					active_dices.add(true);
				
				//compute the score
				int score = getTotalScore();
				
				myScore.setText("Score: " + String.valueOf(score));
				
				//reset the images
				imgDie1.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_1", null, null));
				imgDie2.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_2", null, null));
				imgDie3.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_3", null, null));
				imgDie4.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_4", null, null));
				imgDie5.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_5", null, null));
			}
		});
		
		
		btnShuffle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// we have to generate 5 random numbers
				
				isShuffled = true;
				
				imgDie1.setEnabled(true);
				imgDie2.setEnabled(true);
				imgDie3.setEnabled(true);
				imgDie4.setEnabled(true);
				imgDie5.setEnabled(true);
				
				for (int i = 0; i < 5; i++) {
					Random rand = new Random();
					Integer val = rand.nextInt(6) + 1;
					dices.set(i, val);
				}

				imgDie1.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_"
								+ String.valueOf(dices.get(0)), null, null));
				imgDie2.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_"
								+ String.valueOf(dices.get(1)), null, null));
				imgDie3.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_"
								+ String.valueOf(dices.get(2)), null, null));
				imgDie4.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_"
								+ String.valueOf(dices.get(3)), null, null));
				imgDie5.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_"
								+ String.valueOf(dices.get(4)), null, null));

				btnShuffle.setEnabled(false);
				btnReshuffle.setEnabled(true);
				
				listPlayer1.setClickable(true);
			}
		});

		btnReshuffle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (reshuffle_count > 0) {
					reshuffle_count--;
					btnReshuffle.setText("Reshuffle" + "("
							+ String.valueOf(reshuffle_count) + ")");
					reshuffle();
				}
			}
		});

		imgDie1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(0, false);
				imgDie1.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_black_"
								+ String.valueOf(dices.get(0)), null, null));
				imgDie1.setEnabled(false);
			}
		});

		imgDie2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(1, false);
				imgDie2.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_black_"
								+ String.valueOf(dices.get(1)), null, null));
				imgDie2.setEnabled(false);
			}
		});

		imgDie3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(2, false);
				imgDie3.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_black_"
								+ String.valueOf(dices.get(2)), null, null));
				imgDie3.setEnabled(false);
			}
		});

		imgDie4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(3, false);
				imgDie4.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_black_"
								+ String.valueOf(dices.get(3)), null, null));
				imgDie4.setEnabled(false);
			}
		});

		imgDie5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				active_dices.set(4, false);
				imgDie5.setImageResource(getResources().getIdentifier(
						getPackageName() + ":drawable/" + "die_face_black_"
								+ String.valueOf(dices.get(4)), null, null));
				imgDie5.setEnabled(false);
			}
		});
	}

	public void reshuffle() {
		for (int i = 0; i < 5; i++)
			if (active_dices.get(i) == true) {
				Random rand = new Random();
				Integer val = rand.nextInt(6) + 1;
				dices.set(i, val);

				switch (i) {
					case 0:
						imgDie1.setImageResource(getResources().getIdentifier(
								getPackageName() + ":drawable/" + "die_face_"
										+ String.valueOf(dices.get(0)), null,
								null));
						break;

					case 1:
						imgDie2.setImageResource(getResources().getIdentifier(
								getPackageName() + ":drawable/" + "die_face_"
										+ String.valueOf(dices.get(1)), null,
								null));
						break;

					case 2:
						imgDie3.setImageResource(getResources().getIdentifier(
								getPackageName() + ":drawable/" + "die_face_"
										+ String.valueOf(dices.get(2)), null,
								null));
						break;

					case 3:
						imgDie4.setImageResource(getResources().getIdentifier(
								getPackageName() + ":drawable/" + "die_face_"
										+ String.valueOf(dices.get(3)), null,
								null));
						break;

					case 4:
						imgDie5.setImageResource(getResources().getIdentifier(
								getPackageName() + ":drawable/" + "die_face_"
										+ String.valueOf(dices.get(4)), null,
								null));
						break;

				}
			}
	}

	public Integer getScore(Integer pos) {
		Integer score = 0;
		int semafor = 0;
		switch (pos) {
			case 0:// Ones
				score = diceCount.get(1) * 1;
				break;
			case 1:// Twos
				score = diceCount.get(2) * 2;
				break;
			case 2:// Threes
				score = diceCount.get(3) * 3;
				break;
			case 3:// Fours
				score = diceCount.get(4) * 4;
				break;
			case 4:// Fives
				score = diceCount.get(5) * 5;
				break;
			case 5:// Sixes
				score = diceCount.get(6) * 6;
				break;
			case 6:// 1 pair
				int pairCounter1 = 0;
				for (int i = 1; i <= 6; i++)
					if (diceCount.get(i) == 2)
						pairCounter1++;
				if (pairCounter1 >= 1)
					score = 5;
				break;
			case 7:// 2 pairs
				int pairCounter = 0;
				for (int i = 1; i <= 6; i++)
					if (diceCount.get(i) == 2)
						pairCounter++;
				if (pairCounter == 2)
					score = 10;

				break;
			case 8: // 3 oak
				for (Integer i : diceCount)
					if (i == 3)
						score = 15;
				break;
			case 9: // Full House
				for (int i = 1; i <= 6; i++)
					for (int j = 1; j <= 6; j++)
						if (diceCount.get(i) == 3 && diceCount.get(j) == 2
								|| diceCount.get(i) == 2
								&& diceCount.get(j) == 3)
							score = 20;

				break;
			case 10: // 4 oak
				for (Integer i : diceCount)
					if (i == 4)
						score = 25;
				break;
			case 11:// 5 straight
				if (diceCount.get(2) == 1 && diceCount.get(3) == 1
						&& diceCount.get(4) == 1 && diceCount.get(5) == 1
						&& (diceCount.get(1) == 1 || diceCount.get(6) == 1))
					score = 30;
				break;
			case 12:// Sum
				for (int i = 0; i < dices.size(); i++)
					score = score + dices.get(i);
				break;
			case 13:// Yacht
				for (Integer i : diceCount)
					if (i == 5)
						score = 50;
				break;
			default:
				break;
		}

		return score;
	}

	public void countDice() {
		for (int i = 1; i <= 6; i++)
			diceCount.set(i, 0);
		for (int i = 1; i <= dices.size(); i++)
			diceCount
					.set(dices.get(i - 1), diceCount.get(dices.get(i - 1)) + 1);
		// for (int i = 1; i < diceCount.size(); i++)
		// Log.e("CRISTI's DICE", "nr de" + (i) + " este " + diceCount.get(i));
	}
	
	public Integer getTotalScore(){
		
		int score = 0;
		
		for (int i = 0; i < myScores.size(); i++)
			if(myScores.get(i).getValue() != "")
				score += Integer.valueOf(myScores.get(i).getValue());
		return score;
	}

}
