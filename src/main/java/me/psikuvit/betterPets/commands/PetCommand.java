package me.psikuvit.betterPets.commands;

import me.psikuvit.betterPets.Main;
import me.psikuvit.betterPets.items.PetItem;
import me.psikuvit.betterPets.menu.UpgradeMenu;
import me.psikuvit.betterPets.pet.Pet;
import me.psikuvit.betterPets.pet.PetSkin;
import me.psikuvit.betterPets.utils.Messages;
import me.psikuvit.betterPets.utils.enums.Rarity;
import me.psikuvit.betterPets.utils.enums.XPSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PetCommand extends Command {
    private final Main plugin;

    public PetCommand(Main plugin) {
        super("pet");
        this.plugin = plugin;
        setDescription("Main command for managing pets");
        setUsage("/<command> [give/menu/skin]");
        setAliases(List.of("pets"));
        setPermission("betterpets.use");
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String @NotNull [] args) {
        Player player, target;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage(Component.text("Only players can use this command!").color(NamedTextColor.RED));
            return true;
        }
        if (args.length == 0) {
            showPetHelp(player);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "give":
                if (!player.hasPermission("betterpets.admin")) {
                    player.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage(Component.text("Usage: /pet give <player> <petId> [rarity]").color(NamedTextColor.RED));
                    return true;
                }
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
                    return true;
                }
                String id = args[2];
                String rarity = args.length >= 4 ? args[3] : null;

                givePet(player, target, id, rarity);
                return true;
            case "menu":
                this.plugin.getPetManager().getPlayerData(player).getPetsInventory().openPetsMenu(player, 0);
                return true;
            case "upgrade":
                new UpgradeMenu().openUpgradeMenu(player);
                return true;
            case "skin":
                if (args.length < 3) {
                    player.sendMessage(Component.text("Usage: /pet skin <player> <skinId>").color(NamedTextColor.RED));
                    return true;
                }
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
                    return true;
                }
                giveSkin(player, target, args[2]);
                return true;
            case "list":
                if (!player.hasPermission("betterpets.admin")) {
                    player.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
                    return true;
                }
                showAllAvailablePets(player);
                return true;
            case "giveitem": {
                if (!sender.hasPermission("betterpets.giveitem")) {
                    sender.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(Component.text("Usage: /pet giveitem <player> <itemId>").color(NamedTextColor.RED));
                    return true;
                }

                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
                    return true;
                }

                String itemId = args[2];
                PetItem item = plugin.getPetItemLoader().getItem(itemId);
                if (item == null) {
                    sender.sendMessage(Component.text("Item not found!").color(NamedTextColor.RED));
                    return true;
                }

                target.getInventory().addItem(item.createItemStack());
                sender.sendMessage(Component.text("Given pet item to " + target.getName()).color(NamedTextColor.GREEN));
                return true;
            }
            case "addxp": {
                if (!sender.hasPermission("betterpets.admin")) {
                    sender.sendMessage(Component.text("You don't have permission to use this command!").color(NamedTextColor.RED));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(Component.text("Usage: /pet addxp <player> <amount>").color(NamedTextColor.RED));
                    return true;
                }

                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found!").color(NamedTextColor.RED));
                    return true;
                }

                int xpAmount;
                try {
                    xpAmount = Integer.parseInt(args[2]);
                    if (xpAmount <= 0) {
                        sender.sendMessage(Component.text("XP amount must be positive!").color(NamedTextColor.RED));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Component.text("Invalid XP amount: " + args[2]).color(NamedTextColor.RED));
                    return true;
                }

                addXpToSelectedPet(sender, target, xpAmount);
                return true;
            }
        }
        showPetHelp(player);
        return true;
    }

    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("menu");
            completions.add("upgrade");
            if (sender.hasPermission("betterpets.admin")) {
                completions.add("give");
                completions.add("skin");
                completions.add("list");
                completions.add("addxp");
            }
            if (sender.hasPermission("betterpets.giveitem")) {
                completions.add("giveitem");
            }
            return filterCompletions(completions, args[0]);
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("betterpets.admin")) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            } else if (args[0].equalsIgnoreCase("skin") && sender.hasPermission("betterpets.admin")) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            } else if (args[0].equalsIgnoreCase("giveitem") && sender.hasPermission("betterpets.giveitem")) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            } else if (args[0].equalsIgnoreCase("addxp") && sender.hasPermission("betterpets.admin")) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            }
            return filterCompletions(completions, args[1]);
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("give") && sender.hasPermission("betterpets.admin")) {
            completions.addAll(this.plugin.getRegistryPetManager().getRegisteredPetIds());
            return filterCompletions(completions, args[2]);
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("give") && sender.hasPermission("betterpets.admin")) {
            String petId = args[2];
            Rarity[] rarities =
                    this.plugin.getRegistryPetManager().getAvailableRarities(petId);
            if (rarities != null) {
                for (Rarity rarity : rarities) {
                    completions.add(rarity.name().toLowerCase());
                }
            }
            return filterCompletions(completions, args[3]);
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("skin") && sender.hasPermission("betterpets.admin")) {
            this.plugin.getPetSkinLoader().getAllSkins().forEach(skin -> completions.add(skin.id()));
            return filterCompletions(completions, args[2]);
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("giveitem") && sender.hasPermission("betterpets.giveitem")) {
            completions.addAll(this.plugin.getPetItemLoader().getItems().keySet());
            return filterCompletions(completions, args[2]);
        }
        return completions;
    }

    private void showPetHelp(Player player) {
        player.sendMessage(Component.text("=== Pet Commands ===").color(NamedTextColor.GOLD));
        player.sendMessage(Component.text("/pet menu").color(NamedTextColor.GREEN).append(Component.text(" - Open pet menu").color(NamedTextColor.GRAY)));
        player.sendMessage(Component.text("/pet upgrade").color(NamedTextColor.GREEN).append(Component.text(" - Open upgrade menu").color(NamedTextColor.GRAY)));

        if (player.hasPermission("betterpets.admin")) {
            player.sendMessage(Component.text("/pet give <player> <petId> [rarity]").color(NamedTextColor.YELLOW).append(Component.text(" - Give pet to player").color(NamedTextColor.GRAY)));
            player.sendMessage(Component.text("/pet list").color(NamedTextColor.YELLOW).append(Component.text(" - List all available pets").color(NamedTextColor.GRAY)));
            player.sendMessage(Component.text("/pet skin <player> <skinId>").color(NamedTextColor.YELLOW).append(Component.text(" - Give pet skin").color(NamedTextColor.GRAY)));
            player.sendMessage(Component.text("/pet addxp <player> <amount>").color(NamedTextColor.YELLOW).append(Component.text(" - Add XP to selected pet").color(NamedTextColor.GRAY)));
        }

        if (player.hasPermission("betterpets.giveitem")) {
            player.sendMessage(Component.text("/pet giveitem <player> <itemId>").color(NamedTextColor.AQUA).append(Component.text(" - Give pet item").color(NamedTextColor.GRAY)));
        }
    }

    private void givePet(Player sender, Player target, String petId, String rarityString) {
        Pet template;
        try {
            Rarity rarity = Rarity.valueOf(rarityString.toUpperCase());
            template = plugin.getPetManager().givePet(sender, petId, rarity);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(Component.text("Invalid rarity: " + rarityString).color(NamedTextColor.RED));
            sender.sendMessage(Component.text("Valid rarities: COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC").color(NamedTextColor.GRAY));
            return;
        }


        if (template == null) {
            sender.sendMessage(Component.text("Pet not found or rarity not available for this pet!").color(NamedTextColor.RED));
            showAvailablePets(sender);
            return;
        }

        String rarityText = " (" + rarityString.toUpperCase() + ")";
        sender.sendMessage(Component.text("Successfully gave ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(template.getName() + rarityText))
                .append(Component.text(" to "))
                .append(Component.text(target.getName())));
        target.sendMessage(Component.text("You received a new pet: ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(template.getName() + rarityText)));
    }

    private void showAvailablePets(Player sender) {
        sender.sendMessage(Component.text("Available pet types:").color(NamedTextColor.YELLOW));
        for (String availablePetId : this.plugin.getRegistryPetManager().getRegisteredPetIds()) {
            Rarity[] rarities =
                    this.plugin.getRegistryPetManager().getAvailableRarities(availablePetId);
            if (rarities != null) {
                StringBuilder rarityList = new StringBuilder();
                for (int i = 0; i < rarities.length; i++) {
                    if (i > 0) rarityList.append(", ");
                    rarityList.append(rarities[i].name());
                }
                sender.sendMessage(Component.text("- " + availablePetId + " (" + rarityList + ")").color(NamedTextColor.GRAY));
            }
        }
    }

    private void showAllAvailablePets(Player sender) {
        sender.sendMessage(Component.text("=== Available Pets ===").color(NamedTextColor.GOLD));
        sender.sendMessage(Component.text(this.plugin.getRegistryPetManager().getRegistryInfo()).color(NamedTextColor.GREEN));
        sender.sendMessage(Component.text("").color(NamedTextColor.WHITE));

        for (String availablePetId : this.plugin.getRegistryPetManager().getRegisteredPetIds()) {
            me.psikuvit.betterPets.utils.enums.Rarity[] rarities =
                    this.plugin.getRegistryPetManager().getAvailableRarities(availablePetId);
            if (rarities != null) {
                StringBuilder rarityList = new StringBuilder();
                for (int i = 0; i < rarities.length; i++) {
                    if (i > 0) rarityList.append(", ");
                    rarityList.append(rarities[i].name());
                }
                sender.sendMessage(Component.text("◆ " + availablePetId.toUpperCase()).color(NamedTextColor.YELLOW)
                        .append(Component.text(" - Rarities: " + rarityList).color(NamedTextColor.GRAY)));
            }
        }

        sender.sendMessage(Component.text("").color(NamedTextColor.WHITE));
        sender.sendMessage(Component.text("Usage: /pet give <player> <petId> [rarity]").color(NamedTextColor.AQUA));
    }

    private void giveSkin(Player sender, Player target, String skinId) {
        PetSkin petSkin = this.plugin.getPetSkinLoader().getSkinById(skinId);
        if (petSkin == null) {
            sender.sendMessage(Component.text("Pet not found!").color(NamedTextColor.RED));
            return;
        }
        target.getInventory().addItem(petSkin.createSkinItem());
        sender.sendMessage(Component.text("Successfully gave ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(petSkin.name()))
                .append(Component.text(" to "))
                .append(Component.text(target.getName())));
        target.sendMessage(Component.text("You received a new pet: ")
                .color(NamedTextColor.GREEN)
                .append(Component.text(petSkin.name())));
    }

    private void addXpToSelectedPet(CommandSender sender, Player target, int xpAmount) {
        Pet activePet = plugin.getPetManager().getActivePet(target);

        if (activePet == null) {
            sender.sendMessage(Component.text(target.getName() + " doesn't have an active pet!").color(NamedTextColor.RED));
            return;
        }

        int oldLevel = activePet.getLevel();
        int oldExp = activePet.getCurrentExp();

        activePet.gainExp(xpAmount, XPSource.COMMAND);

        int newLevel = activePet.getLevel();
        int newExp = activePet.getCurrentExp();

        sender.sendMessage(Messages.deserialize("<green>Added <aqua>" + xpAmount
                + " <green>to " + target.getName() + "'s</green>"
                + activePet.getColoredName() + "<green>!"));

        target.sendMessage(Component.text("Your " + activePet.getColoredName() + " gained " + xpAmount + " XP!")
                .color(NamedTextColor.GREEN));

        if (newLevel > oldLevel) {
            sender.sendMessage(Component.text("Pet leveled up from " + oldLevel + " to " + newLevel + "!")
                    .color(NamedTextColor.GOLD));
        }

        int requiredExp = activePet.getRequiredExp();
        if (requiredExp > 0) {
            double progress = (double) newExp / requiredExp * 100;
            sender.sendMessage(Component.text(String.format("Pet progress: Level %d (%d/%d XP - %.1f%%)",
                            newLevel, newExp, requiredExp, progress))
                    .color(NamedTextColor.GRAY));
        } else {
            sender.sendMessage(Component.text("Pet is at maximum level!")
                    .color(NamedTextColor.GOLD));
        }
    }

    private List<String> filterCompletions(List<String> completions, String partial) {
        String lower = partial.toLowerCase();
        completions.removeIf(str -> !str.toLowerCase().startsWith(lower));
        return completions;
    }
}
