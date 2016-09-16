package rmcpe.Kits;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.util.*;

/**
 * Created by user11 on 16.09.2016.
 */
public class Kits extends PluginBase implements Listener {

    ArrayList itemsList = new ArrayList();

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(this, this);
        this.initConfig();
        this.loadCfg();
        this.getItemsList();


    }

    public void initConfig(){
        this.getDataFolder().mkdirs();
        this.saveResource("config.yml");
    }

    public void loadCfg() {
        this.reloadConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean lastPlayed = player.hasPlayedBefore();

        if (!lastPlayed) {

            Iterator var4 = this.itemsList.iterator();

            while (var4.hasNext()) {
                Item item = (Item) var4.next();
                if(player.getInventory().canAddItem(item)) {
                    player.getInventory().addItem(new Item[]{item});
                }
            }
        }

    }


    public  List<Item> getItemsList(){
        Config section = new Config(this.getDataFolder() + File.separator + "config.yml", 2);

        Set keys = section.getKeys();
        Iterator var1 = keys.iterator();

        while(var1.hasNext()) {
            String key = (String)var1.next();
            List firstlvlList = section.getStringList(key);
            Iterator var2 = firstlvlList.iterator();

            while(var2.hasNext()) {
                String coords = (String)var2.next();
                String[] str = coords.split(",");
                Item item = new Item(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
                this.itemsList.add(item);
                this.getLogger().info("§a var2: " +var2 );
                this.getLogger().info("§d item: " +item );
            }
        }
        return itemsList;
    }
}
