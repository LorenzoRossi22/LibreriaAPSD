package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.Set;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WSetBase<Data, Chn extends Chain<Data>> implements Set<Data>{ // Must implement Set; Chn must extend Chain

  protected Chn chn;

  protected abstract WSetBase<Data, Chn> New();

  public WSetBase(){
    ChainAlloc();
  }

  public abstract void ChainAlloc();

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  public Natural Size(){
    return chn == null ? Natural.Of(0) : (chn.Size());
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    ForwardIterator<Data> it = chn.FIterator();
    while (it.IsValid()) {
      Data v = it.DataNNext();
      chn.Remove(v);
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  public boolean Insert(Data dat){
    if (dat == null) return false;
    if (!chn.Apply(dat)) {
      chn.Insert(dat);
      return true;
    }
    return false;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  public boolean Remove(Data dat){
    if (dat == null) return false;
    boolean removed = false;
    ForwardIterator<Data> it = chn.FIterator();
    while (it.IsValid()) {
      Data v = it.DataNNext();
      if (dat.equals(v)) {
        chn.Remove(v);
        removed = true;
      }
    }
    return removed;
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  public ForwardIterator<Data> FIterator(){
    return chn.FIterator();
  }

  public BackwardIterator<Data> BIterator(){
    return chn.BIterator();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  public boolean Filter(Predicate<Data> pred){
    if (pred == null) return false;
    boolean removed = false;
    ForwardIterator<Data> it = chn.FIterator();
    while (it.IsValid()) {
        Data v = it.DataNNext();
        if (!pred.Apply(v)) {
          chn.Remove(v);
          removed = true;
        }
    }
    return removed;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Set                              */
  /* ************************************************************************ */

  public Set<Data> Intersection(Set<Data> set1, Set<Data> set2){
    if (set1 == null || set2 == null) return null;
    WSetBase<Data, Chn> result = New();
    ForwardIterator<Data> it1 = set1.FIterator();
    while (it1.IsValid()) {
        Data v = it1.DataNNext();
        if (set2.Apply(v)) {
            result.Insert(v);
        }
    }
    return result;
  }
}
