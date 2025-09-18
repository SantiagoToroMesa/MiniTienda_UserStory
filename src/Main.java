import Utils.JOptionPaneUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    static ArrayList<String> Nproduct = new ArrayList<>();
    static double [] precio = new double[0];
    static HashMap<String, Integer>stock = new HashMap<>();
    static double totalbuy = 0.0;
    static HashMap<String, Integer>shopcart = new HashMap<>();

    public static void main(String[] args) {


        String nombre = JOptionPaneUtils.InputString("Enter your name: ");
        nombre = (nombre == null) ? "Guest" : nombre;
        String [] opciones = {"Add a product", "Show inventory" , "Buy product", "Stats", "Search product", "Exit"};
        int opcion;

        do {
            opcion = JOptionPaneUtils.InputOption(" welcome " + nombre + "\n" +  " What do you want to do ? 🛠️" , "accion", opciones);
            switch(opcion){
                case 0:
                    precio = addProduct();
                    break;
                case 1:
                    listInventory();
                    break;
                case 2:
                    int idselection = JOptionPaneUtils.InputInt("Enter the product id to buy: ");
                    Integer quantity = JOptionPaneUtils.InputInt("Enter the quantity");
                    buyProduct(idselection, quantity);
                    break;
                case 3:
                    stats();
                    break;
                case 4:
                    String search = JOptionPaneUtils.InputString("Enter the name of the product");
                    searchproduct(search);
                    break;
                case 5:
                    showBill();
                    JOptionPaneUtils.ShowMessageTimed("Leaving the system", 1000);
                    break;
            }
        }while(opcion != 5);

        for(String p : Nproduct){
            System.out.println(p);
        }

        for(double p : precio){
            System.out.println(p);
        }

        for (HashMap.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("Producto: " + entry.getKey() + " | Stock: " + entry.getValue());
        }

        System.out.println(totalbuy);

        for (HashMap.Entry<String, Integer> entry : shopcart.entrySet()) {
            System.out.println("Producto: " + entry.getKey() + " | Stock: " + entry.getValue());
        }
    }

    public static double[] addProduct(){
        String name = "";
        double pricep;
        int quantity;

        while(true) {
            name = JOptionPaneUtils.InputString("Enter the product name");
            pricep = JOptionPaneUtils.InputDouble("Enter the product price");
            quantity = JOptionPaneUtils.InputInt("Enter the product stock");

            if (Nproduct.contains(name)) {
                JOptionPaneUtils.ShowMessageTimed("❌ The product " + name + " exits in the inventory.", 4000);
                return precio;
            }
            else if(name == null || pricep <= 0 || quantity <= 0){
                JOptionPaneUtils.ShowMessage("⚠️ The value cant be null or <= 0!");
                continue;
            }
            else{
                break;
            }
        }

        // Solo se ejecuta si el producto es válido y no existe
        Nproduct.add(name);
        double[] newPrice = Arrays.copyOf(precio, precio.length + 1);
        newPrice[newPrice.length - 1] = pricep;
        stock.put(name, quantity);
        return newPrice;
    }



    public static void listInventory(){
        if(Nproduct.isEmpty()){
            JOptionPaneUtils.ShowMessage("The invetory is empty");
            return;
        }
        else{
            StringBuilder inventory = new StringBuilder();
            inventory.append("---------------------  📦 Inventory  ---------------------\n");

            inventory.append(String.format("%-5s | %-20s | %-15s | %-10s\n",
                    "ID", "Product", "Price", "Stock"));
            inventory.append("------------------------------------------------------------\n");

            for (int i = 0; i < Nproduct.size(); i++) {
                String productname = Nproduct.get(i);
                double price = precio[i];
                Integer stocker = stock.get(productname);

                inventory.append(String.format("%-5d | %-20s | $%,-14.2f | %-10d\n",
                        (i + 1), productname, price, stocker));
            }

            JOptionPaneUtils.messagemenu(inventory);
        }


    }

    public static void buyProduct(int num, Integer quantity){
        try {
            if(quantity <= 0){
                JOptionPaneUtils.ShowMessage("Quantity must be greater than 0");
                return;
            }
            String namechosen = Nproduct.get(num - 1);
            totalbuy += precio[num - 1] * quantity;
            System.out.println(namechosen);
            int actual = stock.get(namechosen);
            int neww = actual - quantity;

            if(neww < 0){
                JOptionPaneUtils.ShowMessage("This product hasn't suficient stock");
                return;

            }else{
                stock.put(namechosen, neww);
                shopcart.put(namechosen, shopcart.getOrDefault(namechosen, 0) + quantity);
            }

        }catch(Exception e){
            JOptionPaneUtils.ShowMessage("The Product  was not found");
        }

    }

    public static void stats(){
        double max = 0;
        double min = 999999999;
        String Nmax = "";
        String Nmin = "";
        for (int i = 0; i < precio.length ; i++) {
            if(precio[i] > max){
                max = precio[i];
                Nmax = Nproduct.get(i);
            }
            if(precio[i] < min){
                min = precio[i];
                Nmin = Nproduct.get(i);
            }
        }
        StringBuilder MaxMin = new StringBuilder("The most expensive product is: " + Nmax + "\n with a price of: "  + max + "\n\n The cheapest product is: " + Nmin + "\n with a price of: " + min);
        JOptionPaneUtils.messagemenu(MaxMin);
    }

    public static void searchproduct(String productname){
        int quantity = 0;
        double price = 0.0;
        boolean found = false;
        try {
        for (int i = 0; i < Nproduct.size(); i++) {
            if(productname.equalsIgnoreCase(Nproduct.get(i))){
                quantity = stock.get(productname);
                price = precio[i];
                found = true;
            }
        }
            if(found){
                StringBuilder productfound = new StringBuilder("The product was found! \nProduct name: " + productname + "\n" + "Product price: " + price + "\n" + "Product stock: " + quantity);
                JOptionPaneUtils.messagemenu(productfound);
            }
            else{
                JOptionPaneUtils.ShowMessage("The product was not found");
            }
        }catch(Exception e){
            return;
        }
    }
    public static void showBill() {
        if (shopcart.isEmpty()) {
            JOptionPaneUtils.ShowMessage("🛒 Your cart is empty, no purchases made.");
            return;
        }

        StringBuilder bill = new StringBuilder();
        bill.append("---------------------  🧾 Final Bill  ---------------------\n");
        bill.append(String.format("%-20s | %-10s | %-10s\n", "Product", "Quantity", "Subtotal"));
        bill.append("----------------------------------------------------------\n");
        for (HashMap.Entry<String, Integer> entry : shopcart.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            int index = Nproduct.indexOf(product);
            double price = precio[index];
            double subtotal = price * quantity;

            bill.append(String.format("%-20s | %-10d | $%,-10.2f\n", product, quantity, subtotal));
        }

        bill.append("----------------------------------------------------------\n");
        bill.append(String.format("TOTAL: $%,.2f", totalbuy));
        JOptionPaneUtils.messagemenu(bill);
    }
}