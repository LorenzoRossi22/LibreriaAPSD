package apsd.interfaces.containers.sequences;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data>{ // Must extend IterableContainer

  default Data GetAt(Natural nat){
    long index = ExcIfOutOfBound(nat);

    ForwardIterator<Data> iterator = FIterator();
    for (long i = 0; i < index; i++) {
      iterator.Next();
    }

    return iterator.DataNNext();
  }

  default Data GetFirst(){
    if (IsEmpty()) throw new IllegalStateException("Sequenza vuota");
    ForwardIterator<Data> iterator = FIterator();
    return iterator.DataNNext();
  }

  default Data GetLast(){
    if (IsEmpty()) throw new IllegalStateException("Sequenza vuota");

    ForwardIterator<Data> iterator = FIterator();
    Data dat = null;

    while (iterator.IsValid()) {
      dat = iterator.DataNNext();
    }

    return dat;
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
    if (num == null) throw new NullPointerException("Natural number cannot be null!");
    long idx = num.ToLong();
    if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
    return idx;
  }

  Sequence SubSequence(Natural start, Natural end);
}
