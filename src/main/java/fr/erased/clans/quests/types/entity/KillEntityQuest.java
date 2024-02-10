package fr.erased.clans.quests.types.entity;

import fr.erased.clans.quests.Quest;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.types.entity.listeners.PlayerKillEntity;
import org.bukkit.plugin.PluginManager;

public class KillEntityQuest extends Quest {

    public KillEntityQuest(QuestsManager questsManager) {
        super(questsManager);
    }

    public void registerListeners(PluginManager pm) {
        pm.registerEvents(new PlayerKillEntity(questsManager), questsManager.getErasedClans());
    }
}
