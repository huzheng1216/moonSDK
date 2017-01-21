package com.flyersoft.sdk.tools;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * 获取手机SIM卡信息的工具类 需要权限：android.permission.READ_PHONE_STATE
 * 
 * @author mingsong.zhang
 * @date 20120627
 */
public class TelephonyManagerTools {

	/**
	 * 唯一的设备ID：<br/>
	 * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID<br/>
	 * 需要权限：android.permission.READ_PHONE_STATE
	 * 
	 * @return null if device ID is not available.
	 */
	public static String getImei(Context context) {
		if (context == null) {
			return "";
		}

		String imei = Tools.getInformain("imei", "", context);
		if (StringTools.isEmpty(imei)) {
			TelephonyManager telMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			try {
				imei = telMgr.getDeviceId();
			}catch (Exception e){
				imei = "" ;
			}

			Tools.setInformain("imei", imei, context);

		}
		return imei;
	}

	/**
	 * 获取mac地址 需要权限：android.permission.READ_PHONE_STATE
	 * 
	 * @return null if device ID is not available.
	 */
	public static String getMacAddress(Context context) {
		if (context == null) {
			return "";
		}

		String mac = Tools.getInformain("mac", "", context);
		if (StringTools.isEmpty(mac)) {
			// tencent过滤了获取服务WIFI_SERVICE，必须获取app的context来获取
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			mac = wifiManager.getConnectionInfo().getMacAddress();

			if (StringTools.isNotEmpty(mac)) {
				if (mac.equals("02:00:00:00:00:00")) {
					mac = getWifiMacAddress();
					if (mac.equals("02:00:00:00:00:00")) {
						mac = "";
					}
				}
				Tools.setInformain("mac", mac, context);
			} else {
				return "";
			}

		}
		return mac;
	}

	/**
	 * 防止某些手机拿到的是默认的mac地址导致产生了某些问题
	 * 
	 * @return
	 */
	private static String getWifiMacAddress() {
		try {
			String interfaceName = "wlan0";
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface
					.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (!intf.getName().equalsIgnoreCase(interfaceName)) {
					continue;
				}

				byte[] mac = intf.getHardwareAddress();
				if (mac == null) {
					return "";
				}

				StringBuilder buf = new StringBuilder();
				for (byte aMac : mac) {
					buf.append(String.format("%02X:", aMac));
				}
				if (buf.length() > 0) {
					buf.deleteCharAt(buf.length() - 1);
				}
				return buf.toString();
			}
		} catch (Exception ex) {
		} // for now eat exceptions
		return "";
	}

	/**
	 * 获取运营商信息
	 * 
	 * @return null if device ID is not available.
	 */
	public static String getProvidersName(Context context) {
		if (context == null) {
			return "";
		}

		String providersName = Tools.getInformain("providersName", "", context);
		if (StringTools.isEmpty(providersName)) {
			TelephonyManager telMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			// 返回唯一的用户ID;就是这张卡的编号神马的
			String IMSI = null ;
			try{
				 IMSI = telMgr.getSubscriberId();
			}catch (Exception e){
				IMSI = "" ;
			}


			LogTools.showLog("op", "imsi:" + IMSI);
			// 可能为null
			if (StringTools.isNotEmpty(IMSI)) {
				// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
						|| IMSI.startsWith("46007")) {
					providersName = "CMCC";
				} else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
					// 联通
					providersName = "CUCC";
				} else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
					// 电信
					providersName = "CTCC";
				}
			}

			return providersName;

		}
		return providersName;
	}

	// /**
	// * 获取ram内存大小
	// * @return null if device ID is not available.
	// */
	// public static String getRam(Context context) {
	//
	// String mac = Tools.getInformain("mac", "", context);
	// if (StringTools.isEmpty(mac)) {
	// // tencent过滤了获取服务WIFI_SERVICE，必须获取app的context来获取
	// WifiManager wifiManager = (WifiManager) context
	// .getApplicationContext().getSystemService(
	// Context.WIFI_SERVICE);
	//
	// mac = wifiManager.getConnectionInfo().getMacAddress();
	// Tools.setInformain("mac", mac, context);
	//
	// }
	// return mac;
	// }

}
