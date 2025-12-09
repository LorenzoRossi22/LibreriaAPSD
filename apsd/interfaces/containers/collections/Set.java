package apsd.interfaces.containers.collections;

import apsd.interfaces.containers.base.IterableContainer;

public interface Set<Data> extends Collection<Data>{ // Must extend Collection

  default void Union(Set<Data> set){
    if (set == null) return;

    var iterator = set.FIterator();
    while (iterator.IsValid()) {
      Data dat = iterator.GetCurrent();
      Insert(dat);
      iterator.Next();
    }
  }

  default void Difference(Set<Data> set){
    if (set == null) return;

    var iterator = set.FIterator();
    while (iterator.IsValid()) {
      Data dat = iterator.GetCurrent();
      Remove(dat);
      iterator.Next();
    }
  }

  default void Intersection(Set<Data> set){
    if (set == null) {
        Clear();
        return;
    }

    var iterator = FIterator();
    while (iterator.IsValid()) {
        Data dat = iterator.GetCurrent();
        boolean found = false;
        var iterator2 = set.FIterator();
        while (iterator2.IsValid() && !found) {
            if (iterator2.GetCurrent().equals(dat)) {
                found = true;
            }
            iterator2.Next();
        }

        if (!found) {
            Remove(dat);
            iterator.Reset();
        } else {
            iterator.Next();
        }
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  default boolean IsEqual(IterableContainer<Data> IterableC){
    if (IterableC == null) return false;
    if (!Size().equals(IterableC.Size())) return false;
    var it1 = FIterator();
    var it2 = IterableC.FIterator();
    while (it1.IsValid() && it2.IsValid()) {
        Data a = it1.DataNNext();
        Data b = it2.DataNNext();
        if (a == null ? b != null : !a.equals(b)) return false;
    }
    return true;
  }

}
