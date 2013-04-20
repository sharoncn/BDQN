package rjfsdo.sharoncn.android.CartoonReaderWeb.Activities.Components.Animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ItemAnimationFactory {
	private static ItemAnimationFactory me = null;
	private static Context context;
	private static int res;
	
	static{
		me = new ItemAnimationFactory();
	}
	
	private ItemAnimationFactory(){}
	
	public static ItemAnimationFactory getInstance(Context c,int anim_res){
		context = c;
		res = anim_res;
		return me;
	}
	
	public Animation getAnimation(){
		return AnimationUtils.loadAnimation(context, res);
	}
}
