package kugge.rendering.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import kugge.rendering.graphics.Window;

public class KeyInput {

    private Map<Integer, Boolean> keysPressed;
    private Map<Integer, Boolean> keysHeld;
    private DefaultKeyRegisterer keyRegisterer;
    
    public KeyInput() {
        keysPressed = new HashMap<>();
        keysHeld = new HashMap<>();
        keyRegisterer = new DefaultKeyRegisterer();
    }

    public void bindToWindow(Window window) {
        window.registerEventListener(keyRegisterer);
    }

    public void keyPressed(KeyEvent e) {
        keysPressed.put(e.getKeyCode(), true);
        keysHeld.put(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        keysHeld.put(e.getKeyCode(), false);
    }

    public boolean isKeyPressed(Integer keyCode) {
        return keysPressed.getOrDefault(keyCode, false);
    }

    public boolean isKeyHeld(Integer keyCode) {
        return keysHeld.getOrDefault(keyCode, false);
    }

    public void clear() {
        keysPressed.clear();
    }

    public void clearHeld() {
        keysHeld.clear();
    }

    private class DefaultKeyRegisterer implements KeyListener {

        public DefaultKeyRegisterer() {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!keysHeld.getOrDefault(e.getKeyCode(), false)) {
                KeyInput.this.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            KeyInput.this.keyReleased(e);
        }
    }
}
