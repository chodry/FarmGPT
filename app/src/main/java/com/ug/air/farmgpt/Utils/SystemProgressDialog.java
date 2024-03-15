package com.ug.air.farmgpt.Utils;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;

public class SystemProgressDialog {
	private ProgressDialog progressDialog;
	private AppCompatActivity compatActivity;


	public SystemProgressDialog(AppCompatActivity compatActivity) {
		this.compatActivity = compatActivity;
	}

	public void showProgressDialog(String message) {
		progressDialog = new ProgressDialog(this.compatActivity);
		progressDialog.setMessage(String.format("%s %s", StringUtils.isNotBlank(message) ? message : "", " Please wait ..."));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	public void closeProgressDialog() {
		try {
			if (progressDialog.isShowing())
				progressDialog.dismiss();
		} catch (Exception e) {
			Log.e(SystemProgressDialog.class.getSimpleName(), e.getMessage(), e);
		}
	}

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}
}