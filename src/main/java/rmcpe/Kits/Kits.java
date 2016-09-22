package rmcpe.Kits;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemColorArmor;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by j0sky on 16.09.2016.
 */
public class Kits extends PluginBase implements Listener {

    private ArrayList itemsList = new ArrayList();

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(this, this);
        this.initConfig();
        this.loadCfg();
        this.getItemsList();


    }

    private void initConfig() {
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
                PlayerInventory inv = player.getInventory();
                if (inv.canAddItem(item)) {
                    if (item.isArmor()) {
//                        TODO: Цвет, названия - перенести в конфиг. Сделать опциональным.
                        if (item.isBoots()) {
                            ItemColorArmor colorizeditem = (ItemColorArmor) item;
                            colorizeditem.setColor(50, 50, 255);
                            colorizeditem.setCustomName("Ботинки новичка");
                            inv.setBoots(colorizeditem);
                        } else if (item.isChestplate()) {
                            ItemColorArmor colorizeditem = (ItemColorArmor) item;
                            colorizeditem.setColor(50, 50, 255);
                            item.setCustomName("Куртка новичка");
                            inv.setChestplate(colorizeditem);
                        } else if (item.isHelmet()) {
                            ItemColorArmor colorizeditem = (ItemColorArmor) item;
                            colorizeditem.setColor(50, 50, 255);
                            item.setCustomName("Шапка новичка");
                            inv.setHelmet(colorizeditem);
                        } else if (item.isLeggings()) {
                            ItemColorArmor colorizeditem = (ItemColorArmor) item;
                            colorizeditem.setColor(50, 50, 255);
                            item.setCustomName("Штаны новичка");
                            inv.setLeggings(colorizeditem);
                        }

                    }
                    //TODO: Добавить предметы в хотбар.
                    /*else if (item.isSword()) {

                    }*/
                    else {
                        inv.addItem(new Item[]{item});
                    }
                }
            }

            player.sendMessage("" + TextFormat.ITALIC + TextFormat.AQUA + "Вам был выдан кит Старт. Загляните в инвентарь ;)");

//            Map<Integer, Item> contents = player.getInventory().getContents();
//            for (Map.Entry<Integer, Item> entry : contents.entrySet())
//            {
//                System.out.println(entry.getKey() + "/" + entry.getValue());
//                this.getLogger().info("§d item: " +Item.get(entry.getValue().getId()).getName() );
//            }
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
                Item item = Item.get(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
                this.itemsList.add(item);
            }
        }
        return itemsList;
    }
}
