/*
 * Copyright (C) 2009-2012 Cyril Jaquier, Felix Bechstein
 * 
 * This file is part of NetCounter.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.conzebit.myplan.android.store.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.net.TrafficStats;
import android.os.Build;

/**
 * Representation of a device.
 */
public abstract class Device {
	
	/** Single instance. */
	private static Device instance = null;
	/** Device's interfaces. */
	private String[] mInterfaces = null;

	/**
	 * @return single instance
	 */
	public static synchronized Device getDevice() {
		if (instance == null) {
			if (Build.PRODUCT.equals("sdk")) {
				instance = new EmulatorDevice();
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				instance = new FroyoDevice();
			} else if (Build.DEVICE.startsWith("GT-")) {
				instance = new SamsungDevice();
			} else {
				instance = new DiscoverableDevice();
			}
		}
		return instance;
	}

	/**
	 * @return device's device file: cell
	 */
	protected abstract String getCell();

	/**
	 * @return received bytes on cell device
	 * @throws IOException
	 *             IOException
	 */
	public abstract long getCellRxBytes() throws IOException;

	/**
	 * @return transmitted bytes on cell device
	 * @throws IOException
	 *             IOException
	 */
	public abstract long getCellTxBytes() throws IOException;

	/**
	 * @return device's device file: wifi
	 */
	protected abstract String getWiFi();

	/**
	 * @return received bytes on wifi device
	 * @throws IOException
	 *             IOException
	 */
	public abstract long getWiFiRxBytes() throws IOException;

	/**
	 * @return transmitted bytes on wifi device
	 * @throws IOException
	 *             IOException
	 */
	public abstract long getWiFiTxBytes() throws IOException;

	/**
	 * @return device's interfaces
	 */
	public final synchronized String[] getInterfaces() {
		if (this.mInterfaces == null) {
			List<String> tmp = new ArrayList<String>();
			if (this.getCell() != null) {
				tmp.add(this.getCell());
			}
			if (this.getWiFi() != null) {
				tmp.add(this.getWiFi());
			}
			this.mInterfaces = tmp.toArray(new String[tmp.size()]);
		}
		return this.mInterfaces;
	}
}

/**
 * Automatically discover the network interfaces. No real magic here, just try
 * different possible solutions.
 */
class DiscoverableDevice extends Device {

	/** List of possible cell interfaces. */
	private static final String[] CELL_INTERFACES = { //
	"rmnet0", "pdp0", "ppp0", "vsnet0", "pdp_ip0", "pdp_ip1", "rmnet_sdio0" //
	};

	/** List of possible wifi interfaces. */
	private static final String[] WIFI_INTERFACES = { //
	"eth0", "tiwlan0", "wlan0", "athwlan0", "eth1" //
	};

	/** My cell interface. */
	private String mCell = null;
	/** My wifi interface. */
	private String mWiFi = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCell() {
		if (this.mCell == null) {
			for (String inter : CELL_INTERFACES) {
				if (SysClassNet.isAvail(inter)) {
					this.mCell = inter;
					break;
				}
			}
		}
		return this.mCell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWiFi() {
		if (this.mWiFi == null) {
			for (String inter : WIFI_INTERFACES) {
				if (SysClassNet.isUp(inter)) {
					this.mWiFi = inter;
					break;
				}
			}
		}
		return this.mWiFi;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellRxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellTxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiRxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiTxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}
}

/**
 * Emulator Device showing all traffic on cell and wifi.
 */
final class EmulatorDevice extends Device {

	/** My cell interface. */
	private final String mCell = "eth0";
	/** My wifi interface. */
	private final String mWiFi = "eth0";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCell() {
		return this.mCell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWiFi() {
		return this.mWiFi;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellRxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellTxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiRxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiTxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}
}

/**
 * Emulator Device showing all traffic on cell and wifi.
 */
final class DebugDevice extends Device {

	/** My cell interface. */
	private final String mCell = "tiwlan0";
	/** My wifi interface. */
	private final String mWiFi = "tiwlan0";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCell() {
		return this.mCell;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWiFi() {
		return this.mWiFi;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellRxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellTxBytes() throws IOException {
		String dev = this.getCell();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiRxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getRxBytes(dev);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiTxBytes() throws IOException {
		String dev = this.getWiFi();
		if (dev == null) {
			return 0L;
		}
		return SysClassNet.getTxBytes(dev);
	}
}

/**
 * Samsung Device with fixed cell device.
 */
final class SamsungDevice extends DiscoverableDevice {

	/** My cell interface. */
	private final String mCell = "pdp0";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCell() {
		return this.mCell;
	}
}

/**
 * Common Device for API>=8.
 */
final class FroyoDevice extends Device {
	/** Tag for output. */
	// private static final String TAG = "FroyoDevice";

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCell() {
		return "TrafficStats";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellRxBytes() throws IOException {
		final long l = TrafficStats.getMobileRxBytes();
		if (l < 0L) {
			return 0L;
		}
		return l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getCellTxBytes() throws IOException {
		final long l = TrafficStats.getMobileTxBytes();
		if (l < 0L) {
			return 0L;
		}
		return l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWiFi() {
		return "TrafficStats";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiRxBytes() throws IOException {
		final long l = TrafficStats.getMobileRxBytes();
		final long la = TrafficStats.getTotalRxBytes();
		if (la < 0L || la < l) {
			return 0L;
		}
		return la - l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getWiFiTxBytes() throws IOException {
		final long l = TrafficStats.getMobileTxBytes();
		final long la = TrafficStats.getTotalTxBytes();
		if (la < 0L || la < l) {
			return 0L;
		}
		return la - l;
	}
}
