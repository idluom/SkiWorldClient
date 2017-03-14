package esprit.skiworld.skiworld_Client;

import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import com.jfoenix.controls.JFXTimePicker;
import Entity.Training;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.TrainingBusiness;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class UpdateTrainingController implements Initializable, Comparable<LocalDate> {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane TableTrack;
	@FXML
	private TextField TitleTF;
	@FXML
	private TextArea DescriptionTF;
	@FXML
	private JFXTimePicker BdTTF;
	@FXML
	private DatePicker BdTF;
	@FXML
	private DatePicker EdTF;
	@FXML
	private TextField PriceTF;

	@FXML
	private TextField NumberTF;

	@FXML
	private ChoiceBox<String> LevelTF;
	ObservableList<String> comboList = FXCollections.observableArrayList("Easy", "Amateur", "Average", "Hard",
			"Professional");
	@FXML
	private Label Pricelab;
	@FXML
	private Label Edlab;
	@FXML
	private Label Numberlab;
	@FXML
	private Label Bdlab;
	@FXML
	private Label Levellab;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		NumberTF.textProperty().addListener(new ChangeListener<String>() {
		    @Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
		            NumberTF.setText(newValue);
		        } else {
		            NumberTF.setText(oldValue);
		        }
		    }
		});
		
		PriceTF.textProperty().addListener(new ChangeListener<String>() {
		    @Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					PriceTF.setText(newValue);
		        } else {
		        	PriceTF.setText(oldValue);
		        }
		    }
		});
		
		TableTrack.setVisible(false);
		TitleTF.setText(TrainingController.comp.getTitle());
		DescriptionTF.setText(TrainingController.comp.getDescription());
		LevelTF.setValue("Hard");
		LevelTF.setItems(comboList);
		PriceTF.setText(Float.valueOf(TrainingController.comp.getPrice()).toString());
		NumberTF.setText(Integer.valueOf(TrainingController.comp.getNumber()).toString());
		
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				TableTrack.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(1), TableTrack);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});
	}

	@FXML
	public void Update() throws ParseException {
		int ok = 0;
		if (BdTF.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Begining Date Field");
			alert.showAndWait();
			ok = 1;
			System.out.println("ffff");
		}
		if (EdTF.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty End Date Field");
			alert.showAndWait();
			ok = 1;
		}
		if (NumberTF.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Number Field");
			alert.showAndWait();
			ok = 1;
		}
		if (PriceTF.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Price Field");
			alert.showAndWait();
			ok = 1;
		}
		LocalDate dateB = BdTF.getValue();
		LocalDate dateE = EdTF.getValue();
		LocalTime dateBT = BdTTF.getValue();
		if (dateB.compareTo(dateE) > 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Begining Date Can not before the End Date !!!!");
			alert.showAndWait();
			ok = 1;
		}
		if (dateB.isBefore(LocalDate.now())) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Begining Date Unsuportable !!!!");
			alert.showAndWait();
			ok = 1;
		}
		if (ok == 0) {
			
			String DD = java.sql.Date.valueOf(dateB) + " " + (java.sql.Time.valueOf(dateBT));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.util.Date da;
			da = df.parse(DD);
			
			Training training = new Training();
			float price = Float.valueOf(PriceTF.getText());
			int number = Integer.valueOf(NumberTF.getText());
			String level = (String) LevelTF.getValue();
			
//				training = proxy.findTrainingById(TrainingController.comp.getIdTraining());
			TrainingController.comp.setBegeningDate(da);
			// training.setBegeningDate(java.sql.Date.valueOf(dateB));
			TrainingController.comp.setEndDate(java.sql.Date.valueOf(dateE));
			TrainingController.comp.setLevel(level);
			TrainingController.comp.setPrice(price);
			TrainingController.comp.setNumber(number);
			TrainingController.comp.setTitle(TitleTF.getText());
			TrainingController.comp.setDescription(DescriptionTF.getText());
			new TrainingBusiness().updateTraing(TrainingController.comp);
			TrainingController.s.close();
			// les alerts
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
					.title("Action Completed").text("The Training was successfuly Updated");
			notBuilder.showConfirm();
		}
		TrainingController.s.close();
	}

	@FXML
	private void Cancel() {
		NumberTF.clear();
		PriceTF.clear();
	}

	@Override
	public int compareTo(LocalDate d2) {
		LocalDate d1 = BdTF.getValue();
		return (d1.compareTo(d2));
	}
}
