package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WOrderedSetBase;
import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VSortedChain;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper ordered set implementation via ordered chain. */
public class WOrderedSet<Data extends Comparable<Data>> extends WOrderedSetBase<Data, VSortedChain<Data>>{ // Must extend WOrderedSetBase

  public WOrderedSet(){
    super();
    ChainAlloc();
  }

  public WOrderedSet(Chain<Data> chn){
    super();
    ChainAlloc();
    if (chn != null) {
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

  public WOrderedSet(Chain<Data> chn, TraversableContainer<Data> con){
    this(chn);
    if (con != null) {
      con.TraverseForward(x -> {
        this.Insert(x);
        return true;
      });
    }
  }

  public void ChainAlloc(){
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

  protected WSetBase<Data, VSortedChain<Data>> New() {
    return new WOrderedSet<>();
  }

  @Override
  public void Clear() {
    if (chn != null) {
      chn.Clear();
    }
  }
}
