package apsd.interfaces.containers.base;


/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container{ // Must extend Container

  boolean Insert(Data dat);
                                
  default boolean InsertAll(TraversableContainer<Data> TravC){

    boolean cont = TravC.TraverseForward(elem -> {
        return !Insert(elem);   
    });

    return !cont;
  }

  default boolean InsertSome(TraversableContainer<Data> TravC) {
    if(TravC == null) return false;
    boolean[] changed = {false};
    
    TravC.TraverseForward(elem -> {
        if (Insert(elem)) {
            changed[0] = true;
        }
        return false; 
    });

    return changed[0];
  }
}
