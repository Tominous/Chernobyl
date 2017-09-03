package me.rtn.ch.util;

import org.bukkit.Bukkit;

/*
 * mc 
 * Created by George at 11:01 PM on 03-Sep-17  
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
enum SI {

    MINECRAFT("net.minecraft.server." + getServerVersion()),
    CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion());

    private final String path;

    SI(String path){
        this.path = path;
    }

    public Class<?> getClass(String className) throws ClassNotFoundException {
        return Class.forName(toString() + "." + className);
    }

    public static String getServerVersion(){
        return Bukkit.getServer().getClass().getName().substring(23);
    }
}
