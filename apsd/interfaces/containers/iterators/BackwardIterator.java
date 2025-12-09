package apsd.interfaces.containers.iterators;

import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Predicate;

/** Interface: Iteratore all'indietro. */
public interface BackwardIterator<Data> extends Iterator<Data>{ // Must extend Iterator

  default void Prev(){
    if (IsValid()) {
        DataNPrev();
    }
  }

  default void Prev(Natural nat){
    if (nat == null) return;

    long steps = nat.ToLong();
    for (long i = 0; i < steps && IsValid(); i = i + 1) {
        DataNPrev();
    }
  }

  Data DataNPrev();

  default boolean ForEachBackward(Predicate<Data> fun) {
    if (fun != null) {
      while (IsValid()) {
        if (fun.Apply(DataNPrev())) { return true; }
      }
    }
    return false;
  }
}
