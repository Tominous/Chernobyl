package me.rtn.renderengine.shaders;/*
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

import me.rtn.renderengine.entities.Camera;
import me.rtn.renderengine.entities.Light;
import me.rtn.renderengine.utils.Maths;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/me/rtn/renderengine/shaders/terrainVertexShader";
    private static final String FRAG_FILE = "src/me/rtn/renderengine/shaders/terrainFragmentShader";

    private int location_transformationMatrix;
    private int location_projectMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_Skycolour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;


    public TerrainShader() {
        super(VERTEX_FILE, FRAG_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightColour = super.getUniformLocation("lightColour");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");
    }

    public void connectTextureUnits(){
        super.loadUiInt(location_backgroundTexture, 0);
        super.loadUiInt(location_rTexture, 1);
        super.loadUiInt(location_gTexture, 2);
        super.loadUiInt(location_bTexture, 3);
        super.loadUiInt(location_blendMap, 4);
    }

    public void loadSkyColour(float r, float g, float b){
        super.loadUiVector(location_Skycolour, new Vector3f(r,g,b));
    }

    public void loadShine(float damper, float reflectivity){
        super.loadUiFloat(location_shineDamper, damper);
        super.loadUiFloat(location_reflectivity, reflectivity);
    }

    public void loadLight(Light light){
        super.loadUiVector(location_lightColour, light.getColour());
        super.loadUiVector(location_lightPosition, light.getPosition());
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normals");
    }

    @Override
    public void loadLights(Light lights) {

    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadUiMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadUiMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectMatrix(Matrix4f projection){
        super.loadUiMatrix(location_projectMatrix, projection);
    }
}

