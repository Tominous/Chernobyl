package me.rtn.renderengine.stats;

import me.rtn.renderengine.DisplayManager;

import java.util.Random;

/*
 * 3DGa,e
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
public class stats {

    private static final String defaultName = "User" + getID();

    private static int ID = generateID();
    private static String name = setName(defaultName);
    private static int timePlayed = setTimePlayed((int) (DisplayManager.getFrameTimeSeconds() * 1000));

    public static int generateID(){
        Random random = new Random();
        int MAX_ID_SIZE = 10000;
        if(random.nextInt() > MAX_ID_SIZE){
            setID(random.nextInt());
        } else {
            random.ints().skip(MAX_ID_SIZE);
        }
        return MAX_ID_SIZE;
    }

    public static void setID(int ID) {
        stats.ID = ID;
    }

    public static String setName(String name) {
        stats.name = name;
        return name;
    }

    public static int getID() {
        return ID;
    }

    public static String getName() {
        return name;
    }

    public static int setTimePlayed(int timePlayed) {
        stats.timePlayed = timePlayed;
        return timePlayed;
    }

    public static int getTimePlayed() {
        return timePlayed;
    }
}

/***
 * ###GENERAL LIST OF UPCOMING SUPPORT FOR STATS ###
 * 1) Deaths
 * 2) Kills
 * 3) Items crafted
 * 4) First login time
 * 5) Last login time
 */

