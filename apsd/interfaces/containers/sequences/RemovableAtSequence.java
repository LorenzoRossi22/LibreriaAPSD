package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data>{ // Must extend Sequence

  default void RemoveAt(Natural nat){
    AtNRemove(nat);
  }

  Data AtNRemove(Natural nat);

  default void RemoveFirst(){
    if(Size().ToLong() == 0) throw new IndexOutOfBoundsException("Sequence vuota");
    AtNRemove(Natural.Of(0));
  }

  default Data FirstNRemove(){
    if(Size().ToLong() == 0) throw new IndexOutOfBoundsException("Sequence vuota");
    Data first = GetFirst();
    RemoveFirst();
    return first;
  }

  default void RemoveLast(){
    Natural size = Size();
    if (size.ToLong() == 0) throw new IndexOutOfBoundsException("Sequence vuota");
    Natural pos = Natural.Of(size.ToLong() - 1);
    AtNRemove(pos);
  }

  default Data LastNRemove(){
    Natural size = Size();
    if (size.ToLong() == 0) throw new IndexOutOfBoundsException("Sequence vuota");
    Natural pos = Natural.Of(size.ToLong() - 1);
    return AtNRemove(pos);
  }
}
