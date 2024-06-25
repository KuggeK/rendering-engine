package kugge.rendering.graphics.opengl;

import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import kugge.rendering.graphics.Window;
import kugge.rendering.graphics.WindowSettings;

public class OpenGLWindow extends JFrame implements Window {

    private WindowSettings settings;
    private static final WindowSettings DEFAULT_SETTINGS = new WindowSettings(800, 600, "Window");
    private GLCanvas canvas;
    private Set<EventListener> listeners;

    public OpenGLWindow() {
        this(DEFAULT_SETTINGS);
    }

    public OpenGLWindow(WindowSettings settings) {
        super(settings.getTitle());
        this.settings = settings;
        listeners = new HashSet<>();
        updateSettings();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new GLCanvas();
        add(canvas);
        setVisible(true);
        canvas.requestFocus();
    }

    @Override
    public void setWindowSettings(WindowSettings settings) {
        this.settings = settings;
        updateSettings();
    }

    public GLCanvas getCanvas() {
        return canvas;
    }

    private void updateSettings() {
        this.setSize(settings.getWidth(), settings.getHeight());
        this.setTitle(settings.getTitle());
    }

    @Override
    public void registerEventListener(EventListener listener) {
        if (listener instanceof GLEventListener) {
            canvas.addGLEventListener((GLEventListener) listener);
        } else if (listener instanceof KeyListener) {
            canvas.addKeyListener((KeyListener) listener);
        } else {
            throw new IllegalArgumentException("Listener type not supported");
        }
        listeners.add(listener);
    }

    @Override
    public void destroy() {
        for (EventListener listener : listeners) {
            if (listener instanceof GLEventListener) {
                ((GLEventListener) listener).dispose(canvas);
                canvas.removeGLEventListener((GLEventListener) listener);
            } else if (listener instanceof KeyListener) {
                canvas.removeKeyListener((KeyListener) listener);
            }
        }
        canvas.destroy();
        dispose();
    }
}
