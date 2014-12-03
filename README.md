hokutoLib
=========

This mod make modding much easier.

#Build script

to make your mod dependent on this mod, fix your mod's build.gradle.

1. add `maven { url 'http://hokutomc.github.io/maven-repo/' }` to your `repositories{}` block
2. add `compile 'com.github.hokutoMc:hokutoLib:(alpha)0.0.0-0:dev'` to your `dependencies{}` block

That's all.

#How to mod

##Add new block

1. add `(variable name) = new HT_Block("modid", Material.wood, "blockid").HT_register();` and it will register a block with the property below.  
texture : "src/main/resources/assets/modid/textures/blocks/blockid.png"  
block id : "modid : blockid"  
unlocalized name : "tile.modid.blockid"  

###Add meta block

1. add `(variable name) = new HT_MultiBlock("modid", Material.wood, "blockid", "black", "white", "red").HT_register();` and it will register a block with the property below.  
texture : "src/main/resources/assets/modid/textures/blocks/blockid_{black, white, red}.png"  
block id : "modid ; blockid"  
unlocalized name : "tile.modid.blockid.{black.white.red}"  

### Add block that contains a TileEntity

1. create a class that extends TileEntity. See *Add TileEntity*.
2. register a block (instance of HT_ContainerBlock).

##Add new item

1. add `(variable name) = new HT_Item("modid", "itemid").HT_register();` and it will register a item with the property below.
texture : "src/main/resources/assets/modid/textures/items/itemid.png"
block id : "modid : itemid"
unlocalized name : "item.modid.itemid"

###Add meta item

1. add `(variable name) = new HT_MultiItem("modid", "itemid", "black", "white", "red").HT_register();` and it will register a item with the property below.  
texture : "src/main/resources/assets/modid/textures/items/itemid_{black, white, red}.png"  
block id : "modid : itemid"  
unlocalized name : "item.modid.itemid.{black.white.red}"  

###Add tool

1. create a class that extends HT_ItemTool.
2. register a block from that class.

###Add armor

1. create a class that extends HT_ItemArmor.
2. register a block from that class.

##Add creativeTab

`HT_CreativeTabsUtil.create("modid", "name", block or item);`

###Gui registration

1. `new HT_GuiHandler(mod : Object);` creates a Gui handler for your mod.
2. `HT_GuiHandler.addGui(id : int, serverAction : GuiAction, clientAction : GuiAction)`