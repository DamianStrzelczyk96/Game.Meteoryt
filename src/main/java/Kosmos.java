import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Kosmos extends JPanel {

    class Meteoryt{
        int x;
        int y;
        int size;
        int dx = 1;
        int dy= 1;

        public void ruch(){
            x += dx;
            y += dy;
        }
        public boolean czyWidoczny() {
            return x + size>=0 && y + size >= 0 && x < 1000 && y <1000;
        }

        public Meteoryt(){
            Random r = new Random();
            size = (r.nextInt(10)+1)*10;
            int kierunek = r.nextInt(4);
            if(kierunek == 0 ) {
                x =0;
                y = r.nextInt(1000);

                dx = r.nextInt(15) + 1;
                dy = r.nextInt(15) -7;
            }else if(kierunek == 1){

                y=0;
                x = r.nextInt(1000);

                dx = r.nextInt(15)-7;
                dy = r.nextInt(15)+1;

            }else if(kierunek ==2){
                y = 1000;
                x = r.nextInt(1000);

                dx = r.nextInt(15)-7;
                dy = r.nextInt(15)-7;
            }else if (kierunek == 3){
                x=1000;
                y = r.nextInt(1000);
                dx = r.nextInt(15)-7;
                dy = r.nextInt(15)-7;
            }
        }
    }


    int x = 10;
    int y = 10;
    int pkt = 0;

    ArrayList<Meteoryt> meteoryty = new ArrayList<Meteoryt>();

    public boolean zderzenie ( int mouseX, int mouseY) {
        Meteoryt[] meteorytTab = new Meteoryt[meteoryty.size()];
        meteoryty.toArray(meteorytTab);
        for(Meteoryt meteor : meteorytTab) {
            if(!meteor.czyWidoczny())
                meteoryty.remove(meteor);
            if(meteor.x <= mouseX && meteor.y <= mouseY && meteor.x + meteor.size >= mouseX && meteor.y + meteor.size >= mouseY)
                return true;
        }
        return false;
    }

    public Kosmos(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

        }

            @Override
            public void mouseMoved(MouseEvent e) {

                if(zderzenie(e.getX(),e.getY())){
                    JOptionPane.showMessageDialog(null, String.format("Zdobyłeś : %d punkty", (int)pkt));
                    pkt = 0;
                    meteoryty.clear();
                    repaint();
                }

                    System.out.println("BOOOM");
                while(x != e.getX() || y != e.getY()){
                    x += e.getX() - x;
                    y += e.getY() - y;
                    repaint();

                    pkt +=1;
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e1){
                        e1.printStackTrace();
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Thread.sleep(250);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    meteoryty.add(new Meteoryt());
                }
            }
        }).start();

    }

    public static BufferedImage resize(BufferedImage img, int height , int width){
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage skalowanie = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = skalowanie.createGraphics();
        g2d.drawImage(tmp , 0,0,null);
        g2d.dispose();
        return skalowanie;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("tlo.png")), 0,0,null);
            g.drawImage(ImageIO.read(new File("ufo.png")),x-25, y-25 , null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD,20));
        g.drawString("SCORE: "+pkt+"",850,30);
        Meteoryt[] meteorytTab = new Meteoryt[meteoryty.size()];
        meteoryty.toArray(meteorytTab);

        for(Meteoryt meteor : meteorytTab) {
            meteor.ruch();
            try {
                g.drawImage(resize((BufferedImage)ImageIO.read(new File("meteor.png")),meteor.size,meteor.size),meteor.x,meteor.y,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }

    public static void main(String[] args) {
        JFrame okno = new JFrame("Kosmos");

        Kosmos kosmos = new Kosmos();
        okno.add(kosmos);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();
    }
}
