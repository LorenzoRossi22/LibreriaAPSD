package apsd.interfaces.containers.base;

import apsd.classes.utilities.Box;

/** Interface: Container con supporto alla rimozione di un dato. */
public interface RemovableContainer<Data> extends Container{ // Must extend Container

  boolean Remove(Data dat);

  default boolean RemoveAll(TraversableContainer<Data> TravC){
    final Box<Boolean> changed = new Box<>(true);
    if(TravC != null){ TravC.TraverseForward(dat -> {changed.Set(changed.Get() && Remove(dat)); return false;}); }
    return changed.Get();
  }

  default boolean RemoveSome(TraversableContainer<Data> TravC) {
    final Box<Boolean> changed = new Box<>(false);
    if(TravC != null){ TravC.TraverseForward(dat -> {changed.Set(changed.Get() || Remove(dat)); return false;}); }
    return changed.Get();
  }
}
