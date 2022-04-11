package model.listener.plugin;




public interface Plugin {

    /**
     * Active the audio changes generated by this plug-in. 
     * Keep the configuration if it has been previously disabled.
     */
    void enable();

    /**
     * Deactive the audio changes and restore the default value. 
     */
    void disable();

    /**
     * 
     * @return the state of activity of the plug-in
     */
    Boolean isEnabled();

}
