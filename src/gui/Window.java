package gui;


import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class Window extends Application {
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static Window window = null;
	private GraphicsContext gc;

	@Override
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(500, 400);
        gc = canvas.getGraphicsContext2D();


		Group root = new Group();
		root.getChildren().add(canvas);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Cycle robot");
        stage.setScene(scene);
        stage.show();

        setStartUpTest(this);

	}

	public static Window waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return window;
    }

    public static void setStartUpTest(Window window) {
        Window.window = window;
        latch.countDown();
    }



    public void printSomething() {
        System.out.println("You called a method on the application");
    }

	public GraphicsContext getContext(){
		return gc;
	}



}
