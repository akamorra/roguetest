package com.rgl.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rgl.game.graphics.TextureRepo;
import com.rgl.game.input.InputListener;
import com.rgl.game.world.level.Level;
import com.rgl.game.world.World;
import com.rgl.game.world.level.Tile;


public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Tile[][] currentLevel;
	OrthographicCamera camera;
	Integer viewportWidth;
	Integer viewportHeight;
	InputListener inputListener;


	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		inputListener = new InputListener(camera);
		viewportWidth=1920;
		viewportHeight=1080;
		camera.setToOrtho(false,viewportWidth,viewportHeight);
		World.INSTANCE.addLevel();
		currentLevel = World.INSTANCE.getLevel();
		Gdx.input.setInputProcessor(new GestureDetector(inputListener));

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		Level.INSTANCE.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		TextureRepo.INSTANCE.dispose();
	}
}
