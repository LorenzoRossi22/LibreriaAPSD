package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore in avanti. */
public interface ForwardIterator<Data> extends Iterator<Data>{ // Must extend Iterator

  default void Next(){
    if (IsValid()) {
        DataNNext();
    }
  }

  default void Next(Natural nat){
    Next(nat.ToLong());
  }

  default void Next(long steps) {
    for(;steps>0; --steps, Next()){};
  }
  Data DataNNext();

  default boolean ForEachForward(Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNNext())) { return true; }
      }
    }
    return false;
  }
}
