package me.rtn.renderengine.shaders;

import me.rtn.renderengine.entities.Light;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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
public abstract class ShaderProgram {

    private int pID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        pID = GL20.glCreateProgram();
        GL20.glAttachShader(pID, vertexShaderID);
        GL20.glAttachShader(pID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(pID);
        GL20.glValidateProgram(pID);
        getAllUniformLocations();

    }
    //assigning uniforms to shaders
    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String unfiromName){
        return GL20.glGetUniformLocation(pID, unfiromName);
    }

    protected void loadUiFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }

    protected void loadUiInt(int location, int value){
        GL20.glUniform1i(location, value);
    }

    protected void loadUiVector(int location, Vector3f vector){
        GL20.glUniform3f(location,vector.x, vector.y, vector.z);
    }

    protected void loadUiBoolean(int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    protected void loadUiMatrix(int location, Matrix4f matrix){;
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    //general starting/stopping shaders
    public void start(){
        GL20.glUseProgram(pID);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

    public void clean(){
        stop();
        GL20.glDetachShader(pID, vertexShaderID);
        GL20.glDetachShader(pID, fragmentShaderID);
        GL20.glDeleteProgram(vertexShaderID);
        GL20.glDeleteProgram(fragmentShaderID);
        GL20.glDeleteProgram(pID);
    }

    //binding attributes
    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(pID, attribute, variableName);
    }

    //need this to load and read the shader files
    private static int loadShader(String file, int type){
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                source.append(line).append("\n");
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, ByteBuffer.allocateDirect(type));
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.out.println("Could not compile shader, abandoning thread");
            System.exit(-1);
        }
        return shaderID;
    }

    public abstract void loadLights(Light lights);
}
