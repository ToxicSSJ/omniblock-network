package net.omniblock.network.library.utils;

@Deprecated
public class TimingsUtil {

	private static long timeStart = 0L;
	private static long lastTime = 0L;

	public static void prepare() {
		timeStart = System.currentTimeMillis();
		lastTime = timeStart;
	}

	public static long next(String message) {
		long diff = System.currentTimeMillis() - timeStart;
		long diffLast = System.currentTimeMillis() - lastTime;

		System.out.println("TIMING <" + message + "> :");
		System.out.println("  TOTAL : " + diff + " (" + (diff / 1000L) + "s)");
		System.out.println("  LAST  : " + diffLast + " (" + (diffLast / 1000L) + "s)");

		lastTime = System.currentTimeMillis();

		return diff;
	}

}
