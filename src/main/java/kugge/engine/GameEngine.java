package kugge.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kugge.engine.rendering.RenderingEngine;
import kugge.engine.rendering.Window;
import kugge.engine.rendering.objects.RenderInstance;
import kugge.engine.core.config.EngineProjectConfiguration;
import kugge.engine.core.json.GameSceneAdapters;
import kugge.engine.ecs.GameScene;
import kugge.engine.physics.PhysicsBody;
import kugge.engine.physics.PhysicsCollider;
import kugge.engine.physics.PhysicsEngine;
import kugge.engine.rendering.opengl.OpenGLWindow;
import kugge.engine.scripting.Script;
import kugge.engine.scripting.ScriptingEngine;

public class GameEngine {

    private Window window;
    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    private ScriptingEngine scriptingEngine;
    private GameScene currentScene;
    private EngineProjectConfiguration projectConfig;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("No project path provided.");
        }

        GameEngine engine = new GameEngine();
        String projectPath = args[0];
        EngineProjectConfiguration config = EngineProjectConfiguration.loadProjectConfiguration(projectPath);
        engine.start(config);
    }

    public void init(EngineProjectConfiguration config) throws IOException {
        window = new OpenGLWindow(config);
        
        renderingEngine = new RenderingEngine();
        renderingEngine.linkToWindow(window);

        physicsEngine = new PhysicsEngine();

        // Link the window's key input to the scripting engine to allow user input to be used in scripts
        scriptingEngine = new ScriptingEngine(renderingEngine.getWindow().getKeyInput());

        // Add the subsystems to the scene
        projectConfig = config;

        currentScene = GameScene.loadScene(projectConfig.getInitialSceneID());

        // Populate subsystems with the scene components
        currentScene.getGameObjects().forEach(gameObject -> {
            for (var component : gameObject.getComponents()) {
                if (component instanceof Script) {
                    scriptingEngine.addScript((Script) component);
                    break;
                }
                if (component instanceof PhysicsBody) {
                    physicsEngine.addBody((PhysicsBody) component);
                    break;
                }
                if (component instanceof PhysicsCollider) {
                    physicsEngine.addCollider((PhysicsCollider) component);
                    break;
                }
                if (component instanceof RenderInstance) {
                    renderingEngine.addInstance((RenderInstance) component);
                    break;
                }
            }
        });
    }

    public void start(EngineProjectConfiguration config) throws Exception {
        // Populate the global paths from the configuration
        EngineProjectConfiguration.populateGlobalPaths(config);

        // Load the initial scene
        GsonBuilder builder = new GsonBuilder();
        GameSceneAdapters.registerAdapters(builder);
        Gson gson = builder.create();
        String jsonString = Files.readString(Paths.get("scene" + config.getInitialSceneID() + ".json"));
        currentScene = gson.fromJson(jsonString, GameScene.class);

        // Timing variables
        long currentTime = 0;
        long previousTime = System.currentTimeMillis();
        long deltaTime = 0;
        long timeTaken = 0;
        int targetFPS = config.getTargetFPS();


        // Game loop
        while (true) {
            currentTime = System.currentTimeMillis();
            deltaTime = currentTime - previousTime;
            previousTime = currentTime;

            renderingEngine.render();
            physicsEngine.updateSimulation(1 / 60.0);
            scriptingEngine.update(deltaTime / 1000.0f);

            // If the time taken by this frame is less than the target time, sleep for the difference
            timeTaken = System.currentTimeMillis() - currentTime;
            if (timeTaken < 1000 / targetFPS) {
                try {
                    if (targetFPS - timeTaken != 0) {
                        Thread.sleep(1000 / targetFPS - timeTaken);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Frame time did not match target FPS for this frame (" + targetFPS + "). Total time taken was " + timeTaken);
            }
        }
    }
}
