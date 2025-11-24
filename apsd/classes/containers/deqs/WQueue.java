package apsd.classes.containers.deqs;

import apsd.classes.utilities.Natural;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.deqs.Queue;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper queue implementation. */
public class WQueue<Data> implements Queue<Data>{ // Must implement Queue

  protected final List<Data> lst;

  public WQueue(){
    lst = new VList<>();
  }

  public WQueue(List<Data> lst){
    this.lst = lst;
  }

  public WQueue(TraversableContainer<Data> con){
    this.lst = new VList<>();
    for(long i = 0; i < con.Size().ToLong(); i++){
      lst.InsertAt(Natural.Of(lst.Size().ToLong()), con.GetAt(Natural.Of(i)));
    }
  }

  public WQueue(List<Data> lst, TraversableContainer<Data> con){
    this.lst = lst;
    for(long i = 0; i < con.Size().ToLong(); i++){
      lst.InsertAt(Natural.Of(lst.Size().ToLong()), con.GetAt(Natural.Of(i)));
    }
  }

  public boolean TraverseForward(Predicate<Data> pred) {
    long i = 0;
    long size = lst.Size().ToLong();

    while (i < size) {
      Data elem = lst.GetAt(Natural.Of(i));
      if (pred.Apply(elem)) return true;
      i++;
    }
    return false;
  }

  public boolean TraverseBackward(Predicate<Data> pred) {
    long i = lst.Size().ToLong();
    if (i == 0) return false;
    i = i - 1;
    while (i >= 0 && i!=0) {
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
    long size = lst.Size().ToLong();
    for (long i = 0; i < size; i++) {
      if (lst.GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    Object result = acc;
    long size = lst.Size().ToLong();
    for (long i = 0; i < size; i++) {
      Object elem = lst.GetAt(Natural.Of(i));

      boolean bothNumbers =
        (result instanceof Number) && (elem instanceof Number);

      if (bothNumbers) {
        double a = ((Number) result).doubleValue();
        double b = ((Number) elem).doubleValue();
        result = a + b;
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
  /* Override specific member functions from Queue                            */
  /* ************************************************************************ */

  public Data Head(){
    if (lst.Size().ToLong() == 0) throw new IllegalStateException("Coda vuota");
    return lst.GetAt(Natural.Of(0));
  }

  public void Dequeue(){
    if (lst.Size().ToLong() == 0) throw new IllegalStateException("Coda vuota");
    lst.RemoveAt(Natural.Of(0));
  }

  public Data HeadNDequeue(){
    Data head = Head();
    Dequeue();
    return head;
  }

  public void Enqueue(Data dat){
    long i = lst.Size().ToLong();
    lst.InsertAt(Natural.Of(i), dat);
  }
}
