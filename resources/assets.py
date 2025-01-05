
import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, atlases, BlockContext
from mcresources.type_definitions import ResourceIdentifier, JsonObject

from constants import *

def generate(rm: ResourceManager):

    for metal, metal_data in METALS.items():
        for metal_item, metal_item_data in METAL_ITEMS.items():
            if metal_item_data.type in metal_data.types or metal_item_data.type == 'all':
                texture = 'tfcscraping:item/metal/%s/%s' % (metal_item, metal) if metal_item != 'shield' or metal in ('red_steel', 'blue_steel', 'wrought_iron') else 'tfc:item/metal/shield/%s_front' % metal
                item = rm.item_model(('metal', metal_item, metal), texture, parent=metal_item_data.parent_model)
                item.with_lang(lang('%s %s', metal, metal_item))

    rm.item_model(('ceramic', 'unfired_scraping_knife_blade_mold'), 'tfcscraping:item/ceramic/unfired_scraping_knife_blade_mold').with_lang(lang('unfired scraping knife blade mold'))
    contained_fluid(rm, ('ceramic', 'scraping_knife_blade_mold'), 'tfcscraping:item/ceramic/fired_mold/scraping_knife_blade_mold_empty', 'tfcscraping:item/ceramic/fired_mold/scraping_knife_blade_mold_overlay').with_lang(lang('scraping knife blade mold'))



def contained_fluid(rm: ResourceManager, name_parts: utils.ResourceIdentifier, base: str, overlay: str) -> 'ItemContext':
    return rm.custom_item_model(name_parts, 'tfc:contained_fluid', {
        'parent': 'forge:item/default',
        'textures': {
            'base': base,
            'fluid': overlay
        }
    })