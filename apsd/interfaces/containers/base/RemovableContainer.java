package apsd.interfaces.containers.base;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container{ // Must extend Container

  boolean Remove(Data dat);

  default boolean RemoveAll(TraversableContainer<Data> TravC){
    
    boolean cont = TravC.TraverseForward(elem -> {
        return !Remove(elem);   
    });

    return !cont;
  }

  default boolean RemoveSome(TraversableContainer<Data> TravC){
    
    boolean cont = TravC.TraverseForward(elem -> {
        if (Remove(elem)) {
            return true;   
        }
        return false;      
    });

    return cont;
  }

}
