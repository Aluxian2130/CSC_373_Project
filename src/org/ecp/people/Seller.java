package org.ecp.people;

import java.util.ArrayList;
import java.util.Scanner;
import org.ecp.items.Product;
import org.ecp.system.Admin;

public class Seller extends User {
   private ArrayList<Product> products;
   private Double overdue;
   //private Product p1;
   //private Admin a1 = new Admin();

   public Seller() {
	   products = new ArrayList<Product>();
	   overdue = 0.0D;
   }
   public Seller(String email, String password, String username, double accountBalance) {
	   this.email = email;
	   this.password = password;
	   this.username = username;
	   this.accountBalance = accountBalance;
   }
   
   public void addStaticProdList(Product p1) {
	   Admin.getProdAdminList().add(p1);
	   p1.setSeller(this);
   }
   public ArrayList<Product> getProductList() {
      return this.products;
   }

   public void setProductList(ArrayList<Product> products) {
      this.products = products;
   }
   
   public void printProductList() {
	   int var = 1;
	   System.out.println("Current product list:");
	   for(Product x: this.getProductList()) {
		   System.out.println(var + ") Product: " + x.getName() 
   		    + ", Price: " + x.getPrice() + "$"
   			+ ", Quantity: " + x.getQuantity());
		   if(x.getDescription() != "unknown") {
			   System.out.println("Description: " + x.getDescription() + "\n");
		   }
   		   else {
   			   System.out.println("No description added\n");
   		   }
   		   var++;
   	   }
   }
   public void setProduct(Product p1) { //sets a product
	   
	   System.out.println("Adding product to Seller's Products: ");
       Scanner myObj = new Scanner(System.in);
       System.out.println("Enter name of product: ");
       p1.setName(myObj.nextLine());
       System.out.println("Enter price of product: ");
       p1.setPrice(myObj.nextDouble());
       System.out.println("Enter quantity of product: ");
       p1.setQuantity(myObj.nextInt());
       while (p1.getQuantity() == 0) {
    	  System.out.println("Cannot input 0 for quantity."); //may need an exit through GUI
          p1.setQuantity(myObj.nextInt());
       }
       
       System.out.println("Do you want to add description of product? (true/false): ");
       if (myObj.nextBoolean()) {
    	   myObj.nextLine(); //BECAUSE \n CHARACTER IS BEING READ AFTER HITTING ENTER. THIS IS A WORKAROUND. 
          System.out.println("Enter description: "); //Dk why this ain't workin
          String descript = myObj.nextLine();
          p1.setDescription(descript);
       }
       p1.setSeller(this);
       //p1.setSeller(username);
      // if (p1.getName() != "unknown" && p1.getPrice() != 0.0D && p1.getQuantity() != 0) {
      //    this.products.add(p1);
      // } else {
      //    System.out.println("Could not add product because one or more of required fields (name, price, quantity) was empty. ");
      // }
       
   }
   public void addProduct() {
	   Product p1 = new Product();
	   if(this.products.size() < Admin.getMaxProducts()) {
		   this.setProduct(p1);
		   if (p1.getName() != "unknown" && p1.getPrice() != null) {
			   this.addToProductList(p1);
			   //p1.setSeller(this); //MAY NEED? ****************
		   }
		   else {
			   System.out.println("Cannot add product to your account. "
				+ "Need both name and price. "  
		  	    + "Product name: " + p1.getName() //should these two be setName and setPrice?
		  	    + " Product price: " + p1.getPrice());     
		   }
	   }
	   else {
		   System.out.println("Cannot add product to your account.\n"
			+ "You have reached the maximum product limit: " + Admin.getMaxProducts());
	   }
   }
	   
   public void addToProductList(Product p1) {
	   this.products.add(p1);
	   p1.setSeller(this);
   }
   
   public void getPaid(Double amount) {
	   while ( (this.accountBalance + amount) > Admin.getMaxLimit()) {
		   amount -= 0.01;
		   overdue += 0.01;
	   }
	   this.accountBalance += amount;
   }
   
   public void removeFunds(Double amount, Double overdue) {
	      if (this.accountBalance - amount >= 0.0) {
	         this.accountBalance -= amount;
	      }
	      if (overdue > 0.0){
	    	 while ( (this.accountBalance + amount) <= Admin.getMaxLimit()) {
	    		 amount -= 0.01;
	    		 this.accountBalance += 0.01;
	    	 }
	    	  
	      }

	   }
	   
   /*public void addProduct(Product product) {
      if (this.products.size() < Admin.getMaxProducts()) {
         System.out.println("Adding product to Seller's Products: ");
         Scanner myObj = new Scanner(System.in);
         System.out.println("Enter name of product: ");
         product.setName(myObj.nextLine());
         System.out.println("Enter price of product: ");
         product.setPrice(myObj.nextDouble());
         System.out.println("Enter quanity of product: ");
         if (myObj.nextInt() != 0) {
            product.setQuantity(myObj.nextInt());
         }
         else {
        	 System.out.println("Cannot input 0 for quantity."); //may need an exit through GUI
             product.setQuantity(myObj.nextInt());
         }

         System.out.println("Add description of product? (true/false): ");
         if (myObj.nextBoolean()) {
            System.out.println("Enter quanity of product to add to shoppingCart: ");
            product.setDescription(myObj.nextLine());
         }

         if (product.getName() != "unknown" && product.getPrice() != 0.0D && product.getQuantity() != 0) {
            this.products.add(product);
         } else {
            System.out.println("Could not add product because one or more of required fields (name, price, quantity) was empty. ");
         }
      }

   }*/
   
   //Tested and works
   public void beginRemoveProduct() {
	   this.printProductList();
	   System.out.println("What product would you like to remove?\nChoose number from list: ");
	   
	   int x = 0;
	   while(x > this.getProductList().size() || x < 1) {
		   Scanner myObj = new Scanner(System.in);
		   x = myObj.nextInt();
		   if(x > this.getProductList().size() || x < 1) {
			   System.out.println("Please pick a valid number from the given list");
		   }
	   } 
	   int y = x;
	   this.removeProduct(this.getProductList().get(y-1));
   }

   //tested in previous method and works
   public void removeProduct(Product product) {
      if (this.products.contains(product)) {
    	 System.out.println("Product: " + product.getName() + " has been removed");
         this.products.remove(product);
      }

   }
   
   public void beginUpdateProduct() {
	   this.printProductList();
	   System.out.println("What product would you like to update?\nChoose number from list: ");
	   
	   int x = 0;
	   while(x > this.getProductList().size() || x < 1) {
		   Scanner myObj = new Scanner(System.in);
		   x = myObj.nextInt();
		   if(x > this.getProductList().size() || x < 1) {
			   System.out.println("Please pick a valid number from the given list");
		   }
	   } 
	   int y = x;
	   this.updateProduct(this.getProductList().get(y-1));
   }
   
   public void updateProduct(Product product) {
      if (this.products.contains(product)) {
         System.out.println("Which field would you like to modify? (name, price, quantity, description): ");
         Scanner myObj = new Scanner(System.in);

         String inputN = myObj.nextLine();
         
         if (inputN.equals("name")) {
            System.out.println("Enter new name of product: ");
            product.setName(myObj.nextLine());
         } else if (inputN.equals("price")) {
            System.out.println("Enter new price of product: ");
            product.setPrice(myObj.nextDouble());
         } else if (inputN.equals("quantity")) {
            System.out.println("Enter new quantity of product: ");
            product.setQuantity(myObj.nextInt());
         } else if (inputN.equals("description")) {
            System.out.println("Enter new description of product: ");
            product.setDescription(myObj.nextLine());
         } else {
            System.out.println("Did not enter correct field. ");
         }
      }

   }
   
}

