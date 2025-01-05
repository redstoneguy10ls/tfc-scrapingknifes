
from enum import Enum, auto

from mcresources import ResourceManager, utils, loot_tables
from mcresources.type_definitions import ResourceIdentifier

from constants import *
from recipes import fluid_ingredient, heat_recipe

def generate(rm: ResourceManager):
    wrought_iron = METALS['wrought_iron']

    for metal, metal_data in METALS.items():
        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            item_name = 'tfcscraping:metal/%s/%s' % (item, metal)
            if item_data.type in metal_data.types:
                item_heat(rm, 'metal/%s_%s' % (metal, item), '#%s/%s' % (item_data.tag, metal) if item_data.tag else item_name, metal_data.ingot_heat_capacity(), metal_data.melt_temperature, mb=item_data.smelt_amount)


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, heat_capacity: float, melt_temperature: Optional[float] = None, mb: Optional[int] = None, destroy_at: Optional[int] = None):
    if melt_temperature is not None:
        forging_temperature = round(melt_temperature * 0.6)
        welding_temperature = round(melt_temperature * 0.8)
    else:
        forging_temperature = welding_temperature = None
    if mb is not None:
        # Interpret heat capacity as a specific heat capacity - so we need to scale by the mB present. Baseline is 100 mB (an ingot)
        # Higher mB = higher heat capacity = heats and cools slower = consumes proportionally more fuel
        heat_capacity = round(10 * heat_capacity * mb) / 1000
    rm.data(('tfc', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })
    if destroy_at is not None:
        heat_recipe(rm, 'destroy_' + name_parts, ingredient, destroy_at)
