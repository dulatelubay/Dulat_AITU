public class Main {
    //Singleton pattern
    static class CoffeeShop {
        private static CoffeeShop instance;

        private CoffeeShop() {}

        //Method to get the single instance of the coffee shop.
        public static CoffeeShop getInstance() {
            if (instance == null) {
                instance = new CoffeeShop();
            }
            return instance;
        }

        //Process of ordering coffee
        public void placeOrder(CoffeeOrder order) {
            System.out.println("Order accepted: " + order);
            prepareOrder(order);
        }

        //Process of making a coffee
        private void prepareOrder(CoffeeOrder order) {
            System.out.println("Preparing your coffee...");
            try {
                Thread.sleep(2000);
                System.out.println("Adding: " + order.milk.getMilk());
                Thread.sleep(1000);
                System.out.println("Adding: " + order.syrup.getSyrup());
                Thread.sleep(1000);
                System.out.println("Coffee " + order.coffee.getName() + " is ready!");
            } catch (InterruptedException e) {
                System.out.println("Error while preparing coffee.");
            }
        }

        //Giving the coffee
        public void serveOrder(CoffeeOrder order) {
            System.out.println("Your coffee is ready: " + order);
        }
    }

    //Factory Method pattern
    abstract static class Coffee {
        protected String name;

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Espresso extends Coffee {
        public Espresso() {
            name = "Espresso";
        }
    }

    static class Cappuccino extends Coffee {
        public Cappuccino() {
            name = "Cappuccino";
        }
    }

    abstract static class CoffeeFactory {
        public abstract Coffee createCoffee();
    }

    static class EspressoFactory extends CoffeeFactory {
        @Override
        public Coffee createCoffee() {
            return new Espresso();
        }
    }

    static class CappuccinoFactory extends CoffeeFactory {
        @Override
        public Coffee createCoffee() {
            return new Cappuccino();
        }
    }

    //Abstract Factory pattern
    interface Milk {
        String getMilk();
    }

    interface Syrup {
        String getSyrup();
    }

    static class WholeMilk implements Milk {
        @Override
        public String getMilk() {
            return "Whole milk";
        }
    }

    static class AlmondMilk implements Milk {
        @Override
        public String getMilk() {
            return "Almond milk";
        }
    }

    static class VanillaSyrup implements Syrup {
        @Override
        public String getSyrup() {
            return "Vanilla syrup";
        }
    }

    static class CaramelSyrup implements Syrup {
        @Override
        public String getSyrup() {
            return "Caramel syrup";
        }
    }

    interface CoffeeIngredientsFactory {
        Milk createMilk();
        Syrup createSyrup();
    }

    static class CappuccinoIngredientsFactory implements CoffeeIngredientsFactory {
        @Override
        public Milk createMilk() {
            return new WholeMilk();
        }

        @Override
        public Syrup createSyrup() {
            return new VanillaSyrup();
        }
    }

    static class CustomIngredientsFactory implements CoffeeIngredientsFactory {
        @Override
        public Milk createMilk() {
            return new AlmondMilk();
        }

        @Override
        public Syrup createSyrup() {
            return new CaramelSyrup();
        }
    }

    //Prototype pattern
    static class CoffeeOrder implements Cloneable {
        private Coffee coffee;
        private Milk milk;
        private Syrup syrup;

        public CoffeeOrder(Coffee coffee, Milk milk, Syrup syrup) {
            this.coffee = coffee;
            this.milk = milk;
            this.syrup = syrup;
        }

        @Override
        protected CoffeeOrder clone() throws CloneNotSupportedException {
            return (CoffeeOrder) super.clone();
        }

        @Override
        public String toString() {
            return "Coffee: " + coffee.getName() + ", Milk: " + milk.getMilk() + ", Syrup: " + syrup.getSyrup();
        }
    }

    //Builder pattern
    static class CoffeeOrderBuilder {
        private Coffee coffee;
        private Milk milk;
        private Syrup syrup;

        public CoffeeOrderBuilder selectCoffee(Coffee coffee) {
            this.coffee = coffee;
            return this;
        }

        public CoffeeOrderBuilder addMilk(Milk milk) {
            this.milk = milk;
            return this;
        }

        public CoffeeOrderBuilder addSyrup(Syrup syrup) {
            this.syrup = syrup;
            return this;
        }

        public CoffeeOrder build() {
            return new CoffeeOrder(coffee, milk, syrup);
        }
    }

    //Main method to show how all of the patterns work
    public static void main(String[] args) throws CloneNotSupportedException {
        //Singleton
        CoffeeShop coffeeShop = CoffeeShop.getInstance();

        //fabric method
        CoffeeFactory espressoFactory = new EspressoFactory();
        Coffee espresso = espressoFactory.createCoffee();

        //Abstraction fabric
        CoffeeIngredientsFactory ingredientsFactory = new CappuccinoIngredientsFactory();
        Milk milk = ingredientsFactory.createMilk();
        Syrup syrup = ingredientsFactory.createSyrup();

        //Builder
        CoffeeOrderBuilder builder = new CoffeeOrderBuilder();
        CoffeeOrder order = builder.selectCoffee(espresso)
                .addMilk(milk)
                .addSyrup(syrup)
                .build();


        coffeeShop.placeOrder(order);
        coffeeShop.serveOrder(order);

        //Prototype
        CoffeeOrder clonedOrder = order.clone();
        coffeeShop.placeOrder(clonedOrder);
        coffeeShop.serveOrder(clonedOrder);
    }
}
