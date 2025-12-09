package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;
import apsd.classes.utilities.MutableNatural;
import apsd.classes.utilities.Natural;
import apsd.interfaces.traits.Accumulator;
import apsd.interfaces.traits.Predicate;

/** Interface: MembershipContainer con supporto all'attraversamento. */
public interface TraversableContainer<Data> extends MembershipContainer<Data>, Predicate<Data>, Accumulator{ // Must extend MembershipContainer

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
    final MutableNatural i = new MutableNatural(0L);

    TraverseForward(dat -> {
        i.Increment();
        return false;
    });

    return i.ToNatural();
  }

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

  @Override
  default boolean Exists(Data dat) {
    if (dat == null) return false;

    return TraverseForward(elem -> {
        if (dat.equals(elem)) return true;         
        return false;      
    });
  }
  Data GetAt(Natural of);
}
