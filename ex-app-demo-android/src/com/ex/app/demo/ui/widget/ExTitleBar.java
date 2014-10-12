package com.ex.app.demo.ui.widget;

import com.ex.app.demo.ui.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ExTitleBar extends LinearLayout {

	public ExTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.ex_widget_titlebar, this, true);
	}

}
