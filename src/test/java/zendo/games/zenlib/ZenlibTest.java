package zendo.games.zenlib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;
import zendo.games.zenlib.assets.ZenPatch;
import zendo.games.zenlib.screens.ZenScreen;

public class ZenlibTest extends ZenMain {

    public static final ZenConfig config = new ZenConfig();

    public ZenlibTest() {
        super(config);
    }

    @Override
    public ZenAssets createAssets() {
        return new Assets();
    }

    @Override
    public ZenScreen<Assets> createStartScreen() {
        return new TestScreen();
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

    public static class TestScreen extends ZenScreen<Assets> {
        public TestScreen() {
            super(Assets.class);
            Gdx.input.setInputProcessor(uiStage);
            var button = new VisTextButton("Screen 1");
            button.setPosition(100, 100);
            button.getStyle().up = ZenPatch.glass_active.ninePatchDrawable;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    ZenlibTest.instance.setScreen(new TestScreen2());
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
                var image = assets.gdx;
                var scale = 1 / 4f;
                var imageWidth = scale * image.getWidth();
                var imageHeight = scale * image.getHeight();
                batch.draw(image,
                        (worldCamera.viewportWidth - imageWidth) / 2f,
                        (worldCamera.viewportHeight - imageHeight) / 2f,
                        imageWidth, imageHeight);
            }
            batch.end();
            uiStage.draw();
        }
    }

    // ------------------------------------------------------------------------
    // Test Screen
    // ------------------------------------------------------------------------

    public static class TestScreen2 extends ZenScreen<Assets> {
        public TestScreen2() {
            super(Assets.class);
            var button = new VisTextButton("Screen 2");
            Gdx.input.setInputProcessor(uiStage);
            button.setPosition(100, 100);
            button.getStyle().up = ZenPatch.glass_red.ninePatchDrawable;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    ZenlibTest.instance.setScreen(new TestScreen());
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
                var image = assets.gdx;
                var scale = 1 / 4f;
                var imageWidth = scale * image.getWidth();
                var imageHeight = scale * image.getHeight();
                batch.draw(image,
                        (worldCamera.viewportWidth - imageWidth) / 3f,
                        (worldCamera.viewportHeight - imageHeight) / 3f,
                        imageWidth, imageHeight);
            }
            batch.end();
            uiStage.draw();
        }
    }

}

