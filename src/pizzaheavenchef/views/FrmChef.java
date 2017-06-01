/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaheavenchef.views;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import pizzaheaven.controllers.OrderController;
import pizzaheaven.controllers.OrderStatusController;
import pizzaheaven.controllers.OrderedItemController;
import pizzaheaven.helpers.ImageHelpers;
import pizzaheaven.models.Order;

/**
 *
 * @author dan
 */
public class FrmChef extends javax.swing.JFrame {
    OrderController orderController;
    OrderStatusController orderStatusController;
    OrderedItemController orderItemController;
    Timer timer; 
    ArrayList<String> shownOrders;
    int selected = 0;
    
    private void timerTick(ActionEvent evt) {
        int ordersDisplayed = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        this.lblTime.setText(sdf.format(new Date()));
        int readyForDelivery = 0;
        for (Order order : orderController.get()){
            if (order.getStatus() != null) {
                if (order.getStatus().equals("Placed") ||
                    order.getStatus().equals("Prep")) {
                    ordersDisplayed++;
                    if (!shownOrders.contains(order.getOrderID())) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) { }
                        PnlTicket pnlTicket = new PnlTicket(order, orderItemController.getForID(Integer.valueOf(order.getOrderID())));
                        pnlTickets.add(pnlTicket);
                        
                        shownOrders.add(order.getOrderID());
                    } 
                } else if (order.getStatus().equals("Ready For Delivery")) {
                    readyForDelivery++;
                } else if (shownOrders.contains(order.getOrderID())) {
                    for (Component comp : pnlTickets.getComponents()) {
                        if (comp instanceof PnlTicket) {
                            if (((PnlTicket)comp).getOrderID().equals(order.getOrderID())) {
                                pnlTickets.remove(comp);
                                shownOrders.remove(order.getOrderID());
                            }
                        }
                    }
                }
                
                for (Component comp : pnlTickets.getComponents()){
                    if (comp instanceof PnlTicket) {
                        ((PnlTicket)comp).updateTime();
                    }
                }
            }
        }
        lblReadyForDelivery.setText(String.valueOf(readyForDelivery));
        lblActiveOrders.setText(String.valueOf(ordersDisplayed));
        pnlTickets.repaint();
        pnlTickets.revalidate();
    }
    
    /**
     * Creates new form FrmChef
     */
    public FrmChef() {
        initComponents();
        FrmReady frmReady = new FrmReady();
        frmReady.setVisible(true);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        shownOrders = new ArrayList<String>();
        WrapLayout wrap = new WrapLayout();
        wrap.setAlignment(FlowLayout.LEFT);
        wrap.setAlignOnBaseline(true);
        pnlTickets.setLayout(wrap);
        orderStatusController = new OrderStatusController();
        orderController = new OrderController();
        orderItemController = new OrderedItemController();
        for (Order order : orderController.get()) {
            if (order.getStatus() != null && (order.getStatus().equals("Placed") 
                    || order.getStatus().equals("Prep"))) {
                PnlTicket pnlTicket = new PnlTicket(order, orderItemController.getForID(Integer.valueOf(order.getOrderID())));
                if (order.getStatus().equals("Prep")) {
                    pnlTicket.setBorder(BorderFactory.createLineBorder(java.awt.Color.GREEN, 10));
                }
                pnlTicket.addMouseListener(new MouseAdapter() {
                    @Override 
                    public void mouseClicked(MouseEvent e) {
                        int onComponent = 0;
                        for (Component comp : pnlTickets.getComponents()) {
                            if (comp instanceof PnlTicket) {
                                if (((PnlTicket)comp).equals(pnlTicket)) {
                                    selected = onComponent;
                                    updateSelected();
                                }
                            }
                            onComponent++;
                        }
                    }
                });
                pnlTickets.add(pnlTicket);
                shownOrders.add(order.getOrderID());
            }
        }
        pnlTickets.repaint();
        pnlTickets.revalidate();
        updateSelected();
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

        lblTime = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblActiveOrders = new javax.swing.JLabel();
        lblReadyForDelivery = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnNextOrder = new javax.swing.JButton();
        btnPreviousOrder = new javax.swing.JButton();
        btnProgress = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlTickets = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizza Heaven - Chef Screen");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lblTime.setText("lblTime");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Active Orders:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Ready For Delivery:");

        lblActiveOrders.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblActiveOrders.setText("lblActiveOrders");

        lblReadyForDelivery.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblReadyForDelivery.setText("lblReadyForDelivery");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNextOrder.setText("<html><center>Next Order<br/><b style=\"font-size:20pt;\">►</b></center></html>");
        btnNextOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextOrderActionPerformed(evt);
            }
        });

        btnPreviousOrder.setText("<html><center>Previous Order<br/><b style=\"font-size:20pt;\">◄</b></center></html>");
        btnPreviousOrder.setPreferredSize(new java.awt.Dimension(120, 47));
        btnPreviousOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousOrderActionPerformed(evt);
            }
        });

        btnProgress.setBackground(new java.awt.Color(0, 153, 0));
        btnProgress.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnProgress.setForeground(new java.awt.Color(255, 255, 255));
        btnProgress.setText("Progress Order");
        btnProgress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgressActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProgress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPreviousOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNextOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnPreviousOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

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
                    int padding = this.getWidth() / 10;
                    BufferedImage resized = ImageHelpers.resize(img, this.getWidth() - padding, (this.getWidth() / 3) - padding);
                    g.drawImage(resized, this.getWidth() / 2 - (this.getWidth() / 2) + padding / 2, this.getHeight() / 2 - ((this.getWidth() / 3) / 2), this);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblActiveOrders)
                            .addComponent(lblReadyForDelivery))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 560, Short.MAX_VALUE)
                        .addComponent(lblTime))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblActiveOrders)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblReadyForDelivery)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTime)
                        .addGap(5, 5, 5)))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextOrderActionPerformed
        // TODO add your handling code here:
        selected++;
        if (selected >= shownOrders.size()) {
            selected = 0;
        }
        updateSelected();
    }//GEN-LAST:event_btnNextOrderActionPerformed

    private void btnPreviousOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousOrderActionPerformed
        // TODO add your handling code here:
        selected--;
        if (selected < 0) {
            selected = shownOrders.size() - 1;
        }
        updateSelected();
    }//GEN-LAST:event_btnPreviousOrderActionPerformed

    private void btnProgressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgressActionPerformed
        // TODO add your handling code here:
        Order order = orderController.get(Integer.valueOf(getSelectedPanel().getOrderID()));
        switch (order.getStatus()) {
            case "Placed":
                order.setStatus("Prep");
                orderController.update(order);
                getSelectedPanel().setBorder(new CompoundBorder(BorderFactory.createLineBorder(java.awt.Color.BLUE, 5), 
                                                                BorderFactory.createLineBorder(java.awt.Color.GREEN, 5)));
                getSelectedPanel().lblStatus.setText("Prep");
                break;
            case "Prep":
                order.setStatus("Ready For Delivery");
                orderController.update(order);
                shownOrders.remove(order.getOrderID());
                pnlTickets.remove(getSelectedPanel());
                updateSelected();
                break;
            default:
                break;
        } 
    }//GEN-LAST:event_btnProgressActionPerformed

    private void updateSelected() {
        int indexOfComponent = 0;
        if (selected > pnlTickets.getComponentCount()) {
            selected = 0;
        }
        for (Component comp : pnlTickets.getComponents()) {
            if (comp instanceof PnlTicket) {
                if (indexOfComponent == selected && orderController.get(Integer.valueOf(((PnlTicket)comp).getOrderID())).getStatus().equals("Prep")) {
                    ((PnlTicket)comp).setBorder(new CompoundBorder(BorderFactory.createLineBorder(java.awt.Color.BLUE, 5), 
                                                                    BorderFactory.createLineBorder(java.awt.Color.GREEN, 5)));
                } else if (indexOfComponent == selected && orderController.get(Integer.valueOf(((PnlTicket)comp).getOrderID())).getStatus().equals("Placed")) {
                    ((PnlTicket)comp).setBorder(new CompoundBorder(BorderFactory.createLineBorder(java.awt.Color.BLUE, 5), 
                                                                    BorderFactory.createLineBorder(java.awt.Color.BLACK, 5)));
                } else if (orderController.get(Integer.valueOf(((PnlTicket)comp).getOrderID())).getStatus().equals("Prep")) {
                    ((PnlTicket)comp).setBorder(BorderFactory.createLineBorder(java.awt.Color.GREEN, 10));
                } else if (orderController.get(Integer.valueOf(((PnlTicket)comp).getOrderID())).getStatus().equals("Baking")) {
                    ((PnlTicket)comp).setBorder(BorderFactory.createLineBorder(java.awt.Color.ORANGE, 10));
                }else {
                    ((PnlTicket)comp).setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 10));
                }
                indexOfComponent++;
            }
        }
    }
    
    public PnlTicket getSelectedPanel() {
        int onComponent = 0;
        for (Component comp : pnlTickets.getComponents()) {
            if (comp instanceof PnlTicket) {
                if (onComponent == selected) {
                    return (PnlTicket)comp;
                }
                onComponent++;
            }
        }
        return null;
    }
    
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
            java.util.logging.Logger.getLogger(FrmChef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmChef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmChef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmChef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmChef().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNextOrder;
    private javax.swing.JButton btnPreviousOrder;
    private javax.swing.JButton btnProgress;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblActiveOrders;
    private javax.swing.JLabel lblReadyForDelivery;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlTickets;
    // End of variables declaration//GEN-END:variables
}
