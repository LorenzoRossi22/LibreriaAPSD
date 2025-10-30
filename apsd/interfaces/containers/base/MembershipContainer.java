package apsd.interfaces.containers.base;

/** Interface: Container con supporto alla verifica di appartenenza. */
public interface MembershipContainer<Data> extends Container{ // Must extend Container

  boolean Exists(Data data); // Verifica se un dato è presente nel contenitore.
}
