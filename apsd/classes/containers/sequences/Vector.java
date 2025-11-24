package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Sequence;

/** Object: Concrete (static linear) vector implementation. */
public class Vector<Data> extends LinearVectorBase<Data>{ // Must extend LinearVectorBase

  public Vector(){
    super();
  }

  public Vector(Natural inisize){
    super();
    ArrayAlloc(inisize);
  }

  public Vector(TraversableContainer<Data> con){
    super();
    ArrayAlloc(con.Size());
    for (long i = 0; i < con.Size().ToLong(); i++) {
      SetAt(con.GetAt(Natural.Of(i)), Natural.Of(i));
    }
  }

  protected Vector(Data[] arr){
    super();
    ArrayAlloc(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) {
      SetAt(arr[i], Natural.Of(i));
    }
  }

  public void NewVector(Data[] dat){
    ArrayAlloc(Natural.Of(dat.length));
    for (int i = 0; i < dat.length; i++) {
      SetAt(dat[i], Natural.Of(i));
    }
  }

  public Vector<Data> New() {
    return new Vector<>();
  }

  public MutableSequence<Data> Subsequence(Natural nat1, Natural nat2) {
    long start = nat1.ToLong();
    long end = nat2.ToLong();
    Vector<Data> sub = new Vector<>(Natural.Of(end - start));
    for (long i = start; i < end; i++) {
      sub.SetAt(GetAt(Natural.Of(i)), Natural.Of(i - start));
    }
    return sub;
  }

  public Sequence SubSequence(Natural start, Natural end) {
    return Subsequence(start, end);
  }

  public boolean Apply(Data dat) {
    for (long i = 0; i < Size().ToLong(); i++) {
      if (GetAt(Natural.Of(i)).equals(dat)) return true;
    }
    return false;
  }

  public Object Apply(Object dat, Object acc) {
    Object result = acc;
    for (long i = 0; i < Size().ToLong(); i++) {
      Object elem = GetAt(Natural.Of(i));
      if (result instanceof Number && elem instanceof Number) {
        result = ((Number) result).doubleValue() + ((Number) elem).doubleValue();
      } else {
        result = String.valueOf(result) + String.valueOf(elem);
      }
    }
    return result;
  }

  public void InsertAt(Natural nat, Data dat) {
    long idx = nat.ToLong();
    if (idx < 0 || idx > Size().ToLong()) throw new IndexOutOfBoundsException();
    ShiftRight(Natural.Of(idx), Natural.Of(1));
    SetAt(dat, Natural.Of(idx));
  }

}
