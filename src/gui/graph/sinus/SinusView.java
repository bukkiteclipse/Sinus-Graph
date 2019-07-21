package gui.graph.sinus;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class SinusView extends VBox
{

    private SinusPresenter sinusPresenter;

    private Pane drawPane;

    private int drawPaneW = 1280;

    private int drawPaneH = 600;

    private Label sinusFunctionLabel;

    private Label zoomPercentLabel;

    private Slider ampSlider;

    private Slider freqSlider;

    private Slider phaseSlider;

    private Slider zoomSlider;

    private double strokeWidth;

    private Path sinusFunctionPath;

    private double amplitude;

    private double frequency;

    private double phase;

    private double zoom;

    private Rectangle clipRect;

    public SinusView(SinusPresenter sinusPresenter)
    {
        this.sinusPresenter = sinusPresenter;
    }

    public void initView()
    {
        drawPane = new Pane();
        drawPane.setMinSize(drawPaneW, drawPaneH);
        drawPane.setPrefWidth(drawPaneW);
        drawPane.setPrefHeight(drawPaneH);
        GridPane sliderPane = new GridPane();
        sinusFunctionLabel = new Label();
        sinusFunctionLabel.setFont(new Font("Arial", 40));
        zoomPercentLabel = new Label();
        zoomPercentLabel.setFont(new Font("Arial", 20));

        drawPane.getChildren().add(new Line(0, drawPaneH * 0.5, drawPaneW, drawPaneH * 0.5));
        drawPane.getChildren().add(new Line(drawPaneW * 0.5, 0, drawPaneW * 0.5, drawPaneH));
        clipRect = new Rectangle();
        clipRect.widthProperty().bind(drawPane.widthProperty());
        clipRect.heightProperty().bind(drawPane.heightProperty());
        drawPane.setClip(clipRect);

        Label ampLabel = new Label("Amplitude:  ");
        ampSlider = new Slider(-6, 6, 1);
        ampSlider.setId("amplitude");
        ampSlider.setPrefWidth(1100);
        ampSlider.setShowTickLabels(true);
        ampSlider.setShowTickMarks(true);
        ampSlider.setMajorTickUnit(2);
        ampSlider.setMinorTickCount(1);
        ampSlider.setBlockIncrement(1);
        Label freqLabel = new Label("Frequenz:  ");
        freqSlider = new Slider(0, 40, 1);
        freqSlider.setId("frequency");
        freqSlider.setPrefWidth(1100);
        freqSlider.setShowTickLabels(true);
        freqSlider.setShowTickMarks(true);
        freqSlider.setMajorTickUnit(10);
        freqSlider.setMinorTickCount(5);
        freqSlider.setBlockIncrement(1);
        Label phaseLabel = new Label("Phase:  ");
        phaseSlider = new Slider(-10, 10, 0);
        phaseSlider.setId("phase");
        phaseSlider.setPrefWidth(1100);
        phaseSlider.setShowTickLabels(true);
        phaseSlider.setShowTickMarks(true);
        phaseSlider.setMajorTickUnit(5);
        phaseSlider.setMinorTickCount(1);
        phaseSlider.setBlockIncrement(1);
        Label zoomLabel = new Label("Zoom:  ");
        zoomSlider = new Slider(10, 210, 100);
        zoomSlider.setId("zoom");
        zoomSlider.setPrefWidth(1100);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(100);
        zoomSlider.setMinorTickCount(40);
        zoomSlider.setBlockIncrement(1);
        ampLabel.setFont(new Font("Arial", 30));
        freqLabel.setFont(new Font("Arial", 30));
        phaseLabel.setFont(new Font("Arial", 30));
        zoomLabel.setFont(new Font("Arial", 30));
        sliderPane.add(ampLabel, 0, 0);
        sliderPane.add(ampSlider, 1, 0);
        sliderPane.add(freqLabel, 0, 1);
        sliderPane.add(freqSlider, 1, 1);
        sliderPane.add(phaseLabel, 0, 2);
        sliderPane.add(phaseSlider, 1, 2);
        sliderPane.add(zoomLabel, 0, 3);
        sliderPane.add(zoomSlider, 1, 3);

        getChildren().add(sinusFunctionLabel);
        getChildren().add(zoomPercentLabel);
        getChildren().add(drawPane);
        getChildren().add(sliderPane);

        sinusFunctionPath = new Path();
        sinusFunctionPath.setStroke(Color.RED);
        strokeWidth = 3;
        sinusFunctionPath.setStrokeWidth(strokeWidth);
        sinusFunctionPath.setStrokeType(StrokeType.CENTERED);
        sinusFunctionPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
        sinusFunctionPath.setStrokeLineCap(StrokeLineCap.ROUND);

        drawPane.getChildren().add(sinusFunctionPath);

        amplitude = ampSlider.getValue();
        frequency = freqSlider.getValue();
        phase = phaseSlider.getValue();
        zoom = zoomSlider.getValue();

        ampSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            amplitude = ampSlider.getValue();
            sendSliderValues();
        });

        freqSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            frequency = freqSlider.getValue();
            sendSliderValues();
        });

        phaseSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            phase = phaseSlider.getValue();
            sendSliderValues();
        });

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            zoom = zoomSlider.getValue();
            sendSliderValues();
        });

        sendSliderValues();
        drawSinusFunction();
    }

    public void drawSinusFunction()
    {
        sinusFunctionPath.getElements().clear();
        strokeWidth = (zoom / 25);
        sinusFunctionPath.setStrokeWidth(strokeWidth);
        for (int x = 0; x <= drawPaneW; x++)
        {
            if (x == 0)
            {
                int helpX = x;
                helpX = x - (int) (drawPaneW * 0.5);
                sinusFunctionPath.getElements().add(new MoveTo(x, getSinusY(helpX)));
            }
            else
            {
                int helpX = x;
                helpX = x - (int) (drawPaneW * 0.5);
                sinusFunctionPath.getElements().add(new LineTo(x, getSinusY(helpX)));
            }
        }

        updateSinusFunctionLabel();

    }

    public double roundDouble(double d)
    {
        double e = d;
        e = 10000 * e;
        e = Math.round(e);
        e = e / 10000;
        return e;
    }

    public void updateSinusFunctionLabel()
    {
        double a = roundDouble(amplitude);
        double f = roundDouble(frequency);
        double p = roundDouble(phase);
        double z = roundDouble(zoom);

        String s = "f(x)= " + a + " * sin(" + f + " * x + (" + p + "))";
        sinusFunctionLabel.setText(s);

        String t = "Zoom: " + z + "%";
        zoomPercentLabel.setText(t);
    }

    public double getSinusY(double x)
    {
        return (drawPaneH * 0.5 - (zoom * 0.01) * 50 * sinusPresenter.sinusY((1 / (zoom * 0.01)) * 0.017 * x));

    }

    public void sendSliderValues()
    {
        sinusPresenter.updateValues(amplitude, frequency, phase);
    }
}
