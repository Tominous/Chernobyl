package me.rtn.renderengine.objConverter;/*
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

import javax.vecmath.Vector3f;

public class Vertex {

    private final int NO_INDEX = -1;

    private Vector3f position;
    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private Vertex duplicateVertex = null;
    private int index;
    private float length;

    public Vertex(int index, Vector3f position){
        this.index = index;
        this.position = position;
        this.length = position.length();
    }

    public int getIndex(){
        return index;
    }
    public float getLength(){
        return length;
    }

    public boolean isSet(){
        return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
    }
    public boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther){
        return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
    }
    public void setTextureIndex(int textureIndex){
        this.textureIndex = textureIndex;
    }
    public void setNormalIndex(int normalIndex){
        this.normalIndex = normalIndex;
    }
    public Vector3f getPosition(){
        return position;
    }
    public int getNormalIndex(){
        return normalIndex;
    }
    public Vertex getDuplicateVertex(){
        return duplicateVertex;
    }

    public int getTextureIndex(){
        return textureIndex;
    }

    public void setDuplicateVertex(Vertex duplicateVertex){
        this.duplicateVertex = duplicateVertex;
    }
}
