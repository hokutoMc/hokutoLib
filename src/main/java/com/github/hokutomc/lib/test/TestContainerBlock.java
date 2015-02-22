package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.Mod_HTLib;
import com.github.hokutomc.lib.block.HT_ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by user on 2014/12/08.
 */
public class TestContainerBlock extends HT_ContainerBlock<TestTE>{
    public TestContainerBlock () {
        super("hokutoLib", Material.iron, "te");
    }

    @Override
    public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
        return new TestTE();
    }

    @Override
    public boolean HT_onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float playerX, float playerY, float playerZ) {
        if (!world.isRemote) {
            TestTE te = this.getTileEntityAt(world, x, y, z);
            if (!player.isSneaking()) { player.openGui(Mod_HTLib.INSTANCE, 0, world, x, y, z); return true; }
            else if (te != null){
                te.save_flags.get(null).add(TestTE.Flags.B);
                te.strSaved = TestTE.StrSaved.Y;
                return true;
            }
        }
        return false;
    }
}
