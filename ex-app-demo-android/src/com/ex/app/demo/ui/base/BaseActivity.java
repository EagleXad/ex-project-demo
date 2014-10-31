package com.ex.app.demo.ui.base;

import java.util.Map;

import android.content.Intent;

import com.ex.sdk.core.ExBaseSwipeBackAcvitiy;
import com.ex.sdk.core.ible.ExNetIble;
import com.ex.sdk.core.ible.ExReceiverIble;

public class BaseActivity extends ExBaseSwipeBackAcvitiy implements ExNetIble, ExReceiverIble {

	@Override
	protected int exInitLayout() {
		return 0;
	}

	@Override
	protected void exInitBundle() {

	}

	@Override
	protected void exInitView() {

	}

	@Override
	protected void exInitAfter() {

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
