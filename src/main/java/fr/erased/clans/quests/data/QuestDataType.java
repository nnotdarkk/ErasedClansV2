package fr.erased.clans.quests.data;

import fr.erased.clans.quests.Numerable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class QuestDataType<V extends Numerable> {

    protected V enumeration;
    protected boolean finished;
    protected int progression;

    public QuestDataType(V numerableEnum, boolean finished, int progression) {
        this.enumeration = numerableEnum;
        this.finished = finished;
        this.progression = progression;
    }
}
