package fr.erased.clans.utils.json;

public interface Defaulteable<V extends JsonSeriazible> {

    V getDefault();
}
