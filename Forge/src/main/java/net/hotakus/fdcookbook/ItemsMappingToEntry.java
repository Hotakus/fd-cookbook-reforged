package net.hotakus.fdcookbook;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Map;

import static net.hotakus.fdcookbook.items.ItemsRegister.make;

public class ItemsMappingToEntry {

    static List<String> mappingRegisterList = List.of(
            "farmersdelight"
    );

    static Map<String, Integer> strawEntryItemMapping = Map.of(
            "straw_bale", 1,
            "canvas_rug", 4,
            "canvas_sign", 5
    );

    static Map<Map<String, Integer>, ResourceLocation> allMapping = Map.of(
            strawEntryItemMapping, make("basics/straw")
    );

    /**
     * Determines if the given resource location is a cookbook item.
     *
     * @param resourceLocation the resource location to check
     * @return true if the resource location is a cookbook item, false otherwise
     */
    public static boolean isCookBookItem(ResourceLocation resourceLocation) {
        boolean res = false;

        // Check Farmer's Delight
        if (mappingRegisterList.contains(resourceLocation.getNamespace())) {
            res = true;
        }

        return res;
    }

    /**
     * Retrieves the location of a given resource entry within the allMapping data structure.
     *
     * @param resourceLocation the resource location to search for
     * @return a Map.Entry object containing the resource location and its associated integer value,
     * or null if the resource location is not found
     */
    public static Map.Entry<ResourceLocation, Integer> getEntryLocation(ResourceLocation resourceLocation) {

        Map.Entry<ResourceLocation, Integer> entry = null;

        for (Map.Entry<Map<String, Integer>, ResourceLocation> mapping : allMapping.entrySet()) {
            for (Map.Entry<String, Integer> entryMap : mapping.getKey().entrySet()) {
                if (entryMap.getKey().equals(resourceLocation.getPath())) {
                    entry = Map.entry(
                            mapping.getValue(),
                            entryMap.getValue()
                    );
                    break;
                }
            }
        }

        return entry;
    }

    /**
     * Opens a specific book entry for a given server player at a specific page.
     *
     * @param sPlayer the server player for whom the book entry is being opened
     * @param entry   the resource location of the book entry to be opened
     * @param page    the page number at which the book entry should be opened
     */
    public static void openEntry(ServerPlayer sPlayer, ResourceLocation entry, int page) {
        PatchouliAPI.get().openBookEntry(sPlayer, make("fd_cookbook"), entry, page);
    }

}
