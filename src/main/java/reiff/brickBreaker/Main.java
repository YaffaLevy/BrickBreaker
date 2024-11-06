package reiff.brickBreaker;

import lesser.brickBuilder.BrickBreakerFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BrickBreakerFrame().setVisible(true);
        });
    }

}
