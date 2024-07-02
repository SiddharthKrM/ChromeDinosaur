// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


import javax.swing.*;
public class App {
    public static void main(String[] args) {
        //
        int boardwidth=750;
        int boardheight=250;
        JFrame frame=new JFrame("ChromeDinosor");
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChromeDinosour chromeDinosour=new ChromeDinosour();
        frame.add(chromeDinosour);
        frame.pack();
        chromeDinosour.requestFocus();
        frame.setVisible(true);
        //
    }
}