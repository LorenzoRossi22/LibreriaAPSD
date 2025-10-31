package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Interface: IterableContainer con supporto alla lettura e ricerca tramite posizione. */
public interface Sequence<Data> extends IterableContainer<Data>{ // Must extend IterableContainer

  Data GetAt(Natural pos);

  default Data GetFirst(){
    return GetAt(Natural.ZERO);
  }
  default Data GetLast(){
    if(IsEmpty()){
      return GetAt(Natural.ZERO);
    }
    else
    {
      return GetAt(Size().Decrement());
    }
  }

  default Natural Search(Data dat){
    final Box<Long> index = new Box<>(-1L);
    if(TraverseForward(element -> {
      index.Set(index.Get() + 1);
      return (element == null && dat == null || element != null && dat != null && element.equals(dat));
    })){ return Natural.Of(index.Get())}
    return null;
  }

  // IsInBound

  // default long ExcIfOutOfBound(Natural num) {
  //   if (num == null) throw new NullPointerException("Natural number cannot be null!");
  //   long idx = num.ToLong();
  //   if (idx >= Size().ToLong()) throw new IndexOutOfBoundsException("Index out of bounds: " + idx + "; Size: " + Size() + "!");
  //   return idx;
  // }

  // SubSequence

}
