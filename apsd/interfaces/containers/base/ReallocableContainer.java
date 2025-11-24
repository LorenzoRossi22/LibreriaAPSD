package apsd.interfaces.containers.base;
import apsd.interfaces.traits.Reallocable;
import apsd.classes.utilities.Natural;


/** Interface: ClearableContainer che è anche Reallocable. */
public interface ReallocableContainer extends ClearableContainer, Reallocable{ // Must extend ClearableContainer, Reallocable

  double GROW_FACTOR = 2.0; // Must be strictly greater than 1.
  double SHRINK_FACTOR = 2.0; // Must be strictly greater than 1.

  Natural Capacity();

  default void Grow() {
    long newCap = (long)(Capacity().ToLong() * GROW_FACTOR);
    Realloc(new Natural(newCap));
  }

  default void Grow(Natural newCapacity) {
    Realloc(newCapacity);
  }

  default void Shrink() {
    long newCap = (long)(Capacity().ToLong() / SHRINK_FACTOR);
    Realloc(new Natural(newCap));
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  default Natural Size(){
    return Capacity();
  }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  default void Clear(){
    Realloc(new Natural(0L));
  }

}
