package net.omniblock.network.library.helpers.fixer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.omniblock.network.OmniNetwork;

public class TeleportFix {

    protected static final int TELEPORT_FIX_DELAY = 15;

    public static void makeFix(Player player) {

        final int visibleDistance = OmniNetwork.getInstance().getServer().getViewDistance() * 16;

        OmniNetwork.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(OmniNetwork.getInstance(),
            new Runnable() {
                @Override
                public void run() {

                    final List < Player > nearby = getPlayersWithin(player, visibleDistance);

                    updateEntities(player, nearby, false);

                    OmniNetwork.getInstance().getServer().getScheduler()
                        .scheduleSyncDelayedTask(OmniNetwork.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                updateEntities(player, nearby, true);
                            }
                        }, 1);
                }
            }, TELEPORT_FIX_DELAY);
    }

    private static void updateEntities(Player tpedPlayer, List < Player > players, boolean visible) {

        for (Player player: players) {

            if (visible) {

                tpedPlayer.showPlayer(player);
                player.showPlayer(tpedPlayer);

            } else {

                tpedPlayer.hidePlayer(player);
                player.hidePlayer(tpedPlayer);

            }

        }

    }

    private static List < Player > getPlayersWithin(Player player, int distance) {

        List < Player > res = new ArrayList < Player > ();

        int d2 = distance * distance;

        for (Player p: OmniNetwork.getInstance().getServer().getOnlinePlayers()) {
            if (p != player && p.getWorld() == player.getWorld() &&
                p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }

        return res;

    }

}