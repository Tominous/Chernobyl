package me.rtn.ch.util;

import com.google.common.base.Preconditions;
import me.rtn.ch.Main;
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

            Object timesPacket = playPacket.getConstructor(int.class, int.class, int.class).newInstance(fadeIn, stay, fadeOut);
            connection.getClass().getMethod("sendPacket").invoke(connection, timesPacket);

            if(title != null && !title.isEmpty()){
                Object titleCompp = serializer.getMethod("a", String.class).invoke(null, title),
                        titlePacket = playPacket.getConstructor(action, chatComponent).newInstance(action.getField("TITLE").get(null), titleCompp);
                connection.getClass().getMethod("sendPacket", genericPacket).invoke(connection, titlePacket);
            }

            if(subtitle != null && !subtitle.isEmpty()){
                Object subComp = serializer.getMethod("a", String.class).invoke(null, subtitle),
                        subPacket = playPacket.getConstructor(action, chatComponent).newInstance(action.getField("SUBTITLE").get(null), subComp);
                connection.getClass().getMethod("sendPacket", genericPacket).invoke(connection, subPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendToAll(){
        for(Player player : Main.getInstance().getServer().getOnlinePlayers()){
            send(player);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public int getStay() {
        return stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }
}
