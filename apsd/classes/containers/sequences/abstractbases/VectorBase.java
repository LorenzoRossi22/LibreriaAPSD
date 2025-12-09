package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data>{ // Must implement Vector

  protected Data[] arr;

  public VectorBase(){
    ArrayAlloc(Natural.Of(0));
  }

  protected abstract void NewVector(Data[] dat);

  public Natural Capacity() {
      return Natural.Of(arr.length);
  }

  @SuppressWarnings("unchecked")
  protected void ArrayAlloc(Natural newsize) {
    long size = newsize.ToLong();
    if (size >= Integer.MAX_VALUE) { throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE!"); }
    arr = (Data[]) new Object[(int) size];
  }

  private void ReallocAndCopy(Natural newCapacity) {
    Data[] old = arr;
    arr = (Data[]) new Object[(int)newCapacity.ToLong()];
    System.arraycopy(old, 0, arr, 0, old.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  public void Clear(){
    for (int i = 0; i < arr.length; i++) {
      arr[i] = null;
    }
  }

  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  public void Expand(Natural newCapacity){
    if (arr.length < newCapacity.ToLong()) ReallocAndCopy(newCapacity);
  }

  public void Reduce(Natural newCapacity){
    if(arr.length > newCapacity.ToLong())  ReallocAndCopy(newCapacity);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  public MutableForwardIterator<Data> FIterator(){
    return new MutableForwardIterator<Data>() {
      private int index = 0;

      public boolean IsValid() {
        return index >= 0 && index < arr.length;
      }

      public void Reset() {
        index = 0;
      }

      public Data GetCurrent() {
        if (!IsValid()) return null;
        return arr[index];
      }

      public Data DataNNext() {
        if (!IsValid()) return null;
        return arr[index++];
      }

      public void SetCurrent(Data dat) {
        if (IsValid()) arr[index] = dat;
      }
    };
  }

  public MutableBackwardIterator<Data> BIterator(){
    return new MutableBackwardIterator<Data>() {
      private int index = arr.length - 1;

      public boolean IsValid() {
        return index >= 0 && index < arr.length;
      }

      public void Reset() {
        index = arr.length - 1;
      }

      public Data GetCurrent() {
        if (!IsValid()) return null;
        return arr[index];
      }

      public Data DataNPrev() {
        if (!IsValid()) return null;
        return arr[index--];
      }

      public void SetCurrent(Data dat) {
        if (IsValid()) arr[index] = dat;
      }
    };
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

}
