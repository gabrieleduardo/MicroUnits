/**
 * MicroUnits - Pauses Analysis of XML files generated by Translog II software.
 * For Translog II details See <http://bridge.cbs.dk/platform/?q=Translog-II>
 *
 * Copyright (C) 2014 Gabriel Ed. da Silva
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package JavaFx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Gabriel Ed
 */
public class MicroUnitsApp extends Application {
    
    /**
     * Para utilização do Controlador
     * For Controller use.
     */
    public static Stage stage;

    /**
     * Método Start
     * Start method
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        
        MicroUnitsApp.stage = stage;

        try {
            stage.setTitle("MicroUnits");

            Parent root = FXMLLoader.load(getClass().getResource("InterfaceGrafica.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(MicroUnitsApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
