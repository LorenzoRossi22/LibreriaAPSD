package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

abstract public class VChainBase<Data> implements Chain<Data> {

    protected final DynVector<Data> vec;

    public VChainBase(DynVector<Data> arr) {
      vec = arr;
    }

    public abstract VChainBase<Data> NewChain(DynVector<Data> arr);

    public Natural Size() {
      return vec.Size();
    }

    public void Clear() {
      vec.Clear();
    }

    public boolean Remove(Data dat) {
      for (long i = 0; i < vec.Size().ToLong(); i++) {
        if (vec.GetAt(Natural.Of(i)).equals(dat)) {
          vec.AtNRemove(Natural.Of(i));
          return true;
        }
      }
      return false;
    }

  public ForwardIterator<Data> FIterator() {
    return new ForwardIterator<>() {
      long index = 0;

      public boolean IsValid() { 
        return index < vec.Size().ToLong(); 
      }

      public void Reset() { 
        index = 0; 
      }

      public Data GetCurrent() { 
        return IsValid() ? vec.GetAt(Natural.Of(index)) : null; 
      }

      public Data DataNNext() { 
        return IsValid() ? vec.GetAt(Natural.Of(index++)) : null; 
      }
    };
  }

  public BackwardIterator<Data> BIterator() {
    return new BackwardIterator<>() {
      long index = vec.Size().ToLong() - 1;

      public boolean IsValid() { 
        return index >= 0; 
      }

      public void Reset() { 
        index = vec.Size().ToLong() - 1; 
      }

      public Data GetCurrent() { 
        return IsValid() ? vec.GetAt(Natural.Of(index)) : null; 
      }

      public Data DataNPrev() { 
        return IsValid() ? vec.GetAt(Natural.Of(index--)) : null; 
      }
    };
  }

  public Data GetAt(Natural nat) { 
    return vec.GetAt(nat); 
  }

  public Sequence SubSequence(Natural start, Natural end) { 
    return vec.Subsequence(start, end); 
  }

  public Data AtNRemove(Natural nat) {
    long idx = nat.ToLong();
    if (idx < 0 || idx >= vec.Size().ToLong()) return null;
    return vec.AtNRemove(nat);
  }

  public boolean Filter(Predicate<Data> fun) {
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
