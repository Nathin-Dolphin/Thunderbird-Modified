
/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file and the Thunderbird application is licensed under the 
 * BSD-3-Clause.
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 * This application utilizes the HttpRequest.java library developed by 
 * Eric Pogue
 * 
 * Version: 1.3
 *****************************************************************************/

// Modified from Original
// Modifier: Nathin Wacher
// Modified Version: 1.0

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

class ContactTile extends JPanel {
    private Color tileColor = Color.LIGHT_GRAY;
    private ThunderbirdContact contactInSeat = null;
    private boolean isSeat = false;

    public void setTileColor() {
        tileColor = Color.BLACK;
        isSeat = true;
    }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // NW - Fully implemented.
        // Todo: Implement visually appealing colors for aisles and seats.
        // NW - Fully implemented.
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();
        contactInSeat = contactInSeatIn;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        g.setColor(tileColor);
        g.fillRect(0, 0, panelWidth, panelHeight);

        if (!isSeat) {
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, panelWidth, panelHeight);
        }

        final int fontSize = 18;
        g.setColor(Color.GREEN);
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int stringX = (panelWidth / 5);
        int stringY = (panelHeight / 3) + 5;
        if (contactInSeat != null) {

            // ToDo: Display preferred name instead of first and last name.
            // NW - Fully implemented.
            String preferredName;
            String location;

            if (isSeat)
                location = "  Seat: " + contactInSeat.getSeat();
            else {
                g.setColor(Color.RED);
                location = "  Aisle: " + contactInSeat.getSeat();
            }

            if (contactInSeat.hasPreferredName())
                preferredName = contactInSeat.getPreferredName();
            else
                preferredName = contactInSeat.getFirstName() + " " + contactInSeat.getLastName();

            g.drawString(preferredName, stringX, stringY);

            stringY = (2 * panelHeight / 3) + 5;
            g.drawString(location, stringX, stringY);
        }
    }
}

class ThunderbirdFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList, reversedList;
    private ThunderbirdModel tbM;
    private JPanel contactGridPanel;

    private boolean reverse = true;

    public ThunderbirdFrame() {
        setTitle("Thunderbird");
        setBounds(350, 50, 1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton reverseView = new JButton("Reverse View");
        buttonPanel.add(reverseView);
        reverseView.addActionListener(this);

        contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);

        contactGridPanel.setLayout(new GridLayout(11, 9));

        tbM = new ThunderbirdModel();
        tbM.LoadIndex();
        tbM.LoadContactsThreaded();

        // Todo: Review ThunderbirdModel in detail and implement a multithreaded version
        // of loading contacts.
        // Hint: Review LoadContact() and LoadContactsThreaded() in detail.
        // [!] WORK IN PROGRESS [!]

        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();

        tileList = new ArrayList<ContactTile>();

        int h = 0;
        for (int i = 0; i < 99; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);
            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat);

            // Todo: Place all the aisle seats in a array or an ArrayList instead of hard
            // coding them.
            // NW - Fully implemented.
            final int[] seatNum = { 11, 13, 14, 16, 17, 20, 25, 26, 29, 31, 32, 34, 35, 38, 47, 49, 50, 52, 53, 56, 61,
                    62, 65, 67, 68, 70, 71, 85, 86, 94, 95 };

            if (seatNum[h] == (i + 1)) {
                tile.setTileColor();
                if (seatNum.length != (h + 1))
                    h++;
            }

            tileList.add(tile);
            contactGridPanel.add(tile);
        }

        // Creates another contact list but in reverse order
        reversedList = new ArrayList<ContactTile>();
        for (int b = 98; b > -1; b--) {
            reversedList.add(tileList.get(b));
        }
    }

    // Attempts to redraw the tiles in a new order
    private void drawTiles() {
        contactGridPanel = new JPanel(new GridLayout(11, 9));

        if (reverse) {
            for (ContactTile tile : reversedList) {
                contactGridPanel.add(tile);
            }
            reverse = false;

        } else {
            for (ContactTile tile : tileList) {
                contactGridPanel.add(tile);
            }
            reverse = true;
        }

        Container contentPane = getContentPane();
        // contentPane.remove(contactGridPanel);
        contentPane.add(contactGridPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        // Todo: Remove randomization functionality and implement a visually appealing
        // view of seats and aisles.
        // NW - Fully implemented.

        // Todo: Implement reverse view where it looks like you are looking at the room
        // from the back instead of the front of the room.
        // WORK IN PROGRESS
        drawTiles();
        repaint();
    }
}

// Todo: Rename the following class to Thunderbird.
// NW - Fully implemented.
public class Thunderbird {
    public static void main(String[] args) {

        // Todo: Update the following line so that it reflects the name change to
        // Thunderbird.
        // NW - Fully implemented.
        System.out.println("Thunderbird Starting...");
        ThunderbirdFrame myThunderbirdFrame = new ThunderbirdFrame();
        myThunderbirdFrame.setVisible(true);
    }
}