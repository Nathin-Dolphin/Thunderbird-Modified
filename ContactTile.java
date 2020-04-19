
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

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

class ContactTile extends JPanel {
    private ThunderbirdContact contactInSeat = null;
    private Color tileColor = Color.LIGHT_GRAY;
    private boolean isSeat = false;

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            showMessage();
        }
    };

    private int seatLocation;

    public void setTileColor() {
        tileColor = Color.BLACK;
        isSeat = true;
    }

    ContactTile() {
        super();
        addMouseListener(mouseAdapter);

        // Todo: Remove everything to do with random colors.
        // NW - Fully implemented.
        // Todo: Implement visually appealing colors for aisles and seats.
        // NW - Fully implemented.
    }

    ContactTile(ThunderbirdContact contactInSeatIn, int seatLocation) {
        super();
        addMouseListener(mouseAdapter);
        this.seatLocation = seatLocation;
        contactInSeat = contactInSeatIn;
    }

    private void showMessage() {
        String messageContents;

        if (isSeat) {
            if (contactInSeat == null)
                messageContents = "Seat Number " + seatLocation;
            else
                messageContents = contactInSeat.toString();

        } else {
            if (contactInSeat == null)
                messageContents = "Aisle Number " + seatLocation;
            else
                messageContents = contactInSeat.toString(false);
        }

        JOptionPane.showMessageDialog(this, messageContents, "Personal Information", JOptionPane.PLAIN_MESSAGE);
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
        int stringX = (panelWidth / 10);
        int stringY = (panelHeight / 3) + 5;
        if (contactInSeat != null) {

            // ToDo: Display preferred name instead of first and last name.
            // NW - Fully implemented.
            String preferredName;
            String location;

            if (isSeat)
                location = "   Seat: " + contactInSeat.getSeat();
            else {
                g.setColor(Color.RED);
                location = "   Aisle: " + contactInSeat.getSeat();
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