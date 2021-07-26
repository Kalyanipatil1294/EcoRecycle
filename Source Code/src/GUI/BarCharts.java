package GUI;

import javax.swing.*;
import java.awt.*;

public class BarCharts extends JPanel {

    private double[] value;
    private String[] machineLocations;
    private String title = "RCM Usage Statistics";

    public BarCharts(double[] val, String[] locations) {
        machineLocations = locations;
        value = val;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (value == null || value.length == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < value.length; i++) {
            if (minValue > value[i])
                minValue = value[i];
            if (maxValue < value[i])
                maxValue = value[i];
        }
        Dimension dim = getSize();
        int clientWidth = dim.width - 200;
        int clientHeight = dim.height;
        int barWidth = (clientWidth / value.length)/2;
        Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);
        FontMetrics titleFontMetrics = graphics.getFontMetrics(titleFont);
        Font labelFont = new Font("Book Antiqua", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = graphics.getFontMetrics(labelFont);
        int titleWidth = titleFontMetrics.stringWidth(title);
        int q = titleFontMetrics.getAscent();
        int p = (clientWidth - titleWidth) / 2;
        graphics.setFont(titleFont);
        graphics.drawString(title, p+10, q);
        int top = titleFontMetrics.getHeight()*3;
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        graphics.setFont(labelFont);
        for (int j = 0; j < value.length; j++) {
            int valueP = j * 2 * barWidth + j + 100;
            int valueQ = top;
            int height = (int) (value[j] * scale)*3;
            if (value[j] >= 0)
                valueQ += (int) ((maxValue - value[j]) * scale);
            else {
                valueQ += (int) (maxValue * scale);
                height = -height;
            }
            graphics.setColor(Color.gray);
            graphics.fillRect(valueP, valueQ, barWidth, height);
            int labelWidth = labelFontMetrics.stringWidth(machineLocations[j])/2;
            p = j * 2* barWidth + 30 + (barWidth*2 - labelWidth);
            q = clientHeight - labelFontMetrics.getDescent();
            graphics.setColor(Color.black);
            graphics.drawString(machineLocations[j], p, q);
        }
    }
}
