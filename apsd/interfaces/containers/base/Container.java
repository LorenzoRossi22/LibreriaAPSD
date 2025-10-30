package apsd.interfaces.containers.base;

// import apsd.classes.utilities.Natural;

/** Interface: Container, la base di tutti i contenitori. */
public interface Container {

  Natural Size(); // Restituisce la dimensione del contenitore.

  // Verifica se il contenitore è vuoto.
  default boolean IsEmpty(){ 
    return Size().IsZero();
  }
}
