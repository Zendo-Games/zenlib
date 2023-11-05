package zendo.games.zenlib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import zendo.games.zenlib.screens.ZenScreen;
import zendo.games.zenlib.ui.ZenTextButton;
import zendo.games.zenlib.ui.ZenWindow;

public class ZenlibMainTest extends ZenMain<ZenlibMainTest.Assets> {

    public static final ZenConfig config = new ZenConfig();

    public static ZenlibMainTest game;

    public ZenlibMainTest() {
        super(config);
        ZenlibMainTest.game = this;
    }

    @Override
    public Assets createAssets() {
        return new Assets();
    }

    @Override
    public ZenScreen createStartScreen() {
        return new TestScreen1();
    }

    // ------------------------------------------------------------------------
    // Test Assets (from src/test/resources)
    // ------------------------------------------------------------------------

    public static class Assets extends ZenAssets {
        Texture gdx;

        @Override
        public void loadManagerAssets() {
            mgr.load("libgdx.png", Texture.class);
        }

        @Override
        public void initCachedAssets() {
            gdx = mgr.get("libgdx.png", Texture.class);
        }
    }

    // ------------------------------------------------------------------------
    // Test Screen
    // ------------------------------------------------------------------------

    public static class TestScreen1 extends ZenScreen {
        public TestScreen1() {
            // override the default 'ScreenViewport'
            int screenWidth = config.window.width / 2;
            int screenHeight = config.window.height / 2;
            this.viewport = new StretchViewport(screenWidth, screenHeight, worldCamera);
            this.viewport.apply(true);

            Gdx.input.setInputProcessor(uiStage);
        }

        @Override
        protected void initializeUI() {
            super.initializeUI();

            var window = new ZenWindow(300f, 400f);
            var button = new ZenTextButton(100f, 100f, "Switch to screen 2");
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new TestScreen2());
                }
            });
            window.add(button);
            uiStage.addActor(window);
        }

        @Override
        public void render(SpriteBatch batch) {
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

            batch.setProjectionMatrix(worldCamera.combined);
            batch.begin();
            {
                var image = game.assets.gdx;
                var scale = 1 / 4f;
                var imageWidth = scale * image.getWidth();
                var imageHeight = scale * image.getHeight();
                batch.draw(
                        image,
                        (worldCamera.viewportWidth - imageWidth) / 2f,
                        (worldCamera.viewportHeight - imageHeight) / 2f,
                        imageWidth,
                        imageHeight);
            }
            batch.end();
            uiStage.draw();
        }
    }

    // ------------------------------------------------------------------------
    // Test Screen 2 (for transition testing)
    // ------------------------------------------------------------------------

    public static class TestScreen2 extends ZenScreen {
        public TestScreen2() {
            // override the default 'ScreenViewport'
            int screenWidth = config.window.width / 4;
            int screenHeight = config.window.height / 4;
            this.viewport = new StretchViewport(screenWidth, screenHeight, worldCamera);
            this.viewport.apply(true);

            Gdx.input.setInputProcessor(uiStage);
        }

        @Override
        protected void initializeUI() {
            super.initializeUI();

            var button = new ZenTextButton("Switch to screen 1");
            button.setPosition(windowCamera.viewportWidth - button.getWidth() - 100, 100);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new TestScreen1());
                }
            });
            uiStage.addActor(button);
        }

        @Override
        public void render(SpriteBatch batch) {
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

            batch.setProjectionMatrix(worldCamera.combined);
            batch.begin();
            {
                var image = game.assets.gdx;
                var scale = 2 / 4f;
                var imageWidth = scale * image.getWidth();
                var imageHeight = scale * image.getHeight();
                batch.draw(
                        image,
                        (worldCamera.viewportWidth - imageWidth) / 2f,
                        (worldCamera.viewportHeight - imageHeight) / 2f,
                        imageWidth,
                        imageHeight);
            }
            batch.end();
            uiStage.draw();
        }
    }
}
