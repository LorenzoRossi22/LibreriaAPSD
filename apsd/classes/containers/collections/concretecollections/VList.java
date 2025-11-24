package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.classes.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on (dynamic circular) vector. */
public class VList<Data> extends VChainBase<Data> implements List<Data>{ // Must extend VChainBase and implement List

  public VList(){
    super(new DynVector<Data>());
  }

  public VList(TraversableContainer<Data> con){
    super(new DynVector<Data>());
    if (con != null) {
        con.TraverseForward(elem -> {
            InsertAt(Natural.Of(vec.Size().ToLong()), elem);
            return false;
        });
    }
  }

  protected VList(DynVector<Data> vec){
     super(vec);
  }

  public boolean Apply(Data dat) {
    for (long i = 0; i < vec.Size().ToLong(); i++) {
      if (vec.GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    return vec.Apply(dat, acc);
  }

  public MutableSequence<Data> Subsequence(Natural nat1, Natural nat2) {
    VList<Data> sub = new VList<>();
    for (long i = nat1.ToLong(); i < nat2.ToLong(); i++) {
        sub.InsertAt(Natural.Of(sub.Size().ToLong()), vec.GetAt(Natural.Of(i)));
    }
    return sub;
  }

  public void InsertAt(Natural nat, Data dat) {
    vec.InsertAt(nat, dat);
  }

  public List<Data> New() {
    return new VList<Data>();
  }

  public VChainBase<Data> NewChain(apsd.interfaces.containers.sequences.DynVector<Data> arr) {
    return new VList<Data>(arr);
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableIterableContainer         */
  /* ************************************************************************ */

  public MutableForwardIterator FIterator(){
    return new MutableForwardIterator() {

        long index = 0;
        long lastIndex = -1;

        public Object DataNNext() {
            if (!IsValid()) return null;
            lastIndex = index;
            return vec.GetAt(Natural.Of(index++));
        }

        public boolean IsValid() {
            return index < vec.Size().ToLong();
        }

        public void Reset() {
            index = 0;
            lastIndex = -1;
        }

        public Object GetCurrent() {
            if (lastIndex == -1) return null;
            return vec.GetAt(Natural.Of(lastIndex));
        }

        public void SetCurrent(Object dat) {
            if (lastIndex != -1) vec.SetAt((Data) dat, Natural.Of(lastIndex));
        }
    };
  }

  public MutableBackwardIterator BIterator(){
    return new MutableBackwardIterator() {
      long index = vec.Size().ToLong() - 1;
      long lastIndex = -1;

      public Object DataNPrev() {
          if (!IsValid()) return null;
          lastIndex = index;
          return vec.GetAt(Natural.Of(index--));
      }

      public boolean IsValid() {
          return index >= 0;
      }

      public void Reset() {
          index = vec.Size().ToLong() - 1;
          lastIndex = -1;
      }

      public Object GetCurrent() {
          if (lastIndex == -1) return null;
          return vec.GetAt(Natural.Of(lastIndex));
      }

      public void SetCurrent(Object dat) {
          if (lastIndex != -1) vec.SetAt((Data) dat, Natural.Of(lastIndex));
      }

    };
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  public void SetAt(Data dat, Natural nat){
    vec.SetAt(dat, nat);
  }

  public MutableSequence SubSequence(Natural start, Natural end){
    return Subsequence(start, end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  public void InsertAt(Data dat, Natural nat){
    vec.InsertAt(nat, dat);
  }
}
