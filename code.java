import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


// Abstract class for the user
abstract class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract void login();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// Admin class inheriting from User
class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (getUsername().equals(username) && getPassword().equals(password)) {
            System.out.println("Admin login successful!");
            // Performing admin-specific operations here
            Supermarket supermarket = new Supermarket();

           // Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Menu:");
                System.out.println("1. Add Product");
                System.out.println("2. Check Stock");
                System.out.println("3.Add New Cashier");
                System.out.println("4.Add New Admin");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        supermarket.addProduct();
                        break;
                    case 2:
                        supermarket.checkStock();
                        break;
                    case 3:

                        Scanner scanner1 = new Scanner(System.in);
                        System.out.print("Enter cashier username: ");
                        String Cashierusername = scanner1.nextLine();
                        System.out.print("Enter cashier password: ");
                       String Cashierpassword = scanner1.nextLine();
                        Cashier NewCashier=new Cashier(Cashierusername,Cashierpassword);
                        System.out.println("New Cashier Added!");
                        NewCashier.login();
                        break;
                    case 4:

                        Scanner scanner2 = new Scanner(System.in);
                        System.out.print("Enter cashier username: ");
                        String Adminusername = scanner2.nextLine();
                        System.out.print("Enter cashier password: ");
                        String Adminpassword = scanner2.nextLine();
                        Admin NewAdmin=new Admin(Adminusername,Adminpassword);
                        System.out.println("New Admin Added!");
                        NewAdmin.login();
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
                System.out.println();
            } while (choice!=0);

            scanner.close();

        } else {
            System.out.println("Invalid admin username or password!");
        }
    }
}

// Cashier class inheriting from User
class Cashier extends User {
    public Cashier(String username, String password) {
        super(username, password);
    }

    @Override
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cashier username: ");
        String username = scanner.nextLine();
        System.out.print("Enter cashier password: ");
        String password = scanner.nextLine();

        if (getUsername().equals(username) && getPassword().equals(password)) {
            System.out.println("Cashier login successful!");
            // Performing cashier-specific operations here
            Supermarket supermarket = new Supermarket();
            supermarket.calculateBill();

        } else {
            System.out.println("Invalid cashier username or password!");
        }
    }
}

// Main class
public class Billing {
    public static void main(String[] args) {
        // Create admin and cashier objects
        Scanner scanner=new Scanner(System.in);
        Admin admin = new Admin("admin", "admin123");
        Cashier cashier = new Cashier("cashier", "cashier123");
        System.out.println("You Must Log in First \n1.Admin \n2.Cashier");
        int role=scanner.nextInt();
        // Login as admin or cashier
        if(Objects.equals(role,1)){
            admin.login();
        } else if (Objects.equals(role,2)) {
            cashier.login();

        }else {
            System.out.println("Wrong Input!");
        }


    }
}


 class Supermarket {
    private List<Product> stock;
    private Scanner scanner;

    public  Supermarket() {
        stock = new ArrayList<>();
        scanner = new Scanner(System.in);
        Product product1 = new Product("Apple", 10, 1.99);
        Product product2 = new Product("Banana", 15, 0.99);
        Product product3 = new Product("Orange", 20, 0.79);

        stock.add(product1);
        stock.add(product2);
        stock.add(product3);

    }

    public void checkStock() {

        System.out.println("Available Products:");
        for (Product product : stock) {
            System.out.println(product.getName() + ": " + product.getQuantity()+"\t"+ product.getPrice());

        }
    }

    public void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.println("Enter price: ");
        double price=scanner.nextDouble();
       scanner.nextLine(); // Consume the newline character

        for (Product product : stock) {
            if (product.getName().equals(name)) {
                product.setQuantity(product.getQuantity() + quantity);
                product.setPrice(product.getPrice());
                System.out.println(name +"\tQuantity->"+quantity+"\t$"+price+"(s) added to stock.");
                return;
            }
        }
        Product newProduct = new Product(name,quantity,price);
        stock.add(newProduct);
        System.out.println(name +"\tQuantity->"+quantity+"\t$"+price+"(s) added to stock.");


    }

     public void calculateBill() {
         System.out.println("Enter the products you want to purchase (enter 'done' to finish):");
         List<Product> productsToPurchase = new ArrayList<>();

         while (true) {
             System.out.print("Product name: ");
             String productName = scanner.nextLine();

             if (productName.equalsIgnoreCase("done")) {
                 break;
             }
             System.out.print("Quantity: ");
             int quantity = scanner.nextInt();
             scanner.nextLine(); // Consume the newline character

             Product product = findProductByName(productName);
                     if (product==null){
                         System.out.println("Product not found");
                         continue;}
                     if (quantity > product.getQuantity()) {
                 System.out.println("Insufficient quantity in stock.");
                 continue;}
                     product.setQuantity(product.getQuantity() - quantity);
             productsToPurchase.add(new Product(product.getName(), quantity, product.getPrice()));}
         double totalBill = 0.0;
         System.out.println("------------------------------");
         System.out.println("Products purchased:");
         for (Product product : productsToPurchase) {
             double productTotal = product.getPrice() * product.getQuantity();
             System.out.println(product.getName() + ": " + product.getQuantity() + "\t$" + productTotal);
             totalBill += productTotal;
         }
         System.out.println("------------------------------");
         System.out.println("Total Bill: $" + totalBill);
     }
     private Product findProductByName(String name) {
         for (Product product : stock) {
             if (product.getName().equalsIgnoreCase(name)) {
                 return product;
             }
         }
         return null;
     }


 }

     class Product {
        private String name;
        private double price;
        private int quantity;

        public Product(String name, int quantity,double price) {
            this.name = name;
            this.quantity = quantity;
            this.price=price;

        }

        public String getName() {
            return name;
        }

         public double getPrice() {
             return price;
         }

         public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
         public void setName(String name) {this.name = name;}
         public void setPrice(double price) {
             this.price = price;
         }
     }
