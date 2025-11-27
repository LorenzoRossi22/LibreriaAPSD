package apsd.interfaces.containers.base;

import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.traits.Predicate;

/** Interface: TraversableContainer con supporto all'iterazione. */
public interface IterableContainer<Data> extends TraversableContainer<Data>{ // Must extend TraversableContainer

  ForwardIterator<Data> FIterator();
  BackwardIterator<Data> BIterator();
    //*******************************VERIFICA SE DUE STRUTTURE DATI SONO UGUALI TRA LORO********************************//
    default boolean IsEqual(IterableContainer<Data> other) {
        if (other == null) return false;
        if( this.Size().ToLong() != other.Size().ToLong()) return false;
        // Otteniamo i due iteratori
        ForwardIterator<Data> i1 = this.FIterator();
        ForwardIterator<Data> i2 = other.FIterator();

        // Finchè ENTRAMBI sono validi (non siamo arrivati alla fine)
        while (i1.IsValid() && i2.IsValid()) {
            
            // Confrontiamo elemento per elemento avanzando entrambi
            Data d1 = i1.DataNNext();
            Data d2 = i2.DataNNext();
            
            if (!d1.equals(d2)) {
                return false; // Diversi!
            }
        }
        
        return true;
    }

  /* ************************************************************************ */
  /* Override specific member functions from TraversableContainer             */
  /* ************************************************************************ */
  //*******************************ATTRAVERSA IN AVANTI LA STRUTTURA DATI E APPLICA IL PREDICATO SU OGNI ELEMENTO********************************//
  @Override
  default boolean TraverseForward(Predicate<Data> pred) {
    if (pred == null) return false;

    ForwardIterator<Data> it = FIterator();

    while (it.IsValid()) {
      if (pred.Apply(it.DataNNext())) {
        return true;
      }
    }

    return false;
  }
  //*******************************ATTRAVERSA IN INDIETRO LA STRUTTURA DATI E APPLICA IL PREDICATO SU OGNI ELEMENTO********************************//
  @Override
  default boolean TraverseBackward(Predicate<Data> pred) {
      if (pred == null) return false;

      BackwardIterator<Data> it = BIterator();

      while (it.IsValid()) {
          if (pred.Apply(it.DataNPrev())) {
              return true;
          }
      }

      return false;
  }

}
