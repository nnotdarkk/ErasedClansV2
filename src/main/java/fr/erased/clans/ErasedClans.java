package fr.erased.clans;

import fr.erased.clans.players.cache.CacheManager;
import fr.erased.clans.clans.chest.ChestManager;
import fr.erased.clans.chunks.ChunkManager;
import fr.erased.clans.clans.ClanManager;
import fr.erased.clans.commands.ClanCommand;
import fr.erased.clans.commands.ClanCompleter;
import fr.erased.clans.commands.subcommands.*;
import fr.erased.clans.commands.subcommands.admin.*;
import fr.erased.clans.commands.subcommands.console.ResetQuestsCommand;
import fr.erased.clans.leaderboard.LeaderboardManager;
import fr.erased.clans.listeners.*;
import fr.erased.clans.players.PlayerManager;
import fr.erased.clans.players.resolver.PlayerUUIDResolver;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.utils.EFileUtils;
import fr.erased.clans.utils.commands.CommandFramework;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ErasedClans extends JavaPlugin {

    private static ErasedClans instance;

    private ClanManager clanManager;
    private PlayerManager playerManager;
    private PlayerUUIDResolver playerUUIDResolver;
    private ChunkManager chunkManager;
    private ChestManager chestManager;
    private CacheManager cacheManager;
    private QuestsManager questsManager;
    private LeaderboardManager leaderboardManager;

    private EFileUtils fileUtils;

    public static ErasedClans get(){
        return instance;
    }

    public void onEnable() {
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        this.clanManager = new ClanManager(this);
        this.playerManager = new PlayerManager(this);
        this.playerUUIDResolver = new PlayerUUIDResolver(this);
        this.chunkManager = new ChunkManager(this);
        this.chestManager = new ChestManager(this);
        this.cacheManager = new CacheManager();
        this.questsManager = new QuestsManager(this);
        this.leaderboardManager = new LeaderboardManager(this);

        this.fileUtils = new EFileUtils(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ClanPlaceholders(this).register();
        }

        this.registerListeners();
        this.registerCommands();
    }

    public void registerListeners(){
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new AsyncPlayerChat(this), this);
        pm.registerEvents(new ClaimsCancels(this), this);
        pm.registerEvents(new ClanChestInteract(this), this);
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new MoveListener(this), this);
        pm.registerEvents(new PlayerConnection(this), this);
    }

    public void registerCommands(){
        CommandFramework framework = new CommandFramework(this);

        framework.registerCommands(new ClanCommand(this));
        framework.registerCommands(new ClanCompleter());

        framework.registerCommands(new BaseCommand(this));
        framework.registerCommands(new ChestCommand(this));
        framework.registerCommands(new ClaimCommand(this));
        framework.registerCommands(new CreateCommand(this));
        framework.registerCommands(new DemoteCommand(this));
        framework.registerCommands(new FlyCommand(this));
        framework.registerCommands(new HelpCommand(this));
        framework.registerCommands(new InviteCommand(this));
        framework.registerCommands(new JoinCommand(this));
        //framework.registerCommands(new KickCommand(this));
        framework.registerCommands(new PromoteCommand(this));
        framework.registerCommands(new QuitCommand(this));
        framework.registerCommands(new RefuseCommand(this));
        framework.registerCommands(new SetBaseCommand(this));
        framework.registerCommands(new UnclaimAllCommand(this));
        framework.registerCommands(new UnclaimCommand(this));

        framework.registerCommands(new AddClanXpCommand(this));
        framework.registerCommands(new BypassClaimsCommand(this));
        framework.registerCommands(new ClanInfoCommand(this));
        framework.registerCommands(new ForceClaimCommand(this));
        framework.registerCommands(new ForceDemoteCommand(this));
        framework.registerCommands(new ForceJoinCommand(this));
        //framework.registerCommands(new ForceKickCommand(this));
        framework.registerCommands(new ForceOpenChestCommand(this));
        framework.registerCommands(new ForcePromoteCommand(this));
        framework.registerCommands(new ForceSetBaseCommand(this));
        framework.registerCommands(new ForceSetLeadCommand(this));
        framework.registerCommands(new ForceUnclaimAllCommand(this));
        framework.registerCommands(new ForceUnclaimCommand(this));
        framework.registerCommands(new PlayerInfoCommand(this));
        framework.registerCommands(new SetClanXpCommand(this));
        framework.registerCommands(new TeleportBaseCommand(this));

        framework.registerCommands(new ResetQuestsCommand(this));
    }
}
