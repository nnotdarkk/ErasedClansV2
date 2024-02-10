package fr.erased.clans.quests.data.types;

import fr.erased.clans.quests.data.QuestDataType;
import fr.erased.clans.quests.types.entity.KillEntityEnum;

public class EntityQuestData extends QuestDataType<KillEntityEnum> {

    public EntityQuestData(KillEntityEnum killEntityEnum, boolean finished, int progression) {
        super(killEntityEnum, finished, progression);
    }

}
