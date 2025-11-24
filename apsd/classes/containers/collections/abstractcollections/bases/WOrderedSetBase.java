package apsd.classes.containers.collections.abstractcollections.bases;

import apsd.interfaces.containers.base.IterableContainer;
import apsd.interfaces.containers.collections.OrderedSet;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.containers.iterators.ForwardIterator;

/** Object: Abstract wrapper set base implementation via chain. */
abstract public class WOrderedSetBase<Data extends Comparable<Data>, Chn extends SortedChain<Data>> extends WSetBase<Data,Chn> implements OrderedSet<Data>{ // Must extend WSetBase and implement OrderedSet; Chn must extend SortedChain

 public WOrderedSetBase(){
  super();
 }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  public boolean IsEqual(IterableContainer<Data> iter){
    if (iter == null) return false;
    ForwardIterator<Data> it1 = this.FIterator();
    ForwardIterator<Data> it2 = iter.FIterator();

    while (it1.IsValid() && it2.IsValid()) {
        Data a = it1.DataNNext();
        Data b = it2.DataNNext();
        if (!a.equals(b)) return false;
    }
    return !it1.IsValid() && !it2.IsValid();
  }

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  public Data Min(){
    return chn.Min();
  }

  public Data Max(){
    return chn.Max();
  }

  public void RemoveMin(){
    chn.RemoveMin();
  }

  public void RemoveMax(){
    chn.RemoveMax();
  }

  public Data MinNRemove(){
    return chn.MinNRemove();
  }

  public Data MaxNRemove(){
    return chn.MaxNRemove();
  }

  public Data Successor(Data dat){
    return chn.Successor(dat);
  }

  public Data Predecessor(Data dat){
    return chn.Predecessor(dat);
  }

  public void RemoveSuccessor(Data dat){
    chn.RemoveSuccessor(dat);
  }

  public void RemovePredecessor(Data dat){
    chn.RemovePredecessor(dat);
  }

  public Data SuccessorNRemove(Data dat){
    return chn.SuccessorNRemove(dat);
  }

  public Data PredecessorNRemove(Data dat){
    return chn.PredecessorNRemove(dat);
  }

}
