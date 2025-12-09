package apsd.classes.containers.sequences.abstractbases;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data>{ // Must extend LinearVectorBase and implement DynVector

  public DynLinearVectorBase(){
    if (arr == null) arr = (Data[]) new Object[0];
  }

  public void ArrayAlloc(Natural newsize){
    long n = newsize.ToLong();
    if (n >= Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    arr = (Data[]) new Object[(int) n];
    size = 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  public Natural Size(){
    return Natural.Of(size);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    for (int i = 0; i < size; i++) {
      arr[i] = null;
    }
    size = 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  public void Realloc(Natural newCapacity){
    long capLong = newCapacity.ToLong();
    if (capLong >= Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");

    int cap = (int) capLong;
    Data[] newArr = (Data[]) new Object[cap];
    int copyCount = 0;
    if (arr != null) {
        copyCount = (int) Math.min(size, cap);
        if (copyCount > 0)  System.arraycopy(arr, 0, newArr, 0, copyCount);
    }

    arr = newArr;
    if (size > cap) size = cap;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  public void Expand(Natural newCapacity){
    long cap = newCapacity.ToLong();
    if (cap < 0) throw new IllegalArgumentException("Capacità negativa");

    if (arr == null) {
        ArrayAlloc(newCapacity);
        return;
    }

    if (arr.length < cap) Realloc(newCapacity);
  }

  public void Reduce(Natural newCapacity){
    long cap = newCapacity.ToLong();
    if (arr != null && arr.length > cap)  Realloc(newCapacity);
  }

}
