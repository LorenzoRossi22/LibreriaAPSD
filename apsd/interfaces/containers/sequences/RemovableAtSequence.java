package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;

/** Interface: Sequence con supporto alla rimozione di un dato tramite posizione. */
public interface RemovableAtSequence<Data> extends Sequence<Data>{ // Must extend Sequence

  default void RemoveAt(Natural nat){
    AtNRemove(nat);
  }

  Data AtNRemove(Natural nat);

  default void RemoveFirst(){
    Natural pos = Search(GetFirst());
    if (pos != null)  AtNRemove(pos);
  }

  default Data FirstNRemove(){
    Data first = GetFirst();
    RemoveFirst();
    return first;
  }

  default void RemoveLast(){
    Natural size = Size();
    if (size.ToLong() == 0) return;
    Natural pos = Natural.Of(size.ToLong() - 1);
    AtNRemove(pos);
  }

  default Data LastNRemove(){
    Natural size = Size();
    if (size.ToLong() == 0) return null;
    Natural pos = Natural.Of(size.ToLong() - 1);
    return AtNRemove(pos);
  }
}
