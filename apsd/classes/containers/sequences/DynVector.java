package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynLinearVectorBase;
import apsd.classes.containers.sequences.abstractbases.LinearVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.containers.sequences.Vector;

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
    Natural size = con.Size();
    Expand(size);
    for (long i = 0; i < size.ToLong(); i++) {
        SetAt(((LinearVectorBase<Data>) con).GetAt(Natural.Of(i)), Natural.Of(i));
    }
  }

  public DynVector(Data[] arr){
    super();
    NewVector(arr);
  }

  public void NewVector(Data[] dat) {
    Expand(Natural.Of(dat.length));
    for (int i = 0; i < dat.length; i++) {
        SetAt(dat[i], Natural.Of(i));
    }
  }

  public Sequence SubSequence(Natural start, Natural end) {
    int from = (int) start.ToLong();
    int to = (int) end.ToLong();
    int length = to - from;
    Data[] subArr = (Data[]) new Object[length];
    for (int i = 0; i < length; i++) {
        subArr[i] = GetAt(Natural.Of(from + i));
    }
    return new DynVector<>(subArr);
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
            result = ((Number)result).doubleValue() + ((Number)elem).doubleValue();
        } else {
            result = String.valueOf(result) + String.valueOf(elem);
        }
    }
    return result;
  }

  public Vector<Data> New() {
    return new DynVector<>();
  }

  public MutableSequence<Data> Subsequence(Natural nat1, Natural nat2) {
    int from = (int) nat1.ToLong();
    int to = (int) nat2.ToLong();
    int length = to - from;
    Data[] subArr = (Data[]) new Object[length];
    for (int i = 0; i < length; i++) {
        subArr[i] = GetAt(Natural.Of(from + i));
    }
    DynVector<Data> subVec = new DynVector<>(subArr);
    return subVec;
  }

}
