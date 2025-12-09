package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto all'inserimento di un dato tramite posizione. */
public interface InsertableAtSequence<Data> extends Sequence<Data>{ // Must extend Sequence

  void InsertAt(Natural nat, Data dat);

  default void InsertFirst(Data dat){
    InsertAt(new Natural(0), dat);
  }

  default void InsertLast(Data dat){
    InsertAt(Size(), dat);
  }
}
