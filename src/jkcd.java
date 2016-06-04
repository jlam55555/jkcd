import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class jkcd extends Application {
  static BorderPane root = new BorderPane();
  static int imageId;
  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage primaryStage) {
    // window title and root element
    primaryStage.setTitle("jkcd - the Java xkcd client");

    // buttons!
    Button random = new Button("Random");
    random.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        setImage(-1);
      }
    });
    Button next = new Button(">|");
    next.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        setImage(imageId+1);
      }
    });
    Button previous = new Button("|<");
    previous.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        setImage(imageId-1);
      }
    });

    // set up layout
    StackPane top = new StackPane();
    top.setPrefHeight(50);
    root.setTop(top);
    StackPane left = new StackPane(previous);
    left.setPrefWidth(50);
    root.setLeft(left);
    StackPane right = new StackPane(next);
    right.setPrefWidth(50);
    root.setRight(right);
    StackPane bottom = new StackPane(random);
    bottom.setPrefHeight(50);
    root.setBottom(bottom);
    primaryStage.setScene(new Scene(root, 750, 500));
    primaryStage.show();

    // get random image
    setImage(-1);

  }
  public static String getHtml(String toDownload) {
    // web download code from http://stackoverflow.com/questions/238547/how-do-you-programmatically-download-a-webpage-in-java
    URL url;
    InputStream is = null;
    BufferedReader br;
    String line, html = "";
    try {
      url = new URL(toDownload);
      is = url.openStream();
      br = new BufferedReader(new InputStreamReader(is));
      while((line = br.readLine()) != null)
        html += line; 
    } catch(Exception e) {
      e.printStackTrace();
    }
    return html;
  }
  public static void setImage(int id) {
    String html = getHtml((id == -1) ? "http://c.xkcd.com/random/comic/" : "http://www.xkcd.com/" + id);
    Pattern titlePattern = Pattern.compile("<img src=\"([^\"]+)\" title=\"([^\"]+)\" alt=\"([^\"]+?)\" />");
    Matcher titleMatcher = titlePattern.matcher(html);
    titleMatcher.find();
    String src = titleMatcher.group(1);
    String caption = titleMatcher.group(2);
    String title = titleMatcher.group(3);
    if(id == -1) {
      Pattern idPattern = Pattern.compile("Permanent link to this comic: http://xkcd.com/([0-9]+)/");
      Matcher idMatcher = idPattern.matcher(html);
      idMatcher.find();
      id = Integer.parseInt(idMatcher.group(1));
    }
    imageId = id;
    Pattern largePattern = Pattern.compile("<div id=\"comic\"><a href=\"([^\"]+(\\.png|large/))\">");
    Matcher largeMatcher = largePattern.matcher(html);
    if(largeMatcher.find()) {
      if(largeMatcher.group(1).substring(largeMatcher.group(1).length()-4, largeMatcher.group(1).length()).equals(".png")) {
        src = largeMatcher.group(1).substring(5);
      } else {
        String imageHtml = getHtml(largeMatcher.group(1));
        Pattern imagePattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
        Matcher imageMatcher = imagePattern.matcher(imageHtml);
        imageMatcher.find();
        src = imageMatcher.group(1).substring(5);
      }
    }

    // show image
    Image comicImage = new Image("http:" + src);
    ImageView comic = new ImageView(comicImage);
    Tooltip urlTextTooltip = new Tooltip(caption);
    Tooltip.install(comic, urlTextTooltip);
    ScrollPane comicPane = new ScrollPane();
    comicPane.setPrefSize(650, 400);
    comicPane.setContent(comic);
    root.setCenter(comicPane);
    Text titleText = new Text(id + ": " + title);
    StackPane top = new StackPane(titleText);
    top.setPrefHeight(50);
    root.setTop(top);
  }
}
