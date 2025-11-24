package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data> extends Sequence<Data>, SortedIterableContainer<Data>{ // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  default boolean Exists(Data dat){
    if (dat == null) return false;

    return FIterator().ForEachForward(elem ->
        dat.equals(elem)
    );
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  default Natural Search(Data dat){
    if (dat == null) return null;

    var iterator = FIterator();
    long index = 0;

    while (iterator.IsValid()) {
        Data elem = iterator.DataNNext();
        if (dat.equals(elem)) {
            return new Natural(index);
        }
        index++;
    }

    return null;
  }

}
