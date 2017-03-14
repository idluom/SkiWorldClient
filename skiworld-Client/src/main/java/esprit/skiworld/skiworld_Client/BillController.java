package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Bill;
import Entity.Equipement;
import Service.BillEJBRemote;
import Service.EquipementEJBRemote;
import esprit.skiworld.Business.Loading;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class BillController implements Initializable {
	@FXML
	ImageView loading;
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	AnchorPane elements;
	@FXML
	private TableView<Bill> TabBill;
	@FXML
	private TableColumn<Bill, Date> date;

	@FXML
	private TableColumn<Bill, Float> cost;

	@FXML
	private TableColumn<Bill, String> eq;

	@FXML
	private Button ref;

	@FXML
	private Button bac;

	@FXML
	private TextField T;
	@FXML
	private DatePicker rech;
	@FXML
	private TextField total;
	@FXML
	private TextField pad;

	@FXML
	private TextField profit;

	ObservableList<Bill> champs;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				billFilter((String) oldValue, (String) newValue);
			}
		});

		elements.setVisible(false);
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				elements.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(2), elements);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});
		InitialContext ctx = null;
		BillEJBRemote proxy = null;

		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy = (BillEJBRemote) ctx.lookup(jndiName);
			champs = FXCollections.observableArrayList(proxy.DisplayAllBills());

		} catch (NamingException e) {
		}

		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
		eq.setCellValueFactory(new PropertyValueFactory<>("equipementName"));
		TabBill.setItems(champs);

	}

	@FXML
	void Back(ActionEvent event) {
		try {
			MainApp.changeScene("/fxml/Menu.fxml", "AddShop");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void Refresh(ActionEvent event) {
		champs.remove(champs);
		InitialContext ctx = null;
		BillEJBRemote proxy = null;

		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy = (BillEJBRemote) ctx.lookup(jndiName);

		} catch (NamingException e) {
		}
		champs = FXCollections.observableArrayList(proxy.DisplayAllBills());
		TabBill.setItems(champs);

	}

	private void billFilter(String oldValue, String newValue) {
		ObservableList<Bill> filterList = FXCollections.observableArrayList();
		if (T == null || (newValue.length() < oldValue.length()) || newValue == null) {

			TabBill.setItems(champs);

		} else {

			newValue = newValue.toUpperCase();

			for (Bill E : TabBill.getItems()) {

				String filterFirstName = E.getEquipementName();

				if (filterFirstName.toUpperCase().contains(newValue)) {

					filterList.add(E);

				}

			}
			TabBill.setItems(filterList);

		}

	}

	@FXML
	void choice(ActionEvent event) {
		Float C = 0f;
		Float D = 0f;
		if (rech.getValue() == null) {

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" error ");
			alert.setHeaderText("Check Fields");
			alert.showAndWait();

			return;
		}
		Date d = new Date();
		Date c = java.sql.Date.valueOf(rech.getValue());
		if (d.before(c)) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" error ");
			alert.setHeaderText("Date not reached");
			alert.showAndWait();

			return;

		}
		champs.remove(champs);
		InitialContext ctx = null;
		BillEJBRemote proxy = null;
		EquipementEJBRemote proxy1 = null;

		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			String jnd = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			proxy = (BillEJBRemote) ctx.lookup(jndiName);
			proxy1 = (EquipementEJBRemote) ctx.lookup(jnd);

		} catch (NamingException e) {
		}
		champs = FXCollections.observableArrayList(proxy.FindByDate(c));
		TabBill.setItems(champs);
		List<Bill> l = proxy.FindByDate(c);

		for (Bill B : l) {
			C = C + B.getCost();
			Equipement E = proxy1.FindByName(B.getEquipementName());

			if (E.getDiscount() == null) {
				D = D + ((E.getPrice() / 100) * 40) * (E.getInventoryquantity() + E.getShopquantity());
				System.out.println("zzzz" + D.toString());
			} else {
				if (B.getDate().before(E.getDiscount().getBeginning()) || B.getDate().after(E.getDiscount().getEnd())) {
					D = D + (((100 * E.getPrice()) / (100 - E.getDiscount().getPercentage()) / 100) * 40)
							* (E.getInventoryquantity() + E.getShopquantity());
					System.out.println("kkkkk" + D.toString());
				} else {
					D = D + ((E.getPrice()
							- (((100 * E.getPrice()) / (100 - E.getDiscount().getPercentage()) / 100) * 60))
							* (E.getInventoryquantity() + E.getShopquantity()));
					System.out.println(((E.getPrice()
							- (((100 * E.getPrice()) / (100 - E.getDiscount().getPercentage()) / 100) * 60))
							* (E.getInventoryquantity() + E.getShopquantity())));

				}
			}
		}

		total.setText(C.toString());
		profit.setText("" + ((C / 0.6) * 0.4));
		System.out.println(D.toString());
		pad.setText("" + D);

	}

}
