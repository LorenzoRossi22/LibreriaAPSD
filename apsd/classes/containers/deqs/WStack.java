package apsd.classes.containers.deqs;

import apsd.interfaces.containers.collections.List;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.deqs.Stack;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper stack implementation. */
public class WStack<Data> implements Stack<Data>{ // Must implement Stack

  protected final List<Data> lst;

  public WStack(){
    lst = new VList<>();
  }

  public WStack(List<Data> lst){
    this.lst = lst;
  }

  public WStack(TraversableContainer<Data> con){
    lst = new VList<>();
    for(long i = 0; i < con.Size().ToLong(); i++){
      lst.InsertAt(con.GetAt(Natural.Of(i)), Natural.Of(i));
    }
  }

  public WStack(List<Data> lst, TraversableContainer<Data> con){
    this.lst = lst;
    for(long i = 0; i < con.Size().ToLong(); i++){
      lst.InsertAt(con.GetAt(Natural.Of(i)), Natural.Of(i));
    }
  }

  public boolean TraverseForward(Predicate<Data> pred) {
    if (pred == null) return true;
    for (long i = 0; i < lst.Size().ToLong(); i++) {
      Data elem = lst.GetAt(Natural.Of(i));
      if (pred.Apply(elem)) return true;
    }
    return false;
  }

  public boolean TraverseBackward(Predicate<Data> pred) {
    if (pred == null) return true;
    long i = lst.Size().ToLong();
    if (i == 0) return false;
    i--;
    while (i >= 0) {
        Data elem = lst.GetAt(Natural.Of(i));
        if (pred.Apply(elem)) return true;
        i--;
    }
    return false;
  }

  public Data GetAt(Natural nat) {
    return lst.GetAt(nat);
  }

  public boolean Apply(Data dat) {
    for(long i = 0; i < lst.Size().ToLong(); i++){
      if (lst.GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    Object result = acc;
    for(long i = 0; i < lst.Size().ToLong(); i++){
      Object elem = lst.GetAt(Natural.Of(i));
      if (result instanceof Number && elem instanceof Number) {
        result = ((Number) result).doubleValue() + ((Number) elem).doubleValue();
      } else {
        result = String.valueOf(result) + String.valueOf(elem);
      }
    }
    return result;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  public Natural Size(){
    return lst.Size();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    lst.Clear();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Stack                            */
  /* ************************************************************************ */

  public Data Top(){
    long size = lst.Size().ToLong();
    if (size == 0) return null;
    return lst.GetAt(Natural.Of(size - 1));
  }

  public void Pop(){
    if (lst.Size().ToLong() == 0) throw new RuntimeException("Stack vuoto");
    lst.RemoveAt(Natural.Of(lst.Size().ToLong() - 1));
  }

  public Data TopNPop(){
    Data tmp = Top();
    Pop();
    return tmp;
  }

  public void SwapTop(Data dat){
    if (lst.Size().ToLong() == 0) throw new RuntimeException("Stack vuoto");
    lst.SetAt(dat, Natural.Of(lst.Size().ToLong() - 1));
  }

  public Data TopNSwap(Data dat){
    long size = lst.Size().ToLong();
    if (size == 0) return null;
    Data old = lst.GetAt(Natural.Of(size - 1));
    lst.SetAt(dat, Natural.Of(size - 1));
    return old;
  }

  public void Push(Data dat){
    lst.InsertAt(dat, lst.Size());
  }

}
