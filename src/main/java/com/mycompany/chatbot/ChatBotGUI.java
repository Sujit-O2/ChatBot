package com.mycompany.chatbot;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChatBotGUI extends JFrame implements ActionListener {

    private ArrayList<String> historyList = new ArrayList<>();

    JButton send;
    JTextArea area;
    JTextField ff;
    JLabel welcomeLabel;

    public ChatBotGUI() {

        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        welcomeLabel = new JLabel("Welcome to Sujit's ChatBot", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(10, 5, 660, 30);

        area = new JTextArea("");
        area.setEditable(false);
        area.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane jt = new JScrollPane(area);
        jt.setBounds(10, 40, 660, 560);

        ff = new JTextField();
        ff.setBounds(10, 620, 500, 30);

        send = new JButton("SEND");
        send.setBounds(530, 620, 100, 30);

        add(welcomeLabel);
        add(jt);
        add(ff);
        add(send);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        send.addActionListener(this);

        ff.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    
                    sendMessage();
                }
            }
        });

        // First bot message
        area.append("BOT 🤖 : Hi, how can I help you? 😊\n\n");
        historyList.add("BOT: Hi, how can I help you?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == send) {
            sendMessage();
        }
    }

    private void sendMessage() {

        String userText = ff.getText().trim();
        ff.setText("");

        if (userText.isEmpty()) {
            return;
        }

        // Print user message
        area.append("\nYOU 🐦‍🔥 : " + userText + "\n\n");
        area.append("BOT 🤖 : typing...\n");
        area.setCaretPosition(area.getDocument().getLength());

        // Store user message in history
        historyList.add("USER: " + userText+"\n");
        if (historyList.size() > 20) historyList.remove(0);
        if(userText.toLowerCase().contains("new chat")){
            historyList.clear();
        }

        String chatHistory = String.join("\n", historyList);

        new Thread(() -> {

            chatbotUrl bot = new chatbotUrl();

            // Send history + user message to backend
            String botReply = bot.chatbotStream("prev:"+chatHistory+"New Chat:"+userText,area);

            historyList.add("BOT: " + botReply);
            if (historyList.size() > 20) historyList.remove(0);

            SwingUtilities.invokeLater(() -> {

                String text = area.getText().replace("BOT 🤖 : typing...\n", "BOT 🤖 : ");
                area.setText(text);

                area.append(botReply+"\n\n");
                area.setCaretPosition(area.getDocument().getLength());

            });

        }).start();
    }
}
