package apsd.classes.containers.sequences.abstractbases;

import apsd.interfaces.containers.sequences.DynVector;
import apsd.classes.utilities.Natural;

/** Object: Abstract dynamic linear vector base implementation. */
abstract public class DynLinearVectorBase<Data> extends LinearVectorBase<Data> implements DynVector<Data>{ // Must extend LinearVectorBase and implement DynVector

  protected long size;

  public DynLinearVectorBase(){
    size = 0L;
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
    int cap = (int)newCapacity.ToLong();
    if (cap >= Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    Data[] newArr = (Data[]) new Object[cap];
    int copyCount = (int) Math.min((int)size, cap);
    System.arraycopy(arr, 0, newArr, 0, copyCount);
    arr = newArr;
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

}
