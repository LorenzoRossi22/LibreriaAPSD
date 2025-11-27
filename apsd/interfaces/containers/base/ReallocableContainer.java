package apsd.interfaces.containers.base;
import apsd.interfaces.traits.Reallocable;
import apsd.classes.utilities.Natural;


/** Interface: ClearableContainer che è anche Reallocable. */
public interface ReallocableContainer extends ClearableContainer, Reallocable{ // Must extend ClearableContainer, Reallocable

  double GROW_FACTOR = 2.0; // Must be strictly greater than 1.
  double SHRINK_FACTOR = 2.0; // Must be strictly greater than 1.

  Natural Capacity();

  default void Grow() {
    long cap = Capacity().ToLong();
    long newCap;

    if (cap == 0) {
        newCap = 1;
    } else {
        newCap = (long)(cap * GROW_FACTOR);
    }

    Realloc(Natural.Of(newCap));
  }

  default void Grow(Natural increment) {
    if (increment == null) {
        return;
    }
    
    long cap = Capacity().ToLong();
    long inc = increment.ToLong();
    long newCap = cap + inc;
    
    Realloc(Natural.Of(newCap));
  }

  default void Shrink() {
    long cap = Capacity().ToLong();
    long newCap = (long)(cap / SHRINK_FACTOR);
    
    Realloc(Natural.Of(newCap));
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
    Realloc(Natural.ZERO);
  }

}
