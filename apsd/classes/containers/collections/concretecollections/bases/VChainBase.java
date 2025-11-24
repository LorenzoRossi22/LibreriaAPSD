package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data>{ // Must implement Chain

  protected final DynVector<Data> vec;

  public VChainBase(DynVector<Data> arr) {
        vec = arr;
    }

  public abstract VChainBase<Data> NewChain(DynVector<Data> arr);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  public Natural Size(){
    return vec.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    vec.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  public boolean Remove(Data dat){
    for (long i = 0; i < vec.Size().ToLong(); i++) {
      if (vec.GetAt(Natural.Of(i)).equals(dat)) {
          vec.ShiftLeft(Natural.Of(i + 1), Natural.Of(vec.Size().ToLong() - i - 1));
          return true;
      }
    }
    return false;
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  public ForwardIterator<Data> FIterator(){
    return new ForwardIterator<>() {
      long index = 0;

      public boolean IsValid() {
          return index < vec.Size().ToLong();
      }

      public void Reset() {
          index = 0;
      }

      public Data GetCurrent() {
          if (!IsValid()) return null;
          return vec.GetAt(Natural.Of(index));
      }

      public Data DataNNext() {
          if (!IsValid()) return null;
          return vec.GetAt(Natural.Of(index++));
      }
    };
  }

  public BackwardIterator<Data> BIterator(){
    return new BackwardIterator<>() {
      long index = vec.Size().ToLong() - 1;

      public boolean IsValid() {
          return index >= 0;
      }

      public void Reset() {
          index = vec.Size().ToLong() - 1;
      }

      public Data GetCurrent() {
          if (!IsValid()) return null;
          return vec.GetAt(Natural.Of(index));
      }

      public Data DataNPrev() {
          if (!IsValid()) return null;
          return vec.GetAt(Natural.Of(index--));
      }
    };
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  public Data GetAt(Natural nat){
     return vec.GetAt(nat);
  }

  public Sequence SubSequence(Natural start, Natural end){
    return vec.Subsequence(start, end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  public Data AtNRemove(Natural nat){
    long idx = nat.ToLong();
    if (idx < 0 || idx >= vec.Size().ToLong()) return null;
    Data dat = vec.GetAt(nat);
    vec.ShiftLeft(Natural.Of(idx + 1), Natural.Of(vec.Size().ToLong() - idx - 1));
    return dat;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  public boolean Filter(Predicate<Data> fun){
    boolean removed = false;
    for (long i = vec.Size().ToLong() - 1; i >= 0; i--) {
        Data dat = vec.GetAt(Natural.Of(i));
        if (!fun.Apply(dat)) {
            AtNRemove(Natural.Of(i));
            removed = true;
        }
    }
    return removed;
  }

}
