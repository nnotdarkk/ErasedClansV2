package fr.erased.clans.quests.data;

import fr.erased.clans.quests.data.types.BlockQuestData;
import fr.erased.clans.quests.data.streak.DailyStreakData;
import fr.erased.clans.quests.data.types.EntityQuestData;
import fr.erased.clans.quests.data.types.FarmQuestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class QuestData {

    private EntityQuestData entityQuestData;
    private BlockQuestData blockQuestData;
    private FarmQuestData farmQuestData;

    private DailyStreakData dailyStreakData;

    private boolean completedQuestsToday;

    public void update(QuestDataType<?> questDataSubType){
        if(questDataSubType instanceof EntityQuestData a){
            entityQuestData = a;
        } else if(questDataSubType instanceof BlockQuestData b){
            blockQuestData = b;
        } else if(questDataSubType instanceof FarmQuestData c){
            farmQuestData = c;
        }
    }

}
