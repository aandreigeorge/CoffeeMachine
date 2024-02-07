package machine;
import java.util.Scanner;

public class CoffeeMachineUser {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CoffeeMachine barista3000 = new CoffeeMachine(CoffeeMachineState.START);
        int[] coffeeMachineResources = barista3000.getResources();

        while (!barista3000.getCoffeeMachineState().equals(CoffeeMachineState.EXIT)) {
            switch (barista3000.getCoffeeMachineState()){
                case START:
                    barista3000.printOptions();
                    barista3000.setCoffeeMachineState(validateInput(scanner.nextLine().toUpperCase()));
                    break;

                case BUY:
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                    coffeeMachineResources = barista3000.buyCoffee(scanner.nextLine(), coffeeMachineResources);
                    barista3000.setCoffeeMachineState(CoffeeMachineState.START);
                    break;

                case FILL:
                    for(int counter = 0; counter < coffeeMachineResources.length - 1; counter++) {
                        barista3000.printFillingMenu(counter);
                        try {
                            coffeeMachineResources = barista3000.fillCoffeeMachineResources(Integer.parseInt(scanner.nextLine()), counter, coffeeMachineResources);
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter only numbers");
                            barista3000.setCoffeeMachineState(CoffeeMachineState.START);
                            break;
                        }
                    }
                    System.out.println();
                    barista3000.setCoffeeMachineState(CoffeeMachineState.START);
                    break;

                case TAKE :
                    coffeeMachineResources = barista3000.collectCoffeeMachinePayment(coffeeMachineResources);
                    barista3000.setCoffeeMachineState(CoffeeMachineState.START);
                    break;

                case REMAINING:
                    barista3000.printCoffeeMachineState(coffeeMachineResources);
                    barista3000.setCoffeeMachineState(CoffeeMachineState.START);
                    break;
            }
        }
        scanner.close();
    }

    private static CoffeeMachineState validateInput(String input) {

        try {
            return CoffeeMachineState.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid selection. Actions are -> buy, fill, take, remaining and exit\n");
            return CoffeeMachineState.START;
        }
    }
}
