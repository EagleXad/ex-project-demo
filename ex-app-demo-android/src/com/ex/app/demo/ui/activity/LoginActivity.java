package com.ex.app.demo.ui.activity;

import java.util.Map;

import android.content.Intent;

import com.ex.app.demo.ui.R;
import com.ex.app.demo.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

	@Override
	protected int exInitLayout() {
		return R.layout.de_activity_login;
	}

	@Override
	protected void exInitBundle() {
		
		super.initIble(this, this);
	}

	@Override
	protected void exInitView() {
		
	}

	@Override
	protected void exInitAfter() {

//		super.startTask("", 0);
	}

	@Override
	protected String[] exInitReceiver() {

		return null;
	}

	@Override
	public void onReceiver(Intent intent) {

	}

	@Override
	public Map<String, String> onStart(int what) {
		return null;
	}

	@Override
	public void onSuccess(int what, String result) {

	}

	@Override
	public void onError(int what, int e, String message) {

	}

}
