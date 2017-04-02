package com.jfilipczyk.lessonreport;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateReportFunctionalTest extends FunctionalTest {

    static final String DEFAULT_FILENAME = FXMLController.DEFAULT_FILENAME;
    
    static final String CALENDAR_URL = "#calendarUrl";
    static final String DATE_FROM = "#dateFrom";
    static final String DATE_TO = "#dateTo";
    static final String SUBMIT = "#submit";
    static final String SUCCESS_MESSAGE = "#successMessage";
    
    @After
    @Override
    public void afterEach() throws TimeoutException
    {
        super.afterEach();
        getReportFile().delete();
    }

    @Test
    public void it_exports_calendar() {
        clickOn(CALENDAR_URL, MouseButton.PRIMARY);
        write(getResource("fixtures/functional/example.ics").toString());
        clickOn(DATE_FROM, MouseButton.PRIMARY);
        write("12/1/2016");
        clickOn(DATE_TO, MouseButton.PRIMARY);
        write("12/31/2016");
        // focus on other field to trigger validation and enable submit button
        clickOn(CALENDAR_URL, MouseButton.PRIMARY);
        clickOn(SUBMIT, MouseButton.PRIMARY);
        // wait for file chooser dialog
        sleep(1, TimeUnit.SECONDS);
        push(KeyCode.ENTER);
        // wait for report processing
        sleep(1, TimeUnit.SECONDS);
        Label successMessage = find(SUCCESS_MESSAGE);
        
        assertThat(successMessage.getText(), equalTo("Report saved!"));
        assertThat(linesOf(getReportFile())).containsExactly(
            "\"Name\",\"Num of events\",\"Total time [h]\"",
            "\"John Doe\",\"5\",\"5.00\"",
            "\"Smith family\",\"8\",\"6.00\""
        );
    }
    
    private File getReportFile() {
        File reportFile = new File(DEFAULT_FILENAME);
        return reportFile;
    }
}
