package rjfsdo.sharoncn.android.BDQN.LeisureLife.Test;

import java.util.ArrayList;
import java.util.List;

import rjfsdo.sharoncn.android.BDQN.LeisureLife.R;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Components.FoodShowcase;
import rjfsdo.sharoncn.android.BDQN.LeisureLife.Data.DataManager.Delicacies.Food;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class TestFoodShowcase extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item_detail_delicacies_two);
		
		TextView tv = (TextView) findViewById(R.id.list_item_detail_delicacies_two_addr);
		tv.setText("123f4d56s45fw");
		tv.setBackgroundColor(Color.BLUE);
		FoodShowcase fs = (FoodShowcase) findViewById(R.id.foodShowcase);
		fs.setBackgroundColor(Color.WHITE);
		List<Food> foods = new ArrayList<Food>();
		for(int i = 0;i < 3;i ++){
			Food food = new Food();
			food.setName("food" + i);
			food.setOprice(i + "");
			food.setNprice(i + "");
			foods.add(food);
		}
		for(Food food: foods){
			fs.putFood(food, getResources().getDrawable(R.drawable.seeshow));
		}
		fs.requestLayout();
	}

}
