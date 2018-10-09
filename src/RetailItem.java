import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RetailItem {
    private String itemName;
    private String description;
    private double unitsInInventory;
    private BigDecimal price;

    public RetailItem(String itemName, String description, double unitsInInventory, BigDecimal price) {
        this.itemName = itemName;
        this.description = description;
        this.unitsInInventory = unitsInInventory;
        setPrice(price);
    }

    BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getUnitsInInventory() {
        return unitsInInventory;
    }

    public String getDescription() {
        return description;
    }

    public void setUnitsInInventory(double unitsInInventory) {
        this.unitsInInventory = unitsInInventory;
    }

    @Override
    public String toString() {
        return "itemName: " + getItemName() + "\n\n" +
                "Description:\n" + getDescription() + "\n" +
                "Price: LKR " + getPrice() +
                "\nUnits in inventory " + getUnitsInInventory();
    }
}

class Coin {
    private BigDecimal coinDenomination;

    public Coin(BigDecimal coinDenomination) {
        this.coinDenomination = coinDenomination;
    }

    public BigDecimal getCoinDenomination() {
        return coinDenomination;
    }
}

class CashRegister {
    private List<RetailItem> retailItems = new ArrayList<>();
    private BigDecimal outstandingBal = new BigDecimal(0);

    public void purchase_item(RetailItem purchase) {
        retailItems.add(purchase);

    }

    public BigDecimal get_total() {
        double total = 0;
        for (RetailItem retailItem : retailItems) {
            total = total + retailItem.getPrice().doubleValue();
        }
        return new BigDecimal(total);
    }

    public List<RetailItem> getRetailItems() {
        return retailItems;
    }

    private void setOutstandingBal(BigDecimal outstandingBal) {
        this.outstandingBal = outstandingBal;
    }

    public BigDecimal getOutstandingBal() {
        return this.outstandingBal;
    }

    void receivePayment(int coinCount, Coin coinType) {
        double amountPaid = coinCount * coinType.getCoinDenomination().doubleValue();
        double currentOutstandingAmount = getOutstandingBal().doubleValue() + amountPaid;
        setOutstandingBal(new BigDecimal(currentOutstandingAmount));
    }

    int giveChange(Coin coinType) {
        if (getOutstandingBal().doubleValue() == get_total().doubleValue()) {
            return 0;
        } else if (getOutstandingBal().doubleValue() < get_total().doubleValue()) {
            return 0;
        } else {
            return (int) ((this.outstandingBal.doubleValue() - get_total().doubleValue()) / coinType.getCoinDenomination().doubleValue());
        }
    }
}

class Tester {
    //Menu to checkout from the cart
    static void purchaseMenu(RetailItem retailItem, CashRegister cashRegister) {
        Scanner scanner = new Scanner(System.in);
        boolean menuOptions = true;
        MENU_LOOP:
        while (menuOptions) {
            char value = scanner.next().charAt(0);
            switch (value) {
                case 'y':
                    cashRegister.purchase_item(retailItem);
                    break MENU_LOOP;
                case 'n':
                    break MENU_LOOP;
                default:
                    System.out.println("Invalid choice!\nPlease enter a valid input");
                    menuOptions = true;
                    break;

            }
        }
    }

    public static void main(String[] args) {

        RetailItem item_1 = new RetailItem("Jacket", "Sleeveless Jacket"
                , 12, new BigDecimal(59.95));
        RetailItem item_2 = new RetailItem("Designer Jeans", "Blue Jeans"
                , 40, new BigDecimal(34.95));
        RetailItem item_3 = new RetailItem("Shirt", "Buttoned Shirt",
                20, new BigDecimal(24.95));
        Scanner scanner = new Scanner(System.in);
        String message = "Welcome to the store\n" +
                "Choose items: \n1:Jacket\n2:Designer Jeans\n3:Shirt\n" +
                "Press \'e\' to exit , \'c\' to checkout, and any other key to continue";
        CashRegister cart = new CashRegister();
        //The user Input for the first menu
        char userInput;
        //The Loop for the menu
        mainLoop:
        do {
            System.out.println(message);
            userInput = scanner.next().charAt(0);
            SWITCH_LOOP:
            switch (userInput) {
                case '1':
                    System.out.println(item_1.toString() + "\nPress \'y\' to purchase\nPress \'n\'to go back");
                    purchaseMenu(item_1, cart);
                    break;
                case '2':
                    System.out.println(item_2.toString() + "\nPress \'y\'\nPress  to purchase\'n\'to go back");
                    purchaseMenu(item_2, cart);
                    break;
                case '3':
                    System.out.println(item_3.toString() + "\nPress \'y\'\nPress  to purchase\'n\'to go back");
                    purchaseMenu(item_3, cart);
                    break;
                case 'e':
                    System.out.println("Thank you for shopping ");
                    break;
                case 'c':
                    for (RetailItem retailItem : cart.getRetailItems()) {
                        System.out.println("Item: " + retailItem.getItemName() + " Price: LKR" + retailItem.getPrice());
                    }
                    System.out.println("_________________________________________________________________________________" +
                            "\n\nYour total is: LKR " + cart.get_total() + "\nTo confirm order press \'y\'\nPress \'n\' " +
                            "to cancel order");
                    //If the Cart is empty it will be sent to the new menu
                    if (cart.get_total().doubleValue() > 0) {
                        WHILE_LOOP:
                        while (true) {
                            char input = scanner.next().charAt(0);
                            switch (input) {
                                case 'y':
                                    for (RetailItem retailItem :
                                            cart.getRetailItems()) {
                                        retailItem.setUnitsInInventory(retailItem.getUnitsInInventory() - 1);
                                        System.out.println("Deposit for each denomination");
                                        do {
                                            //The deposit for the 2 LKR denomination
                                            System.out.println("Enter the number of 2 LKR coins to pay:\n");
                                            int twoCoinCount = scanner.nextInt();
                                            Coin twoRupees = new Coin(new BigDecimal(2));
                                            cart.receivePayment(twoCoinCount, twoRupees);
                                            System.out.println("Change for the number of 2 LKR Denomination is: " + cart.giveChange(twoRupees));

                                            //The deposit for the 5 LKR denomination
                                            System.out.println("Enter the number of 5 LKR  coins to pay:\n");
                                            int fiveCoinCount = scanner.nextInt();
                                            Coin fiveRupees = new Coin(new BigDecimal(5));
                                            cart.receivePayment(fiveCoinCount, fiveRupees);
                                            System.out.println("Change for the number of 5 LKR Denomination is: " + cart.giveChange(fiveRupees));

                                            //The deposit for the 10 LKR denomination
                                            System.out.println("Enter the number of 10 LKR  coins to pay:\n");
                                            int tenCoinCount = scanner.nextInt();
                                            Coin tenRupees = new Coin(new BigDecimal(10));
                                            cart.receivePayment(tenCoinCount, tenRupees);
                                            System.out.println("Change for the number of 10 LKR Denomination is: " + cart.giveChange(tenRupees));


                                        }
                                        while (cart.getOutstandingBal().doubleValue() <= cart.get_total().doubleValue());
                                    }
                                    System.out.println("To exit press \'e\'\nTo go back to the store" +
                                            " press \'c\'");
                                    char new_input = scanner.next().charAt(0);
                                    switch (new_input) {
                                        case 'c':
                                            break WHILE_LOOP;
                                        case 'e':
                                            System.out.println("Thank you for shopping ");
                                            break mainLoop;
                                        default:
                                            System.out.println("Invalid choice!\nPlease enter a valid input");
                                    }
                                    break;
                                case 'n':
                                    break WHILE_LOOP;
                                default:
                                    System.out.println("Invalid choice!\nPlease enter a valid input");
                            }
                        }
                        break;
                    } else {
                        continue;
                    }
                default:
                    System.out.println("Invalid choice!\nPlease enter a valid input");
            }

        } while (userInput != 'e');


    }


}