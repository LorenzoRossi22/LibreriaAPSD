package apsd.interfaces.containers.base;

import apsd.classes.utilities.Natural;

/** Interface: Container, la base di tutti i contenitori. */
public interface Container {

  // Restituisce la dimensione del contenitore.
  Natural Size();

  // Restituisce true se il contenitore è vuoto, false altrimenti.
  default boolean IsEmpty(){
    return Size().IsZero();
  }
}
