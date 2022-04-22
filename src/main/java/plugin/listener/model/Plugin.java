package plugin.listener.model;

/**
 * Plug-in is an additional, visual or sound, feature of listener.
 * This interface represents the basic operation that every
 * plug-in implemented must have.
 *
 */
public interface Plugin {

    /**
     * Active the audio changes generated by this plug-in. 
     * Keep the configuration if it has been previously disabled.
     */
    void enable();

    /**
     * Disable the audio changes and restore the default value. 
     */
    void disable();

    /**
     * 
     * @return the activity status of the plug-in
     */
    Boolean isEnabled();

    /**
     * 
     * @return just the class name of the plug-in.
     */
    String getClassName();


}
