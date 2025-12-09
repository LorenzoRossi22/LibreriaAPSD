package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;

/** Object: Abstract (static) linear vector base implementation. */
abstract public class LinearVectorBase<Data> extends VectorBase<Data>{ // Must extend VectorBase

  protected long size;

  public LinearVectorBase() {
      size = 0;
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  public void Realloc(Natural newCapacity){
    if (newCapacity.ToLong() != arr.length) {
      Data[] old = arr;
      arr = (Data[]) new Object[(int)newCapacity.ToLong()];
      for (int i = 0; i < size && i < arr.length; i++) {
          arr[i] = old[i];
      }
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  public Data GetAt(Natural nat){
    int i = (int)nat.ToLong();
    if (i < 0 || i >= size) throw new IndexOutOfBoundsException("Indice non valido : " + i);
    return arr[i];
  }

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  public void SetAt(Data dat, Natural nat){
    int i = (int)nat.ToLong();
    if (i < 0) throw new IndexOutOfBoundsException("Indice non valido : " + i);

    if (arr == null || i >= arr.length) throw new IndexOutOfBoundsException("Indice non valido : " + i);

    arr[i] = dat;
  }

}
