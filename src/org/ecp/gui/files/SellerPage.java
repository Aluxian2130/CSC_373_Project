package org.ecp.gui.files;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import org.ecp.items.Product;
import org.ecp.people.Seller;
import org.ecp.system.Admin;

public class SellerPage {
    private JFrame frame = new JFrame();
    private JLabel title = new JLabel("E-Commerce Platform");
    private JLabel header = new JLabel("Welcome Seller!");
    private JButton addProductBtn = new JButton("Add Product");
    private JButton editProductBtn = new JButton("Edit Product");
    private JButton removeProductBtn = new JButton("Remove Product");
    private JButton viewProductListBtn = new JButton("View Product List");
    private JButton logoutBtn = new JButton("Log Out");


    private List<Product> productList = new ArrayList<>();

    public SellerPage(Seller s_1) {
        title.setBounds(30, 10, 300, 30);
        title.setFont(new Font("serif", Font.BOLD, 25));
        title.setForeground(new Color(211, 84, 0));
        header.setBounds(40, 60, 200, 35);
        header.setFont(new Font("serif", Font.BOLD, 15));
        header.setForeground(new Color(225, 246, 76));

        addProductBtn.setBounds(150, 110, 200, 30);
        removeProductBtn.setBounds(150, 160, 200, 30);
        viewProductListBtn.setBounds(150, 210, 200, 30);
        editProductBtn.setBounds(150, 260, 200, 30);
        logoutBtn.setBounds(385,425,90,25);

        addProductBtn.addActionListener(this::handleAddProduct);
        removeProductBtn.addActionListener(this::handleRemoveProduct);
        viewProductListBtn.addActionListener(this::handleViewProductList);
        editProductBtn.addActionListener(this::handleEditProduct);
        logoutBtn.addActionListener(this::handleLogout);

        frame.add(header);
        frame.add(title);
        frame.add(addProductBtn);
        frame.add(removeProductBtn);
        frame.add(viewProductListBtn);
        frame.add(editProductBtn);
        frame.add(logoutBtn);

        frame.getContentPane().setBackground(new Color(40, 55, 71));
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void handleLogout(ActionEvent e) {
    	frame.setVisible(false);
    	//frame.dispose();
    }
    private void handleAddProduct(ActionEvent e) {
        JFrame addProductFrame = new JFrame("Add Product");
        addProductFrame.setSize(400, 300);

        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        
        
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price;
            int quantity;
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name is required!");
                return;
            }
            try {
                price = Double.parseDouble(priceField.getText());
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Price or Quantity!");
                return;
            }

            Product newProduct = new Product(name, price, quantity);
            if (!description.isEmpty()) {
                newProduct.setDescription(description);
            }
            Integer flag = 0;
            for(Product p1: Admin.getProdAdminList() ) {
            	if(newProduct.getName().equals(p1.getName())) {
            		flag = 1;
            	}
            }
            if(flag != 1) {
            	productList.add(newProduct);
            	Admin.getProdAdminList().add(newProduct);
            	JOptionPane.showMessageDialog(frame, "Product added successfully!");
            }
            else {
            	JOptionPane.showMessageDialog(frame, "Product named " + newProduct.getName() + " already exists");
            }
        }
    }

    private void handleRemoveProduct(ActionEvent e) {
        String name = JOptionPane.showInputDialog(frame, "Enter Product Name to Remove:");

        boolean found = false;
        for (Product product : Admin.getProdAdminList()) {
            if (product.getName().equalsIgnoreCase(name)) {
                found = true;
                break;
            }
        }

        if (found) {
            Admin.getProdAdminList().removeIf(product -> product.getName().equalsIgnoreCase(name));
            JOptionPane.showMessageDialog(frame, "Product removed successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Product not found!");
        }
    }


    private void handleViewProductList(ActionEvent e) {
        StringBuilder products = new StringBuilder("Product List:\n");
        if (productList.isEmpty()) {
        	products.append("[No products]");
        }
        else {
        	for (Product product : productList) {
                products.append(product.getName()).append(" - $").append(product.getPrice()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(frame, products.toString());
    }
    private void handleEditProduct(ActionEvent e) {
        String productName = JOptionPane.showInputDialog(frame, "Enter Product Name to Edit:");

        Product selectedProduct = null;
        for (Product product : Admin.getProdAdminList()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct != null) {
            JFrame editProductFrame = new JFrame("Edit Product");
            editProductFrame.setSize(400, 300);

            JTextField nameField = new JTextField(selectedProduct.getName());
            JTextField descriptionField = new JTextField(selectedProduct.getDescription());
            JTextField priceField = new JTextField(String.valueOf(selectedProduct.getPrice()));
            JTextField quantityField = new JTextField(String.valueOf(selectedProduct.getQuantity()));

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceField.getText());
                    quantity = Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Price or Quantity!");
                    return;
                }

                selectedProduct.setName(name);
                selectedProduct.setDescription(description);
                selectedProduct.setPrice(price);
                selectedProduct.setQuantity(quantity);

                JOptionPane.showMessageDialog(frame, "Product updated successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Product not found!");
        }
    }


}
//    public static void main(String[] args) {
//        new SellerPage();
//    }
//}
