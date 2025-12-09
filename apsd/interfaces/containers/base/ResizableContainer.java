package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: ReallocableContainer che è espandibile e riducibile. */
public interface ResizableContainer extends ReallocableContainer{ // Must extend ReallocableContainer

  double THRESHOLD_FACTOR = 2.0; // Must be strictly greater than 1.

  default void Expand() {
    if (Size().ToLong() >= Capacity().ToLong()) {
        Grow();   
    }
  }

  void Expand(Natural newCapacity);

  default void Reduce() {
    if ((long)(THRESHOLD_FACTOR * Size().ToLong()) <= Capacity().ToLong()) {
        Shrink();
    }
  }
  
  void Reduce(Natural newCapacity);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */
  @Override
  Natural Size();

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  @Override
  default void Shrink() {
    long cap = Capacity().ToLong();
    long size = Size().ToLong();
    
    // Calcola la nuova capacità teorica
    long newCap = (long)(cap / SHRINK_FACTOR);
    
    //Non scende mai sotto la dimensione logica attuale
    if (newCap < size) {
        newCap = size;
    }
    
    Realloc(Natural.Of(newCap));
  }

  @Override
  default void Grow(Natural dim){
    if(Capacity().ToLong() == Integer.MAX_VALUE) {throw new ArithmeticException("Overflow: capacity cannot overcome");}
    if(Size().ToLong() + dim.ToLong() >= Capacity().ToLong()) ReallocableContainer.super.Grow(dim);
  }
}
