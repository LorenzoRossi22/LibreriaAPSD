package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) circular vector base implementation. */
abstract public class CircularVectorBase<Data> extends VectorBase<Data>{ // Must extend VectorBase

  protected long start;

  public CircularVectorBase(){
    start = 0L;
  }

  protected int realIndex(long logicalIndex) {
    if (arr == null || arr.length == 0) throw new IndexOutOfBoundsException("Indice non valido : " + logicalIndex);

    if (logicalIndex < 0 || logicalIndex >= arr.length) throw new IndexOutOfBoundsException("Indice non valido : " + logicalIndex);

    return (int)((start + logicalIndex) % arr.length);
  }
  
  public void ShiftLeft(Natural nat1, Natural nat2){
    int s = (int)nat1.ToLong();
    int e = (int)nat2.ToLong();

    for (int i = s; i < e - 1; i++) {
      int from = realIndex(i + 1);
      int to   = realIndex(i);
      arr[to] = arr[from];
    }
  }

  public void ShiftRight(Natural nat1, Natural nat2){
    int s = (int)nat1.ToLong();
    int e = (int)nat2.ToLong();

    for (int i = e - 1; i > s; i--) {
      int from = realIndex(i - 1);
      int to   = realIndex(i);
      arr[to] = arr[from];
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  public void Realloc(Natural newCapacity){
    int newCap = (int)newCapacity.ToLong();
    Data[] old = arr;
    Data[] tmp = (Data[]) new Object[newCap];

    for (int i = 0; i < old.length && i < newCap; i++) {
      tmp[i] = old[ realIndex(i) ];
    }

    arr = tmp;
    start = 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  public Data GetAt(Natural nat){
    int index = (int) nat.ToLong();

    if (index < 0 || index >= arr.length) throw new IndexOutOfBoundsException("Indice non valido : " + index);
    if (arr == null || arr.length == 0) throw new IndexOutOfBoundsException("Indice non valido : " + index);
    
    int real = realIndex(index);
    return arr[real];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  public void SetAt(Data dat, Natural nat){
    int index = (int) nat.ToLong();

    if (index < 0 || index >= arr.length) throw new IndexOutOfBoundsException("Indice non valido : " + index);
    if (arr == null || arr.length == 0) throw new IndexOutOfBoundsException("Indice non valido : " + index);

    int real = realIndex(index);
    arr[real] = dat;
  }

  /* ************************************************************************ */
  /* Specific member functions of Vector                                      */
  /* ************************************************************************ */

  public void ArrayAlloc(Natural newsize){
    if((int)newsize.ToLong() > Integer.MAX_VALUE) throw new ArithmeticException("La dimensione dell'array supera il massimo consentito ("+Integer.MAX_VALUE+")");
    int size = (int)newsize.ToLong();
    arr = (Data[]) new Object[size];
    start = 0;
  }

}
