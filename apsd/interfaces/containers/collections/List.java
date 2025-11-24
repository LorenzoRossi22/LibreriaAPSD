package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.sequences.InsertableAtSequence;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.classes.utilities.Natural;

public interface List<Data> extends Chain<Data>, MutableSequence<Data>, InsertableAtSequence<Data>{ // Must extend MutableSequence, InsertableAtSequence, and Chain

  List<Data> New();

  default List<Data> SubList(Natural nat1, Natural nat2){
    long start = ExcIfOutOfBound(nat1);
    long end = ExcIfOutOfBound(nat2);

    if (start > end) {
        long swap = start; 
        start = end; 
        end = swap;
    }

    List<Data> sub = New();

    for (long i = start; i <= end; i++) {
        Data v = GetAt(Natural.Of(i));
        sub.InsertAt(sub.Size(), v);
    }

    return sub;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ExtensibleContainer              */
  /* ************************************************************************ */

  default boolean Insert(Data dat){
    if (dat == null) return false;
    InsertAt(Size(), dat);
    return true;
  }

}
