import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MemoryGameClient extends JFrame {

    private DataOutputStream toServer;
    private DataInputStream fromServer;
    private ButtonGroup difficulties;

    public static void main(String[] args) {
        new MemoryGameClient();
    }

    public MemoryGameClient() {

        JPanel menuPanel = new JPanel(new BorderLayout());
        JPanel singleSelection = new JPanel();
        JPanel multiSelection = new JPanel();
        JPanel difficultySelection = new JPanel();
        JLabel selectionLabel = new JLabel("Please select a difficulty");
        JLabel singlePlayer = new JLabel("Single Player");
        JLabel multiPlayer = new JLabel("Multi-Player");
        JRadioButton singleEasy = new JRadioButton("Easy");
        JRadioButton singleMedium = new JRadioButton("Medium");
        JRadioButton singleHard = new JRadioButton("Hard");
        JRadioButton multEasy = new JRadioButton("Easy");
        JRadioButton multMedium = new JRadioButton("Medium");
        JRadioButton multHard = new JRadioButton("Hard");
        JButton startGame = new JButton("Start Game");

        startGame.addActionListener(new GameListener());

        difficulties = new ButtonGroup();
        difficulties.add(singleEasy);
        difficulties.add(singleMedium);
        difficulties.add(singleHard);
        difficulties.add(multEasy);
        difficulties.add(multMedium);
        difficulties.add(multHard);
        singleEasy.setSelected(true);

        singleEasy.setActionCommand("singleEasy");
        singleMedium.setActionCommand("singleMedium");
        singleHard.setActionCommand("singleHard");
        multEasy.setActionCommand("multEasy");
        multMedium.setActionCommand("multMedium");
        multHard.setActionCommand("multHard");

        singleSelection.setLayout(new BoxLayout(singleSelection, BoxLayout.Y_AXIS));
        singleSelection.add(singlePlayer);
        singleSelection.add(singleEasy);
        singleSelection.add(singleMedium);
        singleSelection.add(singleHard);

        multiSelection.setLayout(new BoxLayout(multiSelection, BoxLayout.Y_AXIS));
        multiSelection.add(multiPlayer);
        multiSelection.add(multEasy);
        multiSelection.add(multMedium);
        multiSelection.add(multHard);

        difficultySelection.add(singleSelection);
        difficultySelection.add(multiSelection);

        selectionLabel.setHorizontalAlignment(JLabel.CENTER);
        menuPanel.add(selectionLabel, BorderLayout.NORTH);
        menuPanel.add(difficultySelection, BorderLayout.CENTER);
        menuPanel.add(startGame, BorderLayout.SOUTH);

        add(menuPanel);
        setTitle("Memory Game Client");
        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            Socket socket = new Socket("localhost", 8000);

            toServer = new DataOutputStream(socket.getOutputStream());
            fromServer = new DataInputStream(socket.getInputStream());


        }
        catch (IOException exc) {
            System.err.println(exc.toString());
        }
    }

    private class GameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String difficulty = difficulties.getSelection().getActionCommand();

            switch (difficulty) {
                case "singleEasy":
                    break;
                case "singleMedium":
                    break;
                case "singleHard":
                    break;
                case "multEasy":
                    break;
                case "multMedium":
                    break;
                case "multHard":
                    break;
            }
        }
    }


}
