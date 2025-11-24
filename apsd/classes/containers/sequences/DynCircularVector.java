package apsd.classes.containers.sequences;

import apsd.classes.containers.sequences.abstractbases.DynCircularVectorBase;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.sequences.MutableSequence;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Concrete dynamic circular vector implementation. */
public class DynCircularVector<Data> extends DynCircularVectorBase<Data>{ // Must extend DynCircularVectorBase

  public DynCircularVector(){
    super();
  }

  public DynCircularVector(Natural inisize){
    super();
    Expand(inisize);
  }

  public DynCircularVector(TraversableContainer<Data> con){
    super();
    Expand(con.Size());
    for (long i = 0; i < con.Size().ToLong(); i++) {
      SetAt(con.GetAt(Natural.Of(i)), Natural.Of(i));
    }
  }

  protected DynCircularVector(Data[] arr){
    super();
    ArrayAlloc(Natural.Of(arr.length));
    for (int i = 0; i < arr.length; i++) {
      SetAt(arr[i], Natural.Of(i));
    }
    size = arr.length;
  }

  public void NewVector(Data[] dat){
    ArrayAlloc(Natural.Of(dat.length));
    for (int i = 0; i < dat.length; i++) {
      SetAt(dat[i], Natural.Of(i));
    }
    size = dat.length;
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

  public Vector<Data> New() {
    return new DynCircularVector<>();
  }

  public MutableSequence<Data> Subsequence(Natural nat1, Natural nat2) {
    long startIdx = nat1.ToLong();
    long endIdx = nat2.ToLong();
    DynCircularVector<Data> sub = new DynCircularVector<>(Natural.Of(endIdx - startIdx));
    for (long i = startIdx; i < endIdx; i++) {
      sub.SetAt(GetAt(Natural.Of(i)), Natural.Of(i - startIdx));
    }
    sub.size = endIdx - startIdx;
    return sub;
  }

}
