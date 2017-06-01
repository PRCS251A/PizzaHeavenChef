/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaheavenchef.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.Timer;
import pizzaheaven.controllers.OrderController;
import pizzaheaven.controllers.OrderStatusController;
import pizzaheaven.controllers.OrderedItemController;
import pizzaheaven.controllers.StaffController;
import pizzaheaven.helpers.ImageHelpers;
import pizzaheaven.models.Order;
import pizzaheaven.models.Session;
import pizzaheaven.models.Staff;
import pizzaheaven.security.Encryptor;

/**
 *
 * @author dansc
 */
public class FrmReady extends javax.swing.JFrame {
    ArrayList<String> shownOrders;
    OrderStatusController orderStatusController;
    OrderController orderController;
    OrderedItemController orderItemController;
    Timer timer;
    
    private void timerTick(ActionEvent evt) {
        int ordersDisplayed = 0;
        int beingDelivered = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        this.lblTime.setText(sdf.format(new Date()));
        for (Order order : orderController.get()){
            if (order.getStatus() != null) {
                if (order.getStatus().equals("Ready For Delivery") || order.getStatus().equals("Out For Delivery")) {
                    if (order.getStatus().equals("Ready For Delivery")) ordersDisplayed++;
                    else if (order.getStatus().equals("Out For Delivery")) { 
                        beingDelivered++;
                        for (Component comp : pnlTickets.getComponents()) {
                            if (comp instanceof PnlTicket) {
                                PnlTicket pnlTicket = (PnlTicket)comp;
                                if (pnlTicket.getOrderID().equals(order.getOrderID()) && !pnlTicket.lblOptionalExtra.getText().contains("Being Delivered")) {
                                    Staff staff = ((StaffController)Session.get().getController("StaffController")).get(order.getStaffID());
                                    if (staff.getFirstName().contains("=")) staff = (Staff)Encryptor.decrypt(staff);
                                    pnlTicket.setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                                    pnlTicket.lblStatus.setText("Out For Delivery");
                                    pnlTicket.lblOptionalExtra.setText("<html>Being Delivered by: " 
                                            +  staff.getFirstName()
                                            + "<br />" + pnlTicket.lblOptionalExtra.getText().substring(6, pnlTicket.lblOptionalExtra.getText().length() - 1));
                                }
                            }
                        }
                    }
                    if (!shownOrders.contains(order.getOrderID())) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) { }
                        PnlTicket pnlTicket = new PnlTicket(order, orderItemController.getForID(Integer.valueOf(order.getOrderID())));
                        pnlTickets.add(pnlTicket);
                        shownOrders.add(order.getOrderID());
                    } else {
                        for (Component comp : pnlTickets.getComponents()){
                            if (comp instanceof PnlTicket) {
                                ((PnlTicket)comp).updateTime();
                            }
                        }
                    }
                } else if (shownOrders.contains(order.getOrderID())) {
                    for (Component comp : pnlTickets.getComponents()) {
                        if (comp instanceof PnlTicket) {
                            if (((PnlTicket)comp).getOrderID().equals(order.getOrderID())) {
                                pnlTickets.remove(comp);
                            }
                        }
                    }
                }
            }
        }
        this.lblAwaitingDelivery.setText(String.valueOf(ordersDisplayed));
        this.lblBeingDelivered.setText(String.valueOf(beingDelivered));
        pnlTickets.revalidate();
        pnlTickets.repaint();
    }
    
    /**
     * Creates new form FrmReady
     */
    public FrmReady() {
        initComponents();
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        shownOrders = new ArrayList<String>();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        WrapLayout wrap = new WrapLayout();
        wrap.setAlignment(FlowLayout.LEFT);
        wrap.setAlignOnBaseline(true);
        pnlTickets.setLayout(wrap);
        orderStatusController = new OrderStatusController();
        orderController = new OrderController();
        orderItemController = new OrderedItemController();
        for (Order order : orderController.get()) {
            if (order.getStatus() != null && (order.getStatus().equals("Ready For Delivery") || order.getStatus().equals("Out For Delivery"))) {
                PnlTicket pnlTicket = new PnlTicket(order, orderItemController.getForID(Integer.valueOf(order.getOrderID())));
                pnlTickets.add(pnlTicket);
                shownOrders.add(order.getOrderID());
                
            }
        }
        pnlTickets.repaint();
        pnlTickets.revalidate();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            timerTick(actionEvent);
        };
        timer = new Timer(1000, actionListener);
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlTickets = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblAwaitingDelivery = new javax.swing.JLabel();
        lblBeingDelivered = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizza Heaven - Orders Ready For Delivery");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlTickets = new javax.swing.JPanel() {
            @Override
            public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
                return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img;
                try {
                    img = ImageIO.read(new File("src/pizzaheavenchef/views/PizzaHeavenBanner.png"));
                    int padding = jScrollPane1.getWidth() / 10;
                    BufferedImage resized = ImageHelpers.resize(img, jScrollPane1.getWidth() - padding, (jScrollPane1.getWidth() / 3) - padding);
                    g.drawImage(resized, jScrollPane1.getWidth() / 2 - (jScrollPane1.getWidth() / 2) + padding / 2, jScrollPane1.getHeight() / 2 - ((jScrollPane1.getWidth() / 3) / 2) + jScrollPane1.getVerticalScrollBar().getValue(), this);
                    //jScrollPane1.getVerticalScrollBar().getValue()
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public int getBaseline(int width, int height) {
                return 0;
            }
        };
        pnlTickets.setPreferredSize(null);
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        pnlTickets.setLayout(flowLayout1);
        jScrollPane1.setViewportView(pnlTickets);

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTime.setText("lblTime");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Awaiting Delivery:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Being Delivered:");

        lblAwaitingDelivery.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblAwaitingDelivery.setText("lblAwaitingDelivery");

        lblBeingDelivered.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBeingDelivered.setText("lblBeingDelivered");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAwaitingDelivery)
                            .addComponent(lblBeingDelivered))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 576, Short.MAX_VALUE)
                        .addComponent(lblTime))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblAwaitingDelivery))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBeingDelivered))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmReady.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReady.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReady.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReady.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReady().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAwaitingDelivery;
    private javax.swing.JLabel lblBeingDelivered;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlTickets;
    // End of variables declaration//GEN-END:variables
}
