package com.cogent.banking.console.app;
import com.cogent.banking.console.app.ui.UI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class GeneralTest {

    private Scanner scanner;
    private UI ui;

    @BeforeEach
    void setUp() {
        scanner = new Scanner(System.in);
        ui = new UI(scanner);
    }

    @Test
    void testLoginRegisterInput() {
        ui.start();
    }

}
