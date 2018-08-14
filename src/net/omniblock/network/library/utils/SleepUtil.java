package net.omniblock.network.library.utils;

import java.util.concurrent.TimeUnit;

@Deprecated
public final class SleepUtil {

	public static void sleep(TimeUnit unit, int time){

		try{

			unit.sleep(time);

		}catch (InterruptedException e){

		}
	}
}
