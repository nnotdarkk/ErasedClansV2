package fr.erased.clans.quests.data.types;

import fr.erased.clans.quests.types.block.BreakingEnum;
import fr.erased.clans.quests.data.QuestDataType;

public class BlockQuestData extends QuestDataType<BreakingEnum> {

    public BlockQuestData(BreakingEnum breakingEnum, boolean finished, int progression) {
        super(breakingEnum, finished, progression);
    }
}
