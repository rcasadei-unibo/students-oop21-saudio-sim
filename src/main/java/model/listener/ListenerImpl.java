package model.listener;

import model.audiomanager.Context;
import model.utility.Vec3f;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.alListener3f;
import static org.lwjgl.openal.AL10.alListenerfv;



public class ListenerImpl implements Listener {

    private final Context context;
    private Vec3f position;
    private Vec3f atOrientation;
    private Vec3f upOrientation;



    ListenerImpl(final Context context) {
        this.context = context;
        this.setPosition(new Vec3f(0.0f, 0.0f, 0.0f));
        this.setOrientation(new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(0.0f, 0.0f, 1.0f));
    }

    ListenerImpl(final Context context, final Vec3f position) {
        this.context = context;
        this.setPosition(position);
        this.setOrientation(new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(0.0f, 0.0f, 1.0f));
    }

    ListenerImpl(final Context context, final Vec3f position, final Vec3f at, final Vec3f up) {
        this.context = context;
        this.setPosition(position);
        this.setOrientation(at, up);
    }


    /**
    * 
    *@inheritDoc
    */
    @Override
    public final void setPosition(final Vec3f pos) {
        this.position = pos;
        alListener3f(AL10.AL_POSITION, this.position.getX(), this.position.getY(), this.position.getZ());
    }

    /**
     * 
     *@inheritDoc
     */
    @Override
    public Vec3f getPosition() {
        return this.position;
    }

    /**
     * 
     *@inheritDoc
     */
    @Override
    public final void setOrientation(final Vec3f at, final Vec3f up) {
        this.atOrientation = at;
        this.upOrientation = up;

        final float[] atUpVec = {this.atOrientation.getX(), this.atOrientation.getY(), this.atOrientation.getZ(), this.upOrientation.getX(), this.upOrientation.getY(), this.upOrientation.getZ()};
        alListenerfv(AL10.AL_ORIENTATION, atUpVec);

    }

    /**
     * 
     *@inheritDoc
     */
    @Override
    public Vec3f getAtOrientation() {
        return this.atOrientation;
    }

    /**
     * 
     *@inheritDoc
     */
    @Override
    public Vec3f getUpOrientation() {
        return this.upOrientation;
    }


    /**
     * 
     *@inheritDoc
     */
    @Override
    public Context getCurrentContext() {
        return this.context;
    }


}

