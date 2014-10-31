package com.ex.app.demo.ui.widget;

import com.ex.app.demo.ui.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DeTitleBar extends LinearLayout {

	public DeTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.de_widget_titlebar, this, true);
	}

}
