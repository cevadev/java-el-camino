/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceva.javaelcamino.watch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author PC
 */
public class Watch6 extends JPanel {
    private final Color backgroundColor = new Color(0x404c0); // color de fondo
    private final Color hourColor = Color.BLACK; // color manecilla de la hora
    private final Color minuteColor = Color.BLACK; // color manecilla minuto
    private final Color secondColor = new Color(0xa00000); // color manecilla segundo
    private final Color faceColor = new Color(0xc0c0c0); // Color de la caratula
    private final Color labelColor = Color.BLACK; // color del marco del reloj
    private final Color gradientA = new Color(224, 224, 224, 255);
    private final Color gradientB = new Color(32, 32, 32, 144);
    
    private Font fSmall;
    private Font fMedium;
    private Font fLarge;
    
    private int hour;
    private int minute;
    private int second;
    
    private int hourStroke = 12;
    private int minuteStroke = 5;
    private int secondStroke = 3;
    
    private javax.swing.Timer timer;
    
    // implementando el timer
    public Watch6(){
        super();
        // actualizamos la hora
        updateTime();
        // set up el timer cada segundo
        timer = new javax.swing.Timer(1000, (e)->{
            // actulizamos la hora y redibujamos el reloj
            updateTime();
            repaint();
        });
        timer.start();
    }
    
    private void stopTimer(){
        timer.stop();
    }
    
    private Font getSmallFont(Graphics2D g2d){
        if(fSmall != null){
            return fSmall;
        }
        fSmall = g2d.getFont().deriveFont(24f);
        return fSmall;
    }
    
    private Font getMediumFont(Graphics2D g2d){
        if(fMedium != null){
            return fMedium;
        }
        fMedium = g2d.getFont().deriveFont(36f);
        return fMedium;
    }
    
    private Font getLargeFont(Graphics2D g2d){
        if(fLarge != null){
            return fLarge;
        }
        fLarge = g2d.getFont().deriveFont(72f);
        return fLarge;
    }
    
    // metodo para saber/obtener la hora
    private void updateTime(){
        // obtenemos instancia de Calendar
        Calendar cal = Calendar.getInstance();
        // retornamos hora minto segundo
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }
    
    // Tamanio de letra (chico, mediano , grande)
    @Override
    protected void paintComponent(Graphics g) {
        // forma del reloj: es un circulo responsivo
        Rectangle bounds = getBounds(); // obtenemos Jpanel size
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(backgroundColor); // color de fondo
        g2d.fillRect(0, 0, bounds.width, bounds.height); // pintamos el fondo

        /**
         * Conforme se redimensiona la pantalla se forma un ovalo pero el reloj
         * siempre debe ser un circulo para determinar el tamanio el ancho debe
         * ser igual al alto
         */
        int size = Math.min(bounds.width, bounds.height) - 1;// obtenemos el minimo valor entre bounds.width y bounds.height

        /**
         * bounds.width/2 - size/2 -> obtenemos la coordenada x centrada
         * bounds.height/2 - size/2 -> obtenemos la coordenada y centrada size
         * -> with size -> height frame -> frame que describe al circulo
         */
        Rectangle frame = new Rectangle(bounds.width / 2 - size / 2, bounds.height / 2 - size / 2, size, size);
        g2d.setColor(faceColor); // color de linea
        g2d.fillOval(frame.x, frame.y, frame.width, frame.height); // dibujamos el circulo en le medio
        
        Stroke simpleStroke = g2d.getStroke();
        Stroke boldStroke = new BasicStroke(2);
        g2d.setColor(Color.BLACK);
        /**
         * Dibujo de lineas para horas, minutos y segundos
         */
        // obtenemos el centro del circulo.
        int centerX = frame.x + frame.width / 2;
        int centerY = frame.y + frame.height / 2;
        double radius = frame.width / 2 - 10; // radio del circulo, con un marge de 5
        double innerRadius = radius * 0.9; // definimos un radio interno que es igual al 90% del radio
        
        // gradiente
        Paint savePaint = g2d.getPaint();
        RadialGradientPaint rp = new RadialGradientPaint(
                // definimos un punto coordenada x que se encuentra en e centro del relos
                (float)(centerX - Math.cos(Math.toRadians(45)) * radius),
                // definimos un punto coordenada y que se encuentr en el centro del reloj
                (float)(centerY - Math.sin(Math.toRadians(45)) * radius),
                // 
                (float)radius * 3.5f,
                new float[] {0.0f, 0.5f},
                new Color[] {gradientA, gradientB});
        // end gradiente
        
        g2d.setPaint(rp);
        // pintamos utilizando el gradiente definido
        g2d.fillOval(frame.x + 4, frame.y + 4, frame.width - 8, frame.height - 8);
        g2d.setPaint(savePaint);
        // color para el marco del reloj
        g2d.setColor(labelColor);
        // dibujamos un marco grueso para el reloj
        g2d.setStroke(new BasicStroke(10));
        g2d.drawOval(frame.x+4, frame.y+4, frame.width-8, frame.height-8);
        g2d.setStroke(simpleStroke);

        // Letra de tamanio 36
//        g2d.setFont(g2d.getFont().deriveFont(36f));

        // establecemos el tamanio del font, de acuerdo al tamanio en pixeles del frame
        Font f;
        if(frame.width < 400){
            f = getSmallFont(g2d);
        }else if(frame.width < 800){
            f = getMediumFont(g2d);
        }else{
            f = getLargeFont(g2d);
        }
        g2d.setFont(f);
        FontMetrics fm = g2d.getFontMetrics();

        
        double angle;
        for (int n = 0; n < 60; n++) {
            // angulo que tiene cada segundo 360/60
            angle = (270 + n * (360 / 60)) % 360.0; // mod 360 nos permite un rango entre 0 y 360
            double coseno = Math.cos(Math.toRadians(angle));// calculamos el coseno de angulo pero primero lo convertimos a radianes
            double seno = Math.sin(Math.toRadians(angle));// calculamos el seno del a angulo
            // si n es divisible por 5 significa que estoy en la marca de una division de hora
            if ((n % 5) == 0) {
                // dibujamos la lineas de hora
                // cuando es 1 hora, el radio es 0.9 del radius
                innerRadius = radius * 0.9;

                // dibujamos la hora
                int h = n / 5;
                // validamos si estamos en as 12
                if (h == 0) {
                    h = 12;
                }
                // calculamos los limites del numero a dibujar
                Rectangle2D r = fm.getStringBounds(String.valueOf(h), g2d);
                // trasladamos hacia el angulo que queremos dibujar
                int x = (int) (-r.getWidth() / 2 + radius * coseno * 0.8);
                int y = (int) (r.getHeight() / 2 - fm.getDescent() + radius * seno * 0.8);
//                    g2d.drawRect(centerX + x, centerY - y - fm.getDescent(), (int) r.getWidth(), (int) r.getHeight());
                g2d.drawString(String.valueOf(h), centerX + x, centerY + y);
                // un punto en el centro del reloj
//                g2d.fillOval(centerX - 2, centerY - 2, 4, 4);
                // fin dibujamos la hora

                g2d.setStroke(boldStroke);
            } else {
                // dibujamos la lineas de minutos
                // cuando es 1 minuto, el radio es 0.95 del radius
                innerRadius = radius * 0.95;
                g2d.setStroke(simpleStroke);
            }

            // dibujamos la linea
            g2d.drawLine((int) (centerX + coseno * innerRadius), (int) (centerY + seno * innerRadius),
                    (int) (centerX + coseno * radius), (int) (centerY + seno * radius));
        }
        // lineas para las manecillas
        double hourRadius = radius * 0.5;
        double minRadius = radius * 0.7;
        double secRadius = radius * 0.9;
        // manecilla de horas
        g2d.setStroke(new BasicStroke(hourStroke));
        g2d.setColor(hourColor);
        angle = (270 + hour * (360.0 / 12) + minute * (360.0 / 12 / 60)) % 360.0;
        g2d.drawLine(centerX, centerY, (int)(centerX + Math.cos(Math.toRadians(angle)) * hourRadius), 
                                       (int)(centerY + Math.sin(Math.toRadians(angle)) * hourRadius));
        // manecilla de minutos
        angle = (270 + minute * (360 / 60)) % 360.0;
        g2d.setColor(minuteColor);
        g2d.setStroke(new BasicStroke(minuteStroke));
        g2d.drawLine(centerX, centerY, (int)(centerX + Math.cos(Math.toRadians(angle)) * minRadius), 
                                       (int)(centerY + Math.sin(Math.toRadians(angle)) * minRadius));
        // manecilla de segundos
        angle = (270 + second * (360 / 60)) % 360.0;
        g2d.setColor(secondColor);
        g2d.setStroke(new BasicStroke(secondStroke));
        g2d.drawLine(centerX, centerY, (int)(centerX + Math.cos(Math.toRadians(angle)) * secRadius), 
                                       (int)(centerY + Math.sin(Math.toRadians(angle)) * secRadius));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Watch6 mainPanel = new Watch6();
            JFrame frame = new JFrame();
            frame.setTitle("Watch 6");
            frame.setMinimumSize(new Dimension(400, 400));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            // cuando se cierra la ventana, terminamos el timer
            frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e) {
                    mainPanel.stopTimer();
                }
            });
            
            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}