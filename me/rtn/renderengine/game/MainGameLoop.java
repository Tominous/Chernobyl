package me.rtn.renderengine.game;

import me.rtn.renderengine.DisplayManager;
import me.rtn.renderengine.Loader;
import me.rtn.renderengine.MasterRenderer;
import me.rtn.renderengine.OBJLoader;
import me.rtn.renderengine.entities.Camera;
import me.rtn.renderengine.entities.Entity;
import me.rtn.renderengine.entities.Light;
import me.rtn.renderengine.entities.Player;
import me.rtn.renderengine.guis.GuiRenderer;
import me.rtn.renderengine.guis.GuiTexture;
import me.rtn.renderengine.models.RawModel;
import me.rtn.renderengine.models.TexturedModel;
import me.rtn.renderengine.objConverter.ModelData;
import me.rtn.renderengine.objConverter.OBJFileLoader;
import me.rtn.renderengine.terrain.Terrain;
import me.rtn.renderengine.textures.ModelTexture;
import me.rtn.renderengine.textures.TerrainTexture;
import me.rtn.renderengine.textures.TerrainTexturePack;
import me.rtn.renderengine.utils.Debugger;
import org.lwjgl.opengl.Display;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
public class MainGameLoop {

    public static void main(String[] args) throws FileNotFoundException {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //Texture pack
        TerrainTexture backGroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        Debugger.useExceptionDebug("Exception caught within loading terrain texture");
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backGroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        //End of texture pack

        ModelData data = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());

        TexturedModel staticModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grass", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setTransparent(true);
        grass.getTexture().setUseFakeLighting(true);

        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setTransparent(true);
        fern.getTexture().setUseFakeLighting(true);

        ArrayList<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {

            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6F));
        }


        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");


        MasterRenderer renderer = new MasterRenderer(loader);

        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
        lights.add(new Light(new Vector3f(185, 10, -239), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));


        RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
        TexturedModel bunnyTexture = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));
        
        RawModel playerModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel playerTexture = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(playerTexture, new Vector3f(100, 0, 50), 0, 0, 0, 1);
        Camera camera = new Camera(player);

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("textureName"), new Vector2f(0.5f, 0.5f),
                new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        while (!Display.isCloseRequested()) {
            player.move(terrain);
            camera.move();

            renderer.processEntities(player);
            renderer.processTerrains(terrain);
            for (Entity entity : entities) {
                renderer.processEntities(entity);
            }
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        guiRenderer.clean();
        renderer.clean();
        loader.clean();
        DisplayManager.disposeDisply();

    }
}