package controls;

import java.io.File;
import java.util.ArrayList;

import functions.App;
import functions.Event;
import functions.Timeline;
import io.FileHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import ui.ApplicationView;

public class ApplicationControl implements ApplicationListener {

	private ApplicationView appView;
	private App app;
	private FileHandler fileHandler;
	private TimelineControl timelineControl;
	private EventControl eventControl;

	/**
	 * Constructor, Creates an ApplicationControl and sets variables for
	 * ApplicationView, App, FileHandler. Also creates a new TimelineControl and
	 * an EventControl.
	 * 
	 * @param av
	 *            , ApplicationView
	 * @param app
	 *            , App
	 * @param fh
	 *            , FileHandler
	 */
	public ApplicationControl(ApplicationView av, App app, FileHandler fh) {
		appView = av;
		this.app = app;
		fileHandler = fh;
		timelineControl = new TimelineControl();
		eventControl = new EventControl();
		eventControl.setApp(app);
		timelineControl.setApp(app);
	}

	/**
	 * Connects the View and Control through the Listener Also connects
	 * ApplicationView to App through the Listener
	 */
	public void setUpListeners() {
		appView.getTimelineView().addListener(timelineControl);
		appView.getEventView().addListener(eventControl);
		appView.addListener(this);
		app.addListener(appView);
	}

	@Override
	public void onTimelineSelected(Timeline t) {
		app.setCurrentTimeline(t);

	}

	@Override
	public void onNewEventSelected(Event e) {
		app.setCurrentEvent(e);

	}
	
	@Override
	public void onTimelineSaved() {
		FileChooser chooseFile = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		chooseFile.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = chooseFile.showSaveDialog(appView.getRoot().getScene().getWindow());
       
		try {
			fileHandler.saveTimeline(app.getCurrentTimeline(), file);
		} catch (Exception saver) {
			// if input is wrong, show error (popup window) message.
						Alert fieldError = new Alert(Alert.AlertType.ERROR, "File not specified");
						fieldError.showAndWait();
					}
		}
	}


	

