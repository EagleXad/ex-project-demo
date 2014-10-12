package com.ex.sdk.lib.share;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ex.sdk.lib.R;
import com.ex.sdk.lib.share.entry.ExKeyShare;

public class MgrSharer {

	private String mTitile = "分享中...";

	private static Activity mActivity;

	private static class SharerHolder {

		private static MgrSharer mgr = new MgrSharer();

	}

	public static MgrSharer getInstance(Activity activity) {

		if (mActivity == null) {
			mActivity = activity;

			SharerHolder.mgr.init();
		}

		return SharerHolder.mgr;
	}

	private void init() {

		ShareSDK.initSDK(mActivity);
		ShareSDK.setConnTimeout(5000);
		ShareSDK.setReadTimeout(10000);

	}

	public void setTitle(String title) {

		mTitile = title;
	}

	public void shareSMS(String content) {

		Uri smsToUri = Uri.parse("smsto:");

		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", content);
		mActivity.startActivity(intent);
	}

	public void shareSend() {

	}

	public void shareSend(String content) {

		openShareByContentOrBinary(content, null);
	}

	public void shareSend(File binary) {

		openShareByContentOrBinary(null, binary);
	}

	public void shareSend(String content, File binary) {

		openShareByContentOrBinary(content, binary);
	}

	public void shareSend(ArrayList<Uri> uris) {

		openShareByList(uris);
	}

	public void shareSend(boolean silent, String platform, ExKeyShare share) {

		shareOther(silent, platform, share);
	}

	private void openShareByContentOrBinary(String content, File binary) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");

		if (binary != null && binary.exists() && binary.isFile()) {
			intent.setType("image/*");

			Uri u = Uri.fromFile(binary);
			intent.putExtra(Intent.EXTRA_STREAM, u);
		}

		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		mActivity.startActivity(Intent.createChooser(intent, mTitile));
	}

	private void openShareByList(ArrayList<Uri> uris) {

		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

		mActivity.startActivity(Intent.createChooser(intent, mTitile));
	}

	private void shareOther(boolean silent, String platform, ExKeyShare share) {

		final OnekeyShare oks = new OnekeyShare();

		oks.setNotification(R.drawable.ex_sdk_share_notification_icon, share.getNotificationAppName());

		// oks.setAddress("12345678901");

		oks.setTitle(share.getTitle());
		oks.setTitleUrl(share.getTitleUrl());
		oks.setImageUrl(share.getImageUrl());
		oks.setText(share.getText());
		oks.setSite(share.getSite());
		oks.setSiteUrl(share.getSiteUrl());
		oks.setUrl(share.getUrl());
		oks.setSilent(silent);

		// oks.setAddress(share.getAddress()); // 12345678901
		// oks.setTitle(share.getTitle()); // 内容分享
		// oks.setTitleUrl(share.getTitleUrl()); // http://sharesdk.cn
		// oks.setText(share.getText()); // 这是一个很好玩的东西
		// oks.setImageUrl(share.getImageUrl()); //
		// http://res.3636.com/cpsapks/png/testkpk_1.6.6.007.png
		// oks.setUrl(share.getUrl()); // http://www.sharesdk.cn
		// oks.setImagePath(share.getImagePath());
		// oks.setFilePath(share.getFilePath());
		// oks.setComment(share.getComment()); //
		// oks.setSite(share.getSite()); //
		// oks.setSiteUrl(share.getSiteUrl()); // http://sharesdk.cn
		// oks.setVenueName(share.getVenueName()); // ShareSDK
		// oks.setVenueDescription(share.getVenueDescription()); // This is a
		// // beautiful
		// // place!
		// oks.setLatitude(share.getLatitude()); // 23.056081f
		// oks.setLongitude(share.getLongitude()); // 113.385708f

		// 是否有自定义编辑页初始化分享平台
		if (platform != null) {
			oks.setPlatform(platform);
		}

		oks.setDialogMode();
		oks.disableSSOWhenAuthorize();

		// 开始分享
		oks.show(mActivity);
	}

	@SuppressWarnings("unused")
	private void demoOther(boolean silent, String platform) {

		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ex_sdk_share_notification_icon, "demo");
		oks.setAddress("12345678901");
		oks.setTitle("ceshi");
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText("ceshi");
		// oks.setImageUrl("http://res.3636.com/cpsapks/png/testkpk_1.6.6.007.png");
		oks.setUrl("http://www.sharesdk.cn");
		oks.setSilent(silent);

		if (platform != null) {
			oks.setPlatform(platform);
		}

		oks.setDialogMode();

		oks.disableSSOWhenAuthorize();

		oks.show(mActivity);
	}

}
