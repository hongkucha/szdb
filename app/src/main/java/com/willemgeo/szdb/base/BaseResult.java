package com.willemgeo.szdb.base;

import android.content.Intent;

/**
 * Activity回调函数的数据对象
 * @author Administrator
 *
 */
public class BaseResult {
	
	private int requestCode;
	private int resultCode;
	private Intent data;
	
	
	public int getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public Intent getData() {
		return data;
	}
	public void setData(Intent data) {
		this.data = data;
	}
	

}
