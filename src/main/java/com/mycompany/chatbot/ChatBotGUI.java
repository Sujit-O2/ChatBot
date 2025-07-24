/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatbot;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 *
 * @author sujit
 */
public class ChatBotGUI extends JFrame implements ActionListener{
    JButton send;
    JTextArea area;
    JTextField ff;
    
    
    public ChatBotGUI(){
        setSize(700,700);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        area=new JTextArea();
        area.setEditable(false);
        ff=new JTextField();
        area.setFont(new Font("",Font.BOLD,14));
        send=new JButton("SEND");
        JScrollPane jt=new JScrollPane(area);
        jt.setBounds(10,10,660,600);
        ff.setBounds(10,620,500,30);
        jt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        send.setBounds(530,620,100,30);
        
        this.add(jt);
        this.add(ff);
        this.add(send);
        
        
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        send.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==send){
            Timer tt=new Timer(1000, this);
            String s=ff.getText();
            ff.setText("");
            area.append("\nYOU : "+s+"\n\n");
            area.repaint();
            

          chatbotUrl uu=new chatbotUrl();
          String res=uu.chatbotUrl1(s);
          
          
          area.append("BOT : "+res+"\n");
          area.setCaretPosition(area.getDocument().getLength());
          
            
       }
      
    
    }
    
}
