package fr.erased.clans.quests.types.block;

import fr.erased.clans.quests.Quest;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.types.block.listener.BlockBreakListener;
import fr.erased.clans.quests.types.block.logic.BlockListener;
import fr.erased.clans.quests.types.farm.FarmEnum;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class BreakingQuest extends Quest {

    private final List<Material> materialList;

    public BreakingQuest(QuestsManager questsManager) {
        super(questsManager);
        this.materialList = BreakingEnum.getMaterials();
        this.materialList.addAll(FarmEnum.getMaterials());
    }

    public void registerListeners(PluginManager pm) {
        pm.registerEvents(new BlockBreakListener(questsManager), questsManager.getErasedClans());
        pm.registerEvents(new BlockListener(this), questsManager.getErasedClans());
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

}
