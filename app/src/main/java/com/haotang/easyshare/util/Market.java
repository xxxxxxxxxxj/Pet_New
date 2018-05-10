package com.haotang.easyshare.util;

import android.content.Context;

public class Market {
	public static String getMarketId(Context context) {
		
		return ChannelUtil.getChannel(context);
	}
}
