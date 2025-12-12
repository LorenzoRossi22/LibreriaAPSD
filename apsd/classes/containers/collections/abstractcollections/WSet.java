package apsd.classes.containers.collections.abstractcollections;

import apsd.classes.containers.collections.abstractcollections.bases.WSetBase;
import apsd.classes.containers.collections.concretecollections.VList;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.traits.Predicate;

/** Object: Wrapper set implementation via chain. */
public class WSet<Data> extends WSetBase<Data, VList<Data>>{ // Must extend WSetBase

  protected Chain<Data> chain;

  public WSet(){
    super();
  }

  public WSet(Chain<Data> chn){
    super();
    this.chn = new VList<>();
    if (chn != null) {
      chn.TraverseForward(x -> {
          this.chn.InsertAt(Natural.Of(this.chn.Size().ToLong()), x);
          return true;
      });
    }
  }

  public WSet(TraversableContainer<Data> con){
    this();
    if (con != null) {
        con.TraverseForward(x -> { 
            this.Insert(x);
            return true;
        });
    }
  }

  public WSet(Chain<Data> chn, TraversableContainer<Data> con){
    super();
    this.chn = new VList<>();
    if (chn != null) {
      chn.TraverseForward(x -> {
          this.chn.InsertAt(Natural.Of(this.chn.Size().ToLong()), x);
          return true;
      });
    }

    if (con != null) {
      con.TraverseForward(x -> {
          this.Insert(x);
          return true;
      });
    }
  }

  public void ChainAlloc() { 
    chn = new VList<>();
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

  protected WSetBase<Data, VList<Data>> New() {
    return new WSet<>();
  }

}