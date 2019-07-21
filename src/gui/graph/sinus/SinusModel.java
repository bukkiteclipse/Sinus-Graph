package gui.graph.sinus;

public class SinusModel
{
    private double amplitude = 1;

    private double frequency = 1;

    private double phase = 0;

    public double sinusY(double x)
    {
        return (amplitude * Math.sin(frequency * x + phase));
    }

    public void setAmplitude(double amplitude)
    {
        this.amplitude = amplitude;
    }

    public void setFrequenz(double frequenz)
    {
        this.frequency = frequenz;
    }

    public void setPhase(double phase)
    {
        this.phase = phase;
    }

}
