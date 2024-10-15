package com.cogent.banking.console.app;

import com.cogent.banking.console.app.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI(scanner);
        ui.start();
    }
}
