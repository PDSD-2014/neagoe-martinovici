package com.example.yacht;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter {

	Context context;
	ArrayList<Choice> list;
	Integer player;

	public ScoreAdapter(Context c, ArrayList<Choice> l, Integer player) {
		this.context = c;
		this.list = l;
		this.player = player;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_left_player, parent,
					false);
		}

		else
			view = convertView;

		TextView combName = (TextView) view.findViewById(R.id.txtCombName);
		TextView combScore = (TextView) view.findViewById(R.id.txtCombScore);

		combName.setText(list.get(position).getName());
		combScore.setText(list.get(position).getValue());
		if (player == 1)
			combName.setBackgroundColor(context.getResources().getColor(
					R.color.pale_red));
		return view;
	}

}
