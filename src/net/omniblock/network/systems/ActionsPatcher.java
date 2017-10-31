package net.omniblock.network.systems;

import java.util.Map;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.omniblock.network.OmniNetwork;
import net.omniblock.network.library.helpers.actions.MultipleEventListener;
import net.omniblock.network.library.helpers.actions.SimpleEventListener;

public class ActionsPatcher implements Listener {

	private static Map<Class<?>, Set<SimpleEventListener<?>>> simpleListeners = Maps.newHashMap();
	private static Set<MultipleEventListener> multipleListeners = Sets.newHashSet();

	public static void setup() {
		OmniNetwork.getInstance().getServer().getPluginManager().registerEvents(new ActionsPatcher(),
				OmniNetwork.getInstance());
	}

	@EventHandler
	private void onPlayerAction(PlayerInteractEvent e) {
		handleEvent(e);
	}

	@EventHandler
	private void onPlayerAction(AsyncPlayerChatEvent e) {
		handleEvent(e);
	}

	@EventHandler
	private void onPlayerAction(PlayerInteractEntityEvent e) {
		handleEvent(e);
	}

	private void handleEvent(Event e) {
		Class<?> clazz = e.getClass();
		if (simpleListeners.containsKey(clazz)) {
			Set<SimpleEventListener<?>> currentListeners = simpleListeners.get(clazz);
			if (currentListeners != null) {
				Sets.newHashSet(currentListeners).forEach(listener -> {
					listener.handle(e);
				});
			}
		}

		Sets.newHashSet(multipleListeners).forEach(listener -> {
			listener.handle(e);
		});
	}

	public static void addSimpleEventListener(Class<?> clazz, SimpleEventListener<?> listener) {
		Set<SimpleEventListener<?>> currentListeners = simpleListeners.get(clazz);
		if (currentListeners == null) {
			currentListeners = Sets.newHashSet();
		}
		currentListeners.add(listener);
		simpleListeners.put(clazz, currentListeners);
	}

	public static void removeSimpleEventListener(Class<?> clazz, SimpleEventListener<?> listener) {
		Set<SimpleEventListener<?>> currentListeners = simpleListeners.get(clazz);
		if (currentListeners == null) {
			currentListeners = Sets.newHashSet();
		}
		currentListeners.remove(listener);
		simpleListeners.put(clazz, currentListeners);
	}

	public static void addMultipleEventListener(MultipleEventListener multipleEventListener) {
		multipleListeners.add(multipleEventListener);
	}

	public static void removeMultipleEventListener(MultipleEventListener multipleEventListener) {
		multipleListeners.remove(multipleEventListener);
	}

}
