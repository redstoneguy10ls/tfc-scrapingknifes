from mcresources import ResourceManager

from constants import *

def generate(rm: ResourceManager):

    rm.item_tag('tfc:sharp_tools', '#tfcscraping:scraping_knives')

    rm.item_tag('tfcscraping:scraping_knives', '#tfcscraping:line_scraping')
    rm.item_tag('tfcscraping:scraping_knives', '#tfcscraping:quarter_scraping')
    rm.item_tag('tfcscraping:scraping_knives', '#tfcscraping:half_scraping')
    rm.item_tag('tfcscraping:scraping_knives', '#tfcscraping:full_scraping')
    rm.item_tag('tfcchannelcasting:accepted_in_mold_table','tfcscraping:ceramic/scraping_knife_blade_mold')

    for metal, metal_data in METALS.items():
        if 'tool' in metal_data.types:
            match metal_data.tier:
                case 1 | 2:
                    rm.item_tag('tfcscraping:line_scraping', 'tfcscraping:metal/scraping_knife/%s' % metal)
                case 3:
                    rm.item_tag('tfcscraping:quarter_scraping', 'tfcscraping:metal/scraping_knife/%s' % metal)
                case 4 | 5:
                    rm.item_tag('tfcscraping:half_scraping', 'tfcscraping:metal/scraping_knife/%s' % metal)
                case 6:
                    rm.item_tag('tfcscraping:full_scraping', 'tfcscraping:metal/scraping_knife/%s' % metal)

            # rm.item_tag('tfcscraping:scraping_knives','tfcscraping:metal/scraping_knife/%s' %metal)
            rm.item_tag('tfc:metal_item/%s_tools' % metal, 'tfcscraping:metal/scraping_knife/%s' % metal)
            rm.item_tag('minecraft:tools','tfcscraping:metal/scraping_knife/%s' % metal)

        if 'tool' in metal_data.types:
            for tool_type, tool_tag in TOOL_TAGS.items():
                rm.item_tag('tfc:usable_on_tool_rack', 'tfcscraping:metal/%s/%s' % (tool_type, metal))

        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            if item_data.type in metal_data.types:
                item_name = 'tfcscraping:metal/%s/%s' % (item, metal)
                rm.item_tag('tfc:metal_item/%s' % metal, item_name)

    rm.item_tag('tfc:unfired_molds', 'tfcscraping:ceramic/unfired_scraping_knife_blade_mold')
    rm.item_tag('tfc:fired_molds', 'tfcscraping:ceramic/scraping_knife_blade_mold')