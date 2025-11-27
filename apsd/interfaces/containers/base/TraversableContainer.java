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
                              /*restituisce la dimensione della struttura dati*/
  @Override
  default Natural Size() {
    final MutableNatural size = new MutableNatural(0);
    TraverseForward(dat -> { size.Increment(); return false; });
    return Natural.Of(size);
  }

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */
  
                              /*verifica che un elemento sia presente o meno nella struttura dati*/
  @Override
  default boolean Exists(Data dat) {
    return TraverseForward(val -> val == null && dat == null || val != null && dat != null && val.equals(dat));
  }
}
