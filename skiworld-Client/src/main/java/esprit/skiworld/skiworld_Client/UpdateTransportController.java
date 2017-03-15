package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import org.controlsfx.ControlsFXSample;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateTransportController extends ControlsFXSample implements Initializable{
	@FXML
	JFXTextField depP;
	@FXML
	JFXTextField nPlaces;
	@FXML
	JFXTextField arrPlace;
	@FXML
	JFXTextField cost;
	@FXML
	ChoiceBox<String> type;
	@FXML
	JFXDatePicker depDate;
	@FXML
	JFXDatePicker arrDate;
	@FXML
	JFXTimePicker depTime;
	@FXML
	JFXTimePicker arrTime;
	@FXML
	JFXButton updateButton;
	private PopOver popOver;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> ls = FXCollections.observableArrayList("Avion","Bus","Taxi","Train");
		type.setItems(ls);
		
		depP.setText(TransportController.upTrans.getDeparturePlace().toString());
		arrPlace.setText(TransportController.upTrans.getArrivalPlace().toString());
		cost.setText(TransportController.upTrans.getPrice().toString());
		type.setValue(TransportController.upTrans.getType().toString());
		nPlaces.setText(TransportController.upTrans.getNumberPlaces().toString());
		arrDate.setValue(LocalDate.now());
		arrTime.setValue(LocalTime.now());
		depDate.setValue(LocalDate.now());
		depTime.setValue(LocalTime.now());
	}
	@FXML
	public void updateAction(ActionEvent event) throws IOException, ParseException {
//		TransportController.upTrans.setArrivalPlace(arrPlace.getText());
//		TransportController.upTrans.setDeparturePlace(depP.getText());
//		TransportController.upTrans.setNumberPlaces(Integer.parseInt(nPlaces.getText()));
//		TransportController.upTrans.setPrice(Float.parseFloat(cost.getText()));
//		TransportController.upTrans.setType(type.getSelectionModel().getSelectedItem());
//		LocalDate dt = depDate.getValue();
//		LocalTime dt2 = depTime.getValue();
//		String s = dt.toString()+" "+dt2.toString();
//		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		LocalDate dt3 = arrDate.getValue();
//		LocalTime dt4 = arrTime.getValue();
//		String s1 = dt3.toString()+" "+dt4.toString();
//		Date d = df1.parse(s);
//		Date d1 = df1.parse(s1);
//		TransportController.upTrans.setDepartureDate(d);
//		TransportController.upTrans.setArrivalDate(d1);
//		new TransportBusiness().updateTransport(TransportController.upTrans);
//		MainApp.changeScene("/fxml/Transport.fxml", "Transport");
	
		popOver = createPopOver();
		popOver.setContentNode(createForm());
		popOver.show(updateButton);
	}
	@Override
	public String getJavaDocURL() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node getPanel(Stage arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSampleName() {
		// TODO Auto-generated method stub
		return null;
	}
	private PopOver createPopOver() {
        PopOver popOver = new PopOver();
        popOver.setDetachable(true);
        popOver.setDetached(false);
        popOver.arrowSizeProperty().set(12);;
        popOver.arrowIndentProperty().set(12);
        popOver.arrowLocationProperty().set(ArrowLocation.LEFT_CENTER);
        popOver.cornerRadiusProperty().set(30);
        popOver.headerAlwaysVisibleProperty().set(true);
        popOver.setAnimated(true);
        popOver.setTitle("ADD");
        return popOver;
    }
	public PopOver getPopOver() {
        return popOver;
    }
	@Override
    public String getControlStylesheetURL() {
    	return "/styles/popover.css";
    }
	public Node createForm() throws IOException{
		/*Slider arrowSize = new Slider(0, 50, 25);
		GridPane.setFillWidth(arrowSize, true);
		GridPane controls = new GridPane();
        controls.setHgap(10);
        controls.setVgap(10);
        BorderPane.setMargin(controls, new Insets(10));
        BorderPane.setAlignment(controls, Pos.BOTTOM_CENTER);

        Label arrowSizeLabel = new Label("Arrow Size:");
        GridPane.setHalignment(arrowSizeLabel, HPos.RIGHT);
        
        controls.add(arrowSizeLabel, 0, 0);
        controls.add(arrowSize, 1, 0);*/
		Pane root = new AnchorPane();
		root = (Pane) FXMLLoader.load(UpdateTransportController.class.getResource("/fxml/AddTransport.fxml"));
		
		return root;
	}
}	
