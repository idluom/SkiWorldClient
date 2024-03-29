package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Entity.Member;
import Service.AdminEJBRemote;
import esprit.skiworld.Business.AdminBusiness;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.StaffBusiness;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminController implements Initializable {
	static PopOver pop = new PopOver();
	String nom = "";
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML
	private AnchorPane Conteneur;
	@FXML
	private JFXTextField CinId;

	@FXML
	private JFXTextField FirstNameId;

	@FXML
	private JFXTextField LastNameId;

	@FXML
	private JFXTextField AddressId;

	@FXML
	private JFXTextField NumTelId;

	@FXML
	private JFXDatePicker BirthdayId;

	@FXML
	private JFXTextField LoginId;

	@FXML
	private JFXPasswordField PwdId;

	@FXML
	private JFXButton Updateid;

	@FXML
	private JFXTextField MailId;

	private Stage dialogStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conteneur.setVisible(false);
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				Conteneur.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(1), Conteneur);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});

		int x = SelectAdminController.getType();
		int ok = 0;
		InitialContext ctx;

		// Admin
		try {
			ctx = new InitialContext();
			AdminEJBRemote admin = (AdminEJBRemote) ctx
					.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
			Member a = new Member();
			if (x == 0) {
				a = admin.getAdmin();
			} else if (x == 1) {// HotelManager a = new HotelManager();
				a = admin.getHotelManager();

			} else if (x == 2) {
				try {
					a = admin.getRestaurantOwner();
				} catch (Exception e) {

				}
			} else if (x == 3) {
				a = admin.getShopOwner();
			}

			CinId.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						CinId.setText(newValue);
					} else
						CinId.setText(oldValue);

				}

			});

			NumTelId.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if (newValue.matches("\\d*")) {
						NumTelId.setText(newValue);
					} else
						NumTelId.setText(oldValue);

				}

			});

			CinId.setText(a.getCin() + "");
			FirstNameId.setText(a.getFirstName());
			AddressId.setText(a.getAddress());
			LastNameId.setText(a.getLastName());
			LoginId.setText(a.getLogin());
			nom = a.getLogin();
			NumTelId.setText(a.getNumTel() + "");
			// BirthdayId.setPromptText(a.getBirthDate()+"");
			PwdId.setText(a.getPassword());
			String s = a.getBirthDate() + "";
			if (s.length() > 5) {

				String t = s.substring(0, 10);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(t, formatter);
				BirthdayId.setValue(localDate);
			}
			if (a.getMail() != null) {
				MailId.setText(a.getMail());
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@FXML
	void UpdateAction(ActionEvent event) throws NamingException, ParseException {
		int x = SelectAdminController.getType();
		if (x != -1) { // ADMIN
			InitialContext ctx = new InitialContext();
			Member a = new Member();
			AdminEJBRemote admin = (AdminEJBRemote) ctx
					.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
			if (x == 0) {

				a = admin.getAdmin();
			} else if (x == 1) {
				a = admin.getHotelManager();
			} else if (x == 2) {
				a = admin.getRestaurantOwner();

			} else if (x == 3) {
				a = admin.getShopOwner();
			}
			String ErrorMsg = "";
			int isOk = 0;
			if (CinId.getText().length() == 0 || CinId.getText().length() < 6) {
				ErrorMsg += "Cin Invalid \n";
				isOk = 1;
			}
			if (FirstNameId.getText().length() == 0 || FirstNameId.getText().length() < 3) {
				ErrorMsg += "First Name Invalid \n";
				isOk = 1;
			}
			if (LastNameId.getText().length() < 3) {
				ErrorMsg += "Last Name Invalid \n";
				isOk = 1;
			}
			if (LoginId.getText().length() < 3) {
				ErrorMsg += "Login Invalid \n";
				isOk = 1;
			}
			if (NumTelId.getText().length() < 8) {
				ErrorMsg += "Phone Number to short \n";
				isOk = 1;
			}
			if (PwdId.getText().length() < 3) {
				ErrorMsg += "Password to short \n";
				isOk = 1;
			}
			if (new StaffBusiness().fetchUsername(LoginId.getText())) {
				ErrorMsg += "Invalid Username";
				isOk = 1;
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse("2000-01-01", formatter);
			if (BirthdayId.getValue() == null) {
				ErrorMsg += "You should select Birthday !! \n";
				isOk = 1;
			}
			if (BirthdayId.getValue() != null)
				if (BirthdayId.getValue().isAfter(localDate)) {
					ErrorMsg += "You should be older then this!! \n";
					isOk = 1;
				}
			boolean dot = false;
			boolean p = false;
			int s = 0;
			int c = 0;
			if (MailId.getText().length() > 5 && MailId.getText().contains("@gmail.com")) {
				String m = MailId.getText();

				for (int i = 0; i < m.length(); i++) {
					c++;
					if (m.charAt(i) == '@' && c > 4) {
						dot = true;
					}
					if (p == true) {
						s++;
					}
					if (dot == true && m.charAt(i) == '.') {
						p = true;
					}

				}

			}
			if (s < 2) {
				ErrorMsg += "Wrong expression of Mail \n";
				isOk = 1;
			}
			if (isOk == 1) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Fields Missing");
				alert.setHeaderText(ErrorMsg);

				alert.showAndWait();
			}

			if (isOk == 0) {
				a.setCin(Integer.parseInt(CinId.getText()));
				a.setFirstName(FirstNameId.getText());
				a.setLastName(LastNameId.getText());
				a.setLogin(LoginId.getText());
				a.setNumTel(Integer.parseInt(NumTelId.getText()));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String xx = BirthdayId.getValue() + "";
				Date da = df.parse(xx);
				a.setBirthDate(da);
				a.setPassword(PwdId.getText());
				a.setMail(MailId.getText());
				// admin.updateAdmin(a);
				new AdminBusiness().updateAdmin(a);
				SelectAdminController.stage.close();
				Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).title("Update")
						.text(FirstNameId.getText() + " Updated !").graphic(FirstNameId).position(Pos.CENTER)
						.hideCloseButton().onAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								MainApp.s.close();
							}
						});
				nb.showConfirm();
			}
		}

	}

	@FXML
	void checkPwd(KeyEvent event) {
		pop.setArrowSize(10);
		pop.setDetachable(true);
		pop.setDetached(false);
		Label label = new Label();
		pop.setContentNode(label);
		pop.hide();
		if (PwdId.getText() != null) {
			if (PwdId.getText().length() < 5) {
				PwdId.focusColorProperty().set(Paint.valueOf("red"));
				PwdId.setPromptText("Low Password");
				label.setText("Week Password");
				pop.show(PwdId);
			} else if (PwdId.getText().length() < 8) {
				PwdId.focusColorProperty().set(Paint.valueOf("orange"));
				PwdId.setPromptText("Average Password");
				label.setText("Average Password");
				pop.show(PwdId);
			} else if (PwdId.getText().length() > 8) {
				PwdId.focusColorProperty().set(Paint.valueOf("green"));
				PwdId.setPromptText("Secure Password");
				label.setText("Strong Password");
				pop.show(PwdId);
			}
		}
	}

	@FXML
	void TestLogin(KeyEvent event) {
		
		pop.setArrowSize(10);
		pop.setDetachable(true);
		pop.setDetached(false);
		pop.hide();
		int t = 0;
		LoginId.setPromptText("");
		LoginId.focusColorProperty().set(Paint.valueOf("#4059a9"));
		List<Member> lm = new AdminBusiness().DisplayAllMember();
		for (Member member : lm) {
			if (LoginId.getText().equals(member.getLogin()) && !LoginId.getText().equals(nom)) {
				t = 1;
			}
		}
		if (t == 1) {
			
			Label label = new Label();
			pop.setContentNode(label);
			LoginId.focusColorProperty().set(Paint.valueOf("red"));
			LoginId.setPromptText("Login Exist !");
			label.setText("Login Already Exists");
			pop.show(LoginId);
		}
	}

	@FXML
	void BackAction(ActionEvent event) {
		SelectAdminController.stage.close();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
