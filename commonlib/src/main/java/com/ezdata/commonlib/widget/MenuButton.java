package com.ezdata.commonlib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezdata.commonlib.R;
import com.ezdata.commonlib.utils.PhoneUtils;


public class MenuButton extends LinearLayout implements Checkable {

	private ImageView imgIcon;
	private TextView tvName;
	private ColorStateList textColor;

	public MenuButton(Context context){
		super(context);
	}
	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.tabWidget);
		imgIcon = new ImageView(context);
		imgIcon.setClickable(false);
		imgIcon.setImageResource(a.getResourceId(R.styleable.tabWidget_icMenu,
				R.drawable.bg_circle));
		tvName = new TextView(context);
		tvName.setClickable(false);
		tvName.setText(a.getString(R.styleable.tabWidget_text));
		tvName.setGravity(Gravity.CENTER);
		final int textSize = a.getInteger(R.styleable.tabWidget_textSize, 14);
//		tvName.setTextSize(sp2px(context,a.getDimension(R.styleable.tabWidget_textSize, 10)));
		tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
		textColor = a.getColorStateList(R.styleable.tabWidget_textColor);
		tvName.setTextColor(textColor);
		addView(imgIcon,
				new LayoutParams(a.getLayoutDimension(
						R.styleable.tabWidget_icWidth, 30), a
						.getLayoutDimension(R.styleable.tabWidget_icHeight,
								30)));
		addView(tvName, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		a.recycle();
		try {
//			setBackgroundResource(R.drawable.menu_default);
//            setBckgroud(R.drawable.menu_default);
//            setBackgroundColor(getResources().getColor(R.color.gray));
		} catch (Exception e) {
		}
	}
	public void init(Context context, int drawable, String name){
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
		imgIcon = new ImageView(context);
		imgIcon.setClickable(false);
		imgIcon.setImageResource(drawable);
		tvName = new TextView(context);
		tvName.setClickable(false);
		tvName.setText(name);
		tvName.setGravity(Gravity.CENTER);
		textColor = getResources().getColorStateList(R.color.main_text);
		tvName.setTextColor(textColor);
		addView(imgIcon,
				new LayoutParams(PhoneUtils.dip2px(context,30), PhoneUtils.dip2px(context,30)));//30像素???
		addView(tvName, new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			tvName.setTextColor(textColor.getColorForState(
					new int[] { android.R.attr.state_pressed,android.R.attr.state_checkable  }, 0));
			imgIcon.setImageState(new int[] { android.R.attr.state_pressed,android.R.attr.checked,
					android.R.attr.state_checkable }, true);
			break;
		case MotionEvent.ACTION_UP:
			if(!checked){
				
				tvName.setTextColor(textColor.getColorForState(
						new int[] { }, 0));
				imgIcon.setImageState(new int[] {  }, true);
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private boolean checked = false;

	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
		if (checked) {
			tvName.setTextColor(textColor.getColorForState(
					new int[] { android.R.attr.state_pressed,
							android.R.attr.state_checkable }, 0));
			imgIcon.setImageState(new int[] {
					android.R.attr.state_pressed,
					android.R.attr.state_checked,
					android.R.attr.state_checkable }, true);
//			try {
////			//	setBackgroundResource(R.drawable.menu_pressed);
////               setBckgroud(R.drawable.menu_pressed);
//				tvName.setTextColor(getResources().getColor(R.color.blue));
//			} catch (Exception e) {
//			}
		} else {
			tvName.setTextColor(textColor.getColorForState(new int[] {}, 0));
			imgIcon.setImageState(new int[] {}, false);
//			try {
////                setBckgroud(R.drawable.menu_default);
//				tvName.setTextColor(getResources().getColor(R.color.blue2_block));
//			} catch (Exception e) {
//			}
		}
	}

    public void setBckgroud(int imgResource){
        setBackgroundResource(imgResource);
    }

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void toggle() {
		setChecked(!checked);
	}

	@Override
	public boolean performClick() {
		toggle();
		return super.performClick();
	}
}
