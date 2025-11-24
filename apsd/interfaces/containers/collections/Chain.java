package apsd.interfaces.containers.collections;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data>,Set<Data>{ // Must extend RemovableAtSequence

  Chain<Data> New();

  default boolean InsertIfAbsent(Data dat){
    if (dat == null) return false;
    Natural pos = Search(dat);
    if (pos != null) return false;
    return Insert(dat); 
  }

  default void RemoveOccurrences(Data dat){
    if (dat == null) return;

    var iterator = FIterator();
    while (iterator.IsValid()) {
        Data cur = iterator.GetCurrent();
        if ((cur == null && dat == null) || (cur != null && cur.equals(dat))) {
            Remove(cur);
            iterator.Reset();
        } else {
            iterator.Next();
        }
    }
  }

  default Chain<Data> SubChain(Natural nat1, Natural nat2){
    long start = ExcIfOutOfBound(nat1);
    long end = ExcIfOutOfBound(nat2);

    if (start > end) {
        long swap = start; 
        start = end; 
        end = swap;
    }

    Chain<Data> sub = New();
    for (long i = start; i <= end; i++) {
        Data dat = GetAt(Natural.Of(i));
        sub.Insert(dat);
    }

    return sub;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  default Natural Search(Data dat){
    var itr = FIterator();
    long index = 0;
    while (itr.IsValid()) {
        Data cur = itr.GetCurrent();
        if ((cur == null && dat == null) || (cur != null && cur.equals(dat))) return Natural.Of(index);
        itr.Next();
        index++;
    }

    return null;
  }

}
