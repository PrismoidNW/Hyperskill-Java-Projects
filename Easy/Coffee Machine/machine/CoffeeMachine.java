package machine;

public class CoffeeMachine {

    public static void main(String[] args) {

    }

    private static int checkValue() {
        /*if (number < 0) {
            return -1;
        } else if (number == 0) {
            return 0;
        }*/
        return 1;
    }
    /*private static int water = 400;
    private static int milk = 540;
    private static int beans = 120;
    private static int disposableCups = 9;
    private static int money = 550;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        whileLoop:
        while (true) {
            String action = scanner.nextLine();
            switch (action.toLowerCase()) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    printStats();
                    break;
                case "exit": break whileLoop;
            }
        }
    }

    private static void printStats() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + money + " of money");
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private static void buy() {
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        int action;
        try {
            action = scanner.nextInt();
        }catch (InputMismatchException e){
            return;
        }

        if (!canMakeCups(action)) {
            System.out.println();
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        switch (action) {
            case 1:
                water = (water - 250) <= 0 ? 0 : water - 250;
                beans = (beans - 16) <= 0 ? 0 : beans - 16;
                money += 4;
                disposableCups--;
                break;
            case 2:
                water = (water - 350) <= 0 ? 0 : water - 350;
                milk = (milk - 75) <= 0 ? 0 : milk - 75;
                beans = (beans - 20) <= 0 ? 0 : beans - 20;
                money += 7;
                disposableCups--;
                break;
            case 3:
                water = (water - 200) <= 0 ? 0 : water - 200;
                milk = (milk - 100) <= 0 ? 0 : milk - 100;
                beans = (beans - 12) <= 0 ? 0 : beans - 12;
                money += 6;
                disposableCups--;
                break;
        }
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private static void fill() {
        System.out.println("Write how many ml of water you want to add:");
        int newWater = scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add:");
        int newMilk = scanner.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        int newBeans = scanner.nextInt();

        System.out.println("Write how many disposable cups of coffee you want to add:");
        int newCups = scanner.nextInt();

        water += newWater;
        milk += newMilk;
        beans += newBeans;
        disposableCups += newCups;
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private static void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    private static boolean canMakeCups(int type) {
        boolean canMake = false;


        if (disposableCups <= 0) {
            System.out.println("Sorry, not enough disposable cups!");
            System.out.println();
            return false;
        } else {
            canMake = true;
        }

        switch (type) {
            case 1:
                if ((water / 250) == 0) {
                    System.out.println("Sorry, not enough water!");
                    return false;
                } else {
                    canMake = true;
                }
                if (((beans / 16) == 0)) {
                    System.out.println("Sorry, not enough coffee beans!");
                    return false;
                } else {
                    canMake = true;
                }
            case 2:
                if ((water / 350) == 0) {
                    System.out.println("Sorry, not enough water!");
                    return false;
                } else {
                    canMake = true;
                }

                if ((milk / 75) == 0) {
                    System.out.println("Sorry, not enough milk!");
                } else {
                    canMake = true;
                }

                if (((beans / 20) == 0)) {
                    System.out.println("Sorry, not enough coffee beans!");
                    return false;
                } else {
                    canMake = true;
                }
            case 3:
                if ((water / 200) == 0) {
                    System.out.println("Sorry, not enough water!");
                    return false;
                } else {
                    canMake = true;
                }

                if ((milk / 100) == 0) {
                    System.out.println("Sorry, not enough milk!");
                } else {
                    canMake = true;
                }
        }
        return canMake;
    }*/
}
