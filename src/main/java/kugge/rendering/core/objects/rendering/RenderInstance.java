package kugge.rendering.core.objects.rendering;

import org.joml.Matrix4f;

import kugge.rendering.core.objects.ComponentField;
import kugge.rendering.core.objects.GameComponent;
import kugge.rendering.core.objects.GameObject;
import kugge.rendering.core.objects.Transform;

/**
 * Represents a concrete instance of a mesh to be rendered.
 */
public class RenderInstance extends GameComponent {
    @ComponentField
    private int ID;

    @ComponentField
    private int meshID;

    @ComponentField
    private int materialID;

    @ComponentField
    private boolean texturingEnabled;

    @ComponentField
    private int textureID;

    @ComponentField
    private boolean normalMapEnabled;
    
    @ComponentField
    private int normalMapID;

    @ComponentField
    private boolean lit;

    @ComponentField
    private boolean castsShadows;

    public RenderInstance(int ID, GameObject gameObject) {
        super(gameObject);
        this.ID = ID;
        this.meshID = -1;
        this.materialID = -1;
        this.texturingEnabled = false;
        this.textureID = -1;
        this.normalMapEnabled = false;
        this.normalMapID = -1;
        this.lit = true;
        this.castsShadows = true;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Matrix4f getModelMatrix() {
        return transform.getModelMatrix();
    }

    public int getMeshID() {
        return meshID;
    }

    public void setMeshID(int meshID) {
        this.meshID = meshID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public boolean isTexturingEnabled() {
        return texturingEnabled;
    }

    public void setTexturingEnabled(boolean texturingEnabled) {
        this.texturingEnabled = texturingEnabled;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public Transform getTransform() {
        return transform;
    }

    public boolean isNormalMapEnabled() {
        return normalMapEnabled;
    }

    public void setNormalMapEnabled(boolean normalMapEnabled) {
        this.normalMapEnabled = normalMapEnabled;
    }

    public int getNormalMapID() {
        return normalMapID;
    }

    public void setNormalMapID(int normalMapID) {
        this.normalMapID = normalMapID;
    }

    public boolean isLit() {
        return lit;
    }

    public void setLit(boolean lit) {
        this.lit = lit;
    }

    public boolean castsShadows() {
        return castsShadows;
    }

    public void setCastsShadows(boolean castsShadows) {
        this.castsShadows = castsShadows;
    }
}