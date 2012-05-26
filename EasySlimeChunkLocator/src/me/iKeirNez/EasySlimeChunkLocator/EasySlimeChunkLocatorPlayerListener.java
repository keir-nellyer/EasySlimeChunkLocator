package me.iKeirNez.EasySlimeChunkLocator;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (player.getItemInHand().getTypeId() == config.getInt("item")){
				if ((config.getBoolean("permissions") && player.hasPermission("easyslimechunklocator.use") || !config.getBoolean("permissions"))){
					if (config.getBoolean("debug-mode")){
						System.out.println("[EasySlimeChunkLocator] The Player " + player.getName() + " has permission to use this plugin, executing code.");
					}
					// Thanks to this for the formula
					// http://www.minecraftforum.net/topic/397835-find-slime-spawning-chunks-125/
					long worldSeed = player.getWorld().getSeed();
				    Chunk playerChunk = player.getWorld().getChunkAt(e.getClickedBlock());
				    int x = playerChunk.getX();
				    int z = playerChunk.getZ();

				    Random random = new Random(worldSeed + 
				    x * x * 4987142 + 
				    x * 5947611 + 
				    z * z * 4392871L + 
				    z * 389711 ^ 0x3AD8025F);
				      
				    if (random.nextInt(10) == 0){
				    	player.sendMessage(ChatColor.GREEN + config.getString("Messages.PartOfSlimeChunk"));
				    	if (config.getBoolean("debug-mode")){
				    		System.out.println("The player " + player.getName() + " has found a slime chunk.");
				    	}
				    } else {
				    	player.sendMessage(ChatColor.RED + config.getString("Messages.NotPartOfSlimeChunk"));
				    	if (config.getBoolean("debug-mode")){
				    		System.out.println("The player " + player.getName() + " has not found a slime chunk.");
				    	}
				    }
				} else {
					if (config.getBoolean("debug-mode")){
						System.out.println("[EasySlimeChunkLocator] The Player " + player.getName() + " does not have permission to use this plugin, not executing code.");
					}
				}
			}
		}
	}
}
