package apsd.interfaces.traits;

/** Trait: E' una funzione accumulatrice. */
@FunctionalInterface
public interface Accumulator<Data, Acc> {

  Acc Apply(Data dat, Acc acc);

}
