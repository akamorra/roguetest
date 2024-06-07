package com.rgl.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rgl.game.screens.EndGame;
import com.rgl.game.screens.MainMenu;
import com.rgl.game.world.game_objects.drawable.player.Player;


public class Main extends Game {

    public SpriteBatch batch;
    public SpriteBatch uibatch;
    public Player player;
    public OrthographicCamera camera;
    public OrthographicCamera uicamera;
    public EndGame endgame;
    public MainMenu mainmenu;

    @Override
    public void create() {
        batch = new SpriteBatch();
        uibatch = new SpriteBatch();
        camera = new OrthographicCamera();
        uicamera = new OrthographicCamera();
        endgame=new EndGame(this);
        mainmenu=new MainMenu(this);
        this.setScreen(mainmenu);
    }

    @Override
    public void render() {
        super.render();
    }
    public SpriteBatch getBatch(){
        return batch;
    }
    @Override
    public void dispose() {
        getScreen().dispose();

    }
}
