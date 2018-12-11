package Controlador;

import javafxapplication3.JavaFXApplication3;

public class newControlador {

	public static void main(String[] args) {
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(JavaFXApplication3.class);
            }
        }.start();

	}

}
