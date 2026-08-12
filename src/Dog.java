public class Dog {
    private String name;
    private String breed;
    private int age;
    private int weight;
    private double tailLength;
    private Owner owner;

    public Dog(String name, String breed, int age, int weight){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("namn kan inte vara null/tomt.");
        }
        if(breed == null || breed.trim().isEmpty()){
            throw new IllegalArgumentException("breed cannot be null or empty.");
        }
        if(weight < 0 || age < 0){
            throw new IllegalArgumentException("ålder eller vikt kan inte vara negativt.");
        }
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        this.breed = breed.substring(0, 1).toUpperCase() + breed.substring(1).toLowerCase();
        this.age = age;
        this.weight = weight;
    }

    public Dog(String name, String breed, int age, int weight, Owner owner){
        this.owner = owner;
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("namn kan inte vara null/tomt.");
        }
        if(breed == null || breed.trim().isEmpty()){
            throw new IllegalArgumentException("breed cannot be null or empty.");
        }
        if(weight < 0 || age < 0){
            throw new IllegalArgumentException("ålder eller vikt kan inte vara negativt.");
        }
    }
    
    public String getName(){
        return name;
    }
    public Owner getOwner(){
        return owner;
    }
     public boolean setOwner(Owner newOwner){
        if (owner == newOwner){
            return false; // gör så inget ändras
        }
        if (owner == null){
            owner = newOwner;
            return true;
        }
        if(owner != null){
            owner = newOwner;
            return true;
        }
        return true;
    }

    public String getBreed(){
        return breed;
    }

    public void updateAge(int newAgeByValue){
        if(newAgeByValue <= 0){
            return;
        }
        if((long)newAgeByValue + (long)age > Integer.MAX_VALUE || newAgeByValue > Integer.MAX_VALUE || age > Integer.MAX_VALUE){
            age = Integer.MAX_VALUE;
            return;
        }
            age = age + newAgeByValue;  
            return;   
    }
    
    public int getAge(){
        return age;
    }
    public int getWeight(){
        return weight;
    }
    public double getTailLength(){
        tailLength = (double)age*(double)weight/10;
        if (breed.equalsIgnoreCase("tax") || breed.equalsIgnoreCase("dachshund")){
            tailLength = 3.7;
        }
        return tailLength;
    }
    public String toString(){
        return getName() +" "+ getBreed() +" "+ getAge() +" "+ getWeight() +" "+ getTailLength();
    }


}
