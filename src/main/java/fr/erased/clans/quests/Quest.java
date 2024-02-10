package fr.erased.clans.quests;

import org.bukkit.plugin.PluginManager;

public abstract class Quest {

    protected final QuestsManager questsManager;

    public Quest(QuestsManager questsManager) {
        this.questsManager = questsManager;
    }

    public abstract void registerListeners(PluginManager pm);

    public QuestsManager getQuestsManager() {
        return questsManager;
    }

}
