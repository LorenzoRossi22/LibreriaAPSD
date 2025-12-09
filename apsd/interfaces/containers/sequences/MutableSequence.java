package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.MutableIterableContainer;
import apsd.interfaces.containers.iterators.MutableForwardIterator;

/** Interface: Sequence & MutableIterableContainer con supporto alla scrittura tramite posizione. */
public interface MutableSequence<Data> extends Sequence<Data>,MutableIterableContainer<Data>{ // Must extend Sequence and MutableIterableContainer

  default void SetAt(Data dat, Natural nat){
    long pos = ExcIfOutOfBound(nat);

    MutableForwardIterator<Data> iterator = FIterator();

    for (long i = 0; i < pos; ++i) {
        if (!iterator.IsValid())  throw new IndexOutOfBoundsException();
        iterator.Next();
    }

    if (!iterator.IsValid())  throw new IndexOutOfBoundsException();

    iterator.SetCurrent(dat);
  }

  default Data GetNSetAt(Data dat, Natural nat){
    long pos = ExcIfOutOfBound(nat);

    MutableForwardIterator<Data> iterator = FIterator();

    for (long i = 0; i < pos; ++i) {
        if (!iterator.IsValid())
            throw new IndexOutOfBoundsException();
        iterator.Next();
    }

    if (!iterator.IsValid())  throw new IndexOutOfBoundsException();

    Data old = iterator.GetCurrent();
    iterator.SetCurrent(dat);
    return old;
  }

  default void SetFirst(Data dat){
    if (Size().ToLong() == 0) throw new IndexOutOfBoundsException("Sequenza vuota");
    SetAt(dat, new Natural(0));
  }

  default Data GetNSetFirst(Data dat){
    if (Size().ToLong() == 0) throw new IndexOutOfBoundsException("Sequenza vuota");
    return GetNSetAt(dat, new Natural(0));
  }

  default void SetLast(Data dat){
    long size = Size().ToLong();
    if (size == 0)  throw new IndexOutOfBoundsException("Sequenza vuota");
    SetAt(dat, new Natural(size - 1));
  }

  default Data GetNSetLast(Data dat){
    long size = Size().ToLong();
    if (size == 0)  throw new IndexOutOfBoundsException("Sequenza vuota");
    return GetNSetAt(dat, new Natural(size - 1));
  }

  default void Swap(Natural nat1, Natural nat2){
    long pos1 = ExcIfOutOfBound(nat1);
    long pos2 = ExcIfOutOfBound(nat2);

    if (pos1 == pos2) return;

    Data v1 = GetAt(nat1);
    Data v2 = GetAt(nat2);
    
    SetAt(v1, nat2);
    SetAt(v2, nat1);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  MutableSequence<Data> Subsequence(Natural nat1, Natural nat2);

}
