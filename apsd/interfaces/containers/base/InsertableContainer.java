package apsd.interfaces.containers.base;
import apsd.interfaces.containers.base.TraversableContainer;
/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container{ // Must extend Container

  boolean Insert(Data data); // Insert a single data item.
  // Insert multiple data items. true->success, false -> fail
  default boolean InsertAll(TraversableContainer<Data> TravCont){ 
    boolean inserted = false;
    if (TravCont == null) {
      return inserted;
    }
    inserted = TravCont.TraverseForward(dat -> {
      return !Insert(dat);
    });
    return !inserted;
  }

  // InsertSome

}
