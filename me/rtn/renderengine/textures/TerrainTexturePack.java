package me.rtn.renderengine.textures;/*
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

public class TerrainTexturePack {

    private TerrainTexture backgroundTexture;
    private TerrainTexture rTexture;
    private TerrainTexture gTexture;
    private TerrainTexture bTexture;

    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
        this.backgroundTexture = backgroundTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    public TerrainTexture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(TerrainTexture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public TerrainTexture getrTexture() {
        return rTexture;
    }

    public void setrTexture(TerrainTexture rTexture) {
        this.rTexture = rTexture;
    }

    public TerrainTexture getgTexture() {
        return gTexture;
    }

    public void setgTexture(TerrainTexture gTexture) {
        this.gTexture = gTexture;
    }

    public TerrainTexture getbTexture() {
        return bTexture;
    }

    public void setbTexture(TerrainTexture bTexture) {
        this.bTexture = bTexture;
    }
}
