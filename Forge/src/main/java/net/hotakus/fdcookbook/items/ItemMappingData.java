package net.hotakus.fdcookbook.items;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.hotakus.fdcookbook.utils.utils.make;

public class ItemMappingData {
    static List<String> mappingRegisterList = List.of(
            "farmersdelight"
    );

    static Map<String, Integer> strawEntryItemMapping = Map.of(
            "blocks/straw_bale", 1,
            "blocks/canvas_rug", 4
    );

    static Map<String, Integer> signsEntryItemMapping = new HashMap<>() {{
        put("blocks/canvas_sign", 6);
        put("blocks/white_canvas_sign", 6);
        put("blocks/red_canvas_sign", 6);
        put("blocks/blue_canvas_sign", 6);
        put("blocks/yellow_canvas_sign", 6);
        put("blocks/orange_canvas_sign", 6);
        put("blocks/brown_canvas_sign", 6);
        put("blocks/magenta_canvas_sign", 6);
        put("blocks/pink_canvas_sign", 6);
        put("blocks/purple_canvas_sign", 6);
        put("blocks/black_canvas_sign", 6);
        put("blocks/cyan_canvas_sign", 6);
        put("blocks/gray_canvas_sign", 6);
        put("blocks/light_gray_canvas_sign", 6);
        put("blocks/lime_canvas_sign", 6);
        put("blocks/light_blue_canvas_sign", 6);
        put("blocks/green_canvas_sign", 6);
    }};

    static Map<String, Integer> cuttingBoardEntryItemMapping = Map.of(
            "blocks/cutting_board", 0
    );

    static Map<String, Integer> cookingPotEntryItemMapping = Map.of(
            "blocks/cooking_pot", 0,
            "blocks/skillet", 2,
            "blocks/stove", 4
    );

    static Map<String, Integer> ropeEntryItemMapping = Map.of(
            "blocks/rope", 0,
            "blocks/safety_net", 1
    );

    static Map<String, Integer> storageEntryItemMapping = new HashMap<>() {
        {
            put("blocks/basket", 1);
            put("blocks/acacia_cabinet", 0);
            put("blocks/birch_cabinet", 0);
            put("blocks/crimson_cabinet", 0);
            put("blocks/oak_cabinet", 0);
            put("blocks/dark_oak_cabinet", 0);
            put("blocks/jungle_cabinet", 0);
            put("blocks/spruce_cabinet", 0);
            put("blocks/warped_cabinet", 0);

            put("blocks/beetroot_crate", 2);
            put("blocks/carrot_crate", 2);
            put("blocks/cabbage_crate", 2);
            put("blocks/onion_crate", 2);
            put("blocks/potato_crate", 2);
            put("blocks/tomato_crate", 2);
            put("blocks/rice_bag", 2);
            put("blocks/rice_bale", 2);
        }
    };

    static Map<String, Integer> newPlantsEntryItemMapping = Map.of(
            "blocks/wild_beetroots", 3,
            "blocks/wild_carrots", 1,
            "blocks/wild_potatoes", 2,
            "blocks/wild_onions", 5,
            "blocks/wild_cabbages", 4,
            "blocks/wild_tomatoes", 6,
            "blocks/wild_rice", 7
            // TODO: add sandy shrubs
    );

    static Map<String, Integer> tatamiEntryItemMapping = Map.of(
            "blocks/tatami", 0,
            "blocks/full_tatami_mat", 2,
            "blocks/half_tatami_mat", 2
    );

    static Map<String, Integer> mushroomEntryItemMapping = Map.of(
            "blocks/red_mushroom_colony", 0,
            "blocks/brown_mushroom_colony", 0
    );

    static Map<String, Integer> pieEntryItemMapping = Map.of(
            "blocks/apple_pie", 4,
            "blocks/sweet_berry_cheesecake", 3,
            "blocks/chocolate_pie", 2
    );

    static Map<String, Integer> feastsEntryItemMapping = Map.of(
            "blocks/roast_chicken_block", 1,
            "blocks/stuffed_pumpkin_block", 2,
            "blocks/honey_glazed_ham_block", 3,
            "blocks/shepherds_pie_block", 4
    );

    static Map<String, Integer> sushisEntryItemMapping = Map.of(
            "blocks/rice_roll_medley_block", 5
    );
    static Map<String, Integer> richSoilEntryItemMapping = Map.of(
            "blocks/rich_soil", 0,
            "blocks/organic_compost", 1,
            "blocks/rich_soil_farmland", 0
    );

    static Map<Map<String, Integer>, ResourceLocation> allMapping = new HashMap<>() {
        {
            put(strawEntryItemMapping, make("basics/straw"));
            put(signsEntryItemMapping, make("basics/straw"));
            put(ropeEntryItemMapping, make("basics/rope"));
            put(tatamiEntryItemMapping, make("basics/tatami"));
            put(newPlantsEntryItemMapping, make("basics/wild_crops"));
            put(mushroomEntryItemMapping, make("basics/mushroom_colonies"));
            put(richSoilEntryItemMapping, make("basics/rich_soil"));
            put(cuttingBoardEntryItemMapping, make("tools/cutting_board"));
            put(cookingPotEntryItemMapping, make("tools/cookingpot_stove"));
            put(storageEntryItemMapping, make("tools/storage_blocks"));
            put(pieEntryItemMapping, make("recipes/pies"));
            put(feastsEntryItemMapping, make("recipes/feasts"));
            put(sushisEntryItemMapping, make("recipes/sushis"));
        }
    };

    /**
     * Registers an entry mapping with the given mapping and resource location.
     *
     * @param mapping          The mapping to be registered.
     * @param resourceLocation The resource location to be associated with the mapping.
     */
    public static void registerEntryMapping(Map<String, Integer> mapping, ResourceLocation resourceLocation) {
        allMapping.put(mapping, resourceLocation);
    }

    public static void extraMapping() {

    }
}
