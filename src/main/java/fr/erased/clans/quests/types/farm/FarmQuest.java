package fr.erased.clans.quests.types.farm;

import fr.erased.clans.quests.Quest;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.types.farm.listeners.FarmBreakListener;
import org.bukkit.plugin.PluginManager;

public class FarmQuest extends Quest {

    public FarmQuest(QuestsManager questsManager) {
        super(questsManager);
    }

    @Override
    public void registerListeners(PluginManager pm) {
        pm.registerEvents(new FarmBreakListener(questsManager), questsManager.getErasedClans());
    }
}
