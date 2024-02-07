package machine;

public class CoffeeMachine {
    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int disposableCups;
    private final int money;
    private CoffeeMachineState currentState;

    public CoffeeMachine(CoffeeMachineState currentState) {

        this.water = 400;
        this.milk = 540;
        this.coffeeBeans = 120;
        this.disposableCups = 9;
        this.money = 550;
        this.currentState = currentState;
    }

    public int[] getResources() {
        return new int[] {water, milk, coffeeBeans, disposableCups, money};
    }

    private String[] getOrderOfResources() {
        return new String[] {"water", "milk", "coffee beans", "cups", "money"};
    }

    public CoffeeMachineState getCoffeeMachineState() {
        return this.currentState;
    }

    public void setCoffeeMachineState(CoffeeMachineState newState) {
        this.currentState = newState;
    }

    private static int[] getIngredientsPerCoffee (int selectedCoffee) {

        int[] espresso = new int[] {250, 0, 16, 4};
        int[] latte = new int[] {350, 75, 20, 7};
        int[] cappuccino = new int[] {200, 100, 12, 6};
        int[][] coffeeTypes = {espresso, latte, cappuccino};
        return coffeeTypes[selectedCoffee];
    }

    public void printOptions() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public void printCoffeeMachineState(int[] state) {

        System.out.println("\nThe coffee machine has:");
        System.out.println(state[0] + " ml of water");
        System.out.println(state[1] + " ml of milk");
        System.out.println(state[2] + " g of coffee beans");
        System.out.println(state[3] + " disposable cups");
        System.out.println("$" + state[4] + " of money\n");
    }

    public void printFillingMenu(int counter) {

        String[] fillingMenu = new String[]
                {"\nWrite how many ml of water you want to add:",
                        "Write how many ml of milk you want to add:",
                        "Write how many grams of coffee beans you want to add:",
                        "Write how many disposable cups you want to add:"};
        System.out.println(fillingMenu[counter]);
    }

    public int[] fillCoffeeMachineResources(int quantity, int itemCounter, int[] currentResources) {

        if(itemCounter == 0) {
            currentResources[0] += quantity;
            return currentResources;

        } else if(itemCounter == 1) {
            currentResources[1] += quantity;
            return currentResources;

        } else if(itemCounter == 2) {
            currentResources[2] += quantity;
            return currentResources;

        } else if(itemCounter == 3) {
            currentResources[3] += quantity;
            return currentResources;
        }
        System.out.println("Invalid option");
        return currentResources;
    }

    public int[] collectCoffeeMachinePayment(int [] currentResources) {

        System.out.println("\nI gave you $" + currentResources[4] + "\n");
        currentResources[4] = 0;
        return currentResources;
    }

    public int[] buyCoffee(String userInput, int[] currentCoffeeMachineResources) {

        int selection;

        if(userInput.equalsIgnoreCase("back")) {
            return currentCoffeeMachineResources;
        }

        try {
            selection = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input. Please enter a option between 1 and 3 or 'Back'.\n");
            return currentCoffeeMachineResources;
        }

        if (selection < 1 || selection > 3) {
            System.out.println("Invalid selection. Please enter a option between 1 and 3 or 'Back'.\n");
            return currentCoffeeMachineResources;
        }

        String[] orderOfResources = getOrderOfResources();
        int[] selectedCoffeeTypeIngredients = getIngredientsPerCoffee(selection - 1);
        int coffeeCounter = 0;

        for (int i = 0; i < 3; i++) {

            if (selectedCoffeeTypeIngredients[i] == 0) {
                continue;
            }

            if (currentCoffeeMachineResources[i] < selectedCoffeeTypeIngredients[i]) {
                coffeeCounter = 0;
                System.out.println("Sorry, not enough " + orderOfResources[i] + "!");
                break;
            }

            int currentCoffeeCounter = currentCoffeeMachineResources[i] / selectedCoffeeTypeIngredients[i];

            if (coffeeCounter == 0 || currentCoffeeCounter <= coffeeCounter) {
                coffeeCounter = currentCoffeeCounter;
            }
        }

        if (coffeeCounter > 0) {
            for (int i = 0; i < 3; i++) {
                currentCoffeeMachineResources[i] -= selectedCoffeeTypeIngredients[i];
            }

            currentCoffeeMachineResources[3] -= 1;
            currentCoffeeMachineResources[4] += selectedCoffeeTypeIngredients[3];
            System.out.println("I have enough resources, making you a coffee!");

        }
        System.out.println();
        return currentCoffeeMachineResources;
    }
}
