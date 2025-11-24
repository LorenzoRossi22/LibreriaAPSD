package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.ResizableContainer;

public interface DynVector<Data> extends ResizableContainer,RemovableAtSequence<Data>,Vector<Data>{ // Must extend ResizableContainer, InsertableAtSequence, RemovableAtSequence, and Vector

  /* ************************************************************************ */
  /* Override specific member functions from InsertableAtSequence             */
  /* ************************************************************************ */

  default void InsertAt(Natural nat, Data dat){
    long idx = ExcIfOutOfBound(nat);
    long size = Size().ToLong();
    if (size >= Capacity().ToLong()) Grow();
    ShiftRight(Natural.Of(idx));
    SetAt(dat, Natural.Of(idx));
  }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  default Data AtNRemove(Natural nat){
    long idx = ExcIfOutOfBound(nat);
    Data val = GetAt(Natural.Of(idx));
    ShiftLeft(Natural.Of(idx));
    return val;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                       */
  /* ************************************************************************ */

  default void ShiftLeft(Natural pos, Natural num){
    Vector.super.ShiftLeft(pos, num);
  }

  default void ShiftRight(Natural pos, Natural num){
    Vector.super.ShiftRight(pos, num);
  }

  default Vector<Data> SubVector(Natural nat1, Natural nat2){
    return Vector.super.SubVector(nat1, nat2);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  default Natural Size(){
    return Vector.super.Size();
  }

}
