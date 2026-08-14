import java.util.Arrays;
import java.util.Comparator;

public class Owner {
    private String name;
    private static final int MAX_AMOUNT_DOG = 7;
    private Dog[] currentDogs = new Dog[MAX_AMOUNT_DOG];
    private int howManyDogsCurrently;

    // ok behövdes tydligen inte after all
    // public Owner(String name) {
    //     this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    // }

    public Owner(String name, Dog... currentDogs) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        this.currentDogs = new Dog[MAX_AMOUNT_DOG];

        for (Dog dog : currentDogs) {
            if (dog != null) {
                addDog(dog);
            }
        }
    }

    public String getName() {
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
        // letar i en loop för att kolla om hunden redan finns by name och objekt
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i].getName().equalsIgnoreCase(dog.getName()) || currentDogs[i] == dog) {
                return false;
            }
        }

        currentDogs[howManyDogsCurrently] = dog;
        howManyDogsCurrently++;
        
        if(dog.getOwner() == null || dog.getOwner() != this){ //byt ut null eller annan ägare mot denna ägare
            dog.setOwner(this);
        }
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
        if (currentDogs[i] != null && currentDogs[i].getName().equalsIgnoreCase(dogName)) {
            Dog saveDog = currentDogs[i]; // måste spara hunden typ innan jag tar bort den
            for (int j = i; j < howManyDogsCurrently - 1; j++) {
                currentDogs[j] = currentDogs[j + 1];
            }

            currentDogs[howManyDogsCurrently - 1] = null; //gör förra platsen till oanvänd(null)
            howManyDogsCurrently--;
            saveDog.setOwner(null); // nu kan jag faktiskt ta bort hunden från ägaren
            return true;
            }
        }
        return false;
    }

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
                dog.setOwner(null);

                return true;
            }
        }

        return false;
    }

    public boolean ownsDog(String dogName) { // ska göra en loop som går igenom arrayen och letar
        for (int i = 0; i < howManyDogsCurrently; i++) {
            if (currentDogs[i]!= null && currentDogs[i].getName().equalsIgnoreCase(dogName)) {
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
        Dog[] preventNulls = new Dog[howManyDogsCurrently]; //SLUTA HA NULL SPACES
        for (int i = 0; i < howManyDogsCurrently; i++) {
            preventNulls[i] = currentDogs[i];
        }
        Dog[] sortedCopied = Arrays.copyOf(preventNulls, howManyDogsCurrently);
        DogSorter.sort(SortingAlgorithm.SELECTION_SORT, Comparator.comparing(Dog::getName), sortedCopied);
        
        return sortedCopied; // måste sortera arrayen innan jag kan kopiera den, annars finns nulls kvar
    }

    @Override
    public String toString(){
        if (ownsAnyDog() == false) {
            return name + howManyDogsCurrently;
        }
        
        return name + howManyDogsCurrently + Arrays.toString(getDogs());
    }

}
