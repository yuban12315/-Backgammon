package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class GameCtrl  {
    @FXML
    Canvas canvas;
    @FXML
    TextField tfIP;
    @FXML
    TextField tfMessage;
    @FXML
    TextArea taContent;
    @FXML
    Label lbIP;
    @FXML
    Button btnConnect;
    @FXML
    Button btnStart;
    @FXML
    Button btnSend;
    @FXML
    Button btnUndo;

    private Color colorChessboard = Color.valueOf("#FBE39B");
    private Color colorLine = Color.valueOf("#884B09");
    private Color colorMark = Color.valueOf("#FF7F27");
    private GraphicsContext gc;
    private double gapX, gapY;
    private double chessSize;
    private double broadPadding = 20;
    private String[] markX = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"};
    private String[] markY = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};

    private class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }
    }
}
