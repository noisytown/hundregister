import java.util.ArrayList;
import java.util.Comparator;

public class DogRegister {

    private final OwnerCollection ownerCollection = new OwnerCollection();
    private final InputReader input = new InputReader();

    public static void main(String[] args) {
        new DogRegister().run();
    }

    private void run() {
        boolean running = true;

        while (running) {
            System.out.println("-----SUper duper dog register!-----");
            System.out.println("1. Add owner (AO)");
            System.out.println("2. Remove owner (RO)");
            System.out.println("3. Add dog (AD)");
            System.out.println("4. Remove dog (RD)");
            System.out.println("5. Change owner (CO)");
            System.out.println("6. List owners (LO)");
            System.out.println("7. List dogs (LD)");
            System.out.println("8. Increase age (IA)");
            System.out.println("9. Exit (EX)");

            String command = input.readString("Enter a command to choose option: ").trim();
            switch (command.toUpperCase()) {
                case "1":
                case "AO":
                case "ADD OWNER":
                    addOwner();
                    break;
                case "2":
                case "RO":
                case "REMOVE OWNER":
                    removeOwner();
                    break;
                case "3":
                case "AD":
                case "ADD DOG":
                    addDog();
                    break;
                case "4":
                case "RD":
                case "REMOVE DOG":
                    removeDog();
                    break;
                case "5":
                case "CO":
                case "CHANGE OWNER":
                    changeOwner();
                    break;
                case "6":
                case "LO":
                case "LIST OWNERS":
                    listOwners();
                    break;
                case "7":
                case "LD":
                case "LIST DOGS":
                    listDogs();
                    break;
                case "8":
                case "IA":
                case "INCREASE AGE":
                    increaseAge();
                    break;
                case "9":
                case "EX":
                case "EXIT":
                    running = false;
                    break;
                default:
                    System.out.println("ERROR: invalid command.");
            }
        }
    }


    private void addOwner() {
        String ownerName = input.readString("Enter the owner's name");
        if (ownerName == null || ownerName.isBlank()) {
            System.out.println("ERROR: invalid owner name.");
            return;
        }

        if (ownerCollection.containsOwner(ownerName)) {
            System.out.println("Looks like " + ownerName + " is already added.");
            return;
        }

        ownerCollection.addOwner(new Owner(ownerName));
        System.out.println(ownerName + " added to register.");
    }

    private void removeOwner() {
        String ownerName = input.readString("Enter the name of the owner you want to remove");
        if (ownerCollection.getOwner(ownerName) == null) {
            System.out.println(ownerName + " not found.");
            return;
        }

        ownerCollection.removeOwner(ownerName);
        System.out.println(ownerName + " removed from register");
    }

    private void addDog() {
        String ownerName = input.readString("Enter the owner's name");

        Owner owner = ownerCollection.getOwner(ownerName);
        if (owner == null) {
            System.out.println(ownerName + " not found.");
            return;
        }
        String dogName = input.readString("Enter the dog's name");
        
        if (owner.ownsDog(dogName)) {
            System.out.println("Looks like " + dogName + " is already added.");
            return;
        }
            String dogBreed = input.readString("Enter the dog's breed");
            int dogAge = input.readInt("Enter the dog's age");
            int dogWeight = input.readInt("Enter the dog's weight without decimals");

            Dog dog = new Dog(dogName, dogBreed, dogAge, dogWeight);

            if (dog.setOwner(owner)) {
                System.out.println(dogName + " added to registry.");
            } else {
                System.out.println("ERROR: could not add dog");
            }
    }

    private void removeDog() {
        String ownerName = input.readString("To remove dog; enter the owner's name");
    
        Owner owner = ownerCollection.getOwner(ownerName);
        if (owner == null) {
            System.out.println(ownerName + " not found.");
            return;
        }
        String dogName = input.readString("To remove dog; enter the dog's name");
        if (owner.removeDog(dogName)) {
            System.out.println(dogName + " removed from register");
        } else {
            System.out.println(dogName + " not found.");
        }
    }

    private void changeOwner() {

        String oldOwnerName = input.readString("Enter the name of the current owner");
        Owner oldOwner = ownerCollection.getOwner(oldOwnerName);
        if (oldOwner == null) {
            System.out.println(oldOwnerName + " not found.");
            return;
        }

        String dogName = input.readString("Enter the dog's name");
        Dog dog = confirmIfDog(oldOwner, dogName);
        if (dog == null) {
            System.out.println(dogName + " not found.");
            return;
        }

        String newOwnerName = input.readString("Enter the name of the new owner");
        Owner newOwner = ownerCollection.getOwner(newOwnerName);
        if (newOwner == null) {
            System.out.println(newOwnerName + " not found.");
            return;
        }

        

        oldOwner.removeDog(dog);
        newOwner.addDog(dog);
        System.out.println(dogName + " changed owner from " + oldOwnerName + " to " + newOwnerName + ".");
    }

    private Dog confirmIfDog(Owner owner, String dogName) {
        if (owner == null || dogName == null) {
            return null;
        }

        for (Dog dog : owner.getDogs()) { //basically kolla alla hundar hos ägaren..
            if (dog != null && dog.getName().equalsIgnoreCase(dogName)) { //findung match
                return dog;
            }
        }
        return null;
    }

    private void listOwners() {
        ArrayList<Owner> owners = ownerCollection.getAllOwners();
        if (owners == null || owners.isEmpty()) {
            System.out.println("No owners registered yet.");
            return;
        }

        for (Owner owner : owners) {
            System.out.println(owner);
        }
    }

    private void listDogs() {
        double minimumTailLength = input.readDouble("Enter the dog's minimum tail length");
        ArrayList<Dog> dogs = getAllDogs();
        dogs.sort(Comparator.comparing(Dog::getName));

        for (Dog dog : dogs) {
            if (dog.getTailLength() >= minimumTailLength) {
                System.out.println(dog);
            }
        }
    }

    private ArrayList<Dog> getAllDogs() {
        ArrayList<Dog> dogs = new ArrayList<>();

        for (Owner owner : ownerCollection.getAllOwners()) { // alla owners alla dogs
            for (Dog dog : owner.getDogs()) {
                dogs.add(dog);
            }
        }
        return dogs;
    }

    private void increaseAge() {
        for (Dog dog : getAllDogs()) {
            dog.updateAge(1);
        }
        System.out.println("Age has been updated.");
    }
}
