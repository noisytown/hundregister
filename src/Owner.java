public class Owner {
    private String name;
    private static final int MAX_AMOUNT_DOG = 7;
    private Dog[] currentDogs = new Dog[MAX_AMOUNT_DOG];
    private int howManyDogsCurrently;
    

    public Owner(String name){
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return name;
    }

    public Owner (String name, Dog[] currentDogs){
        this.name = name;
        this.currentDogs = currentDogs;
    }

    public boolean ownsMaxDogs(){
        if(howManyDogsCurrently >= MAX_AMOUNT_DOG){
            return true;
        }
        return false;
    }
 public boolean addDog(Dog dog){
        if(ownsMaxDogs() == true){
            return false;
        }
        for(int i = 0; i < howManyDogsCurrently; i++){ // måste loopa för att kontrollera varje plats i arrayen
            if(currentDogs[i].getName() == dog.getName()){
                return false;
            }
        }
        currentDogs[howManyDogsCurrently] = dog;
        howManyDogsCurrently++;
        return true;
    }
    public boolean ownsAnyDog(){
        if(howManyDogsCurrently == 0 ){
            return false;
        }
        return true;
    }

  //  public boolean removeDog(String dogName){
        // lalala kolla senare
    
    public boolean removeDog(Dog dog){
    if(ownsAnyDog() == false){
        return false;
    }    
    howManyDogsCurrently --;
    return true;    
    }


    public boolean ownsDog(String dogName){ //ska göra en loop som går igenom arrayen och letar
        for(int i = 0; i < howManyDogsCurrently; i++){
            if(currentDogs[i].getName().equalsIgnoreCase(dogName)){
                return true;
            }
        }
        return false;
    }
    public boolean ownsDog(Dog dog){
         for(int i = 0; i < howManyDogsCurrently; i++){
            if(currentDogs[i] == dog){
                return true;
            }
        }
        return false;
    }

    public Dog[] getDogs(){
        return currentDogs;
    }

}
