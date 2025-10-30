package apsd.interfaces.containers.base;
import TraversableContainer;
/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container{ // Must extend Container

  boolean Insert(Data data); // Insert a single data item.
  // Insert multiple data items.
  default boolean InsertAll(TraversableContainer<Data> TravCont){ 
  }

  // InsertSome

}
