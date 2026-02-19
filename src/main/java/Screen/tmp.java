//package Screen;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//
//public class tmp extends HBox {
//
//    private final Button go_back;
//    //private final Button player1;
//    private final Button player2;
//    private final Button player3;
//    //private final Button player4;
//
//
//
//    public ChoosingScreen(ScreenManager choose) {
//
//        this.setSpacing(40);
//        this.setAlignment(Pos.CENTER);
//
//
//        // First player //
//
//        VBox frame1 = new VBox();
//        frame1.setAlignment(Pos.CENTER);
//        frame1.setSpacing(20);
//        frame1.setMinWidth(100);
//        this.go_back = new Button("play player1");
//        this.go_back.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
//                choose.showTitle();
//
//            }
//        });
//
//        Text descirption = new Text("The kid who just a kid");
//
//        frame1.getChildren().addAll(go_back, descirption);
//
//        //
//
//        // Seconds player //
//
//        VBox frame2 = new VBox();
//        frame2.setAlignment(Pos.CENTER);
//        frame2.setSpacing(20);
//        frame2.setMinWidth(100);
//        this.player2 = new Button("play player2");
//        Text des2 = new Text("The gymbro Who love hitting gym");
//
//        frame2.getChildren().addAll(player2, des2);
//
//
//        //
//
//
//        // Thrid player //
//
//        VBox frame3 = new VBox();
//        frame3.setAlignment(Pos.CENTER);
//        frame3.setSpacing(20);
//        frame3.setMinWidth(100);
//        this.player3 = new Button("play player3");
//        Text des3 = new Text("The nerd As bookWorm");
//
//        frame3.getChildren().addAll(player3, des3);
//
//
//
//
//
//
//
//        this.getChildren().addAll(frame1 , frame2 , frame3);
//
//
//    }
//
//}