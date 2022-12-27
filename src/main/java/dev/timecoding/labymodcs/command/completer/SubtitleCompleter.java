package dev.timecoding.labymodcs.command.completer;

import dev.timecoding.labymodcs.api.CustomSubtitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SubtitleCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        if(command.getName().equalsIgnoreCase("lst") || command.getName().equalsIgnoreCase("labymodsubtitle")){
            List<String> list = new ArrayList<>();
            if(strings.length == 1){
                list.add("list");
                list.add("create");
                list.add("delete");
                list.add("set");
                list.add("remove");
                list.add("setperm");
                list.add("remperm");
            }else if(strings.length == 2){
                String first = strings[0];
                if(first.equalsIgnoreCase("delete") || first.equalsIgnoreCase("remperm") || first.equalsIgnoreCase("setperm")){
                    for(int i = 1; i <= CustomSubtitleAPI.getSubtitleSize(); i++){
                        list.add(String.valueOf(i));
                    }
                }
                if(first.equalsIgnoreCase("set") || first.equalsIgnoreCase("remove")){
                    for(Player all : Bukkit.getOnlinePlayers()){
                        list.add(all.getName());
                    }
                }
            }else if(strings.length == 3){
                String first = strings[0];
                if(first.equalsIgnoreCase("set")){
                    for(int i = 1; i <= CustomSubtitleAPI.getSubtitleSize(); i++){
                        list.add(String.valueOf(i));
                    }
                }
            }

            return list;
        }
        return new ArrayList<>();
    }
}
