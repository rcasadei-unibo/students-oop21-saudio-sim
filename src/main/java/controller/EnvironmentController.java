package controller;

import controller.view.EnvironmentControllerView;

public class EnvironmentController {

    private final MainController mainCtr;
    private EnvironmentControllerView ctrlView;

    public EnvironmentController(final MainController mainCtr) {
        this.mainCtr = mainCtr;
    }

    /**
     * 
     * 
     */
    public void addEnvironment() {
    }
    
    
    /**
     * 
     * @param controllerView
     */
    public void setControllerView(final EnvironmentControllerView controllerView) {
        ctrlView = controllerView;
    }

}
