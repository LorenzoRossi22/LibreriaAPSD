package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;

/** Object: Concrete sorted chain implementation on linked-list. */
public class LLSortedChain<Data extends Comparable<Data>> extends LLChainBase<Data> implements SortedChain<Data>{ // Must extend LLChainBase and implement SortedChain

  public LLSortedChain(){
    super();
  }

  public LLSortedChain(LLSortedChain<Data> chn){
    super();
    if (chn == null) return;
    for (long i = 0; i < chn.Size().ToLong(); i++) {
      Insert(chn.GetAt(Natural.Of(i)));
    }
  }

  public LLSortedChain(TraversableContainer<Data> con){
    super();
    if (con == null) return;
    con.TraverseForward(elem -> {
      Insert(elem);
      return false;
    });
  }

  protected LLSortedChain(long size1, LLNode<Data> head, LLNode<Data> tail){
    super();
    if (head != null) headref.Set(head); 
      else headref.Set(null);
    if (tail != null) tailref.Set(tail); 
      else tailref.Set(null);
    size.Assign(Natural.Of(size1));
  }

  public LLChainBase<Data> NewChain(long a, LLNode<Data> node1, LLNode<Data> node2){
    return new LLSortedChain<>(a, node1, node2);
  }

  public Chain<Data> New() {
    return new LLSortedChain<>();
  }

  public boolean Apply(Data dat) {
    if (dat == null) return false;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      Data v = cur.Get();
      if (v != null && v.equals(dat)) return true;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    Object result = acc;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      Object elem = cur.Get();
      if (result instanceof Number && elem instanceof Number) {
        result = ((Number) result).doubleValue() + ((Number) elem).doubleValue();
      } else {
        result = String.valueOf(result) + String.valueOf(elem);
      }
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return result;
  }

  /* ************************************************************************ */
  /* Specific member functions of LLSortedChain                               */
  /* ************************************************************************ */

  public LLNode<Data> PredFind(Data dat){
    if (dat == null) return null;
    LLNode<Data> prev = null;
    LLNode<Data> cur = headref.Get();
    boolean cont = false;
    Box<LLNode<Data>> nextBox;
    while (cur != null && cont == false) {
      Data v = cur.Get();
      if (v.compareTo(dat) < 0) {
        prev = cur;
        nextBox = cur.GetNext();
        cur = (nextBox == null) ? null : nextBox.Get();
      }else{
        cont = true;
      }
    }
    return prev;
  }

  public LLNode<Data> PredPredFind(Data dat){
    LLNode<Data> pred = PredFind(dat);
    if (pred == null) return null;
    LLNode<Data> cur = headref.Get();
    LLNode<Data> prev = null;
    Box<LLNode<Data>> nextBox;
    while (cur != null && cur != pred) {
      prev = cur;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return prev; 
  }

  public LLNode<Data> PredSuccFind(Data dat){
    if (dat == null) return null;
    LLNode<Data> prev = null;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      Data v = cur.Get();
      if (v.compareTo(dat) > 0) {
        break;
      }
      prev = cur;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return prev;
  }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  public boolean Insert(Data dat){
    if (dat == null) return false;

    LLNode<Data> newNode = new LLNode<>(dat);

    LLNode<Data> pred = PredFind(dat);

    if (pred == null) {
      LLNode<Data> oldHead = headref.Get();
      newNode.SetNext(oldHead);
      headref.Set(newNode);
      if (tailref.Get() == null) {
        tailref.Set(newNode);
      }
    } else {
      Box<LLNode<Data>> predNextBox = pred.GetNext();
      LLNode<Data> nextNode = predNextBox == null ? null : predNextBox.Get();
      pred.SetNext(newNode);
      newNode.SetNext(nextNode);
      if (nextNode == null) tailref.Set(newNode);
    }

    size.Increment();
    return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  public boolean Remove(Data dat){
    if (dat == null) return false;
    LLNode<Data> prev = null;
    LLNode<Data> cur = headref.Get();

    while (cur != null) {
      Data v = cur.Get();
      if (v != null && v.equals(dat)) {
        if (prev == null) {
          Box<LLNode<Data>> nextBox = cur.GetNext();
          headref.Set(nextBox == null ? null : nextBox.Get());
          if (tailref.Get() == cur) {
            tailref.Set(null);
          }
        } else {
          Box<LLNode<Data>> nextBox = cur.GetNext();
          prev.SetNext(nextBox == null ? null : nextBox.Get());
          if (tailref.Get() == cur) {
            tailref.Set(prev);
          }
        }
        size.Decrement();
        return true;
      }
      prev = cur;
      cur = cur.GetNext() == null ? null : cur.GetNext().Get();
    }
    return false;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from SortedSequence                   */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from OrderedSet                       */
  /* ************************************************************************ */

  // ...

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  public Natural Search(Data dat){
    if (dat == null) return null;
    long idx = 0;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      Data v = cur.Get();
      if (v.equals(dat)) return Natural.Of(idx);
      idx++;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return null;
  }

  public boolean InsertIfAbsent(Data dat){
    if (dat == null) return false;

    LLNode<Data> cur = headref.Get();
    LLNode<Data> pred = null;

    while (cur != null) {
        int cmp = cur.Get().compareTo(dat);
        if (cmp == 0) return false;
        if (cmp > 0) break;
        pred = cur;
        Box<LLNode<Data>> nextBox = cur.GetNext();
        cur = (nextBox == null) ? null : nextBox.Get();
    }
    LLNode<Data> newNode = new LLNode<>(dat);

    if (pred == null) {
        newNode.SetNext(headref.Get());
        headref.Set(newNode);
        if (tailref.Get() == null) tailref.Set(newNode);
    } else {
        LLNode<Data> nextNode = pred.GetNext() == null ? null : pred.GetNext().Get();
        pred.SetNext(newNode);
        newNode.SetNext(nextNode);
        if (nextNode == null) tailref.Set(newNode);
    }

    size.Increment();
    return true;
  }

  public void RemoveOccurrences(Data dat){
    if (dat == null) return;

    LLNode<Data> prev = null;
    LLNode<Data> cur = headref.Get();

    while (cur != null) {
        Data v = cur.Get();
        if (v != null && v.equals(dat)) {
            Box<LLNode<Data>> nextBox = cur.GetNext();
            LLNode<Data> next = (nextBox == null ? null : nextBox.Get());
            if (prev == null) headref.Set(next);
             else prev.SetNext(next);

            if (tailref.Get() == cur) tailref.Set(prev);

            cur = next;
            size.Decrement();
        }
        else {
            prev = cur;
            Box<LLNode<Data>> nextBox = cur.GetNext();
            cur = (nextBox == null ? null : nextBox.Get());
        }
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from SortedChain                      */
  /* ************************************************************************ */

  public Natural SearchPredecessor(Data dat){
    if (dat == null) return null;
    long idx = -1;
    long i = 0;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      if (cur.Get().compareTo(dat) < 0) {
        idx = i;
      } else {
        break;
      }
      i++;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    if (idx >= 0) return Natural.Of(idx);
    return null;
  }

  public Natural SearchSuccessor(Data dat){
    if (dat == null) return null;
    long i = 0;
    LLNode<Data> cur = headref.Get();
    Box<LLNode<Data>> nextBox;
    while (cur != null) {
      if (cur.Get().compareTo(dat) > 0) {
        return Natural.Of(i);
      }
      i++;
      nextBox = cur.GetNext();
      cur = (nextBox == null) ? null : nextBox.Get();
    }
    return null;
  }

  public Data Predecessor(Data dat){
    Natural idx = SearchPredecessor(dat);
    if (idx == null) return null;
    return GetAt(idx);
  }

  public Data Successor(Data dat){
    Natural idx = SearchSuccessor(dat);
    if (idx == null) return null;
    return GetAt(idx);
  }

  public void RemovePredecessor(Data dat){
    Natural idx = SearchPredecessor(dat);
    if (idx != null) AtNRemove(idx);
  }

  public void RemoveSuccessor(Data dat){
    Natural idx = SearchSuccessor(dat);
    if (idx != null) AtNRemove(idx);
  }

  public Data PredecessorNRemove(Data dat){
    Natural idx = SearchPredecessor(dat);
    if (idx == null) return null;
    return AtNRemove(idx);
  }

  public Data SuccessorNRemove(Data dat){
    Natural idx = SearchSuccessor(dat);
    if (idx == null) return null;
    return AtNRemove(idx);
  }
}
