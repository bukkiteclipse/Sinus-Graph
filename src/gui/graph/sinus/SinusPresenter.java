package gui.graphics.sinus;

public class SinusPresenter
{

    private SinusView sinusView;

    private SinusModel sinusModel;

    public SinusPresenter(SinusModel sinusModel)
    {
        this.sinusModel = sinusModel;
    }

    public void setSinusView(SinusView sinusView)
    {
        this.sinusView = sinusView;
    }

    public SinusView getSinusView()
    {
        return this.sinusView;
    }

    public double sinusY(double x)
    {
        return sinusModel.sinusY(x);
    }

    public void updateValues(double amplitude, double frequency, double phase)
    {
        sinusModel.setAmplitude(amplitude);
        sinusModel.setFrequenz(frequency);
        sinusModel.setPhase(phase);

        sinusView.drawSinusFunction();
    }
}
