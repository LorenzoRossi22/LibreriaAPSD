package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto all'inserimento di un dato tramite posizione. */
public interface InsertableAtSequence<Data> extends Sequence<Data>{ // Must extend Sequence

  void InsertAt(Data dat, Natural nat);

  default void InsertFirst(Data dat){
    InsertAt(dat, new Natural(0));
  }

  default void InsertLast(Data dat){
    InsertAt(dat, Size());
  }
}
