package kugge.rendering.core.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends GameComponent {
    @ComponentField
    private float fov;

    @ComponentField
    private float near;

    @ComponentField
    private float far;

    @ComponentField
    private boolean orthographic;

    public Camera() {
        super();
    }

    public Camera(GameObject gameObject) {
        this(gameObject, 70f, 0.01f, 1000, false);
    }

    public Camera(GameObject gameObject, float fov, float near, float far, boolean orthographic) {
        super(gameObject);
        this.fov = fov;
        this.near = near;
        this.far = far;
        this.orthographic = orthographic;
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(
            transform.getPosition(),
            transform.getPosition().add(transform.getForward(), new Vector3f()),
            transform.getUp()
        );
    }

    public Matrix4f getProjectionMatrix(float aspectRatio) {
        if (orthographic) {
            return new Matrix4f().ortho(-1, 1, -1, 1, near, far);
        } else {
            return new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, near, far);
        }
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public boolean isOrthographic() {
        return orthographic;
    }

    public void setOrthographic(boolean orthographic) {
        this.orthographic = orthographic;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
