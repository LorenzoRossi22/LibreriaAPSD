package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.containers.sequences.Vector;
import apsd.interfaces.traits.Predicate;

/** Object: Concrete dynamic (linear) vector implementation. */
public class DynVector<Data> extends DynLinearVectorBase<Data>{ // Must extend DynLinearVectorBase

  public DynVector(){
    super();
  }

  public DynVector(Natural inisize){
    super();
    Expand(inisize);
  }

  public DynVector(TraversableContainer<Data> con){
    super();
    if (con == null) return;

    long count = con.Size().ToLong();
    Expand(Natural.Of(count));

    final long[] idx = {0};
    con.TraverseForward(elem -> {
        InsertAt(Natural.Of(size), elem);
        return true;
    });
  }

  public DynVector(Data[] arr){
    super();
    NewVector(arr);
  }

  public void NewVector(Data[] dat) {
    if (dat == null) return;
    Realloc(Natural.Of(dat.length));
    size = 0;
    for (int i = 0; i < dat.length; i++) {
        InsertAt(Natural.Of(size), dat[i]);
    }
  }

  public Sequence SubSequence(Natural start, Natural end) {
    DynVector<Data> sub = new DynVector<>();

    long from = start.ToLong();
    long to = end.ToLong();

    for (long i = from; i < to; i++) {
        sub.InsertAt(Natural.Of(sub.Size().ToLong()), GetAt(Natural.Of(i)));
    }

    return sub;
  }

  public boolean Apply(Data dat) {
    for (long i = 0; i < Size().ToLong(); i++) {
      if (GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    for (long i = 0; i < Size().ToLong(); i++) {
        acc = ((Predicate<Object>) dat).Apply(GetAt(Natural.Of(i)));
    }
    return acc;
  }

  public Vector<Data> New() {
    return new DynVector<>();
  }

  public MutableSequence<Data> Subsequence(Natural nat1, Natural nat2) {
    DynVector<Data> sub = new DynVector<>();

    long from = nat1.ToLong();
    long to = nat2.ToLong();

    for (long i = from; i < to; i++) {
        sub.InsertAt(Natural.Of(sub.Size().ToLong()), GetAt(Natural.Of(i)));
    }

    return sub;
  }

  public void InsertAt(Natural nat, Data dat) {
    long idx = nat.ToLong();
    if (idx < 0 || idx > size) throw new IndexOutOfBoundsException("Indice non valido");

    if (arr == null || arr.length == 0) {
      Realloc(Natural.Of(1));
    } else if (size >= arr.length) {
      long newCap = Math.max(1, arr.length * 2);
      Realloc(Natural.Of(newCap));
    }

    for (long i = size - 1; i >= idx; i--) {
      arr[(int)(i + 1)] = arr[(int)i];
    }

    arr[(int)idx] = dat;
    size++;
  }

  @Override
  public void Expand(Natural n) {
    long k = n.ToLong();
    if (k <= 0) return;

    long newSize = size + k;

    if (arr == null || newSize > arr.length) {
      long newCap = Math.max(newSize, (arr == null ? 1 : arr.length) * 2);
      Realloc(Natural.Of(newCap));
    }

    size = newSize;
  }

  @Override
  public Data AtNRemove(Natural nat) {
    long idx = nat.ToLong();
    if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException("Indice non valido");

    Data removed = arr[(int)idx];

    for (long i = idx; i < size - 1; i++) {
        arr[(int)i] = arr[(int)(i + 1)];
    }

    arr[(int)(size - 1)] = null;
    size--;

    return removed;
  }
}
