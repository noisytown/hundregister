import java.util.ArrayList;
import java.util.Comparator;

public class OwnerCollection {
    // get all owners ska returnera en kopia, precis som getdogs

    private ArrayList<Owner> owners = new ArrayList<>();


    public boolean addOwner(Owner newOwner){
        if(newOwner == null && containsOwner(newOwner)){ //aja baja inte lägga till samma
            return false;
        }
        if(!containsOwner(newOwner)){
            owners.add(newOwner);
             return true;
        }
       return false;
    }

    public boolean removeOwner(String ownerName){
        if(ownerName == null){
            return false;
        }
        if(ownerName != null && containsOwner(ownerName)){
            for(Owner o : owners){
                if(o.getName().equalsIgnoreCase(ownerName)){ // kollar om något o innehåller ownerName
                    owners.remove(o);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeOwner(Owner owner){
        if(owner == null){
            return false;
        }
        if(owner != null && containsOwner(owner)){
            owners.remove(owner);
            return true;
        }

        return false;
    }

    public boolean containsOwner(String ownerName){
        if(ownerName != null){
            for(Owner o : owners){
                if(o.getName().equalsIgnoreCase(ownerName)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsOwner(Owner owner){
        if(owner != null){
            if(containsOwner(owner.getName())){
                return true;
            }
        }
        return false;
    }

    public Owner getOwner(String ownerName){
        if(ownerName != null){
            for(Owner o : owners){
                if(o.getName().equalsIgnoreCase(ownerName))
                    return o;
            }
        }
        return null;
    }  
    
    public ArrayList<Owner> getAllOwners(){
        ArrayList<Owner> copyOfOwnersSort = new ArrayList<>(owners);
        copyOfOwnersSort.sort(Comparator.comparing(Owner::getName));
        return copyOfOwnersSort;
    }

    public int size(){
        return owners.size();
    } 
}
