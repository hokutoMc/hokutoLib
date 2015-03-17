package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.Mod_HTLib;
import com.github.hokutomc.lib.block.HT_ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by user on 2014/12/08.
 */
public class TestContainerBlock extends HT_ContainerBlock<TestContainerBlock, TestTE> {
    public TestContainerBlock () {
        super("hokutoLib", Material.iron, "te");
    }

    @Override
    public TileEntity createNewTileEntity (World p_149915_1_, int p_149915_2_) {
        return new TestTE();
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TestTE te = this.getTileEntityAt(worldIn, pos);
            if (!playerIn.isSneaking()) {
                playerIn.openGui(Mod_HTLib.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
            else if (te != null){
                te.save_flags.get(null).add(TestTE.Flags.B);
                te.strSaved = TestTE.StrSaved.Y;
                return true;
            }
        }
        return false;
    }
}
