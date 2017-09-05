package me.rtn.ch.events;

import com.google.common.collect.Maps;
import me.rtn.ch.Main;
import me.rtn.ch.util.TitleUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
 * mc 
 * Created by George at 11:15 PM on 03-Sep-17  
 * Copyright (C) 2017 RapidTheNerd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PlayerJoin implements Listener {

    //title animation
    private HashMap<UUID, Integer> aProgress;
    private List<String> title;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        //title animation
        aProgress = Maps.newHashMap();
        title = Arrays.asList(
                ChatColor.GREEN + "< >",
                ChatColor.GREEN + "<   >",
                ChatColor.GREEN + "<       >",
                ChatColor.GREEN + "<        >",
                ChatColor.GREEN + "<           >"

        );

        aProgress.put(player.getUniqueId(), 0);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!aProgress.containsKey(player.getUniqueId())){
                    cancel();
                    return;
                }
                int progress = aProgress.get(player.getUniqueId());
                if(title.size() > progress){
                    new TitleUtil(title.get(progress), "", 0, 60, 0).send(player);
                    aProgress.put(player.getUniqueId(), progress + 1);
                } else {
                    aProgress.remove(player.getUniqueId());
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 60L);
    }
}
