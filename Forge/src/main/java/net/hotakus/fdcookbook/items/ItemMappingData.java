package net.hotakus.fdcookbook.items;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.hotakus.fdcookbook.items.ItemsRegister.make;

public class ItemMappingData {
    static List<String> mappingRegisterList = List.of(
            "farmersdelight"
    );

    static Map<String, Integer> strawEntryItemMapping = Map.of(
            "straw_bale", 1,
            "canvas_rug", 4
    );

    static Map<String, Integer> signsEntryItemMapping = new HashMap<>() {{
        put("canvas_sign", 6);
        put("white_canvas_sign", 6);
        put("red_canvas_sign", 6);
        put("blue_canvas_sign", 6);
        put("yellow_canvas_sign", 6);
        put("orange_canvas_sign", 6);
        put("brown_canvas_sign", 6);
        put("magenta_canvas_sign", 6);
        put("pink_canvas_sign", 6);
        put("purple_canvas_sign", 6);
        put("black_canvas_sign", 6);
        put("cyan_canvas_sign", 6);
        put("gray_canvas_sign", 6);
        put("light_gray_canvas_sign", 6);
        put("lime_canvas_sign", 6);
        put("light_blue_canvas_sign", 6);
        put("green_canvas_sign", 6);
    }};

    static Map<String, Integer> cuttingBoardEntryItemMapping = Map.of(
            "cutting_board", 0
    );

    static Map<String, Integer> cookingPotEntryItemMapping = Map.of(
            "cooking_pot", 0,
            "skillet", 2,
            "stove", 4
    );

    static Map<String, Integer> ropeEntryItemMapping = Map.of(
            "rope", 0,
            "safety_net", 1
    );

    static Map<String, Integer> storageEntryItemMapping = new HashMap<>() {
        {
            put("basket", 1);
            put("acacia_cabinet", 0);
            put("birch_cabinet", 0);
            put("crimson_cabinet", 0);
            put("oak_cabinet", 0);
            put("dark_oak_cabinet", 0);
            put("jungle_cabinet", 0);
            put("spruce_cabinet", 0);
            put("warped_cabinet", 0);

            put("beetroot_crate", 2);
            put("carrot_crate", 2);
            put("cabbage_crate", 2);
            put("onion_crate", 2);
            put("potato_crate", 2);
            put("tomato_crate", 2);
            put("rice_bag", 2);
            put("rice_bale", 2);
        }
    };

    static Map<String, Integer> newPlantsEntryItemMapping = Map.of(
            "wild_beetroots", 3,
            "wild_carrots", 1,
            "wild_potatoes", 2,
            "wild_onions", 5,
            "wild_cabbages", 4,
            "wild_tomatoes", 6,
            "wild_rices", 7
            // TODO: add sandy shrubs
    );

    static Map<String, Integer> tatamiEntryItemMapping = Map.of(
            "tatami", 0,
            "full_tatami_mat", 2,
            "half_tatami_mat", 2
    );

    static Map<String, Integer> mushroomEntryItemMapping = Map.of(
            "red_mushroom_colony", 0,
            "brown_mushroom_colony", 0
    );

    static Map<String, Integer> pieEntryItemMapping = Map.of(
            "apple_pie", 4,
            "sweet_berry_cheesecake", 3,
            "chocolate_pie", 2
    );

    static Map<String, Integer> feastsEntryItemMapping = Map.of(
            "roast_chicken_block", 1,
            "stuffed_pumpkin_block", 2,
            "honey_glazed_ham_block", 3,
            "shepherds_pie_block", 4
    );

    static Map<String, Integer> sushisEntryItemMapping = Map.of(
            "rice_roll_medley_block", 5
    );
    static Map<String, Integer> richSoilEntryItemMapping = Map.of(
            "rich_soil", 0,
            "organic_compost", 1,
            "rich_soil_farmland", 0
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
