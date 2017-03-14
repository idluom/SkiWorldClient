package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class HotelPageController implements Initializable {
	@FXML
	private HBox stars;
	public static int i = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		i = 0;
		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setFromValue(0);
		ft.setToValue(1);
		ImageView imgv = new ImageView(new Image("/images/star.png"));
		stars.getChildren().add(imgv);
		ft.setNode(imgv);
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (i < 6) {
					i++;
					FadeTransition ft1 = new FadeTransition(Duration.millis(1500));
					ft1.setFromValue(0);
					ft1.setToValue(1);
					ImageView imgv = new ImageView(new Image("/images/star.png"));
					stars.getChildren().add(imgv);
					ft1.setNode(imgv);
					ft1.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if (i < 6) {
								i++;
								FadeTransition ft2 = new FadeTransition(Duration.millis(1500));
								ft2.setFromValue(0);
								ft2.setToValue(1);
								ImageView imgv = new ImageView(new Image("/images/star.png"));
								stars.getChildren().add(imgv);
								ft2.setNode(imgv);
								ft2.setOnFinished(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										if (i < 6) {
											i++;
											FadeTransition ft3 = new FadeTransition(Duration.millis(1500));
											ft3.setFromValue(0);
											ft3.setToValue(1);
											ImageView imgv = new ImageView(new Image("/images/star.png"));
											stars.getChildren().add(imgv);
											ft3.setNode(imgv);
											ft3.setOnFinished(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													if (i < 6) {
														i++;
														FadeTransition ft4 = new FadeTransition(Duration.millis(1500));
														ft4.setFromValue(0);
														ft4.setToValue(1);
														ImageView imgv = new ImageView(new Image("/images/star.png"));
														stars.getChildren().add(imgv);
														ft4.setNode(imgv);
														ft4.play();
													}
												}
											});
											ft3.play();
										}
									}
								});
								ft2.play();
							}
						}
					});
					ft1.play();
				}
			}
		});
		ft.play();
	}

}
