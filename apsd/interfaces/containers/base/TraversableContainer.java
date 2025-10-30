package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data>{ // Must extend MembershipContainer

  boolean TraverseForward(Predicate<Data> pred);
  boolean TraverseBackward(Predicate<Data> pred);

  default <Acc> Acc FoldForward(Accumulator<Data, Acc> fun, Acc ini) {
    final Box<Acc> acc = new Box<>(ini);
    if (fun != null) TraverseForward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
    return acc.Get();
  }

  default <Acc> Acc FoldBackward(Accumulator<Data, Acc> fun, Acc ini) {
    final Box<Acc> acc = new Box<>(ini);
    if (fun != null) TraverseBackward(dat -> { acc.Set(fun.Apply(dat, acc.Get())); return false; });
    return acc.Get();
  }

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
  default Natural Size() {
    final MutableNatural count = new MutableNatural(0L); // Usa un contatore mutabile
    
    // Attraversa tutti gli elementi
    TraverseForward(dat -> {
        count.Increment(); // Incrementa il contatore
        return false;      // Ritorna 'false' per continuare l'attraversamento
    });
    
    return count.ToNatural(); // Ritorna il risultato (convertito in immutabile)
  }
  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data dat){}
}