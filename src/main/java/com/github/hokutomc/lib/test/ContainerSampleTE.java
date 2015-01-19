package com.github.hokutomc.lib.test;

import com.github.hokutomc.lib.client.gui.HT_GuiContainer;
import com.github.hokutomc.lib.inventory.HT_ContainerProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by user on 2014/11/28.
 */
public class ContainerSampleTE extends HT_ContainerProcessor<TestTE> {
    public ContainerSampleTE (EntityPlayer player, TestTE tileEntity) {
        super(player, tileEntity);
        this.setPlayerSlotPosition(151);
        this.addSlotToContainer(new Slot(tileEntity, 0, 56, 17));
        this.addSlotToContainer(new SlotFurnace(player, tileEntity, 1, 116, 35));
    }

    @Override
    protected int getInventorySize () {
        return 2;
    }

    @Override
    public boolean canInteractWith (EntityPlayer p_75145_1_) {
        return true;
    }

    public static class GuiSampleTE extends HT_GuiContainer<TestTE>{

        private static ResourceLocation texture = new ResourceLocation("textures/gui/container/furnace.png");

        public GuiSampleTE (EntityPlayer player, TestTE tileEntity) {
            super(new ContainerSampleTE(player, tileEntity));
        }

        @Override
        protected void drawGuiContainerBackgroundLayer (float p_146976_1_, int p_146976_2_, int p_146976_3_) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(texture);
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        }

        @Override
        protected void drawGuiContainerForegroundLayer (int p_146979_1_, int p_146979_2_) {
            super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
            this.drawString(this.fontRendererObj, this.getTileEntity().save_flags.get(null).toString(), 10, 64, 16);
        }
    }
}
