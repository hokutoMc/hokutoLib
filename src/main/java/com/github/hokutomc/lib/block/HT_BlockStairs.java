package com.github.hokutomc.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.BlockStairs.*;

/**
 * Created by user on 2015/03/18.
 */
public class HT_BlockStairs extends HT_Block<HT_BlockStairs> {
    private final IBlockState modelState;
    private final Block modelBlock;
    private static final int[][] matrix = new int[][]{{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
    private boolean field_150152_N;
    private int field_150153_O;


    private static BlockStairs DUMMY () {
        return (BlockStairs) Blocks.stone_stairs;
    }

    public HT_BlockStairs (String modid, IBlockState baseState, String innerName) {
        super(modid, baseState.getBlock().getMaterial(), innerName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, EnumHalf.BOTTOM).withProperty(SHAPE, EnumShape.STRAIGHT));
        this.modelState = baseState;
        this.modelBlock = baseState.getBlock();
        this.setStepSound(this.modelBlock.stepSound);
        this.setLightOpacity(255);
    }

    @Override
    public float getBlockHardness (World worldIn, BlockPos pos) {
        return modelBlock.getBlockHardness(worldIn, pos);
    }

    @Override
    public float getExplosionResistance (World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return modelBlock.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public void setBlockBoundsBasedOnState (IBlockAccess access, BlockPos pos) {
        DUMMY().setBlockBoundsBasedOnState(access, pos);
    }

    @Override
    public boolean isOpaqueCube () {
        return false;
    }

    @Override
    public boolean isFullCube () {
        return false;
    }

    public static boolean isBlockStairs (Block block) {
        return block instanceof BlockStairs || block instanceof HT_BlockStairs;
    }

    public static boolean isSameStair (IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        IBlockState iblockstate1 = worldIn.getBlockState(pos);
        Block block = iblockstate1.getBlock();
        /**
         * Checks if a block is stairs
         */
        return isBlockStairs(block) && iblockstate1.getValue(HALF) == state.getValue(HALF) && iblockstate1.getValue(FACING) == state.getValue(FACING);
    }

    public int func_176307_f (IBlockAccess world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);
        EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
        BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(HALF);
        boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
        IBlockState iblockstate1;
        Block block;
        EnumFacing enumfacing1;

        if (enumfacing == EnumFacing.EAST) {
            iblockstate1 = world.getBlockState(pos.offsetEast());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    return flag ? 1 : 2;
                }

                if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    return flag ? 2 : 1;
                }
            }
        } else if (enumfacing == EnumFacing.WEST) {
            iblockstate1 = world.getBlockState(pos.offsetWest());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    return flag ? 2 : 1;
                }

                if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumfacing == EnumFacing.SOUTH) {
            iblockstate1 = world.getBlockState(pos.offsetSouth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    return flag ? 2 : 1;
                }

                if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumfacing == EnumFacing.NORTH) {
            iblockstate1 = world.getBlockState(pos.offsetNorth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    return flag ? 1 : 2;
                }

                if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    return flag ? 2 : 1;
                }
            }
        }
        return 0;
    }

    public int func_176305_g (IBlockAccess world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);
        EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
        BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(HALF);
        boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
        IBlockState iblockstate1;
        Block block;
        EnumFacing enumfacing1;

        if (enumfacing == EnumFacing.EAST) {
            iblockstate1 = world.getBlockState(pos.offsetWest());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    return flag ? 1 : 2;
                }

                if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    return flag ? 2 : 1;
                }
            }
        } else if (enumfacing == EnumFacing.WEST) {
            iblockstate1 = world.getBlockState(pos.offsetEast());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    return flag ? 2 : 1;
                }

                if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumfacing == EnumFacing.SOUTH) {
            iblockstate1 = world.getBlockState(pos.offsetNorth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    return flag ? 2 : 1;
                }

                if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    return flag ? 1 : 2;
                }
            }
        } else if (enumfacing == EnumFacing.NORTH) {
            iblockstate1 = world.getBlockState(pos.offsetSouth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    return flag ? 1 : 2;
                }

                if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    return flag ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public boolean func_176306_h (IBlockAccess world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);
        EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
        BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(HALF);
        boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.5F;
        boolean flag1 = true;
        IBlockState iblockstate1;
        Block block;
        EnumFacing enumfacing1;

        if (enumfacing == EnumFacing.EAST) {
            f2 = 0.5F;
            f5 = 1.0F;
            iblockstate1 = world.getBlockState(pos.offsetEast());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    f5 = 0.5F;
                    flag1 = false;
                } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumfacing == EnumFacing.WEST) {
            f3 = 0.5F;
            f5 = 1.0F;
            iblockstate1 = world.getBlockState(pos.offsetWest());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    f5 = 0.5F;
                    flag1 = false;
                } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumfacing == EnumFacing.SOUTH) {
            f4 = 0.5F;
            f5 = 1.0F;
            iblockstate1 = world.getBlockState(pos.offsetSouth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    f3 = 0.5F;
                    flag1 = false;
                } else if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        } else if (enumfacing == EnumFacing.NORTH) {
            iblockstate1 = world.getBlockState(pos.offsetNorth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    f3 = 0.5F;
                    flag1 = false;
                } else if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        }

        this.setBlockBounds(f2, f, f4, f3, f1, f5);
        return flag1;
    }

    public boolean func_176304_i (IBlockAccess world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);
        EnumFacing enumfacing = (EnumFacing) iblockstate.getValue(FACING);
        BlockStairs.EnumHalf enumhalf = (BlockStairs.EnumHalf) iblockstate.getValue(HALF);
        boolean flag = enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = 0.5F;
        float f5 = 1.0F;
        boolean flag1 = false;
        IBlockState iblockstate1;
        Block block;
        EnumFacing enumfacing1;

        if (enumfacing == EnumFacing.EAST) {
            iblockstate1 = world.getBlockState(pos.offsetWest());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumfacing == EnumFacing.WEST) {
            iblockstate1 = world.getBlockState(pos.offsetEast());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                f2 = 0.5F;
                f3 = 1.0F;
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.NORTH && !isSameStair(world, pos.offsetNorth(), iblockstate)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(world, pos.offsetSouth(), iblockstate)) {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumfacing == EnumFacing.SOUTH) {
            iblockstate1 = world.getBlockState(pos.offsetNorth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                f4 = 0.0F;
                f5 = 0.5F;
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    flag1 = true;
                } else if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        } else if (enumfacing == EnumFacing.NORTH) {
            iblockstate1 = world.getBlockState(pos.offsetSouth());
            block = iblockstate1.getBlock();

            if (isBlockStairs(block) && enumhalf == iblockstate1.getValue(HALF)) {
                enumfacing1 = (EnumFacing) iblockstate1.getValue(FACING);

                if (enumfacing1 == EnumFacing.WEST && !isSameStair(world, pos.offsetWest(), iblockstate)) {
                    flag1 = true;
                } else if (enumfacing1 == EnumFacing.EAST && !isSameStair(world, pos.offsetEast(), iblockstate)) {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        }

        if (flag1) {
            this.setBlockBounds(f2, f, f4, f3, f1, f5);
        }

        return flag1;
    }

    public void addCollisionBoxesToList (World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        DUMMY().setBaseCollisionBounds(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        boolean flag = this.func_176306_h(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

        if (flag && this.func_176304_i(worldIn, pos)) {
            super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void onBlockClicked (World worldIn, BlockPos pos, EntityPlayer playerIn) {
        modelBlock.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public void randomDisplayTick (World worldIn, BlockPos pos, IBlockState state, Random rand) {
        modelBlock.randomDisplayTick(worldIn, pos, state, rand);
    }

    @Override
    public void onBlockDestroyedByPlayer (World worldIn, BlockPos pos, IBlockState state) {
        modelBlock.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getMixedBrightnessForBlock (IBlockAccess worldIn, BlockPos pos) {
        return modelBlock.getMixedBrightnessForBlock(worldIn, pos);
    }

    @Override
    public int tickRate (World worldIn) {
        return modelBlock.tickRate(worldIn);
    }

    @Override
    public Vec3 modifyAcceleration (World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
        return modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer () {
        return modelBlock.getBlockLayer();
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox (World worldIn, BlockPos pos) {
        return modelBlock.getSelectedBoundingBox(worldIn, pos);
    }

    @Override
    public boolean isCollidable () {
        return this.modelBlock.isCollidable();
    }

    @Override
    public boolean canCollideCheck (IBlockState state, boolean p_176209_2_) {
        return this.modelBlock.canCollideCheck(state, p_176209_2_);
    }

    @Override
    public boolean canPlaceBlockAt (World worldIn, BlockPos pos) {
        return this.modelBlock.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public void onBlockAdded (World worldIn, BlockPos pos, IBlockState state) {
        this.onNeighborBlockChange(worldIn, pos, this.modelState, Blocks.air);
        this.modelBlock.onBlockAdded(worldIn, pos, this.modelState);
    }

    @Override
    public void breakBlock (World worldIn, BlockPos pos, IBlockState state) {
        this.modelBlock.breakBlock(worldIn, pos, this.modelState);
    }

    @Override
    public void onEntityCollidedWithBlock (World worldIn, BlockPos pos, Entity entityIn) {
        this.modelBlock.onEntityCollidedWithBlock(worldIn, pos, entityIn);
    }

    @Override
    public void updateTick (World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.modelBlock.updateTick(worldIn, pos, state, rand);
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        return this.modelBlock.onBlockActivated(worldIn, pos, this.modelState, playerIn, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void onBlockDestroyedByExplosion (World worldIn, BlockPos pos, Explosion explosionIn) {
        this.modelBlock.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }

    @Override
    public MapColor getMapColor (IBlockState state) {
        return this.modelBlock.getMapColor(this.modelState);
    }

    public IBlockState onBlockPlaced (World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        iblockstate = iblockstate.withProperty(FACING, placer.func_174811_aO()).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
        return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate.withProperty(HALF, BlockStairs.EnumHalf.BOTTOM) : iblockstate.withProperty(HALF, BlockStairs.EnumHalf.TOP);
    }

    public MovingObjectPosition collisionRayTrace (World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
        MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
        IBlockState iblockstate = worldIn.getBlockState(pos);
        int i = ((EnumFacing) iblockstate.getValue(FACING)).getHorizontalIndex();
        boolean flag = iblockstate.getValue(HALF) == BlockStairs.EnumHalf.TOP;
        int[] aint = matrix[i + (flag ? 4 : 0)];
        this.field_150152_N = true;

        for (int j = 0; j < 8; ++j) {
            this.field_150153_O = j;

            if (Arrays.binarySearch(aint, j) < 0) {
                amovingobjectposition[j] = super.collisionRayTrace(worldIn, pos, start, end);
            }
        }

        int[] aint1 = aint;
        int k = aint.length;

        for (int l = 0; l < k; ++l) {
            int i1 = aint1[l];
            amovingobjectposition[i1] = null;
        }

        MovingObjectPosition movingobjectposition1 = null;
        double d1 = 0.0D;
        MovingObjectPosition[] amovingobjectposition1 = amovingobjectposition;
        int j1 = amovingobjectposition.length;

        for (int k1 = 0; k1 < j1; ++k1) {
            MovingObjectPosition movingobjectposition = amovingobjectposition1[k1];

            if (movingobjectposition != null) {
                double d0 = movingobjectposition.hitVec.squareDistanceTo(end);

                if (d0 > d1) {
                    movingobjectposition1 = movingobjectposition;
                    d1 = d0;
                }
            }
        }

        return movingobjectposition1;
    }

    @Override
    public int getMetaFromState (IBlockState state) {
        return DUMMY().getMetaFromState(state);
    }

    @Override
    public IBlockState getActualState (IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (this.func_176306_h(worldIn, pos)) {
            switch (this.func_176305_g(worldIn, pos)) {
                case 0:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
                    break;
                case 1:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.INNER_RIGHT);
                    break;
                case 2:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.INNER_LEFT);
            }
        } else {
            switch (this.func_176307_f(worldIn, pos)) {
                case 0:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
                    break;
                case 1:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_RIGHT);
                    break;
                case 2:
                    state = state.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_LEFT);
            }
        }

        return state;
    }

    @Override
    protected BlockState createBlockState () {
        return new BlockState(this, FACING, HALF, SHAPE);
    }
}
