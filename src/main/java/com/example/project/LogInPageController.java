

package com.example.project;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import java.sql.*;

public class LogInPageController {

    @FXML
    private TextField inputbox;

    @FXML
    private Button login;

    @FXML
    private PasswordField passwordbox;

    @FXML
    void goToSignUpPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void loginClicked(ActionEvent event) {
        String input=inputbox.getText();
        String pass=passwordbox.getText();

        System.out.println(input);
        System.out.println(pass);


        String url = "jdbc:mysql://127.0.0.1/musfiq";
        String username = "musfiq";
        String password = "1122";
        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        try {
            String chk1 = "Select * from useraccounts where email=?";
            Connection con;
            PreparedStatement pst1, pst2;
            con = DriverManager.getConnection(url, username, password);
            pst1 = con.prepareStatement(chk1);

            pst1.setString(1, input);

            ResultSet rs1;
            rs1 = pst1.executeQuery();
            if (rs1.next()) {
                String pas = rs1.getString("password");
                if (pas.equals(password)) {
                    try {
                        Parent root = FXMLLoader.load((getClass().getResource("HomePage.fxml")));
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                        stage.setScene(scene);
                    }catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong password");
                    alert.setContentText("Please enter your password correctly!");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong email/password");
                alert.setContentText("Please enter your email and password correctly!");
                alert.showAndWait();
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
