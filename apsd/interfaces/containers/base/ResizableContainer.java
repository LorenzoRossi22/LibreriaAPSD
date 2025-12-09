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
    long size = Size().ToLong();
    long cap  = Capacity().ToLong();

    if (cap > size) {
        Realloc(new Natural(size));
    }
  }
  
  void Reduce(Natural newCapacity);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  default Natural Size(){
    return Capacity();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ReallocableContainer             */
  /* ************************************************************************ */

  default void Grow(Natural newCapacity) {
    if (newCapacity.ToLong() > Capacity().ToLong()) {
        Realloc(newCapacity);
    }
  }

  @Override
  default void Shrink() {
     if ((long) (THRESHOLD_FACTOR * SHRINK_FACTOR * Size().ToLong()) <= Capacity().ToLong()) ReallocableContainer.super.Shrink();
  }

}
