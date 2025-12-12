package apsd.interfaces.containers.sequences;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data>{ 

  default Data GetAt(Natural nat){
    long index = ExcIfOutOfBound(nat);

    ForwardIterator<Data> iterator = FIterator();
    for (long i = 0; i < index; i++) {
      iterator.Next();
    }

    return iterator.DataNNext();
  }

  default Data GetFirst(){
    // JUnit si aspetta IndexOutOfBoundsException, non IllegalStateException
    if (Size().ToLong() == 0) throw new IndexOutOfBoundsException("Sequence vuota"); 
    return GetAt(Natural.Of(0));
  }

  default Data GetLast(){
    long size = Size().ToLong();
    if (size == 0) throw new IndexOutOfBoundsException("Sequence vuota");
    return GetAt(Natural.Of(size - 1));
  }

  default Natural Search(Data dat){
    ForwardIterator<Data> iterator = FIterator();
    long index = 0;

    while (iterator.IsValid()) {
      Data current = iterator.DataNNext();
      if ((current == null && dat == null) || (current != null && current.equals(dat))) {
        return new Natural(index);
      }
      index++;
    }

    return null;
  }

  default boolean IsInBound(Natural nat){
    if (nat == null) return false;
    long i = nat.ToLong();
    return i < Size().ToLong();
  }

  default long ExcIfOutOfBound(Natural num) {
    if (num == null) throw new NullPointerException("Un numero naturale non può essere vuoto");
    long idx = num.ToLong();
    if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Indice non valido");
    return idx;
  }

  Sequence SubSequence(Natural start, Natural end);
}