package me.iKeirNez.EasySlimeChunkLocator;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EasySlimeChunkLocatorPlayerListener implements Listener {
	
	public EasySlimeChunkLocator plugin;
	public EasySlimeChunkLocatorPlayerListener(EasySlimeChunkLocator instance){
		this.plugin = instance;
	}
	
	@EventHandler (ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent e){
		FileConfiguration config = plugin.getConfig();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (e.getItem().getTypeId() == config.getInt("item")){
				// Thanks to this for the formula
				// http://www.minecraftforum.net/topic/397835-find-slime-spawning-chunks-125/
				long worldSeed = e.getPlayer().getWorld().getSeed();
			    Chunk playerChunk = e.getPlayer().getWorld().getChunkAt(e.getClickedBlock());
			    int x = playerChunk.getX();
			    int z = playerChunk.getZ();

			    Random random = new Random(worldSeed + 
			    x * x * 4987142 + 
			    x * 5947611 + 
			    z * z * 4392871L + 
			    z * 389711 ^ 0x3AD8025F);
			      
			    if (random.nextInt(10) == 0){
			    	e.getPlayer().sendMessage(ChatColor.GREEN + config.getString("Messages.PartOfSlimeChunk"));
			    } else {
			    	e.getPlayer().sendMessage(ChatColor.RED + config.getString("Messages.NotPartOfSlimeChunk"));
			    }
			}
		}
	}
}
