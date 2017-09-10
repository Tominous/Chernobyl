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

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJFileLoader {

    private static final String RES_LOC = "/res/";

    public static ModelData loadOBJ(String objFileName) throws FileNotFoundException {
        FileReader reader = new FileReader(objFileName);
        File objFile = new File(RES_LOC + objFileName + ".obj");
        try{
            reader = new FileReader(objFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bReader = new BufferedReader(reader);
        String line;
        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        try {
            while(true){
                line = bReader.readLine();
                if(line.startsWith("v ")){
                    String[] currentLine = line.split(" ");
                    Vector3f vertex = new Vector3f((float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]),
                            (float) Float.valueOf(currentLine[3]));
                    Vertex newVertex = new Vertex(vertices.size(), vertex);
                    vertices.add(newVertex);
                } else if(line.startsWith("vt ")){
                    String[] currentLine = line.split(" ");
                    Vector3f normal = new Vector3f((float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]),
                            (float) Float.valueOf(currentLine[3]));
                    normals.add(normal);
                } else if(line.startsWith("f ")){
                    break;
                }
            }
            while(line != null && line.startsWith("f ")){
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                processVertex(vertex1, vertices, indices);
                processVertex(vertex2, vertices, indices);
                processVertex(vertex3, vertices, indices);
                line = bReader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeUnusedVertices(vertices);
        float[] verticesArray = new float[vertices.size() * 3];
        float[] textureArray = new float[vertices.size() * 2];
        float[] normalArray = new float[vertices.size() * 3];
        float furthest = convertDataToArrays(vertices, textures, normals, verticesArray, textureArray, normalArray);
        int[] indicesArray = convertIndicesToArray(indices);
        ModelData data = new ModelData(verticesArray, textureArray, normalArray, indicesArray, furthest);
        return data;
    }

    private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices){
        int index = Integer.parseInt(vertex[0]) - 1;
        Vertex currnetVertex = vertices.get(index);
        int textureIndex = Integer.parseInt(vertex[1]) - 1;
        int normalIndex = Integer.parseInt(vertex[2]) - 1;
        if(!currnetVertex.isSet()){
            currnetVertex.setTextureIndex(textureIndex);
            currnetVertex.setNormalIndex(normalIndex);
            indices.add(index);
        } else {
            dealWithProcessedVertex(currnetVertex, textureIndex, normalIndex, indices, vertices);
        }
    }
    private static int[] convertIndicesToArray(List<Integer> indices){
        int[] indicesArray = new int[indices.size()];
        for(int i = 0; i < indicesArray.length; i++){
            indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }

    private static float convertDataToArrays(List<Vertex> vertices, List<Vector2f> textures, List<Vector3f> normals, float[] verticesArray,
                                      float[] textureArray, float[] normalsArray){
        float furthestPoint = 0;
        for(int i = 0; i < vertices.size(); i++) {
            Vertex currentVertex = vertices.get(i);
            if (currentVertex.getLength() > furthestPoint) {
                furthestPoint = currentVertex.getLength();
            }
            Vector3f position = currentVertex.getPosition();
            Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
            Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
            verticesArray[i * 3] = position.x;
            verticesArray[i * 3 + 1] = position.y;
            verticesArray[i * 3 + 2] = position.z;
            textureArray[i * 2] = textureCoord.x;
            textureArray[i * 2 + 1] = 1 - textureCoord.y;
            normalsArray[i * 3] = normalVector.x;
            normalsArray[i * 3 + 1] = normalVector.y;
            normalsArray[i * 3 + 2] = normalVector.z;
        }
        return furthestPoint;
    }

    private static void dealWithProcessedVertex(Vertex previousVertex, int newTextureIndex, int newNormalIndex, List<Integer> indices, List<Vertex> vertices){
        if(previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)){
            indices.add(previousVertex.getIndex());
        } else {
            Vertex anotherDamnVertex = previousVertex.getDuplicateVertex();
            if(anotherDamnVertex != null){
                dealWithProcessedVertex(anotherDamnVertex, newTextureIndex, newNormalIndex, indices, vertices);
            } else {
                Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
                duplicateVertex.setTextureIndex(newTextureIndex);
                duplicateVertex.setNormalIndex(newNormalIndex);
                duplicateVertex.setDuplicateVertex(duplicateVertex);
                vertices.add(duplicateVertex);
                indices.add(duplicateVertex.getIndex());
            }
        }
    }
    private static void removeUnusedVertices(List<Vertex> vertices){
        for(Vertex vertex : vertices){
            if(!vertex.isSet()){
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }
}
