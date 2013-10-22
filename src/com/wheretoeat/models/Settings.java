package com.wheretoeat.models;

public class Settings {

	private final static String PRICE_MIN_KEY = "price_min";
	private final static String PRICE_MAX_KEY = "price_max";

	private final static String VISITED_KEY = "show_visited";

	private final static String OPEN_NOW_KEY = "open_now_key";

	private boolean showVisited;
	private boolean openNow;
	private int price;

	public Settings(boolean showVisited, boolean openNow, int price) {
		this.showVisited = showVisited;
		this.openNow = openNow;
		this.price = price;
	}

	public static String getPriceMinKey() {
		return PRICE_MIN_KEY;
	}

	public static String getPriceMaxKey() {
		return PRICE_MAX_KEY;
	}

	public static String getVisitedKey() {
		return VISITED_KEY;
	}

	public static String getOpenNowKey() {
		return OPEN_NOW_KEY;
	}

	public boolean isShowVisited() {
		return showVisited;
	}

	public boolean isOpenNow() {
		return openNow;
	}

	public int getPrice() {
		return price;
	}
}
