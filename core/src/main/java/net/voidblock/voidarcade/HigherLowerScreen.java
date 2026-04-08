package net.voidblock.voidarcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HigherLowerScreen implements Screen {
    private final VoidArcade game;
    private Texture exitbutton;
    private Texture blackbg;
    private FitViewport viewport;

    private float buttonSize = 24f;
    private float x, y;

    public HigherLowerScreen(final VoidArcade game) {
        this.game = game;
        this.viewport = new FitViewport(480, 270);


        blackbg = new Texture("standard_black_bg.png");
        exitbutton = new Texture("return_button.png");

        blackbg.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        exitbutton.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = viewport.unproject(new Vector3(screenX, screenY, 0));


                if (worldCoords.x >= x && worldCoords.x <= x + buttonSize &&
                    worldCoords.y >= y && worldCoords.y <= y + buttonSize) {

                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        x = viewport.getWorldWidth() - buttonSize - 5; // 5 pixels marge van de rand
        y = viewport.getWorldHeight() - buttonSize - 5;

        ScreenUtils.clear(0f, 0f, 0f, 1f);
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        game.batch.begin();
        game.batch.draw(blackbg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.draw(exitbutton, x, y, buttonSize, buttonSize);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        blackbg.dispose();
        exitbutton.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
}
