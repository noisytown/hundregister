public class Owner {
    private String name;
    private static final int MAX_AMOUNT_DOG = 7;
    private Dog[] currentDogs = new Dog[MAX_AMOUNT_DOG];
    private int howManyDogsCurrently;

    public Owner(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public Owner(String name, Dog[] currentDogs) {
        this.name = name;
        this.currentDogs = currentDogs;

        for (Dog dog : currentDogs) {
            if (dog != null) {
                howManyDogsCurrently++;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean ownsMaxDogs() {
        if (howManyDogsCurrently >= MAX_AMOUNT_DOG) {
            return true;
        }
        return false;
    }

    public boolean addDog(Dog dog) {
        if (ownsMaxDogs() == true) {
            return false;
        }
        // Check if dog with same name or same object already exists
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i].getName().equalsIgnoreCase(dog.getName()) || currentDogs[i] == dog) {
                return false;
            }
        }
        currentDogs[howManyDogsCurrently] = dog;
        howManyDogsCurrently++;
        return true;
    }

    public boolean ownsAnyDog() {
        if (howManyDogsCurrently == 0) {
            return false;
        }
        return true;
    }

    public boolean removeDog(String dogName) {
        if (ownsAnyDog() == false) {
            return false;
        }
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i].getName().equalsIgnoreCase(dogName)) {
                currentDogs[i] = null;
                for (int j = i; j < howManyDogsCurrently - 1; j++) {
                    currentDogs[j] = currentDogs[j + 1];
                }
                currentDogs[howManyDogsCurrently - 1] = null;
                howManyDogsCurrently--;
                return true;
            }
        }
        return false;
    }
    // lalala kolla senare

    public boolean removeDog(Dog dog) {
        if (ownsAnyDog() == false) {
            return false;
        }

        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i] == dog) {
                for (int j = i; j < howManyDogsCurrently - 1; j++) {
                    currentDogs[j] = currentDogs[j + 1];
                }

                currentDogs[howManyDogsCurrently - 1] = null;
                howManyDogsCurrently--;

                return true;
            }
        }

        return false;
    }

    public boolean ownsDog(String dogName) { // ska göra en loop som går igenom arrayen och letar
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i].getName().equalsIgnoreCase(dogName)) {
                return true;
            }
        }
        return false;
    }

    public boolean ownsDog(Dog dog) {
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i] == dog) {
                return true;
            }
        }
        return false;
    }

    public Dog[] getDogs() {
        return currentDogs;
    }

}
