package me.rtn.ch.util;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;


/*
 * mc 
 * Created by George at 11:04 PM on 03-Sep-17  
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
public class TitleUtil {

    private String title, subtitle;
    private int fadeIn, stay, fadeOut;

    public TitleUtil(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(Player player){
        Preconditions.checkNotNull(player);

        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player),
                    connection = handle.getClass().getField("playerConnection").get(handle);
            Class<?> playPacket = SI.MINECRAFT.getClass("PacketPlayOutTitle"),
                    genericPacket = SI.MINECRAFT.getClass("Packet"),
                    chatComponent = SI.MINECRAFT.getClass("IChatBaseComponent"),
                    serializer = SI.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer"),
                    action = SI.MINECRAFT.getClass("PacketPlayOutTitle$EnumTitleAction");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
