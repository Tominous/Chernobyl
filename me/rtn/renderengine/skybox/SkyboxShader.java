package me.rtn.renderengine.skybox;

import me.rtn.renderengine.DisplayManager;
import me.rtn.renderengine.entities.Camera;
import me.rtn.renderengine.entities.Light;
import me.rtn.renderengine.shaders.ShaderProgram;
import me.rtn.renderengine.utils.Maths;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

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
public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader.txt";

    private static final float ROT_SPEED = 1f;

    private int location_projectionMatrix;;
    private int location_viewMatrix;
    private int location_fogColour;
    private int location_cubeMap;
    private int location_cubeMap2;
    private int location_blendFactor;

    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadUiMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30= 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROT_SPEED  * DisplayManager.getFrameTimeSeconds();
        //matrix.setRotation(Math.toRadians(rotation), new Vector3f(0,1,0), matrix, matrix);
        //^^ java being retarded
        super.loadUiMatrix(location_viewMatrix, matrix);
        location_fogColour = super.getUniformLocation("fogColour");
    }

    public void connectTextureUnits(){
        super.loadUiInt(location_cubeMap, 0);
        super.loadUiInt(location_cubeMap2, 1);
    }

    public void loadBlendFactor(float blend){
        super.loadUiFloat(location_blendFactor, blend);
    }

    public void loadFogColour(float r, float g, float b){
        super.loadUiVector(location_fogColour, new Vector3f(r,g,b));
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_cubeMap = super.getUniformLocation("cubeMap");
        location_cubeMap2 = super.getUniformLocation("cubeMap2");
        location_blendFactor = super.getUniformLocation("blendMap");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    @Override
    public void loadLights(Light lights) {

    }
}
