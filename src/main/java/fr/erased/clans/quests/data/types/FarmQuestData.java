package fr.erased.clans.quests.data.types;

import fr.erased.clans.quests.data.QuestDataType;
import fr.erased.clans.quests.types.farm.FarmEnum;

public class FarmQuestData extends QuestDataType<FarmEnum> {

    public FarmQuestData(FarmEnum farmEnum, boolean finished, int progression) {
        super(farmEnum, finished, progression);
    }
}
