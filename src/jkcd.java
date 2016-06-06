import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.Pos.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class jkcd extends Application {
  static BorderPane root = new BorderPane();
  static int imageId, lastId = -1, currentIndex = 0;
  static ArrayList<Integer> history = new ArrayList<Integer>();
  static Stage stage;
  static boolean regularSize = true;
  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage primaryStage) {
    // window title and root element
    primaryStage.setTitle("jkcd - the Java xkcd client");
    stage = primaryStage;

    // buttons (and other control elements)! 
    Button nextButton = new Button(">>");
    Tooltip nextButtonTooltip = new Tooltip("Next Comic");
    Tooltip.install(nextButton, nextButtonTooltip);
    nextButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentIndex++;
        setImage((imageId == lastId) ? 1 : imageId+1);
      }
    });
    Button lastButton = new Button(">|");
    Tooltip lastButtonTooltip = new Tooltip("Last Comic");
    Tooltip.install(lastButton, lastButtonTooltip);
    lastButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentIndex++;
        setImage(lastId);
      }
    });
    Button forwardButton = new Button("->"); 
    Tooltip forwardButtonTooltip = new Tooltip("Forward");
    Tooltip.install(forwardButton, forwardButtonTooltip);
    forwardButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(history.size()-1 > currentIndex)
          setImage(history.get(++currentIndex));
      }
    });
    VBox rightPane = new VBox(100, nextButton, lastButton, forwardButton);
    rightPane.setAlignment(CENTER);
    Button previousButton = new Button("<<");
    Tooltip previousButtonTooltip = new Tooltip("Previous Comic");
    Tooltip.install(previousButton, previousButtonTooltip);
    previousButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentIndex++;
        setImage(imageId-1);
      }
    }); 
    Button firstButton = new Button("|<");
    Tooltip firstButtonTooltip = new Tooltip("First Comic");
    Tooltip.install(firstButton, firstButtonTooltip);
    firstButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentIndex++;
        setImage(1);
      }
    });
    Button backButton = new Button("<-");
    Tooltip backButtonTooltip = new Tooltip("Back");
    Tooltip.install(backButton, backButtonTooltip);
    backButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(currentIndex != 0)
          setImage(history.get(--currentIndex));
      }
    });
    VBox leftPane = new VBox(100, previousButton, firstButton, backButton);
    leftPane.setAlignment(CENTER);
    TextField inputId = new TextField();
    inputId.setPrefWidth(50);
    inputId.setPromptText("ID");
    inputId.setAlignment(CENTER);
    Button getId = new Button(":#");
    Tooltip getIdTooltip = new Tooltip("Goto comic with given ID");
    Tooltip.install(getId, getIdTooltip);
    getId.setPrefWidth(50);
    getId.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          int id = Integer.parseInt(inputId.getText());
          if(id <= -1 || id > lastId)
            throw new Exception();
          currentIndex++;
          setImage(id);
        } catch(Exception e) {}
        inputId.setText("");
      }
    });
    HBox inputPane = new HBox(inputId, getId);
    inputPane.setAlignment(CENTER);
    Button random = new Button(":?");
    Tooltip randomButtonTooltip = new Tooltip("Get random comic");
    Tooltip.install(random, randomButtonTooltip);
    random.setPrefWidth(75);
    random.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentIndex++;
        setImage((int) Math.floor(Math.random() * (lastId + 1)));
      }
    });
    Button toggleSizeButton = new Button("<>");
    toggleSizeButton.setPrefWidth(75);
    Tooltip toggleSizeButtonTooltip = new Tooltip("Toggle regular-size/fit-to-size option for comic");
    Tooltip.install(toggleSizeButton, toggleSizeButtonTooltip);
    toggleSizeButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        regularSize = !regularSize;
        setImage(imageId);
      }
    });
    HBox bottomPane = new HBox(50, random, inputPane, toggleSizeButton);
    bottomPane.setAlignment(CENTER);

    // set up layout
    StackPane top = new StackPane();
    top.setPrefHeight(50);
    root.setTop(top);
    StackPane left = new StackPane(leftPane);
    left.setPrefWidth(75);
    root.setLeft(left);
    StackPane right = new StackPane(rightPane);
    right.setPrefWidth(75);
    root.setRight(right);
    StackPane bottom = new StackPane(bottomPane);
    bottom.setPrefHeight(50);
    root.setBottom(bottom);
    Scene scene = new Scene(root, 750, 500);
    primaryStage.setScene(scene);
    scene.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number oldv, Number newv) {
        setImage(imageId);
      }
    });
    scene.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> ov, Number oldv, Number newv) {
        setImage(imageId);
      }
    });
    scene.getStylesheets().clear();
    scene.getStylesheets().add("style.css");
    primaryStage.show();

    // get current image
    setImage(0);

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
    String html = getHtml((id == 0) ? "http://www.xkcd.com/info.0.json" : "http://www.xkcd.com/" + id + "/info.0.json");
    Pattern infoPattern = Pattern.compile(
      "\"num\": ([0-9]+).+" + 
      "\"link\": \"([^\"]*)\".+" + 
      "\"safe_title\": \"([^\"]+)\".+" + 
      "\"alt\": \"([^\"]+)\".+" + 
      "\"img\": \"([^\"]+)\""
    );
    Matcher infoMatcher = infoPattern.matcher(html);
    infoMatcher.find();
    String link = infoMatcher.group(2).replaceAll("\\\\/", "/");
    String title = infoMatcher.group(3);
    String caption = infoMatcher.group(4).replaceAll("&#39;", "'");
    String src = infoMatcher.group(5).replaceAll("\\\\/", "/");
    try {
      id = Integer.parseInt(infoMatcher.group(1));
    } catch(NumberFormatException e) {}
    if(lastId == -1) {
      lastId = id;
      history.add(id);
    }
    imageId = id;
    if(currentIndex == history.size())
      history.add(id);
    else if(history.get(currentIndex) != id) {
      for(int i = history.size()-1; i >= currentIndex; i--)
        history.remove(i);
      history.add(id);
    }
    Pattern largePattern = Pattern.compile("(\\.png|large/)$");
    Matcher largeMatcher = largePattern.matcher(link);
    if(largeMatcher.find()) {
      if(link.substring(link.length()-4, link.length()).equals(".png")) {
        src = largeMatcher.group();
      } else {
        String imageHtml = getHtml(link);
        Pattern imagePattern = Pattern.compile("<img[^>]+src=\"([^\"]+)\"");
        Matcher imageMatcher = imagePattern.matcher(imageHtml);
        imageMatcher.find();
        src = imageMatcher.group(1);
      }
    }

    // show image
    double prefHeight = root.getHeight() - 100;
    double prefWidth = root.getWidth() - 150;
    Image comicImage = (regularSize) ? new Image(src) : new Image(src, prefWidth, prefHeight, true, false);
    ImageView comic = new ImageView(comicImage);
    Tooltip urlTextTooltip = new Tooltip(caption);
    urlTextTooltip.setPrefWidth(300);
    urlTextTooltip.setWrapText(true);
    Tooltip.install(comic, urlTextTooltip);
    Region comicPane;
    double imgHeight = comicImage.getHeight();
    double imgWidth = comicImage.getWidth();
    if(imgWidth > prefWidth || imgHeight > prefHeight) {
      if(imgWidth > prefWidth && imgHeight > prefHeight) {
        comicPane = new ScrollPane(comic);
      } else if(imgWidth > prefWidth) {
        ScrollPane innerPane = new ScrollPane(comic);
        innerPane.setMinWidth(prefWidth);
        innerPane.setPrefWidth(prefWidth);
        innerPane.setMaxWidth(prefWidth);
        innerPane.setMinHeight(imgHeight+2);
        innerPane.setPrefHeight(imgHeight+2);
        innerPane.setMaxHeight(imgHeight+2);
        comicPane = new StackPane(innerPane);
      } else {
        ScrollPane innerPane = new ScrollPane(comic);
        innerPane.setMinWidth(imgWidth+2);
        innerPane.setPrefWidth(imgWidth+2);
        innerPane.setMaxWidth(imgWidth+2);
        innerPane.setMinHeight(prefHeight);
        innerPane.setPrefHeight(prefHeight);
        innerPane.setMaxHeight(prefHeight);
        comicPane = new StackPane(innerPane);
      }
    } else
      comicPane = new StackPane(comic);
    comicPane.setMinHeight(prefHeight);
    comicPane.setPrefHeight(prefHeight);
    comicPane.setMaxHeight(prefHeight);
    comicPane.setMinWidth(prefWidth);
    comicPane.setPrefWidth(prefWidth);
    comicPane.setMaxWidth(prefWidth);
    root.setCenter(comicPane);
    Text titleText = new Text(id + ": " + title);
    StackPane top = new StackPane(titleText);
    top.setPrefHeight(50);
    root.setTop(top);
  }
}
