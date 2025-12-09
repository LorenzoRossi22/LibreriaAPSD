package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ReallocableContainer;

public interface Vector<Data> extends ReallocableContainer,MutableSequence<Data>,InsertableAtSequence<Data>{ // Must extend ReallocableContainer and MutableSequence

  Vector<Data> New();

  default void ShiftLeft(Natural nat){
    ShiftLeft(nat, new Natural(1));
  }

  default void ShiftLeft(Natural pos, Natural num) {
    long idx = pos.ToLong();
    long size = Size().ToLong();
    if (idx < 0 || idx > size)  throw new IndexOutOfBoundsException("Indice non valido");
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long iniwrt = idx;
      long wrt = iniwrt;
      for (long rdr = wrt + len; rdr < size; rdr++, wrt++) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
      }
      for (; wrt - iniwrt < len; wrt++) {
        SetAt(null, Natural.Of(wrt));
      }
    }
  }

  default void ShiftFirstLeft(){
    if (Size().ToLong() == 0) return;
    ShiftLeft(new Natural(0), new Natural(1));
  }

  default void ShiftLastLeft(){
    long size = Size().ToLong();
    if (size == 0) return;
    SetAt(null, Natural.Of(size - 1));
  }

  default void ShiftRight(Natural nat){
    ShiftRight(nat, new Natural(1));
  }

  default void ShiftRight(Natural pos, Natural num) {
    long idx = pos.ToLong();
    long size = Size().ToLong();
    if (idx < 0 || idx > size) throw new IndexOutOfBoundsException("Indice non valido");
    long len = num.ToLong();
    len = (len <= size - idx) ? len : size - idx;
    if (len > 0) {
      long iniwrt = size - 1;
      long wrt = iniwrt + len;
      while (wrt >= Capacity().ToLong()) Grow();
      for (long rdr = iniwrt; rdr >= idx; rdr--, wrt--) {
        Natural natrdr = Natural.Of(rdr);
        SetAt(GetAt(natrdr), Natural.Of(wrt));
        SetAt(null, natrdr);
        if (rdr == 0) break;
      }
    }
  }
  
  default void ShiftFirstRight(){
    if (Size().ToLong() == 0) return;
    ShiftRight(new Natural(0));
  }

  default void ShiftLastRight(){
    long size = Size().ToLong();
    ShiftRight(Natural.Of(size - 1), new Natural(1));
  }

  default Vector<Data> SubVector(Natural nat1, Natural nat2){
    long size = Size().ToLong();
    long start = nat1.ToLong();
    long end   = nat2.ToLong();

    if (start < 0 || start > size || end < 0 || end > size) throw new IndexOutOfBoundsException("Indice non valido");

    if (start > end) {
        long t = start;
        start = end;
        end = t;
    }

    Vector<Data> sub = New();

    for (long i = start; i < end; i++) {
        sub.InsertLast(GetAt(Natural.Of(i)));
    }

    return sub;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  default Natural Size(){
    return MutableSequence.super.Size();
  }

}
