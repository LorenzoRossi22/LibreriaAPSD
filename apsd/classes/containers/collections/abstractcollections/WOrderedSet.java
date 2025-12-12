package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.collections.SortedChain;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<Data>> extends WOrderedSetBase<Data, SortedChain<Data>> { 

  public WOrderedSet(){
    super();
    // ChainAlloc is called by super()
  }

  public WOrderedSet(SortedChain<Data> chn){
    super();
    // ChainAlloc has run, but we override the chain with the user-provided one
    if (chn != null) {
        this.chn = chn;
    }
  }

  // Costruttore di supporto per accettare Chain generiche (se compatibili)
  public WOrderedSet(Chain<Data> chn){
      this();
      if (chn instanceof SortedChain) {
          this.chn = (SortedChain<Data>) chn;
      } else if (chn != null) {
          // Se non è una SortedChain, copiamo i dati
          chn.TraverseForward(x -> {
              this.Insert(x);
              return true;
          });
      }
  }

  public WOrderedSet(TraversableContainer<Data> con){
    this();
    if (con != null) {
      con.TraverseForward(x -> {
        this.Insert(x);
        return true;
      });
    }
  }

  public WOrderedSet(SortedChain<Data> chn, TraversableContainer<Data> con){
    this(chn);
    if (con != null) {
      con.TraverseForward(x -> {
        this.Insert(x);
        return true;
      });
    }
  }

  // Override obbligatorio
  @Override
  public void ChainAlloc() {
    chn = new VSortedChain<>();
  }

  public boolean TraverseForward(Predicate<Data> pred) {
    return chn.TraverseForward(pred);
  }

  public boolean TraverseBackward(Predicate<Data> pred) {
    return chn.TraverseBackward(pred);
  }

  public Data GetAt(Natural of) {
    return chn.GetAt(of);
  }

  public boolean Apply(Data dat) {
    return chn.Apply(dat);
  }

  public Object Apply(Object dat, Object acc) {
    return chn.Apply(dat, acc);
  }

  protected WSetBase<Data, SortedChain<Data>> New() {
    return new WOrderedSet<>();
  }

  @Override
  public void Clear() {
    if (chn != null) {
      chn.Clear();
    }
  }
}