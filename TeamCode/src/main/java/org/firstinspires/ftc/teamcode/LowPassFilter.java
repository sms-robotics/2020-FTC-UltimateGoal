package org.firstinspires.ftc.teamcode;

public class LowPassFilter {
    private double lastOutputValue = Double.NaN;
    private long lastInputTimestamp;
    private final double rc;

    public LowPassFilter(double rc) {
        this.rc = rc;
    }

    public double addSample(double inputValue, long inputTimestamp) {
        if (Double.isNaN(lastOutputValue)) {
            lastOutputValue = inputValue;
            lastInputTimestamp = inputTimestamp;

            return inputValue;
        }

        double dt = inputTimestamp - lastInputTimestamp;
        double rc = 1000;
        double alpha = Math.max(0, Math.min(1, ((double)dt) / (dt + rc)));

        double outputValue = (inputValue * alpha) + ((1-alpha) * lastOutputValue);

        lastOutputValue = outputValue;
        lastInputTimestamp = inputTimestamp;

        return outputValue;
    }

    public double getValue() {
        return lastOutputValue;
    }
}
