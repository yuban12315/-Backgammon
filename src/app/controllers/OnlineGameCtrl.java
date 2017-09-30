package app.controllers;


import app.services.NetService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class OnlineGameCtrl extends GameCtrl {
    public static final String HEAD_NET = "net";
    public static final String HEAD_MSG = "msg";
    public static final String HEAD_CHESS = "chess";
    public static final String HEAD_GAME = "game";
    public static final String HEAD_UNDO = "undo";
    public static final String BODY_OK = "ok";
    public static final String BODY_NO = "no";

    private NetService server;
    private NetService client;


    public static NetType netType;

    public enum NetType {
        SERVER, CLIENT
    }

    @FXML
    void back(){
        super.stageController.back();
    }

//    protected void handleCanvasClicked(MouseEvent event) {
//        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
//        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
//            return;
//        }
//        if (netType == NetType.SERVER) {
//            if (isOtherOK) {
//                if (currentChess == Chess.BLACK) {
//                    drawChess(currentChess, p);
//                    server.sendMessage(buildMessage(HEAD_CHESS, p.toString()));
//                    taContent.appendText("[主机]走子：" + markX[p.x] + "," + markY[p.y] + "\n");
//                    currentChess = Chess.WHITE;
//                    game[p.x][p.y] = Chess.BLACK;
//                    lastPostion = p;
//                    btnUndo.setDisable(false);
//                    checkWinner(p.x, p.y);
//                } else {
//                    taContent.appendText("[系统]请等待客户端走棋！\n");
//                }
//            } else {
//                taContent.appendText("[系统]客户端还没有准备好！\n");
//            }
//        } else if (netType == NetType.CLIENT) {
//            if (isOtherOK) {
//                if (currentChess == Chess.WHITE) {
//                    drawChess(currentChess, p);
//                    client.sendMessage(buildMessage(HEAD_CHESS, p.toString()));
//                    taContent.appendText("[客户端]走子：" + markX[p.x] + "," + markY[p.y] + "\n");
//                    currentChess = Chess.BLACK;
//                    game[p.x][p.y] = Chess.WHITE;
//                    lastPostion = p;
//                    btnUndo.setDisable(false);
//                    checkWinner(p.x, p.y);
//                } else {
//                    taContent.appendText("[系统]请等待主机走棋！\n");
//                }
//            } else {
//                taContent.appendText("[系统]主机还没有准备好！\n");
//            }
//        }
//
//    }
}

