package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.classes.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<Data>> extends VChainBase<Data> implements SortedChain<Data>{ // Must extend VChainBase and implements SortedChain

  public VSortedChain(){
    super(new DynVector<>());
  }

  public VSortedChain(VSortedChain<Data> chn){
    super(new DynVector<>());
    for (long i = 0; i < chn.Size().ToLong(); i++) {
      Insert(chn.GetAt(Natural.Of(i)));
    }
  }

  public VSortedChain(TraversableContainer<Data> con){
    super(new DynVector<>());
    con.TraverseForward(elem -> {
        Insert(elem);
        return true;
    });
  }

  protected VSortedChain(DynVector<Data> vec){
    super(vec);
  }

  public VChainBase<Data> NewChain(apsd.interfaces.containers.sequences.DynVector<Data> arr) {
    VSortedChain<Data> newChain = new VSortedChain<>();
    if (arr != null) {
        for (long i = 0; i < arr.Size().ToLong(); i++) {
            newChain.Insert(arr.GetAt(Natural.Of(i)));
        }
    }
    return newChain;
  }

  public Chain<Data> New() {
    return new VSortedChain<>();
  }

  public boolean Apply(Data dat) {
    if(dat == null) throw new NullPointerException();
    for (long i = 0; i < vec.Size().ToLong(); i++) {
      if (vec.GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    return vec.Apply(dat, acc);
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  public boolean Insert(Data dat){
    if(dat == null) throw new NullPointerException();
    long n = vec.Size().ToLong();

    long pos = 0;
    while (pos < n && vec.GetAt(Natural.Of(pos)).compareTo(dat) < 0) {
        pos++;
    }

    vec.InsertAt(Natural.Of(pos), dat);
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  public boolean InsertIfAbsent(Data dat){
    if(dat == null) throw new NullPointerException();
    long n = vec.Size().ToLong();

    long pos = 0;
    int cmp = 0;
    while (pos < n && cmp<=0) {
        cmp = vec.GetAt(Natural.Of(pos)).compareTo(dat);
        if (cmp == 0) return false;
        if (cmp > 0) break;
        pos++;
    }

    vec.InsertAt(Natural.Of(pos), dat);
    return true;
  }

  public void RemoveOccurrences(Data dat){
    if(dat == null) throw new NullPointerException();
    long i = 0;
    while (i < vec.Size().ToLong()) {
      if (vec.GetAt(Natural.Of(i)).equals(dat)) {
        AtNRemove(Natural.Of(i));
      } else {
        i++;
      }
    }
  }
}
