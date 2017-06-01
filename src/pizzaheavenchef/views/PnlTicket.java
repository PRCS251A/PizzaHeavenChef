/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaheavenchef.views;
import java.awt.Color;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import pizzaheaven.controllers.CustomerController;
import pizzaheaven.controllers.StaffController;
import pizzaheaven.models.Customer;
import pizzaheaven.models.Order;
import pizzaheaven.models.OrderItem;
import pizzaheaven.models.Session;
import pizzaheaven.models.Staff;
import pizzaheaven.security.Encryptor;

/**
 *
 * @author dan
 */
public class PnlTicket extends javax.swing.JPanel {
    Timer timer;
    Order order;
    OrderItem[] items;
    
    /**
     * Creates new form PnlTicket
     */
    public PnlTicket() {
        initComponents();
    }
    
    public String getOrderID() {
        return order.getOrderID();
    }
    
    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, timeUnit);
    }
    
    public void updateTime() {
        try {
            String timeString = order.getOrderDateTime().split("T")[1];
            int hours = Integer.valueOf(timeString.split(":")[0]);
            int minutes = Integer.valueOf(timeString.split(":")[1]);
            Date requestedDate = new Date();
            String dateString = order.getOrderDateTime().split("T")[0];
            requestedDate.setMonth((Integer.valueOf(dateString.split(Pattern.quote("-"))[1]) - 1));
            requestedDate.setDate(Integer.valueOf(dateString.split(Pattern.quote("-"))[2]));
            requestedDate.setHours(hours);
            requestedDate.setMinutes(minutes);
            requestedDate.setSeconds(Double.valueOf(timeString.split(":")[2]).intValue());
            long diff = getDateDiff(requestedDate, new Date(), TimeUnit.MILLISECONDS);
            //long offset = 4 * 60 * 1000 + (1000 * 10);
            if (diff >= ((1000 * 60 * 15))) {
                this.lblTimeActive.setBackground(Color.white);
                this.lblTimeActive.setForeground(Color.red);
            }
            //diff += offset;
            long hoursToConvert = diff / 1000 / 60 / 60 % 60;
            long minutesToConvert = (diff / 1000 / 60 % 60);
            long secondsToConvert  = diff / 1000 % 60;
            lblTimeActive.setText(((hoursToConvert > 0) ? hoursToConvert + ":" : "") 
                    + (minutesToConvert < 10 && minutesToConvert > 0 ? "0" + minutesToConvert : minutesToConvert) + ":" 
                       + (secondsToConvert < 10 && secondsToConvert > 0 ? "0" + secondsToConvert : secondsToConvert));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public PnlTicket(Order order, OrderItem[] items) {
        initComponents();
        lblOptionalExtra.setVisible(false);
        this.order = order;
        this.items = items;
        if (order.getStatus().equals("Ready For Delivery") || order.getStatus().equals("Out For Delivery")) {
            lblOptionalExtra.setVisible(true);
            Customer cust = ((CustomerController)Session.get().getController("CustomerController")).getCustomer(Integer.valueOf(order.getCustomerID()));
            lblOptionalExtra.setText("<html>");
            if (order.getStatus().equals("Out For Delivery")) {
                setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
                Staff staff = ((StaffController)Session.get().getController("StaffController")).get(order.getStaffID());
                if (staff.getFirstName().contains("=")) staff = (Staff)Encryptor.decrypt(staff);
                lblOptionalExtra.setText(lblOptionalExtra.getText() + "Being Delivered By: " + staff.getFirstName() + "<br />");
            }
            lblOptionalExtra.setText(lblOptionalExtra.getText() + "Customer: " + cust.getFirstName() + " " + cust.getSurname() + " " + 
                                    cust.getDeliveryLineOne() + " " + cust.getDeliveryLineTwo() + " " + 
                                    cust.getDeliveryPostCode() + " " + cust.getDeliveryCity() + "</html>");
        }
        lblOrderNumber.setText("#" + order.getOrderID());
        lblStatus.setText(order.getStatus());
        updateTime();
        lblItems.setText("<html>");
        for (OrderItem item : items) {
            if (item.getItemName().contains("^")) {
                String[] ingredients = item.getItemName().split(Pattern.quote("^"));
                int toppingCount = ingredients.length - 4;
                lblItems.setText(lblItems.getText() + "[Custom Pizza]<br/>" + ingredients[0] + " pizza, "
                        + ingredients[1] + " base, " + ingredients[2] + " crust, "
                        + ingredients[3] + " sauce, " + ingredients[4] + " cheese"
                        + (toppingCount > 0 ? "<br />Toppings: " : ""));
                for (int i = 0; i < toppingCount; i++) {
                    lblItems.setText(lblItems.getText() + ingredients[i + 4] + " " );
                }
                lblItems.setText(lblItems.getText() + "<br />");
            } else {
                lblItems.setText(lblItems.getText() + "[" + item.getQuantity() + "] " + item.getItemName() + "<br />");
            }
        }
        lblItems.setText(lblItems.getText() + "</html>");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlItems = new javax.swing.JPanel();
        lblItems = new javax.swing.JLabel();
        lblOrderNumber = new javax.swing.JLabel();
        lblTimeActive = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblOptionalExtra = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));
        setMaximumSize(new java.awt.Dimension(355, 32767));

        pnlItems.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblItems.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblItems.setText("jLabel3");

        javax.swing.GroupLayout pnlItemsLayout = new javax.swing.GroupLayout(pnlItems);
        pnlItems.setLayout(pnlItemsLayout);
        pnlItemsLayout.setHorizontalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlItemsLayout.setVerticalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblOrderNumber.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblOrderNumber.setText("lblOrderNumber");

        lblTimeActive.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblTimeActive.setText("lblTimeActive");

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblStatus.setText("lblStatus");

        lblOptionalExtra.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblOptionalExtra.setText("lblOptionalExtra");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOrderNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTimeActive))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStatus)
                            .addComponent(lblOptionalExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderNumber)
                    .addComponent(lblTimeActive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOptionalExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblItems;
    public javax.swing.JLabel lblOptionalExtra;
    private javax.swing.JLabel lblOrderNumber;
    public javax.swing.JLabel lblStatus;
    public javax.swing.JLabel lblTimeActive;
    private javax.swing.JPanel pnlItems;
    // End of variables declaration//GEN-END:variables
}
