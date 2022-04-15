package plugin.listener.model;


import static org.lwjgl.openal.AL10.alListener3f;
import org.lwjgl.openal.AL10;

import model.listener.plugin.AbstractPlugin;
import model.utility.Vec3f;

public class DopplerPlugin extends AbstractPlugin {
    private static final float DEFAULT_DROPPLER_LV = 1.0f;
    private Vec3f velocity;
    private float dropplerLv;
    private float lastDropplerLv;

    public DopplerPlugin() {
        this.velocity = new Vec3f(0.0f, 0.0f, 0.0f);
        this.dropplerLv = 1.0f;
    }

    /**
     * 
     */
    @Override
    public void restoreSettings() {
        this.setDropplerLv(this.lastDropplerLv);
    }

    /**
     * 
     */
    @Override
    public void saveSettings() {
        this.lastDropplerLv = this.dropplerLv;
        this.setDropplerLv(DEFAULT_DROPPLER_LV);
        this.velocity = new Vec3f(0.0f, 0.0f, 0.0f);
    }

    /**
     * 
     * @param vector
     */
    public void setVelocity(final Vec3f vector) {
        this.velocity = vector;
        alListener3f(AL10.AL_VELOCITY, this.velocity.getX(), this.velocity.getY(), this.velocity.getZ());
    }

    /**
     * 
     * @return velocity vector
     */
    public Vec3f getVelocity() {
        return this.velocity;
    }

    /**
     * @param value 
     */
    public void setDropplerLv(final float value) {
        this.dropplerLv = value;
    }

    /**
     * @return droppler level
     */
    public float getDropplerLv() {
        return this.dropplerLv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassName() {
        return "DopplerPlugin";
    }

}
