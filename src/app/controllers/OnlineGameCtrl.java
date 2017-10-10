package app.controllers;


import app.services.NetService;
import app.views.GameOnline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;


import java.awt.event.ActionEvent;

import static app.utils.kit.buildMessage;

public class OnlineGameCtrl extends GameCtrl implements NetService.NetStateChange {
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
    void back() {
        System.out.println("关闭连接");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(netType!=null){
                    System.out.println("关闭连接");
                    NetService.getInstance(netType).close();
                }

            }
        });
        super.stageController.back();
    }

    @FXML
    protected void handleCanvasClicked(MouseEvent event) {
        Position p = convertPosition(event.getX() - broadPadding, event.getY() - broadPadding);
//        System.out.println(p.toString());
        //判断棋子落点是否超出棋盘，以及是否已经落子
        if (p.x < 0 || p.y < 0 || p.x > 20 || p.y > 20 || game[p.x][p.y] != null) {
            return;
        }
        if (netType == NetType.SERVER) {
            //如果为服务器
            if (isOtherOK) {
                if (currentChess == Chess.BLACK) {
                    System.out.println("黑棋走子:" + p.toString());
                    drawChess(currentChess, p);
                    server.sendMessage(buildMessage(HEAD_CHESS, p.toString()));

                    game[p.x][p.y] = currentChess;
                    lastPostion = p;
                    //悔棋暂定
                    checkWinner(p.x,p.y);
                    currentChess = Chess.WHITE;
                } else {
                    System.out.println("chess:"+currentChess+",now:"+Chess.BLACK);
                    System.out.println("请等待对方下棋");
                }
            } else {
                System.out.println("对方还未准备好");
            }
        } else if (netType == NetType.CLIENT) {
            //如果为客户端
            if (isOtherOK) {
                if (currentChess == Chess.WHITE) {
                    System.out.println("白棋走子:" + p.toString());
                    drawChess(currentChess,p);
                    client.sendMessage(buildMessage(HEAD_CHESS,p.toString()));

                    game[p.x][p.y]=currentChess;
                    lastPostion = p;

                    checkWinner(p.x,p.y);
                    currentChess=Chess.BLACK;
                } else {
                    System.out.println("chess:"+currentChess+",now:"+Chess.WHITE);
                    System.out.println("请等待对方下棋");
                }
            } else {
                System.out.println("对方还未准备好");
            }
        }
        else {
            System.out.println("游戏尚未开始！");
        }
    }

    @FXML
    protected void startServer(){
        server=NetService.getInstance(NetType.SERVER);
        server.startServer();
        netType=NetType.SERVER;
        server.setNetStateChangeListener(this);
        GameOnline.setNetType(NetType.SERVER);

        System.out.println("服务器已启动，游戏已创建，等待玩家加入");
    }

    @FXML
    protected void connectServer(){
        client=NetService.getInstance(NetType.CLIENT);
        client.setNetStateChangeListener(this);
        client.connectToServer("0.0.0.0");
        netType = NetType.CLIENT;
        client.sendMessage(buildMessage(HEAD_GAME, BODY_OK));
    }

    @FXML
    protected void again() {
        cleanChessBoard();
        System.out.println("重新开始");
    }

    public void initialize(){
        gc = canvas.getGraphicsContext2D();
        gapX = (canvas.getWidth() - broadPadding * 2) / 20;
        gapY = (canvas.getWidth() - broadPadding * 2) / 20;
        chessSize = gapX * 0.8;
        cleanChessBoard();
    }
    @Override
    public void onConnect() {
        System.out.println("some one connected");
        server.sendMessage(buildMessage(HEAD_NET, BODY_OK));
        System.out.println("客户端已连接");
        server.sendMessage(buildMessage(HEAD_GAME, BODY_OK));
        currentChess = Chess.BLACK;
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        String[] msgArray = message.split(":");
        switch (msgArray[0]) {
            case HEAD_NET:
                if (msgArray[1].equals(BODY_OK)) {
//                    tfMessage.setDisable(false);
//                    btnSend.setDisable(false);
//                    tfIP.setDisable(true);
//                    btnStart.setDisable(true);
//                    btnConnect.setDisable(true);
//                    taContent.appendText("[系统]客户端执白棋，请等待主机先走\n");
                }
                break;
            case HEAD_MSG:
                StringBuilder msgContent = new StringBuilder();
                for (int i = 1; i < msgArray.length; i++) {
                    msgContent.append(msgArray[i]);
                    if (i + 1 < msgArray.length) {
                        msgContent.append(':');
                    }
                }

                if (netType == NetType.SERVER) {
                   // taContent.appendText("[客户端]" + msgContent.toString() + "\n");
                } else if (netType == NetType.CLIENT) {
                   // taContent.appendText("[主机]" + msgContent.toString() + "\n");
                }
                break;
            case HEAD_CHESS:
                //btnUndo.setDisable(true);
                int x = Integer.parseInt(msgArray[1]);
                int y = Integer.parseInt(msgArray[2]);
                Position p = new Position(x, y);
                lastPostion = p;
                if (netType == NetType.SERVER) {
                    //taContent.appendText("[客户端]走子：" + markX[x] + "," + markY[y] + "\n");
                    drawChess(Chess.WHITE, p);
                    game[p.x][p.y] = Chess.WHITE;
                    currentChess = Chess.BLACK;
                   checkWinner(p.x, p.y);
                } else if (netType == NetType.CLIENT) {
                   // taContent.appendText("[主机]走子：" + markX[x] + "," + markY[y] + "\n");
                    drawChess(Chess.BLACK, p);
                    game[p.x][p.y] = Chess.BLACK;
                    currentChess = Chess.WHITE;
                    checkWinner(p.x, p.y);
                }
                break;
            case HEAD_GAME:
                isOtherOK = true;
                break;
        }
    }

    @Override
    public void onDisconnect() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "连接已断开！", ButtonType.OK);
        //alert.setOnCloseRequest(event -> System.exit(0));
        alert.show();
    }

    private void win(Chess thisChess) {
        isOtherOK = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION, thisChess == Chess.BLACK ? "黑棋获胜！" : "白棋获胜", ButtonType.OK);
        alert.show();
        currentChess = Chess.BLACK;
        alert.setOnCloseRequest(event -> {
            cleanChessBoard();
            if (netType == NetType.CLIENT) {
                client.sendMessage(buildMessage(HEAD_GAME, BODY_OK));
            } else {
                server.sendMessage(buildMessage(HEAD_GAME, BODY_OK));
            }
            game = new Chess[21][21];
            System.out.println("新的一局开始了");
            currentChess = Chess.BLACK;
        });
    }

    private int countChessNum(Direction direction, Chess thisChess, int x, int y) {
        int num = 0;
        switch (direction) {
            case TOP_LEFT:
                if (x - 1 >= 0 && y - 1 >= 0 && thisChess == game[x - 1][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y - 1);
                }
                break;
            case TOP:
                if (y - 1 >= 0 && thisChess == game[x][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x, y - 1);
                }
                break;
            case TOP_RIGHT:
                if (x + 1 <= 20 && y - 1 >= 0 && thisChess == game[x + 1][y - 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y - 1);
                }
                break;
            case RIGHT:
                if (x + 1 <= 20 && thisChess == game[x + 1][y]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y);
                }
                break;
            case RIGHT_DOWN:
                if (x + 1 <= 20 && y + 1 <= 20 && thisChess == game[x + 1][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x + 1, y + 1);
                }
                break;
            case DOWN:
                if (y + 1 <= 20 && thisChess == game[x][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x, y + 1);
                }
                break;
            case DOWN_LEFT:
                if (x - 1 >= 0 && y + 1 <= 20 && thisChess == game[x - 1][y + 1]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y + 1);
                }
                break;
            case LEFT:
                if (x - 1 >= 0 && thisChess == game[x - 1][y]) {
                    num++;
                    num += countChessNum(direction, thisChess, x - 1, y);
                }
                break;
        }
        return num;
    }

    private void checkWinner(int x, int y) {
        Chess thisChess = game[x][y];
        if (thisChess == null) {
            return;
        }

        int left2Right = 1 + countChessNum(Direction.LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT, thisChess, x, y);
        //System.out.println("--" + left2Right);
        if (left2Right >= 5) {
            win(thisChess);
            return;
        }

        int top2Down = 1 + countChessNum(Direction.TOP, thisChess, x, y) + countChessNum(Direction.DOWN, thisChess, x, y);
        //System.out.println("|" + top2Down);
        if (top2Down >= 5) {
            win(thisChess);
            return;
        }

        int topLeft2RightDown = 1 + countChessNum(Direction.TOP_LEFT, thisChess, x, y) + countChessNum(Direction.RIGHT_DOWN, thisChess, x, y);
        //System.out.println("\\" + topLeft2RightDown);
        if (topLeft2RightDown >= 5) {
            win(thisChess);
            return;
        }

        int topRight2DownLeft = 1 + countChessNum(Direction.TOP_RIGHT, thisChess, x, y) + countChessNum(Direction.DOWN_LEFT, thisChess, x, y);
        //System.out.println("/" + topRight2DownLeft);
        if (topRight2DownLeft >= 5) {
            win(thisChess);
        }
    }

    @Override
    public void onServerOK() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Alert alert = new Alert(Alert.AlertType.INFORMATION, "游戏已创建！", ButtonType.OK);
                //alert.setOnCloseRequest(event -> System.exit(0));
                //alert.show();
            }
        });
        System.out.println("server OK");
    }

}