package apsd.classes.containers.sequences.abstractbases;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;
// import apsd.interfaces.containers.base.TraversableContainer;

/** Object: Abstract dynamic circular vector base implementation. */
abstract public class DynCircularVectorBase<Data> extends CircularVectorBase<Data> implements DynVector<Data>{ // Must extend CircularVectorBase and implement DynVector

  protected long size;

  public DynCircularVectorBase(){
    size = 0L;
  }

  public void ShiftLeft(Natural nat1, Natural nat2){
    long from = nat1.ToLong();
    long count = nat2.ToLong();
    for (long i = from; i < size - count; i++) {
      int r1 = realIndex(i);
      int r2 = realIndex(i + count);
      arr[r1] = arr[r2];
    }
    size -= count;
  }

  public void ShiftRight(Natural nat1, Natural nat2){
    long from = nat1.ToLong();
    long count = nat2.ToLong();
    for (long i = size - 1; i >= from; i--) {
      int r1 = realIndex(i + count);
      int r2 = realIndex(i);
      arr[r1] = arr[r2];
    }
    size += count;
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
    start = 0;
    size = 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  public void Realloc(Natural newCapacity){
    if((int)newCapacity.ToLong() > Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    int cap = (int) newCapacity.ToLong();
    Data[] newArr = (Data[]) new Object[cap];
    int copyCount = (int) Math.min(size, cap);
    for (int i = 0; i < copyCount; i++) {
        newArr[i] = arr[realIndex(i)];
    }
    arr = newArr;
    start = 0;
    size = copyCount;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  public void Expand(Natural newCapacity){
    if (arr.length < newCapacity.ToLong()) Realloc(newCapacity);
  }

  public void Reduce(Natural newCapacity){
    if (arr.length > newCapacity.ToLong()) Realloc(newCapacity);
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  public void ArrayAlloc(Natural newsize){
    if((int)newsize.ToLong() > Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    int cap = (int) newsize.ToLong();
    arr = (Data[]) new Object[cap];
  }

}
