package com.jfilipczyk.lessonreport;

import java.net.URL;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public abstract class FunctionalTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        stage.show();
    }
    
    @Before
    public void beforeEach() throws Exception {
        ApplicationTest.launch(MainApp.class, new String[]{});
    }

    @After
    public void afterEach() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    protected <T extends Node> T find(String query) {
        return (T) lookup(query).queryFirst();
    }

    protected URL getResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName);
    }
}
