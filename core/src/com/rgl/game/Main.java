package com.rgl.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rgl.game.graphics.TextureRepo;
import com.rgl.game.gui.GuiInspect;
import com.rgl.game.input.CameraInputListener;
import com.rgl.game.input.GuiInputListener;
import com.rgl.game.input.WorldInputListener;
import com.rgl.game.world.MapCFG;
import com.rgl.game.world.World;
import com.rgl.game.world.level.DungeonLevelGenerator;
import com.rgl.game.world.level.Level;
import com.rgl.game.world.level.Tile;


public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    SpriteBatch uibatch;
    Tile[][] currentLevel;
    OrthographicCamera camera;
    OrthographicCamera uicamera;
    CameraInputListener cameraInputListener;
    InputMultiplexer inputMultiplexer;
    WorldInputListener worldInputListener;
    GuiInputListener guiInputListener;
    ShapeRenderer shapeBatch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();
        shapeBatch.setAutoShapeType(true);
        shapeBatch.setColor(Color.RED);
        uibatch = new SpriteBatch();
        camera = new OrthographicCamera();
        uicamera = new OrthographicCamera();
        uicamera.setToOrtho(false, MapCFG.VIEWPORTWIDTH, MapCFG.VIEWPORTHEIGHT);
        cameraInputListener = new CameraInputListener(camera);
        worldInputListener = new WorldInputListener(camera, batch);
        guiInputListener = new GuiInputListener(uicamera);
        camera.setToOrtho(false, MapCFG.VIEWPORTWIDTH, MapCFG.VIEWPORTHEIGHT);
        World.INSTANCE.addLevel();
        currentLevel = World.INSTANCE.getLevel();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(guiInputListener));
        inputMultiplexer.addProcessor(new GestureDetector(cameraInputListener));
        inputMultiplexer.addProcessor(new GestureDetector(worldInputListener));
        Gdx.input.setInputProcessor(inputMultiplexer);
        camera.zoom = MapCFG.ZOOM;
        camera.position.set(755.90f, -9303.10f, 0.0f);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Level.INSTANCE.render(batch);
        batch.setProjectionMatrix(uicamera.combined);
        batch.end();
        shapeBatch.setProjectionMatrix(camera.combined);
        shapeBatch.begin();
        DungeonLevelGenerator.INSTANCE.renderLinks(shapeBatch);
        shapeBatch.end();
        batch.begin();
        GuiInspect.INSTANCE.render(batch, uicamera);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        TextureRepo.INSTANCE.dispose();
    }
}
