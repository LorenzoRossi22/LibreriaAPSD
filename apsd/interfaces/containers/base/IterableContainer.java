package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data>{ // Must extend TraversableContainer

  ForwardIterator<Data> FIterator();
  BackwardIterator<Data> BIterator();

  default boolean IsEqual(IterableContainer<Data> IterableC){
    if (IterableC == null) return false;

      if (!this.Size().equals(IterableC.Size()))
          return false;

      ForwardIterator<Data> it1 = this.FIterator();
      ForwardIterator<Data> it2 = IterableC.FIterator();

      while (it1.IsValid() && it2.IsValid()) {

          Data a = it1.DataNNext();
          Data b = it2.DataNNext();

          if (a == null) {
              if (b != null) return false;
          } else {
              if (!a.equals(b)) return false;
          }
      }

      return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */

  @Override
  default boolean TraverseForward(Predicate<Data> pred) {
      if (pred == null) return false;

      ForwardIterator<Data> it = FIterator();

      while (it.IsValid()) {
          if (pred.Apply(it.DataNNext())) {
              return true;
          }
      }

      return false;
  }

  @Override
  default boolean TraverseBackward(Predicate<Data> pred) {
      if (pred == null) return false;

      BackwardIterator<Data> it = BIterator();

      while (it.IsValid()) {
          if (pred.Apply(it.DataNPrev())) {
              return true;
          }
      }

      return false;
  }

}
