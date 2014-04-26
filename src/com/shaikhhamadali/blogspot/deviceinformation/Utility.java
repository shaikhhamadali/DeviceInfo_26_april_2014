package com.shaikhhamadali.blogspot.deviceinformation;

import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.util.*;
import org.apache.http.conn.util.InetAddressUtils;

public class Utility {

	public Utility() {

	}

	public static ArrayList<String> getlist() {
		// Create an instance of ArrayList of String.
		ArrayList<String> str = new ArrayList<String>();
		// add information to list
		str.add("Version : " + System.getProperty("os.version"));
		str.add("Version Release : " + android.os.Build.VERSION.RELEASE);
		str.add("Device : " + android.os.Build.DEVICE);
		str.add("Model : " + android.os.Build.MODEL);
		str.add("Product : " + android.os.Build.PRODUCT);
		str.add("Brand : " + android.os.Build.BRAND);
		str.add("Display : " + android.os.Build.DISPLAY);
		str.add("CPU_ABI : " + android.os.Build.CPU_ABI);
		str.add("CPU_ABI2 : " + android.os.Build.CPU_ABI2);
		str.add("Unknown  :" + android.os.Build.UNKNOWN);
		str.add("HARDWARE : " + android.os.Build.HARDWARE);
		str.add("ID : " + android.os.Build.ID);
		str.add("Manufecturer : " + android.os.Build.MANUFACTURER);
		str.add("Serial : " + android.os.Build.SERIAL);
		str.add("Host : " + android.os.Build.HOST);
		str.add("FingerPrint : " + android.os.Build.FINGERPRINT);
		str.add("User : " + android.os.Build.USER);
		str.add("Lan Mac Add : " + getMACAddress("wlan0"));
		str.add("ether Add : " + getMACAddress("eth0"));
		str.add("ipv4 : " + getIPAddress(true));
		str.add("ipv6 : " + getIPAddress(false));
		// return list
		return str;
	}

	/**
	 * Returns MAC address of the given interface name like wlan0 or eth0.
	 * 
	 * @param interfaceName
	 *            eth0, wlan0 or NULL=use first interface
	 * @return mac address or empty string
	 */
	public static String getMACAddress(String interfaceName) {
		try {
			// create instance of List to store List of NetworkInterfaces
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			// iterate to every item of interfaces
			for (NetworkInterface intf : interfaces) {
				// check only not null interfaces
				if (interfaceName != null) {
					// if interface matches to the required or not
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				// get hardware address that is MAC address
				byte[] mac = intf.getHardwareAddress();
				if (mac == null)
					return "";
				// create instance of StringBuilder
				StringBuilder buf = new StringBuilder();
				// iterate though every byte to format the address in Particular
				// MAC address format.
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0)
					buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (Exception ex) {
		} // for now ignore exceptions
		return "";
	}

	/**
	 * Get IP address from first non-localhost interface
	 * 
	 * @param ipv4
	 *            true=return ipv4, false=return ipv6
	 * @return address or empty string
	 */
	public static String getIPAddress(boolean useIPv4) {
		try {
			// create instance of List to store List of NetworkInterfaces
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			// iterate to every item of interfaces
			for (NetworkInterface intf : interfaces) {
				// create instance of List to store List of InetAddress
				List<InetAddress> addrs = Collections.list(intf
						.getInetAddresses());
				// iterate to every item of InetAddress list
				for (InetAddress addr : addrs) {
					// check that is loop back address
					/*
					 * Valid IPv4 loopback addresses have the prefix 127/8. The
					 * only valid IPv6 loopback address is ::1.
					 */
					if (!addr.isLoopbackAddress()) {
						// get HostAddress
						String sAddr = addr.getHostAddress().toUpperCase();
						// check that is ipv4
						boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
						// if ipv4 return address
						if (useIPv4) {
							if (isIPv4)
								return sAddr;
						} else {
							// or ipv6
							if (!isIPv4) {
								int delim = sAddr.indexOf('%'); // drop ip6 port
																// suffix
								// return ipv6
								return delim < 0 ? sAddr : sAddr.substring(0,
										delim);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		} // for now ignore Exceptions
		return "";
	}
}
