package model.source;

import model.utility.Vec3f;

public interface Source {


    /**
     * Returns the id of the Source.
     * 
     * @return id
     */
    Integer getId();

    /**
     * Starts playback.
     */
    void play();

    /**
     * Pauses playback.
     */
    void pause();

    /**
     * Stop playback.
     */
    void stop();

    /**
     * Returns true if the source is playing, false otherwise.
     * 
     * @return boolean
     */
    boolean isPlaying();

    /**
     * Generate the Source with the buffer.
     * 
     * @param buffer
     * @return this
     */
    Source generateSource(int buffer);

    /**
     * Sets the position of the Source.
     * 
     * @param position
     */
    void setPosition(Vec3f position);

    /**
     * Gets the position of the Source.
     * 
     * @return position
     */
    Vec3f getPosition();

    /**
     * Delete the source.
     */
    void delete();
}
