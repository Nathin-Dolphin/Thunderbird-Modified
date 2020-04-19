
/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file is licensed under the BSD-3-Clause
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 *****************************************************************************/

// Modified from Original

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

class ThunderbirdFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList;
    private ThunderbirdModel tbM;
    private JPanel contactGridPanel;

    private boolean reverse = true;

    public ThunderbirdFrame() {
        setTitle("Thunderbird Classroom Seating Chart");
        setBounds(300, 25, 1200, 800);
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
        tbM.loadIndex();
        tbM.loadContactsThreaded();

        // Todo: Review ThunderbirdModel in detail and implement a multithreaded version
        // of loading contacts.
        // Hint: Review LoadContact() and LoadContactsThreaded() in detail.
        // NW - Fully implemented and reviewed.

        System.out.println("#### Printing Model:");
        System.out.println(tbM);
        tbM.validateContacts();

        tileList = new ArrayList<ContactTile>();

        int h = 0;
        for (int i = 0; i < 99; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i + 1);
            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat, i + 1);

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

        setVisible(true);
    }

    // Redraws the tiles in a new order
    private void redrawTiles() {
        Container contentPane = getContentPane();
        contentPane.remove(contactGridPanel);
        contactGridPanel = new JPanel(new GridLayout(11, 9));

        if (reverse) {
            for (int b = 98; b > -1; b--) {
                contactGridPanel.add(tileList.get(b));
            }
            reverse = false;

        } else {
            for (ContactTile tile : tileList) {
                contactGridPanel.add(tile);
            }
            reverse = true;
        }

        contentPane.add(contactGridPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Todo: Remove randomization functionality and implement a visually appealing
        // view of seats and aisles.
        // NW - Fully implemented.

        // Todo: Implement reverse view where it looks like you are looking at the room
        // from the back instead of the front of the room.
        // NW - Fully implemented.
        redrawTiles();
        repaint();
    }
}